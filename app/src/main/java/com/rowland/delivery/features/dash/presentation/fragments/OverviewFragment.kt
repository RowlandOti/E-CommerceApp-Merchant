package com.rowland.delivery.features.dash.presentation.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import com.rowland.delivery.features.dash.presentation.activities.DashActivity
import com.rowland.delivery.merchant.R
import kotlinx.android.synthetic.main.activity_dash.*
import kotlinx.android.synthetic.main.fragment_overview.*
import java.util.*


class OverviewFragment : Fragment() {
    private var movePosition: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            movePosition = arguments!!.getInt(TAB_POSITION, 0)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_overview, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activity = activity as AppCompatActivity?
        val drawerLayout = (activity as DashActivity).dash_drawer_layout

        if (overview_toolbar != null) {
            activity.setSupportActionBar(overview_toolbar)
            val drawerToggle = object : ActionBarDrawerToggle(activity, drawerLayout, overview_toolbar, R.string.drawer_open, R.string.drawer_close) {
                override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
                    super.onDrawerSlide(drawerView, slideOffset)
                    this.syncState()
                    try {
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                            val window = getActivity()!!.window
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
                            val window = getActivity()!!.window
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
        }

        val adapter = ViewPagerAdapter(childFragmentManager)
        adapter.addFragment(CategoryFragment.Companion.newInstance(null), resources.getString(R.string.tab_categories))
        adapter.addFragment(OrderFragment.Companion.newInstance(null), resources.getString(R.string.tab_orders))
        overview_viewpager.adapter = adapter

        overview_viewpager.currentItem = movePosition
        overview_tabs.setupWithViewPager(overview_viewpager)
    }

    private inner class ViewPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

        private val mFragmentList: MutableList<Fragment>
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

        private val TAB_POSITION = "tabposition"

        fun newInstance(tabPosition: Int): OverviewFragment {
            val frag = OverviewFragment()
            val bundle = Bundle()
            bundle.putInt(TAB_POSITION, tabPosition)

            frag.arguments = bundle
            return frag
        }
    }
}
