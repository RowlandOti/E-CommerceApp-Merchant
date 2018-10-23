package com.rowland.delivery.merchant.features.dash.fragments

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import com.rowland.delivery.merchant.R
import com.rowland.delivery.merchant.features.dash.activities.DashActivity
import com.rowland.delivery.merchant.features.dash.adapters.ProductAdapter
import com.rowland.delivery.merchant.features.dash.tools.recylerview.RecyclerItemClickListener
import com.rowland.delivery.presentation.viewmodels.product.ProductEvent
import com.rowland.delivery.presentation.viewmodels.product.ProductViewModel
import kotlinx.android.synthetic.main.fragment_products.*
import javax.inject.Inject

class ProductFragment : Fragment() {

    private lateinit var productViewModel: ProductViewModel

    @Inject
    lateinit var productFactory: ViewModelProvider.Factory

    @Inject
    lateinit var productAdapter: ProductAdapter

    companion object {
        fun newInstance(args: Bundle?): androidx.fragment.app.Fragment {
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
        (activity as DashActivity).dashComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity).setSupportActionBar(toolbar_product)

        if (arguments != null) {
            val category = arguments!!.getString("selected_category")
            product_category.text = category
        }

        val linearLayoutManager = androidx.recyclerview.widget.LinearLayoutManager(activity)
        product_recyclerview.setLayoutManager(linearLayoutManager)
        product_recyclerview.setItemAnimator(androidx.recyclerview.widget.DefaultItemAnimator())
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
                            Toast.makeText(activity, "ProductEntity successfully deleted", Toast.LENGTH_SHORT).show()
                        }) {
                            Toast.makeText(activity, "ProductEntity Could not be deleted", Toast.LENGTH_SHORT).show()
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
