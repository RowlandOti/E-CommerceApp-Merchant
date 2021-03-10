package com.rowland.delivery.merchant.features.dash.activities

import android.app.Activity
import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import com.rowland.delivery.merchant.R
import com.rowland.delivery.merchant.databinding.ActivityDashBinding
import com.rowland.delivery.merchant.features.dash.fragments.OrderItemFragment
import com.rowland.delivery.merchant.features.dash.fragments.OverviewFragment
import com.rowland.delivery.merchant.features.dash.fragments.OverviewFragmentDirections
import com.rowland.delivery.merchant.features.dash.fragments.ProductFragment
import com.rowland.delivery.merchant.features.splash.ui.SplashActivity
import com.rowland.delivery.merchant.services.session.SessionManager
import com.rowland.delivery.presentation.viewmodels.category.CategoryViewModel
import com.rowland.delivery.presentation.viewmodels.order.OrderViewModel
import com.rowland.delivery.presentation.viewmodels.product.ProductViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DashActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener,
    NavController.OnDestinationChangedListener {

    @Inject
    lateinit var sessionManager: SessionManager

    private lateinit var _binding: ActivityDashBinding
    val binding get() = _binding

    private lateinit var navController: NavController
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
            (context as Activity).finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        navController.addOnDestinationChangedListener(this)

        setupDrawerWithToolbar()
        setupDrawerWithNavigator()
        binding.dashDrawerNavigation.setNavigationItemSelectedListener(this)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        item.isChecked = true
        mSelectedMenuId = item.itemId
        itemSelection(mSelectedMenuId)
        return true
    }

    private fun setupDrawerWithNavigator() {
        val appBarConfiguration = AppBarConfiguration(
            setOf(R.id.overviewFragment),
            binding.dashDrawerLayout
        )
        NavigationUI.setupWithNavController(binding.dashToolbar, navController, appBarConfiguration)
        binding.dashDrawerNavigation.setupWithNavController(navController)
    }

    private fun setupDrawerWithToolbar() {
        setSupportActionBar(binding.dashToolbar)
        val drawerToggle = object : ActionBarDrawerToggle(
            this,
            binding.dashDrawerLayout,
            binding.dashToolbar,
            R.string.drawer_open,
            R.string.drawer_close
        ) {
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
                super.onDrawerSlide(drawerView, slideOffset)
                this.syncState()
                try {
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                        window.statusBarColor = resources.getColor(R.color.colorPrimaryDarkTransparent)
                    }
                } catch (e: Exception) {
                    Log.e(OverviewFragment::class.java.simpleName, ":" + e.fillInStackTrace().toString())
                }
            }

            override fun onDrawerStateChanged(newState: Int) {
                super.onDrawerStateChanged(newState)
                this.syncState()
            }

            override fun onDrawerClosed(view: View) {
                super.onDrawerClosed(view)
                this.syncState()

                try {
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                        window.statusBarColor = resources.getColor(R.color.colorPrimaryDark)
                    }
                } catch (e: Exception) {
                    Log.e(DashActivity::class.java.simpleName, ":" + e.fillInStackTrace().toString())
                }
            }

            override fun onDrawerOpened(drawerView: View) {
                super.onDrawerOpened(drawerView)
                this.syncState()
            }
        }
        binding.dashDrawerLayout.addDrawerListener(drawerToggle)
        drawerToggle.syncState()
    }

    private fun itemSelection(mSelectedId: Int) {

        when (mSelectedId) {
            R.id.action_business -> {
                navController.navigate(R.id.overviewFragment)
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


    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() ||
                super.onSupportNavigateUp()
    }

    override fun onDestinationChanged(controller: NavController, destination: NavDestination, arguments: Bundle?) {
        when (destination.id) {
            R.id.overviewFragment -> {
                supportActionBar?.let { it.subtitle = "" }
                binding.dashDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
            }
            else -> {
                binding.dashDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
            }
        }
    }
}
