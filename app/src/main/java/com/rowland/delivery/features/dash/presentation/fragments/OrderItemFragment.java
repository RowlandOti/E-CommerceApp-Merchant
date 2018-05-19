package com.rowland.delivery.features.dash.presentation.fragments;


import android.Manifest;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.dekoservidoni.omfm.OneMoreFabMenu;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.rowland.delivery.features.dash.di.modules.order.OrderModule;
import com.rowland.delivery.features.dash.domain.models.order.OrderData;
import com.rowland.delivery.features.dash.presentation.activities.DashActivity;
import com.rowland.delivery.features.dash.presentation.adapters.OrderItemAdapter;
import com.rowland.delivery.features.dash.presentation.viewmodels.order.OrderViewModel;
import com.rowland.delivery.merchant.R;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Rowland on 5/7/2018.
 */
public class OrderItemFragment extends Fragment {

    private Unbinder unbinder;
    private OrderViewModel orderViewModel;
    private OrderData orderData;
    private OrderItemAdapter adapter;
    private static final int CALL_PERMISSION = 100;

    @BindView(R.id.fab)
    OneMoreFabMenu fab;

    @BindView(R.id.main_toolbar_orders)
    Toolbar toolbar;

    @BindView(R.id.customer_name)
    TextView customer_name;

    @BindView(R.id.status)
    TextView status;

    @BindView(R.id.phone)
    TextView phone;

    @BindView(R.id.ordered_items)
    EasyRecyclerView recyclerViewManager;

    @BindView(R.id.email)
    TextView email;

    @BindView(R.id.address)
    TextView address;

    @BindView(R.id.order_ref)
    TextView order_ref;

    @BindView(R.id.all_items)
    TextView all_items;

    @BindView(R.id.total_item_price)
    TextView total_item_price;

    @BindView(R.id.delivery_fee)
    TextView delivery_fee;

    @BindView(R.id.total_price)
    TextView total_price;


    @Inject
    @Named("order")
    ViewModelProvider.Factory orderFactory;

    public OrderItemFragment() {
        // Required empty public constructor
    }

    public static OrderItemFragment newInstance(Bundle args) {
        OrderItemFragment frag = new OrderItemFragment();
        frag.setArguments(args);
        return frag;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        orderViewModel = ViewModelProviders.of(getActivity(), orderFactory).get(OrderViewModel.class);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_order_item, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ((DashActivity) getActivity()).getDashComponent()
                .getOrderComponent(new OrderModule())
                .inject(this);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);

        if (toolbar != null) {
            ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        }

        adapter = new OrderItemAdapter();
        recyclerViewManager.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewManager.setItemAnimator(new DefaultItemAnimator());
        recyclerViewManager.setAdapterWithProgress(adapter);

        fab.setOptionsClick(id -> {
            switch (id) {
                case R.id.action_pending:
                    changeStatus("pending");
                    break;
                case R.id.action_complete:
                    changeStatus("completed");
                    break;
                case R.id.action_in_progress:
                    changeStatus("in_progress");
                    break;
                case R.id.action_canceled:
                    changeStatus("cancelled");
            }
            fab.performClick();
        });

        orderViewModel.getSelectedOrderData()
                .observe(this, orderData -> {
                    this.orderData = orderData;
                    setupData();
                });
    }

    @Override
    public void onDetach() {
        super.onDetach();
        unbinder.unbind();
    }

    @OnClick(R.id.action_call_btn)
    public void makeCall() {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse(String.format("tel:%s", orderData.getPhone())));
            startActivity(callIntent);
        } else {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CALL_PHONE}, CALL_PERMISSION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == CALL_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                makeCall();
            } else {
                Toast.makeText(getActivity(), "Please grant permission to call", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void changeTextStatus(String s) {
        status.setText(s);
        switch (s) {
            case "active":
                status.setTextColor(Color.parseColor("#378E3D"));
                break;
            case "delivered":
                status.setTextColor(Color.parseColor("#FF6D00"));
                break;
            case "failed":
                status.setTextColor(Color.parseColor("#B94464"));
                break;
            case "missed":
                status.setTextColor(Color.parseColor("#B94464"));
                break;
            case "completed":
                status.setTextColor(Color.parseColor("#AAAAAA"));
                break;
            case "cancelled":
                status.setTextColor(Color.parseColor("#B94464"));
                break;
            case "in_progress":
                status.setTextColor(Color.parseColor("#378E3D"));
                break;
            case "pending":
                status.setTextColor(Color.parseColor("#B94464"));
                break;
            default:
                status.setTextColor(Color.parseColor("#AAAAAA"));
        }
    }

    private final void changeStatus(String status) {
        orderViewModel.updateOrderStatus(status)
                .subscribe(() -> Toast.makeText(getActivity(), "Order Status Updated Successfull", Toast.LENGTH_SHORT).show()
                        , throwable -> Toast.makeText(getActivity(), "Update Failed", Toast.LENGTH_SHORT).show());
    }


    private void setupData() {
        customer_name.setText(orderData.getName());
        changeTextStatus(orderData.getStatus());

        phone.setText(orderData.getPhone());
        email.setText("Email not provided");

        if (orderData.getAddress().isEmpty()) {
            address.setText("Address not provided");
        } else {
            address.setText(orderData.getAddress());
        }

        order_ref.setText(orderData.getOrderRef());
        all_items.setText("items (" + orderData.getOrderItemsQuantity() + ")");
        total_item_price.setText("Ksh " + orderData.getOrderSubTotal());
        delivery_fee.setText("Ksh " + orderData.getDeliveryFee());
        total_price.setText("Ksh " + orderData.getOrderTotal());

        adapter.setList(orderData.getItems());
        adapter.notifyDataSetChanged();
    }
}
