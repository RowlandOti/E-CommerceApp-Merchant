/*
 * Copyright 2021 Otieno Rowland
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

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.storage.FirebaseStorage
import com.rowland.delivery.merchant.R.string
import com.rowland.delivery.merchant.databinding.FragmentEditProductBinding
import com.rowland.delivery.presentation.viewmodels.product.EditProductViewModel
import com.rowland.rxgallery.RxGallery
import com.tbruyelle.rxpermissions2.RxPermissions
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.Date
import java.util.HashMap

@AndroidEntryPoint
class EditProductFragment : Fragment() {

    private val editProductViewModel: EditProductViewModel by viewModels()
    private val args by navArgs<EditProductFragmentArgs>()

    companion object {

        fun newInstance(args: Bundle?): Fragment {
            val frag = EditProductFragment()
            frag.arguments = args
            return frag
        }
    }

    private var _binding: FragmentEditProductBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentEditProductBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.inputEditPriceView.quantity = args.productItem.price!!
        binding.inputEditStockView.quantity = args.productItem.itemQuantity!!
        binding.inputEditProductName.setText(args.productItem.name)
        binding.inputEditProductDescription.setText(args.productItem.description)

        FirebaseStorage.getInstance().reference.child(args.productItem.imageUrls[0]).downloadUrl
            .addOnSuccessListener { uri ->
                val options = RequestOptions()
                options.centerCrop().diskCacheStrategy(DiskCacheStrategy.ALL)
                Glide.with(requireActivity())
                    .load(uri)
                    .apply(options)
                    .into(binding.editInputProductImageview)
            }

        editProductViewModel.setProductUid(args.productItem.firestoreUid!!)
        editProductViewModel.setCategory(args.productCategory)

        editProductViewModel.images.observe(
            viewLifecycleOwner,
            { uris ->
                val options = RequestOptions()
                options.centerCrop().diskCacheStrategy(DiskCacheStrategy.ALL)
                Glide.with(requireActivity())
                    .load(uris!![uris.size - 1])
                    .apply(options)
                    .into(binding.editInputProductImageview)
            }
        )

        binding.editFabAddimage.setOnClickListener { _ ->
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                RxPermissions(requireActivity()).request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    .subscribe { granted ->
                        if (!granted) {
                            Toast.makeText(
                                activity, getString(string.grant_permissions_gallery_msg),
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            RxGallery.gallery(requireActivity(), true, RxGallery.MimeType.IMAGE)
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
                                    }
                                ) {
                                    Log.d(EditProductViewModel::class.java.simpleName, "Done Selecting Images")
                                }
                        }
                    }
            }
        }

        binding.editBtnUpdate.setOnClickListener {

            val productUpdateFields = HashMap<String, Any>()
            productUpdateFields["price"] = Integer.valueOf(binding.inputEditPriceView.quantity.toString())
            productUpdateFields["name"] = binding.inputEditProductName.text!!.toString()
            productUpdateFields["itemQuantity"] = Integer.valueOf(binding.inputEditStockView.quantity.toString())
            productUpdateFields["description"] = binding.inputEditProductDescription.text!!.toString()
            productUpdateFields["updatedAt"] = Date()

            editProductViewModel.updateProduct(productUpdateFields)
                .subscribe(
                    {
                        Toast.makeText(activity, getString(string.product_update_success), Toast.LENGTH_SHORT).show()
                        findNavController().popBackStack()
                    },
                    {
                        Toast.makeText(activity, getString(string.product_update_failure), Toast.LENGTH_SHORT).show()
                    }
                )
        }

        binding.editBtnCancell.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}
