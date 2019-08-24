package com.rowland.delivery.merchant.features.dash.fragments


import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.afollestad.materialdialogs.MaterialDialog
import com.google.firebase.auth.FirebaseAuth
import com.rowland.delivery.domain.models.category.CategoryEntity
import com.rowland.delivery.merchant.R
import com.rowland.delivery.merchant.features.dash.activities.DashActivity
import com.rowland.delivery.merchant.features.dash.adapters.CategoryAdapter
import com.rowland.delivery.merchant.features.dash.tools.recylerview.GridSpacingItemDecoration
import com.rowland.delivery.merchant.features.dash.tools.recylerview.RecyclerItemClickListener
import com.rowland.delivery.merchant.utilities.ScreenUtils
import com.rowland.delivery.presentation.data.ResourceState
import com.rowland.delivery.presentation.model.category.CategoryModel
import com.rowland.delivery.presentation.viewmodels.category.CategoryViewModel
import kotlinx.android.synthetic.main.fragment_category.*
import javax.inject.Inject

/**
 * Created by Rowland on 5/7/2018.
 */
class CategoryFragment : Fragment() {

    private lateinit var categoryViewModel: CategoryViewModel

    //@field:[Inject Named("category")]
    @Inject
    lateinit var categoryFactory: ViewModelProvider.Factory

    @Inject
    lateinit var categoryAdapter: CategoryAdapter

    companion object {
        fun newInstance(args: Bundle?): androidx.fragment.app.Fragment {
            val frag = CategoryFragment()
            frag.arguments = args
            return frag
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        categoryViewModel = ViewModelProviders.of(activity!!, categoryFactory).get(CategoryViewModel::class.java)
        categoryViewModel.loadCategories(FirebaseAuth.getInstance().currentUser!!.uid)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_category, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as DashActivity).dashComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cat_recyclerview.setLayoutManager(androidx.recyclerview.widget.GridLayoutManager(activity, 3))
        cat_recyclerview.addItemDecoration(GridSpacingItemDecoration(3, ScreenUtils.dpToPx(activity!!, 10), true))
        cat_recyclerview.setItemAnimator(androidx.recyclerview.widget.DefaultItemAnimator())
        cat_recyclerview.setAdapterWithProgress(categoryAdapter)

        cat_recyclerview.addOnItemTouchListener(RecyclerItemClickListener(activity, cat_recyclerview.recyclerView, object : RecyclerItemClickListener.OnItemClickListener {
            override fun onItemClick(view: View, position: Int) {
                categoryViewModel.setSelectedListItem(position)
            }

            override fun onItemLongClick(view: View, position: Int) {}
        }))

        create_cat_btn.setOnClickListener { view ->
            MaterialDialog.Builder(activity!!)
                    .backgroundColorRes(android.R.color.white)
                    .title("Create Category")
                    .inputRangeRes(3, 12, android.R.color.holo_red_dark)
                    .input("e.g Fruits", null) { dialog, input -> saveCategory(input.toString(), FirebaseAuth.getInstance().currentUser!!.uid!!) }
                    .show()
        }


        categoryViewModel.getDataList()
                .observe(this, Observer { categories ->
                    handleDataState(categories.status, categories.data, categories.message)
                })
    }

    private fun handleDataState(resourceState: ResourceState, data: List<CategoryModel>?, message: String?) {
        when (resourceState) {
            ResourceState.LOADING -> cat_recyclerview.showProgress()
            ResourceState.SUCCESS -> {
                cat_recyclerview.showRecycler()
                if(data != null)
                    categoryAdapter.setList(data!!)
            }
            ResourceState.ERROR -> {
                cat_recyclerview.showError()
                Log.d(CategoryFragment::class.java.simpleName, message)
            }
        }
    }


    private fun saveCategory(name: String, userUID: String) {
        categoryViewModel.createCategory(CategoryEntity(name.toLowerCase()), userUID)
                .subscribe({ category ->
                    Toast.makeText(activity, String.format("Category: %s created", category.name), Toast.LENGTH_SHORT)
                            .show()
                }, { throwable ->
                    Toast.makeText(activity, String.format("Category: %s not created", name), Toast.LENGTH_SHORT)
                            .show()
                })
    }


}
