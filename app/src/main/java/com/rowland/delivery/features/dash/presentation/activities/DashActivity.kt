package com.rowland.delivery.features.dash.presentation.activities

import android.app.Activity
import android.app.ActivityOptions
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.google.android.material.navigation.NavigationView
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityOptionsCompat
import androidx.fragment.app.FragmentManager
import androidx.core.util.Pair
import androidx.core.view.GravityCompat
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.view.MenuItem
import android.view.View
import butterknife.ButterKnife
import com.rowland.delivery.features.dash.di.components.DaggerDashComponent
import com.rowland.delivery.features.dash.di.components.DashComponent
import com.rowland.delivery.features.dash.di.modules.DashModule
import com.rowland.delivery.features.dash.presentation.fragments.OrderItemFragment
import com.rowland.delivery.features.dash.presentation.fragments.OverviewFragment
import com.rowland.delivery.features.dash.presentation.fragments.ProductFragment
import com.rowland.delivery.features.dash.presentation.viewmodels.category.CategoryViewModel
import com.rowland.delivery.features.dash.presentation.viewmodels.order.OrderViewModel
import com.rowland.delivery.features.splash.ui.SplashActivity
import com.rowland.delivery.merchant.R
import com.rowland.delivery.merchant.application.di.modules.ContextModule
import com.rowland.delivery.services.session.SessionManager
import kotlinx.android.synthetic.main.activity_dash.*
import javax.inject.Inject

class DashActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, androidx.fragment.app.FragmentManager.OnBackStackChangedListener {

    @Inject
    lateinit var orderFactory: ViewModelProvider.Factory

    @Inject
    lateinit var categoryFactory: ViewModelProvider.Factory

    @Inject
    lateinit var sessionManager: SessionManager

    private lateinit var orderViewModel: OrderViewModel
    private lateinit var categoryViewModel: CategoryViewModel
    private var mSelectedMenuId: Int = 0

    lateinit var dashComponent: DashComponent
        private set

    companion object {
        fun startActivity(context: Context) {
            val intent = Intent(context, DashActivity::class.java)
            context.startActivity(intent)
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                val bundle = ActivityOptions.makeSceneTransitionAnimation(context as Activity).toBundle()
                context.startActivity(intent, bundle)
            } else {
                context.startActivity(intent)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        dashComponent = DaggerDashComponent.builder()
                .dashModule(DashModule())
                .contextModule(ContextModule(this))
                .build()

        dashComponent.inject(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dash)

        ButterKnife.bind(this)

        dash_drawer.setNavigationItemSelectedListener(this)
        mSelectedMenuId = savedInstanceState?.getInt("SELECTED_ID") ?: R.id.action_business
        itemSelection(mSelectedMenuId)

        supportFragmentManager.addOnBackStackChangedListener(this)

        orderViewModel = ViewModelProviders.of(this, orderFactory).get(OrderViewModel::class.java)
        orderViewModel.getSelectedListItem()
                .observe(this, Observer { orderData ->
                    supportFragmentManager.beginTransaction()
                            .replace(R.id.dash_container_body, OrderItemFragment.newInstance(null))
                            .addToBackStack(OrderItemFragment::class.java.simpleName)
                            .commit()
                })

        categoryViewModel = ViewModelProviders.of(this, categoryFactory).get(CategoryViewModel::class.java)
        categoryViewModel.getSelectedListItem()
                .observe(this, Observer { category ->
                    val args = Bundle()
                    args.putString("selected_category", category!!.name)
                    supportFragmentManager.beginTransaction()
                            .replace(R.id.dash_container_body, ProductFragment.newInstance(args))
                            .addToBackStack(ProductFragment::class.java.simpleName)
                            .commit()
                })
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        item.isChecked = true
        mSelectedMenuId = item.itemId
        itemSelection(mSelectedMenuId)
        return true
    }

    private fun itemSelection(mSelectedId: Int) {
        val manager = supportFragmentManager
        when (mSelectedId) {
            R.id.action_business -> {
                manager.beginTransaction().replace(R.id.dash_container_body, OverviewFragment.newInstance(0)).commit()
                dash_drawer_layout.closeDrawer(GravityCompat.START)
            }
            R.id.action_ratings ->
                //
                dash_drawer_layout.closeDrawer(GravityCompat.START)
            R.id.action_logout -> {
                sessionManager.logout()
                dash_drawer_layout.closeDrawer(GravityCompat.START)
                SplashActivity.startActivity(this)
            }
            else -> dash_drawer_layout.closeDrawer(GravityCompat.START)
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

    fun shouldDisplayHomeUp() {
        val canGoBack = supportFragmentManager.backStackEntryCount > 0
        supportActionBar!!.setDisplayHomeAsUpEnabled(canGoBack)
    }
}
