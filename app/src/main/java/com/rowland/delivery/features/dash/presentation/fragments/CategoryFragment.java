package com.rowland.delivery.features.dash.presentation.fragments;


import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.rowland.delivery.features.dash.di.modules.category.CategoryModule;
import com.rowland.delivery.features.dash.domain.models.Category;
import com.rowland.delivery.features.dash.presentation.activities.DashActivity;
import com.rowland.delivery.features.dash.presentation.adapters.CategoryAdapter;
import com.rowland.delivery.features.dash.presentation.tools.recylerview.GridSpacingItemDecoration;
import com.rowland.delivery.features.dash.presentation.viewmodels.category.CategoryViewModel;
import com.rowland.delivery.merchant.R;
import com.rowland.delivery.utilities.ScreenUtils;
import com.vlonjatg.progressactivity.ProgressFrameLayout;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class CategoryFragment extends Fragment {

    private Unbinder unbinder;
    private CategoryViewModel categoryViewModel;

    @BindView(R.id.create_cat_btn)
    AppCompatButton createCategory;

    @BindView(R.id.cat_recyclerview)
    RecyclerView recyclerView;

    @BindView(R.id.progressActivity)
    ProgressFrameLayout progressActivity;

    @Inject
    @Named("category")
    ViewModelProvider.Factory categoryFactory;
    @Inject
    CategoryAdapter categoryAdapter;

    public CategoryFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        categoryViewModel = ViewModelProviders.of(this, categoryFactory).get(CategoryViewModel.class);
        categoryViewModel.setFirebaseUserUid(FirebaseAuth.getInstance().getCurrentUser().getUid());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_category, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ((DashActivity) getActivity()).getDashComponent()
                .getCategoryComponent(new CategoryModule())
                .injectCategoryFragment(this);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);

        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(3, ScreenUtils.dpToPx(getActivity(), 10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(categoryAdapter);

        categoryViewModel.getCategoryList()
                .observe(this, categories -> {
                    categoryAdapter.setList(categories);
                });
    }

    @Override
    public void onDetach() {
        super.onDetach();
        unbinder.unbind();
    }

    @OnClick(R.id.create_cat_btn)
    public void createCategory() {
        new MaterialDialog.Builder(getActivity())
                .backgroundColorRes(android.R.color.white)
                .title("Create Category")
                .inputRangeRes(3, 12, android.R.color.holo_red_dark)
                .input("e.g Fruits", null, (dialog, input) -> saveCategory(input.toString()))
                .show();
    }

    private void saveCategory(String name) {
        categoryViewModel.saveCategory(new Category(name.toLowerCase()))
                .subscribe(category -> {
                    Toast.makeText(getActivity(), String.format("Category: %s created", name), Toast.LENGTH_SHORT)
                            .show();
                }, throwable -> {
                    Toast.makeText(getActivity(), String.format("Category: %s not created", name), Toast.LENGTH_SHORT)
                            .show();
                });
    }
}
