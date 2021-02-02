package com.rowland.delivery.merchant.features.dash.fragments

import android.Manifest
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.storage.FirebaseStorage
import com.rowland.delivery.merchant.R.string
import com.rowland.delivery.merchant.databinding.FragmentEditProductBinding
import com.rowland.delivery.merchant.features.dash.activities.DashActivity
import com.rowland.delivery.presentation.viewmodels.product.EditProductViewModel
import com.rowland.delivery.presentation.viewmodels.product.ProductViewModel
import com.rowland.rxgallery.RxGallery
import com.tbruyelle.rxpermissions2.RxPermissions
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.Date
import java.util.HashMap
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

    private var _binding: FragmentEditProductBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        productViewModel = ViewModelProviders.of(activity!!, productFactory).get(ProductViewModel::class.java)
        editProductViewModel = ViewModelProviders.of(this, productFactory).get(EditProductViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentEditProductBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as DashActivity).dashComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).setSupportActionBar(binding.editproductToolbar)

        productViewModel.getSelectedListItem()
            .observe(this, Observer { product ->
                binding.inputEditPriceView.quantity = product!!.price!!
                binding.inputEditStockView.quantity = product.itemQuantity!!
                binding.inputEditProductName.setText(product.name)
                binding.inputEditProductDescription.setText(product.description)
                val options = RequestOptions()
                FirebaseStorage.getInstance().reference.child(product.imageUrls.get(0)).downloadUrl
                    .addOnSuccessListener { uri ->
                        options.centerCrop().diskCacheStrategy(DiskCacheStrategy.ALL)
                        Glide.with(activity!!)
                            .load(uri)
                            .apply(options)
                            .into(binding.editInputProductImageview)
                    }

                editProductViewModel.setProductUid(product.firestoreUid!!)
                editProductViewModel.setCategory(productViewModel.category.value!!)
            })

        editProductViewModel.images.observe(this, Observer { uris ->
            val options = RequestOptions()
            options.centerCrop().diskCacheStrategy(DiskCacheStrategy.ALL)
            Glide.with(activity!!)
                .load(uris!!.get(uris.size - 1))
                .apply(options)
                .into(binding.editInputProductImageview)
        })

        binding.editFabAddimage.setOnClickListener { _ ->
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                RxPermissions(activity!!).request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    .subscribe { granted ->
                        if (!granted) {
                            Toast.makeText(activity, "Please grant permissions to select image", Toast.LENGTH_SHORT)
                                .show()
                        } else {
                            RxGallery.gallery(activity!!, true, RxGallery.MimeType.IMAGE)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(
                                    { uris ->
                                        editProductViewModel.setImageUri(uris)
                                        editProductViewModel.setImagesIsSelected(true)
                                    },
                                    { throwable ->
                                        Log.d(
                                            EditProductViewModel::class.java.simpleName,
                                            "Error Selecting Images: $throwable"
                                        )
                                    }) {
                                    Log.d(EditProductViewModel::class.java.simpleName, "Done Selecting Images")
                                }
                        }
                    }
            }
        }

        binding.editBtnUpdate.setOnClickListener { _ ->

            val productUpdateFields = HashMap<String, Any>()
            productUpdateFields.put("price", Integer.valueOf(binding.inputEditPriceView.quantity.toString()))
            productUpdateFields.put("name", binding.inputEditProductName.text!!.toString())
            productUpdateFields.put("itemQuantity", Integer.valueOf(binding.inputEditStockView.quantity.toString()))
            productUpdateFields.put("description", binding.inputEditProductDescription.text!!.toString())
            productUpdateFields.put("updatedAt", Date())

            editProductViewModel.updateProduct(productUpdateFields)
                .subscribe({
                    Toast.makeText(activity, getString(string.product_update_success), Toast.LENGTH_SHORT).show()
                    activity!!.supportFragmentManager.popBackStack(
                        EditProductFragment::class.java.simpleName,
                        FragmentManager.POP_BACK_STACK_INCLUSIVE
                    )

                }, {
                    Toast.makeText(activity, getString(string.product_update_failure), Toast.LENGTH_SHORT).show()
                })
        }

        binding.editBtnCancell.setOnClickListener {
            activity!!.supportFragmentManager.popBackStack(
                EditProductFragment::class.java.simpleName,
                FragmentManager.POP_BACK_STACK_INCLUSIVE
            )
        }
    }
}
