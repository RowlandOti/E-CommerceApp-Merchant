package com.rowland.delivery.features.dash.presentation.fragments

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rowland.delivery.features.dash.di.modules.product.ProductModule
import com.rowland.delivery.features.dash.presentation.activities.DashActivity
import com.rowland.delivery.features.dash.presentation.viewmodels.product.ProductViewModel
import com.rowland.delivery.merchant.R
import kotlinx.android.synthetic.main.fragment_edit_product.*
import javax.inject.Inject

class EditProductFragment : Fragment() {

    private lateinit var productViewModel: ProductViewModel

    @Inject
    lateinit var productFactory: ViewModelProvider.Factory


    companion object {
        fun newInstance(args: Bundle?): Fragment {
            val frag = EditProductFragment()
            frag.arguments = args
            return frag
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        productViewModel = ViewModelProviders.of(activity!!, productFactory).get(ProductViewModel::class.java)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_edit_product, container, false)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        (activity as DashActivity).dashComponent.getProductComponent(ProductModule()).inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity).setSupportActionBar(editproduct_toolbar)


        productViewModel.getSelectedListItem()
                .observe(this, Observer { product ->
                    input_edit_tv_price.text = product!!.price.toString()
                    input_edit_tv_stock.text = product.itemQuantity.toString()
                })
    }
}
