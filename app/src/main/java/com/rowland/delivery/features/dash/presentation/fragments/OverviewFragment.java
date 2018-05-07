package com.rowland.delivery.features.dash.presentation.fragments;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rowland.delivery.merchant.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class OverviewFragment extends Fragment {

    private static final String TAB_POSITION = "tabposition";
    private int movePosition;
    private Unbinder unbinder;

    @BindView(R.id.tabs)
    TabLayout tabLayout;

    @BindView(R.id.viewpager)
    ViewPager viewPager;


    public static OverviewFragment newInstance(int tabPosition) {
        OverviewFragment meals = new OverviewFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(TAB_POSITION, tabPosition);

        meals.setArguments(bundle);
        return meals;
    }

    public OverviewFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            movePosition = getArguments().getInt(TAB_POSITION, 0);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_overview, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        unbinder = ButterKnife.bind(this, view);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager());
        adapter.addFragment(new CategoryFragment(), getResources().getString(R.string.tab_mymeal));
        adapter.addFragment(new OrdersFragment(), getResources().getString(R.string.tab_orders));
        viewPager.setAdapter(adapter);

        viewPager.setCurrentItem(movePosition);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        unbinder.unbind();
    }


    private class ViewPagerAdapter extends FragmentStatePagerAdapter {

        private List<Fragment> mFragmentList;
        private final List<String> mFragmentTitleList;


        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
            mFragmentList = new ArrayList<>();
            mFragmentTitleList = new ArrayList<>();
        }

        public void addFragment(Fragment fragment, String title) {
            this.mFragmentList.add(fragment);
            this.mFragmentTitleList.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
