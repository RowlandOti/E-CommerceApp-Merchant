package com.rowland.delivery.features.dash.presentation.fragments


import android.Manifest
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager.POP_BACK_STACK_INCLUSIVE
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.rowland.delivery.features.dash.di.modules.product.ProductModule
import com.rowland.delivery.features.dash.domain.models.product.Product
import com.rowland.delivery.features.dash.presentation.activities.DashActivity
import com.rowland.delivery.features.dash.presentation.viewmodels.product.NewProductViewModel
import com.rowland.delivery.merchant.R
import com.tbruyelle.rxpermissions2.RxPermissions
import kotlinx.android.synthetic.main.content_single_product.*
import kotlinx.android.synthetic.main.fragment_new_product.*
import java.util.*
import javax.inject.Inject


class NewProductFragment : Fragment() {

    private lateinit var newProductViewModel: NewProductViewModel

    @Inject
    lateinit var newProductFactory: ViewModelProvider.Factory

    companion object {
        fun newInstance(args: Bundle?): Fragment {
            val frag = NewProductFragment()
            frag.arguments = args
            return frag
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        newProductViewModel = ViewModelProviders.of(this, newProductFactory).get(NewProductViewModel::class.java)

        if (arguments != null) {
            val category = arguments!!.getString("selected_category")
            newProductViewModel.setCategory(category!!)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_new_product, container, false)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        (activity as DashActivity).dashComponent.getProductComponent(ProductModule()).inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity).setSupportActionBar(newproduct_toolbar)

        fab_addimage.setOnClickListener({ view ->
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                RxPermissions(activity!!).request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        .subscribe { granted ->
                            if (!granted) {
                                Toast.makeText(activity, "Please grant permissions to select image", Toast.LENGTH_SHORT).show()
                            } else {
                                newProductViewModel.selectImages()
                            }
                        }
            }
        })

        btn_save.setOnClickListener({ view ->
            val product = Product()
            product.price = Integer.valueOf(input_product_pricing.text!!.toString())
            product.name = input_product_name.text!!.toString()
            product.saleQuantity = Integer.valueOf(input_product_quantity.text!!.toString())
            product.itemQuantity = Integer.valueOf(input_product_stock!!.text!!.toString())
            product.description = input_product_description.text!!.toString()
            product.createdAt = Date()

            newProductViewModel.saveProduct(product)
                    .subscribe({ newProduct ->
                        Toast.makeText(activity, "Product added succesfully", Toast.LENGTH_SHORT).show()
                        activity!!.supportFragmentManager.popBackStack(NewProductFragment::class.java.simpleName, POP_BACK_STACK_INCLUSIVE)
                    }) { throwable -> Toast.makeText(activity, "Product not added", Toast.LENGTH_SHORT).show() }

        })

        btn_cancell.setOnClickListener({ view ->
            activity!!.supportFragmentManager.popBackStack(NewProductFragment::class.java.simpleName, POP_BACK_STACK_INCLUSIVE)
        })

        newProductViewModel.images.observe(this, Observer { uris ->
            Glide.with(this)
                    .load(uris!!.get(0))
                    .crossFade()
                    .fitCenter()
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .into(product_imageview)
        })
    }
}
