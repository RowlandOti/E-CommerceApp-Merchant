package com.rowland.delivery.merchant.features.dash.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commitNow
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter.FragmentTransactionCallback.OnPostEventListener
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.rowland.delivery.merchant.R
import com.rowland.delivery.merchant.databinding.FragmentOverviewBinding
import com.rowland.delivery.presentation.viewmodels.category.CategoryViewModel
import com.rowland.delivery.presentation.viewmodels.order.OrderViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OverviewFragment : Fragment(), BottomNavigationView.OnNavigationItemSelectedListener {

    private var movePosition: Int = 0
    private var _binding: FragmentOverviewBinding? = null
    private val binding get() = _binding!!
    private val orderViewModel: OrderViewModel by viewModels()
    private val categoryViewModel: CategoryViewModel by viewModels()

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

        val adapter = ViewPagerAdapter()
        binding.overviewViewpager.adapter = adapter
        binding.overviewViewpager.currentItem = movePosition
        binding.overviewViewpager.registerOnPageChangeCallback(object : OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                syncWithDependentViews(position)
            }
        })
        binding.overviewBottomNavigation.setOnNavigationItemSelectedListener(this)

        orderViewModel.getSelectedListItem()
            .observe(viewLifecycleOwner, {
                    findNavController(this).navigate(
                        OverviewFragmentDirections.actionOverviewFragmentToOrderItemFragment(it)
                    )
            })

        categoryViewModel.getSelectedListItem()
            .observe(viewLifecycleOwner, {
                    findNavController(this).navigate(
                        OverviewFragmentDirections.actionOverviewFragmentToProductFragment(it.name)
                    )
            })
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
        (activity as AppCompatActivity?)?.supportActionBar?.let { it.title = item.title }

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
        (activity as AppCompatActivity?)?.supportActionBar?.let { it.title = item.title }
        binding.overviewBottomNavigation.selectedItemId = item.itemId
    }

    private inner class ViewPagerAdapter : FragmentStateAdapter(this) {

        /*init {
            registerFragmentTransactionCallback(object : FragmentTransactionCallback() {
                override fun onFragmentMaxLifecyclePreUpdated(
                    fragment: Fragment,
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
        }
*/
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
