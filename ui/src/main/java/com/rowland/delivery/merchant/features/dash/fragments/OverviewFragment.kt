package com.rowland.delivery.merchant.features.dash.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.rowland.delivery.merchant.R
import com.rowland.delivery.merchant.databinding.FragmentOverviewBinding
import com.rowland.delivery.merchant.features.dash.activities.DashActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OverviewFragment : Fragment(), BottomNavigationView.OnNavigationItemSelectedListener {

    private var movePosition: Int = 0

    private var _binding: FragmentOverviewBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            movePosition = it.getInt(TAB_POSITION, 0)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentOverviewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activity = activity as AppCompatActivity?
        val drawerLayout = (activity as DashActivity).binding.dashDrawerLayout

        activity.setSupportActionBar(binding.overviewToolbar)
        val drawerToggle = object : ActionBarDrawerToggle(
            activity,
            drawerLayout,
            binding.overviewToolbar,
            R.string.drawer_open,
            R.string.drawer_close
        ) {
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
                super.onDrawerSlide(drawerView, slideOffset)
                this.syncState()
                try {
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                        val window = requireActivity().window
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
                        val window = requireActivity().window
                        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                        window.statusBarColor = resources.getColor(R.color.colorPrimaryDark)
                    }
                } catch (e: Exception) {
                    Log.e(OverviewFragment::class.java.simpleName, ":" + e.fillInStackTrace().toString())
                }
            }

            override fun onDrawerOpened(drawerView: View) {
                super.onDrawerOpened(drawerView)
                this.syncState()
            }
        }
        drawerLayout.addDrawerListener(drawerToggle)
        drawerToggle.syncState()

        val adapter = ViewPagerAdapter()
        binding.overviewViewpager.adapter = adapter
        binding.overviewViewpager.currentItem = movePosition
        binding.overviewViewpager.registerOnPageChangeCallback(object : OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                syncWithDependentViews(position)
            }
        })
        binding.overviewBottomNavigation.setOnNavigationItemSelectedListener(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {

        private const val TAB_POSITION = "tab_position"
        internal const val OVERVIEW_SCREENS_COUNT = 2

        internal const val CATEGORY_SCREEN_POSITION = 0
        internal const val ORDERS_SCREEN_POSITION = 1

        fun newInstance(tabPosition: Int): OverviewFragment {
            val frag = OverviewFragment()
            val bundle = Bundle()
            bundle.putInt(TAB_POSITION, tabPosition)

            frag.arguments = bundle
            return frag
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        return syncWithDependentViews(item)
    }

    private fun syncWithDependentViews(item: MenuItem): Boolean {
        binding.overviewToolbar.title = item.title
        return when (item.itemId) {
            R.id.action_category -> {
                binding.overviewViewpager.currentItem = 0
                true
            }
            R.id.action_orders -> {
                binding.overviewViewpager.currentItem = 1
                true
            }
            else -> false
        }
    }

    private fun syncWithDependentViews(position: Int) {
        if (position > OVERVIEW_SCREENS_COUNT) {
            throw IllegalStateException("Menu items count should sync with ViewPager items")
        }
        val item = binding.overviewBottomNavigation.menu.getItem(position)
        binding.overviewToolbar.title = item.title
        binding.overviewBottomNavigation.selectedItemId = item.itemId
    }

    private inner class ViewPagerAdapter : FragmentStateAdapter(this) {

/*        init {
            registerFragmentTransactionCallback(object : FragmentTransactionCallback() {
                override fun onFragmentMaxLifecyclePreUpdated(fragment: Fragment,
                    maxLifecycleState: Lifecycle.State
                ) = if (maxLifecycleState == Lifecycle.State.RESUMED) {
                    OnPostEventListener {
                        fragment.parentFragmentManager.commitNow {
                            setPrimaryNavigationFragment(fragment)
                        }
                    }
                } else {
                    super.onFragmentMaxLifecyclePreUpdated(fragment, maxLifecycleState)
                }
            })
        }*/

        override fun getItemCount(): Int {
            return OVERVIEW_SCREENS_COUNT
        }

        override fun createFragment(position: Int): Fragment = when (position) {
            CATEGORY_SCREEN_POSITION -> {
                CategoryFragment.newInstance(null)
            }
            ORDERS_SCREEN_POSITION -> OrderFragment.newInstance(null)
            else -> throw IllegalStateException("Invalid adapter position")
        }
    }
}
