package com.rowland.delivery.features.dash.presentation.fragments

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.rowland.delivery.features.dash.di.modules.product.ProductModule
import com.rowland.delivery.features.dash.presentation.activities.DashActivity
import com.rowland.delivery.features.dash.presentation.adapters.ProductAdapter
import com.rowland.delivery.features.dash.presentation.tools.recylerview.RecyclerItemClickListener
import com.rowland.delivery.features.dash.presentation.viewmodels.product.ProductEvent
import com.rowland.delivery.features.dash.presentation.viewmodels.product.ProductViewModel
import com.rowland.delivery.merchant.R
import kotlinx.android.synthetic.main.fragment_products.*
import javax.inject.Inject

class ProductFragment : Fragment() {

    private lateinit var productViewModel: ProductViewModel

    @Inject
    lateinit var productFactory: ViewModelProvider.Factory

    @Inject
    lateinit var productAdapter: ProductAdapter

    companion object {
        fun newInstance(args: Bundle?): Fragment {
            val frag = ProductFragment()
            frag.arguments = args
            return frag
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        productViewModel = ViewModelProviders.of(activity!!, productFactory).get(ProductViewModel::class.java)

        if (arguments != null) {
            val category = arguments!!.getString("selected_category")
            productViewModel.setCategory(category!!)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_products, container, false)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        (activity as DashActivity).dashComponent.getProductComponent(ProductModule()).inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity).setSupportActionBar(toolbar_product)

        if (arguments != null) {
            val category = arguments!!.getString("selected_category")
            product_category.text = category
        }

        val linearLayoutManager = LinearLayoutManager(activity)
        product_recyclerview.setLayoutManager(linearLayoutManager)
        product_recyclerview.setItemAnimator(DefaultItemAnimator())
        product_recyclerview.setAdapterWithProgress(productAdapter)

        if (savedInstanceState != null) {
            productAdapter.restoreStates(savedInstanceState)
        }

        productAdapter.setActionListener(object : ProductAdapter.ProductActionListener {
            override fun onProductActionListener(event: ProductEvent, fn: () -> Unit) {
                when (event) {
                    is ProductEvent.Edit -> {
                        fn()
                        activity!!.supportFragmentManager.beginTransaction()
                                .replace(R.id.dash_container_body, EditProductFragment.Companion.newInstance(null))
                                .addToBackStack(EditProductFragment::class.java.simpleName)
                                .commit()
                    }
                    is ProductEvent.Unpublish -> {
                        productViewModel.deleteProduct().subscribe({
                            fn()
                            Toast.makeText(activity, "Product successfully deleted", Toast.LENGTH_SHORT).show()
                        }) {
                            Toast.makeText(activity, "Product Could not be deleted", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }

        })

        product_recyclerview.addOnItemTouchListener(RecyclerItemClickListener(activity, product_recyclerview.recyclerView, object : RecyclerItemClickListener.OnItemClickListener {
            override fun onItemClick(view: View, position: Int) {
                productViewModel.setSelectedListItem(position)
            }

            override fun onItemLongClick(view: View, position: Int) {}
        }))

        fab.setOnClickListener({ view ->
            var args: Bundle? = null
            if (arguments != null) {
                args = Bundle()
                args.putString("selected_category", arguments!!.getString("selected_category"))
            }
            activity!!.supportFragmentManager.beginTransaction()
                    .replace(R.id.dash_container_body, NewProductFragment.Companion.newInstance(args))
                    .addToBackStack(NewProductFragment::class.java.simpleName)
                    .commit()
        })

        productViewModel.getDataList()
                .observe(this, Observer { products -> productAdapter.setList(products!!) })
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        productAdapter.saveStates(outState)
    }
}
