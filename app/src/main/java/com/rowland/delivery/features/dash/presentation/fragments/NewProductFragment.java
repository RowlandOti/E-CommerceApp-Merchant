package com.rowland.delivery.features.dash.presentation.fragments;


import android.Manifest;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.firebase.auth.FirebaseAuth;
import com.rowland.delivery.features.dash.di.modules.product.ProductModule;
import com.rowland.delivery.features.dash.domain.models.product.Product;
import com.rowland.delivery.features.dash.presentation.activities.DashActivity;
import com.rowland.delivery.features.dash.presentation.viewmodels.product.NewProductViewModel;
import com.rowland.delivery.merchant.R;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.Date;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static android.support.v4.app.FragmentManager.POP_BACK_STACK_INCLUSIVE;


public class NewProductFragment extends Fragment {

    private Unbinder unbinder;
    private NewProductViewModel newProductViewModel;

    @BindView(R.id.main_toolbar_newproduct)
    Toolbar toolbar;

    @BindView(R.id.imageview)
    ImageView productImage;

    @BindView(R.id.input_meal_title)
    EditText edtTitle;

    @BindView(R.id.input_meal_description)
    EditText edtDescription;

    @BindView(R.id.input_meal_quantity)
    EditText edtQuantity;

    @BindView(R.id.input_meal_unit)
    Spinner edtUnit;

    @BindView(R.id.input_meal_price)
    EditText edtPrice;

    @BindView(R.id.input_meal_stock)
    EditText edtStock;

    @Inject
    @Named("newproduct")
    ViewModelProvider.Factory newProductFactory;


    public NewProductFragment() {
        // Required empty public constructor
    }

    public static Fragment newInstance(Bundle args) {
        NewProductFragment frag = new NewProductFragment();
        frag.setArguments(args);
        return frag;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        newProductViewModel = ViewModelProviders.of(this, newProductFactory).get(NewProductViewModel.class);
        newProductViewModel.setFirebaseUserUid(FirebaseAuth.getInstance().getCurrentUser().getUid());

        if (getArguments() != null) {
            String category = getArguments().getString("selected_category");
            newProductViewModel.setCategory(category);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_new_product, container, false);
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

        newProductViewModel.getImages().observe(this, uris -> {
            Glide.with(this)
                    .load(uris.get(0))
                    .crossFade()
                    .fitCenter()
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .into(productImage);
        });
    }

    @OnClick(R.id.fab_addimage)
    public void addImage() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            new RxPermissions(getActivity()).request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    .subscribe(granted -> {
                        if (!granted) {
                            Toast.makeText(getActivity(), "Please grant permissions to select image", Toast.LENGTH_SHORT).show();
                        } else {
                            newProductViewModel.selectImages();
                        }
                    });
        }
    }

    @OnClick(R.id.btn_save)
    public void saveProduct() {

        Product product = new Product();
        product.setPrice(Integer.valueOf(edtPrice.getText().toString()));
        product.setName(edtTitle.getText().toString());
        product.setSaleQuantity(Integer.valueOf(edtQuantity.getText().toString()));
        product.setItemQuantity(Integer.valueOf(edtStock.getText().toString()));
        product.setDescription(edtDescription.getText().toString());
        product.setCreatedAt(new Date());

        newProductViewModel.saveProduct(product)
                .subscribe(newProduct -> {
                    Toast.makeText(getActivity(), "Product added succesfully", Toast.LENGTH_SHORT).show();
                    getActivity().getSupportFragmentManager().popBackStack(NewProductFragment.class.getSimpleName(), POP_BACK_STACK_INCLUSIVE);
                }, throwable -> {
                    Toast.makeText(getActivity(), "Product not added", Toast.LENGTH_SHORT).show();
                });
    }

    @OnClick(R.id.btn_cancell)
    public void cancell() {
        getActivity().getSupportFragmentManager().popBackStack(NewProductFragment.class.getSimpleName(), POP_BACK_STACK_INCLUSIVE);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        unbinder.unbind();
    }
}
