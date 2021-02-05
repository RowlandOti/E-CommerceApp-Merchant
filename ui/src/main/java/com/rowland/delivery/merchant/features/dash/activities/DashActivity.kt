package com.rowland.delivery.merchant.features.dash.activities

import android.app.Activity
import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.navigation.NavigationView
import com.rowland.delivery.merchant.R
import com.rowland.delivery.merchant.databinding.ActivityDashBinding
import com.rowland.delivery.merchant.features.dash.fragments.OrderItemFragment
import com.rowland.delivery.merchant.features.dash.fragments.OverviewFragment
import com.rowland.delivery.merchant.features.dash.fragments.ProductFragment
import com.rowland.delivery.merchant.features.splash.ui.SplashActivity
import com.rowland.delivery.merchant.services.session.SessionManager
import com.rowland.delivery.presentation.viewmodels.category.CategoryViewModel
import com.rowland.delivery.presentation.viewmodels.order.OrderViewModel
import com.rowland.delivery.presentation.viewmodels.product.ProductViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import androidx.activity.viewModels
import com.rowland.delivery.merchant.features.dash.fragments.CategoryFragment

@AndroidEntryPoint
class DashActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener,
    FragmentManager.OnBackStackChangedListener {

    @Inject
    lateinit var sessionManager: SessionManager

    private lateinit var _binding: ActivityDashBinding
    val binding get() = _binding

    private val orderViewModel: OrderViewModel by viewModels()
    private val categoryViewModel: CategoryViewModel by viewModels()
    private var mSelectedMenuId: Int = 0


    companion object {

        fun startActivity(context: Context) {
            val intent = Intent(context, DashActivity::class.java)
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                val bundle = ActivityOptions.makeSceneTransitionAnimation(context as Activity).toBundle()
                context.startActivity(intent, bundle)
            } else {
                context.startActivity(intent)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityDashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.dashDrawer.setNavigationItemSelectedListener(this)
        mSelectedMenuId = savedInstanceState?.getInt("SELECTED_ID") ?: R.id.action_business
        itemSelection(mSelectedMenuId)

        supportFragmentManager.addOnBackStackChangedListener(this)

        Log.d("Hash-${DashActivity::class.java.simpleName}", orderViewModel.toString())
        Log.d("Hash-${DashActivity::class.java.simpleName}", categoryViewModel.toString())
        orderViewModel.getSelectedListItem()
            .observe(this, {
                if (supportFragmentManager.findFragmentByTag(OrderItemFragment::class.java.simpleName) == null) {
                    supportFragmentManager.beginTransaction()
                        .replace(
                            R.id.dash_container_body,
                            OrderItemFragment.newInstance(null),
                            OrderItemFragment::class.java.simpleName
                        )
                        .addToBackStack(OrderItemFragment::class.java.simpleName)
                        .commit()
                }
            })

        categoryViewModel.getSelectedListItem()
            .observe(this, { category ->
                if (supportFragmentManager.findFragmentByTag(ProductFragment::class.java.simpleName) == null) {
                    val productViewModel = ViewModelProvider(this).get(ProductViewModel::class.java)
                    productViewModel.clearDataList()
                    val args = Bundle()
                    args.putString("selected_category", category!!.name)
                    supportFragmentManager.beginTransaction()
                        .replace(
                            R.id.dash_container_body,
                            ProductFragment.newInstance(args),
                            ProductFragment::class.java.simpleName
                        )
                        .addToBackStack(ProductFragment::class.java.simpleName)
                        .commit()
                }
            })
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        item.isChecked = true
        mSelectedMenuId = item.itemId
        itemSelection(mSelectedMenuId)
        return true
    }

    private fun itemSelection(mSelectedId: Int) {

        when (mSelectedId) {
            R.id.action_business -> {
                if (supportFragmentManager.findFragmentByTag(OverviewFragment::class.java.simpleName) == null) {
                    supportFragmentManager.beginTransaction().replace(
                        R.id.dash_container_body,
                        OverviewFragment.newInstance(0),
                        OverviewFragment::class.java.simpleName
                    ).commit()
                }
                binding.dashDrawerLayout.closeDrawer(GravityCompat.START)
            }
            R.id.action_ratings ->
                //
                binding.dashDrawerLayout.closeDrawer(GravityCompat.START)
            R.id.action_logout -> {
                sessionManager.logout()
                binding.dashDrawerLayout.closeDrawer(GravityCompat.START)
                SplashActivity.startActivity(this)
            }
            else -> binding.dashDrawerLayout.closeDrawer(GravityCompat.START)
        }
    }

    public override fun onResume() {
        super.onResume()
        shouldDisplayHomeUp()
    }

    override fun onBackStackChanged() {
        shouldDisplayHomeUp()
    }

    override fun onSupportNavigateUp(): Boolean {
        super.onSupportNavigateUp()
        supportFragmentManager.popBackStack()
        return true
    }

    private fun shouldDisplayHomeUp() {
        val canGoBack = supportFragmentManager.backStackEntryCount > 0
        supportActionBar!!.setDisplayHomeAsUpEnabled(canGoBack)
    }
}
