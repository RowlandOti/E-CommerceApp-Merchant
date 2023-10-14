/*
 * Copyright (c) Otieno Rowland,  2021. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.rowland.delivery.merchant.features.dash.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import com.afollestad.materialdialogs.MaterialDialog
import com.google.firebase.auth.FirebaseAuth
import com.rowland.delivery.domain.models.category.CategoryEntity
import com.rowland.delivery.merchant.R
import com.rowland.delivery.merchant.R.string
import com.rowland.delivery.merchant.databinding.FragmentCategoryBinding
import com.rowland.delivery.merchant.features.dash.adapters.CategoryAdapter
import com.rowland.delivery.merchant.features.dash.tools.recylerview.GridSpacingItemDecoration
import com.rowland.delivery.merchant.features.dash.tools.recylerview.RecyclerItemClickListener
import com.rowland.delivery.merchant.utilities.ScreenUtils
import com.rowland.delivery.presentation.data.ResourceState
import com.rowland.delivery.presentation.model.category.CategoryModel
import com.rowland.delivery.presentation.viewmodels.category.CategoryViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale
import javax.inject.Inject

/**
 * Created by Rowland on 5/7/2018.
 */

@AndroidEntryPoint
class CategoryFragment : Fragment() {

    private val categoryViewModel: CategoryViewModel by viewModels()

    @Inject
    lateinit var categoryAdapter: CategoryAdapter

    companion object {

        const val REQUEST_CATEGORY_SELECTED = "category_selected_"
        const val CATEGORY = "category_"

        fun newInstance(args: Bundle?): Fragment {
            val frag = CategoryFragment()
            frag.arguments = args
            return frag
        }
    }

    private var _binding: FragmentCategoryBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        categoryViewModel.loadCategories(FirebaseAuth.getInstance().currentUser!!.uid)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.catRecyclerview.setLayoutManager(GridLayoutManager(activity, 3))
        binding.catRecyclerview.addItemDecoration(
            GridSpacingItemDecoration(
                3,
                ScreenUtils.dpToPx(requireActivity(), 10),
                true
            )
        )
        binding.catRecyclerview.setItemAnimator(DefaultItemAnimator())
        binding.catRecyclerview.setAdapterWithProgress(categoryAdapter)

        binding.catRecyclerview.addOnItemTouchListener(
            RecyclerItemClickListener(
                activity,
                binding.catRecyclerview.recyclerView,
                object : RecyclerItemClickListener.OnItemClickListener {
                    override fun onItemClick(view: View, position: Int) {
                        categoryViewModel.setSelectedListItem(position)
                        setFragmentResult(
                            REQUEST_CATEGORY_SELECTED,
                            bundleOf(
                                CATEGORY to
                                    categoryViewModel.getSelectedListItem().value!!.name
                            )
                        )
                    }

                    override fun onItemLongClick(view: View, position: Int) {}
                }
            )
        )

        binding.createCatBtn.setOnClickListener {
            MaterialDialog.Builder(requireActivity())
                .backgroundColorRes(android.R.color.white)
                .title(getString(string.create_category))
                .inputRangeRes(3, 12, android.R.color.holo_red_dark)
                .input(getString(string.create_category_hint), null) { _, input ->
                    saveCategory(
                        input.toString(),
                        FirebaseAuth.getInstance().currentUser!!.uid
                    )
                }
                .show()
        }

        categoryViewModel.getDataList()
            .observe(
                viewLifecycleOwner,
                { categories ->
                    handleDataState(categories.status, categories.data, categories.message)
                }
            )
    }

    private fun handleDataState(resourceState: ResourceState, data: List<CategoryModel>?, message: String?) {
        when (resourceState) {
            ResourceState.LOADING -> binding.catRecyclerview.showProgress()
            ResourceState.SUCCESS -> {
                binding.catRecyclerview.showRecycler()
                if (data != null)
                    categoryAdapter.setList(data)
            }
            ResourceState.ERROR -> {
                binding.catRecyclerview.showError()
                message?.let { Log.d(CategoryFragment::class.java.simpleName, it) }
            }
        }
    }

    @SuppressLint("CheckResult")
    private fun saveCategory(name: String, userUID: String) {
        categoryViewModel.createCategory(CategoryEntity(name.toLowerCase(Locale.ROOT)), userUID)
            .subscribe(
                { category ->
                    Toast.makeText(
                        activity,
                        String.format(getString(R.string.category_name), category.name),
                        Toast.LENGTH_SHORT
                    )
                        .show()
                },
                {
                    Toast.makeText(
                        activity,
                        String.format(getString(R.string.category_not_created), name),
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            )
    }
}
