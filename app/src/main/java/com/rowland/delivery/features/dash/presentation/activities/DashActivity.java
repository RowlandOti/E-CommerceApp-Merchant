package com.rowland.delivery.features.dash.presentation.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.rowland.delivery.features.dash.di.components.DaggerDashComponent;
import com.rowland.delivery.features.dash.di.components.DashComponent;
import com.rowland.delivery.features.dash.di.modules.DashModule;
import com.rowland.delivery.features.dash.presentation.fragments.OverviewFragment;
import com.rowland.delivery.merchant.R;
import com.rowland.delivery.merchant.application.di.modules.ContextModule;
import com.rowland.delivery.services.session.SessionManager;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DashActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public final static String ACTION_SHOW_LOADING_ITEM = "loading";

    //tags to allow switching of fragment instead of recreating them
    private static final String MEALS_TAG = "meals";
    private static final String ORDERS_TAG = "orders";
    private static final String SALES_TAG = "sales";
    private static final String SETTINGS_TAG = "settings";
    private static final String LOGOUT_TAG = "logout";

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.main_drawer)
    NavigationView mDrawer;

    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;

    @BindView(R.id.main_content)
    CoordinatorLayout main_content_layout;

    @Inject
    SessionManager sessionManager;

    private ActionBarDrawerToggle drawerToggle;

    private int mSelectedId;

    public DashComponent getDashComponent() {
        return dashComponent;
    }

    private DashComponent dashComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash);

        ButterKnife.bind(this);

        dashComponent = DaggerDashComponent.builder()
                //.dashModule(new DashModule())
                .build();

        if (toolbar != null) {
            setSupportActionBar(toolbar);
            drawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
            mDrawerLayout.addDrawerListener(drawerToggle);
            drawerToggle.syncState();
        }

        mDrawer.setNavigationItemSelectedListener(this);

        //default it set first item as selected
        mSelectedId = savedInstanceState == null ? R.id.action_meals : savedInstanceState.getInt("SELECTED_ID");
        itemSelection(mSelectedId);
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
            case R.id.action_meals:
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
}
