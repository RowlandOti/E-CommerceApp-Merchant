package com.rowland.delivery.features.dash.presentation.activities;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.rowland.delivery.features.dash.di.components.DaggerDashComponent;
import com.rowland.delivery.features.dash.di.components.DashComponent;
import com.rowland.delivery.features.dash.di.modules.DashModule;
import com.rowland.delivery.features.dash.presentation.fragments.OrderItemFragment;
import com.rowland.delivery.features.dash.presentation.fragments.OverviewFragment;
import com.rowland.delivery.features.dash.presentation.fragments.ProductFragment;
import com.rowland.delivery.features.dash.presentation.viewmodels.category.CategoryViewModel;
import com.rowland.delivery.features.dash.presentation.viewmodels.order.OrderViewModel;
import com.rowland.delivery.merchant.R;
import com.rowland.delivery.merchant.application.di.modules.ContextModule;
import com.rowland.delivery.services.session.SessionManager;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DashActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, FragmentManager.OnBackStackChangedListener {

    public final static String ACTION_SHOW_LOADING_ITEM = "loading";

    @BindView(R.id.main_drawer)
    NavigationView mDrawer;

    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;

    @Inject
    @Named("order")
    ViewModelProvider.Factory orderFactory;

    @Inject
    @Named("category")
    ViewModelProvider.Factory categoryFactory;

    @Inject
    SessionManager sessionManager;

    private OrderViewModel orderViewModel;
    private CategoryViewModel categoryViewModel;
    private int mSelectedMenuId;

    public DashComponent getDashComponent() {
        return dashComponent;
    }

    private DashComponent dashComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        dashComponent = DaggerDashComponent.builder()
                .dashModule(new DashModule())
                .contextModule(new ContextModule(this))
                .build();

        dashComponent.inject(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash);

        ButterKnife.bind(this);

        mDrawer.setNavigationItemSelectedListener(this);
        mSelectedMenuId = savedInstanceState == null ? R.id.action_business : savedInstanceState.getInt("SELECTED_ID");
        itemSelection(mSelectedMenuId);

        getSupportFragmentManager().addOnBackStackChangedListener(this);

        orderViewModel = ViewModelProviders.of(this, orderFactory).get(OrderViewModel.class);
        orderViewModel.getSelectedListItem()
                .observe(this, orderData -> {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container_body, OrderItemFragment.newInstance(null))
                            .addToBackStack(OrderItemFragment.class.getSimpleName())
                            .commit();
                });

        categoryViewModel = ViewModelProviders.of(this, categoryFactory).get(CategoryViewModel.class);
        categoryViewModel.getSelectedListItem()
                .observe(this, category -> {
                    Bundle args = new Bundle();
                    args.putString("selected_category", category.getName());
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container_body, ProductFragment.newInstance(args))
                            .addToBackStack(ProductFragment.class.getSimpleName())
                            .commit();
                });
    }

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, DashActivity.class);
        context.startActivity(intent);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        item.setChecked(true);
        mSelectedMenuId = item.getItemId();
        itemSelection(mSelectedMenuId);
        return true;
    }

    private void itemSelection(int mSelectedId) {
        FragmentManager manager = getSupportFragmentManager();
        switch (mSelectedId) {
            case R.id.action_business:
                manager.beginTransaction().replace(R.id.container_body, OverviewFragment.newInstance(0)).commit();
                mDrawerLayout.closeDrawer(GravityCompat.START);
                break;
            case R.id.action_ratings:
                //
                mDrawerLayout.closeDrawer(GravityCompat.START);
                break;
            case R.id.action_logout:
                sessionManager.logout();
                mDrawerLayout.closeDrawer(GravityCompat.START);
                break;
            default:
                mDrawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    public DrawerLayout getDrawerLayout() {
        return mDrawerLayout;
    }

    @Override
    public void onResume() {
        super.onResume();
        shouldDisplayHomeUp();
    }

    @Override
    public void onBackStackChanged() {
        shouldDisplayHomeUp();
    }

    @Override
    public boolean onSupportNavigateUp() {
        super.onSupportNavigateUp();
        getSupportFragmentManager().popBackStack();
        return true;
    }

    public void shouldDisplayHomeUp() {
        boolean canGoBack = getSupportFragmentManager().getBackStackEntryCount() > 0;
        getSupportActionBar().setDisplayHomeAsUpEnabled(canGoBack);
    }
}
