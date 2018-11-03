package com.rowland.delivery.features.dash.presentation.fragments

import android.Manifest
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.appcompat.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.storage.FirebaseStorage
import com.rowland.delivery.features.dash.di.modules.product.ProductModule
import com.rowland.delivery.features.dash.presentation.activities.DashActivity
import com.rowland.delivery.features.dash.presentation.viewmodels.product.EditProductViewModel
import com.rowland.delivery.features.dash.presentation.viewmodels.product.ProductViewModel

import com.rowland.delivery.merchant.R
import com.rowland.rxgallery.RxGallery
import com.tbruyelle.rxpermissions2.RxPermissions
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_edit_product.*
import java.util.*
import javax.inject.Inject

class EditProductFragment : Fragment() {

    private lateinit var productViewModel: ProductViewModel
    private lateinit var editProductViewModel: EditProductViewModel

    @Inject
    lateinit var productFactory: ViewModelProvider.Factory


    companion object {
        fun newInstance(args: Bundle?): androidx.fragment.app.Fragment {
            val frag = EditProductFragment()
            frag.arguments = args
            return frag
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        productViewModel = ViewModelProviders.of(activity!!, productFactory).get(ProductViewModel::class.java)
        editProductViewModel = ViewModelProviders.of(this, productFactory).get(EditProductViewModel::class.java)

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
                    input_edit_price_view.quantity = product!!.price!!
                    input_edit_stock_view.quantity = product.itemQuantity!!
                    input_edit_product_name.setText(product.name)
                    input_edit_product_description.setText(product.description)
                    val options = RequestOptions()
                    FirebaseStorage.getInstance().reference.child(product.imageUrls.get(0)).downloadUrl
                            .addOnSuccessListener { uri ->
                                options.centerCrop().diskCacheStrategy(DiskCacheStrategy.ALL)
                                Glide.with(activity!!)
                                        .load(uri)
                                        .apply(options)
                                        .into(edit_input_product_imageview)
                            }

                    editProductViewModel.setProductFirebaseUid(product.firestoreUid!!)
                    editProductViewModel.setCategory(productViewModel.productCategory.value!!)
                })

        editProductViewModel.images.observe(this, Observer { uris ->
            val options = RequestOptions()
            options.centerCrop().diskCacheStrategy(DiskCacheStrategy.ALL)
            Glide.with(activity!!)
                    .load(uris!!.get(uris.size -1))
                    .apply(options)
                    .into(edit_input_product_imageview)
        })

        edit_fab_addimage.setOnClickListener { view ->
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                RxPermissions(activity!!).request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        .subscribe { granted ->
                            if (!granted) {
                                Toast.makeText(activity, "Please grant permissions to select image", Toast.LENGTH_SHORT).show()
                            } else {
                                editProductViewModel.selectImages()
                            }
                        }
            }
        }

        edit_btn_update.setOnClickListener { view ->

            val productUpdateFields = HashMap<String, Any>()
            productUpdateFields.put("price", Integer.valueOf(input_edit_price_view.quantity!!.toString()))
            productUpdateFields.put("name", input_edit_product_name.text!!.toString())
            productUpdateFields.put("itemQuantity", Integer.valueOf(input_edit_stock_view.quantity!!.toString()))
            productUpdateFields.put("description", input_edit_product_description.text!!.toString())
            productUpdateFields.put("updatedAt", Date())

            editProductViewModel.updateProduct(productUpdateFields)
                    .subscribe({ newProduct ->
                        Toast.makeText(activity, "ProductEntity updated succesfully", Toast.LENGTH_SHORT).show()
                        activity!!.supportFragmentManager.popBackStack(EditProductFragment::class.java.simpleName, androidx.fragment.app.FragmentManager.POP_BACK_STACK_INCLUSIVE)
                    }) { throwable -> Toast.makeText(activity, "ProductEntity not updated", Toast.LENGTH_SHORT).show() }

        }

        edit_btn_cancell.setOnClickListener { view ->
            activity!!.supportFragmentManager.popBackStack(EditProductFragment::class.java.simpleName, androidx.fragment.app.FragmentManager.POP_BACK_STACK_INCLUSIVE)
        }
    }
}
