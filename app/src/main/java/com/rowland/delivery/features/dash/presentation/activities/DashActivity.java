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
import com.rowland.delivery.features.dash.presentation.viewmodels.order.OrderViewModel;
import com.rowland.delivery.merchant.R;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DashActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, FragmentManager.OnBackStackChangedListener {

    public final static String ACTION_SHOW_LOADING_ITEM = "loading";

    //tags to allow switching of fragment instead of recreating them
    private static final String MEALS_TAG = "categories";
    private static final String ORDERS_TAG = "orders";
    private static final String SALES_TAG = "sales";
    private static final String SETTINGS_TAG = "settings";
    private static final String LOGOUT_TAG = "logout";

    @BindView(R.id.main_drawer)
    NavigationView mDrawer;

    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;

    @Inject
    @Named("order")
    ViewModelProvider.Factory orderFactory;

    private OrderViewModel orderViewModel;
    private int mSelectedId;

    public DashComponent getDashComponent() {
        return dashComponent;
    }

    private DashComponent dashComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        dashComponent = DaggerDashComponent.builder()
                .dashModule(new DashModule())
                .build();

        dashComponent.inject(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash);

        ButterKnife.bind(this);

        mDrawer.setNavigationItemSelectedListener(this);
        mSelectedId = savedInstanceState == null ? R.id.action_business : savedInstanceState.getInt("SELECTED_ID");
        itemSelection(mSelectedId);

        getSupportFragmentManager().addOnBackStackChangedListener(this);

        orderViewModel = ViewModelProviders.of(this, orderFactory).get(OrderViewModel.class);
        orderViewModel.getSelectedOrderData()
                .observe(this, orderData -> {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container_body, OrderItemFragment.newInstance(null))
                            .addToBackStack(OrderItemFragment.class.getSimpleName())
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
        mSelectedId = item.getItemId();
        itemSelection(mSelectedId);
        return true;
    }

    private void itemSelection(int mSelectedId) {
        FragmentManager manager = getSupportFragmentManager();
        switch (mSelectedId) {
            case R.id.action_business:
                manager.beginTransaction().replace(R.id.container_body, OverviewFragment.newInstance(0)).commit();
                mDrawerLayout.closeDrawer(GravityCompat.START);
                break;
            case R.id.action_orders:
                manager.beginTransaction().replace(R.id.container_body, OverviewFragment.newInstance(1)).commit();
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
        boolean canback = getSupportFragmentManager().getBackStackEntryCount() > 0;
        getSupportActionBar().setDisplayHomeAsUpEnabled(canback);
    }
}
