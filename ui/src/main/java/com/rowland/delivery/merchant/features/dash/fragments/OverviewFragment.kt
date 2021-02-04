package com.rowland.delivery.merchant.features.dash.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import com.rowland.delivery.merchant.features.dash.activities.DashActivity
import com.rowland.delivery.merchant.R
import com.rowland.delivery.merchant.databinding.FragmentOverviewBinding
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class OverviewFragment : Fragment() {
    private var movePosition: Int = 0

    private var _binding: FragmentOverviewBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            movePosition = requireArguments().getInt(TAB_POSITION, 0)
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
            val drawerToggle = object : ActionBarDrawerToggle(activity, drawerLayout, binding.overviewToolbar, R.string.drawer_open, R.string.drawer_close) {
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

        val adapter = ViewPagerAdapter(childFragmentManager)
        adapter.addFragment(CategoryFragment.Companion.newInstance(null), resources.getString(R.string.tab_categories))
        adapter.addFragment(OrderFragment.Companion.newInstance(null), resources.getString(R.string.tab_orders))
        binding.overviewViewpager.adapter = adapter

        binding.overviewViewpager.currentItem = movePosition
        binding.overviewTabs.setupWithViewPager(binding.overviewViewpager)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private inner class ViewPagerAdapter(fm: androidx.fragment.app.FragmentManager) : FragmentStatePagerAdapter(fm) {

        private val mFragmentList: MutableList<androidx.fragment.app.Fragment>
        private val mFragmentTitleList: MutableList<String>


        init {
            mFragmentList = ArrayList()
            mFragmentTitleList = ArrayList()
        }

        fun addFragment(fragment: Fragment, title: String) {
            this.mFragmentList.add(fragment)
            this.mFragmentTitleList.add(title)
        }

        override fun getItem(position: Int): Fragment {
            return mFragmentList[position]
        }

        override fun getCount(): Int {
            return mFragmentList.size
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return mFragmentTitleList[position]
        }
    }

    companion object {

        private const val TAB_POSITION = "tabposition"

        fun newInstance(tabPosition: Int): OverviewFragment {
            val frag = OverviewFragment()
            val bundle = Bundle()
            bundle.putInt(TAB_POSITION, tabPosition)

            frag.arguments = bundle
            return frag
        }
    }
}
