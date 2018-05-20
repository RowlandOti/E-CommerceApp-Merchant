package com.rowland.delivery.features.dash.presentation.fragments


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.afollestad.materialdialogs.MaterialDialog
import com.rowland.delivery.features.dash.di.modules.category.CategoryModule
import com.rowland.delivery.features.dash.domain.models.category.Category
import com.rowland.delivery.features.dash.presentation.activities.DashActivity
import com.rowland.delivery.features.dash.presentation.adapters.CategoryAdapter
import com.rowland.delivery.features.dash.presentation.tools.recylerview.GridSpacingItemDecoration
import com.rowland.delivery.features.dash.presentation.tools.recylerview.RecyclerItemClickListener
import com.rowland.delivery.features.dash.presentation.viewmodels.ViewEvent
import com.rowland.delivery.features.dash.presentation.viewmodels.category.CategoryViewModel
import com.rowland.delivery.merchant.R
import com.rowland.delivery.utilities.ScreenUtils
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
        fun newInstance(args: Bundle?): Fragment {
            val frag = CategoryFragment()
            frag.arguments = args
            return frag
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        categoryViewModel = ViewModelProviders.of(activity!!, categoryFactory).get(CategoryViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_category, container, false)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        (activity as DashActivity).dashComponent.getCategoryComponent(CategoryModule()).inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cat_recyclerview.setLayoutManager(GridLayoutManager(activity, 3))
        cat_recyclerview.addItemDecoration(GridSpacingItemDecoration(3, ScreenUtils.dpToPx(activity!!, 10), true))
        cat_recyclerview.setItemAnimator(DefaultItemAnimator())
        cat_recyclerview.setAdapterWithProgress(categoryAdapter)

        cat_recyclerview.addOnItemTouchListener(RecyclerItemClickListener(activity, cat_recyclerview.recyclerView, object : RecyclerItemClickListener.OnItemClickListener {
            override fun onItemClick(view: View, position: Int) {
                categoryViewModel.setSelectedListItem(position)
            }

            override fun onItemLongClick(view: View, position: Int) {}
        }))

        create_cat_btn.setOnClickListener({ view ->
            MaterialDialog.Builder(activity!!)
                    .backgroundColorRes(android.R.color.white)
                    .title("Create Category")
                    .inputRangeRes(3, 12, android.R.color.holo_red_dark)
                    .input("e.g Fruits", null) { dialog, input -> saveCategory(input.toString()) }
                    .show()
        })

        categoryViewModel.state.observe(this, Observer {
            when (it!!.event) {
                is ViewEvent.Success -> it.event.handle {
                    /* Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT)
                             .show();*/
                }
                is ViewEvent.Error -> it.event.handle {
                    /* Toast.makeText(getActivity(), "Error Occured", Toast.LENGTH_SHORT)
                             .show();*/
                    cat_recyclerview.showError()
                }
            }
        })


        categoryViewModel.getDataList()
                .observe(this, Observer { categories ->
                    categoryAdapter.setList(categories)
                })
    }


    private fun saveCategory(name: String) {
        categoryViewModel.saveCategory(Category(name.toLowerCase()))
                .subscribe({ category ->
                    Toast.makeText(activity, String.format("Category: %s created", name), Toast.LENGTH_SHORT)
                            .show()
                }) { throwable ->
                    Toast.makeText(activity, String.format("Category: %s not created", name), Toast.LENGTH_SHORT)
                            .show()
                }
    }
}
