package com.rowland.delivery.features.dash.presentation.fragments;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.rowland.delivery.features.dash.di.modules.product.ProductModule;
import com.rowland.delivery.features.dash.presentation.activities.DashActivity;
import com.rowland.delivery.features.dash.presentation.adapters.ProductAdapter;
import com.rowland.delivery.features.dash.presentation.tools.recylerview.RecyclerItemClickListener;
import com.rowland.delivery.features.dash.presentation.viewmodels.product.ProductViewModel;
import com.rowland.delivery.merchant.R;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class ProductFragment extends Fragment {

    private Unbinder unbinder;
    private ProductViewModel productViewModel;

    @BindView(R.id.recyclerview)
    RecyclerView productRecyclerview;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.main_toolbar_product)
    Toolbar toolbar;

    @BindView(R.id.product_category)
    TextView tvProductCategory;

    @Inject
    @Named("product")
    ViewModelProvider.Factory productFactory;
    @Inject
    ProductAdapter productAdapter;

    public ProductFragment() {
        // Required empty public constructor
    }

    public static Fragment newInstance(Bundle args) {
        ProductFragment frag = new ProductFragment();
        frag.setArguments(args);
        return frag;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        productViewModel = ViewModelProviders.of(this, productFactory).get(ProductViewModel.class);
        productViewModel.setFirebaseUserUid(FirebaseAuth.getInstance().getCurrentUser().getUid());

        if (getArguments() != null) {
            String category = getArguments().getString("selected_category");
            productViewModel.setCategory(category);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_products, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ((DashActivity) getActivity()).getDashComponent()
                .getProductComponent(new ProductModule())
                .inject(this);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);

        if (toolbar != null) {
            ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        }

        if (getArguments() != null) {
            String category = getArguments().getString("selected_category");
            tvProductCategory.setText(category);
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        productRecyclerview.setLayoutManager(linearLayoutManager);
        productRecyclerview.setItemAnimator(new DefaultItemAnimator());
        productRecyclerview.setAdapter(productAdapter);

        productRecyclerview.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), productRecyclerview, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                productViewModel.setSelectedListItem(position);
            }

            @Override
            public void onItemLongClick(View view, int position) {
            }
        }));

        productViewModel.getDataList()
                .observe(this, products -> productAdapter.setList(products));
    }

    @Override
    public void onDetach() {
        super.onDetach();
        unbinder.unbind();
    }

    @OnClick(R.id.fab)
    public void fabClick() {
        Bundle args = null;
        if (getArguments() != null) {
            args = new Bundle();
            args.putString("selected_category", getArguments().getString("selected_category"));
        }
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.container_body, NewProductFragment.newInstance(args))
                .addToBackStack(NewProductFragment.class.getSimpleName())
                .commit();
    }
}
