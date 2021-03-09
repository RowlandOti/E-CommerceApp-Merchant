package com.rowland.delivery.merchant.features.dash.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import com.afollestad.materialdialogs.MaterialDialog
import com.google.firebase.auth.FirebaseAuth
import com.rowland.delivery.domain.models.category.CategoryEntity
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

    private val categoryViewModel: CategoryViewModel by viewModels({ requireParentFragment()} )

    @Inject
    lateinit var categoryAdapter: CategoryAdapter

    companion object {

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
        Log.d("Hash-${CategoryFragment::class.java.simpleName}", categoryViewModel.toString())
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
                    }

                    override fun onItemLongClick(view: View, position: Int) {}
                })
        )

        binding.createCatBtn.setOnClickListener {
            MaterialDialog.Builder(requireActivity())
                .backgroundColorRes(android.R.color.white)
                .title("Create Category")
                .inputRangeRes(3, 12, android.R.color.holo_red_dark)
                .input("e.g Fruits", null) { dialog, input ->
                    saveCategory(
                        input.toString(),
                        FirebaseAuth.getInstance().currentUser!!.uid
                    )
                }
                .show()
        }


        categoryViewModel.getDataList()
            .observe(viewLifecycleOwner, Observer { categories ->
                handleDataState(categories.status, categories.data, categories.message)
            })
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
            .subscribe({ category ->
                Toast.makeText(
                    activity,
                    String.format(getString(string.category_name), category.name),
                    Toast.LENGTH_SHORT
                )
                    .show()
            }, {
                Toast.makeText(
                    activity,
                    String.format(getString(string.category_not_created), name),
                    Toast.LENGTH_SHORT
                )
                    .show()
            })
    }
}
