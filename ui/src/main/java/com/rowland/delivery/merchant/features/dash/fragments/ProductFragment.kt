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

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DefaultItemAnimator
import com.google.firebase.auth.FirebaseAuth
import com.rowland.delivery.merchant.R.string
import com.rowland.delivery.merchant.databinding.FragmentProductsBinding
import com.rowland.delivery.merchant.features.dash.adapters.ProductAdapter
import com.rowland.delivery.merchant.features.dash.tools.recylerview.RecyclerItemClickListener
import com.rowland.delivery.presentation.data.ResourceState
import com.rowland.delivery.presentation.model.product.ProductModel
import com.rowland.delivery.presentation.viewmodels.product.ProductEvent
import com.rowland.delivery.presentation.viewmodels.product.ProductViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ProductFragment : Fragment() {

    private val productViewModel: ProductViewModel by activityViewModels()
    private val args by navArgs<ProductFragmentArgs>()

    @Inject
    lateinit var productAdapter: ProductAdapter

    companion object {

        fun newInstance(args: Bundle?): Fragment {
            val frag = ProductFragment()
            frag.arguments = args
            return frag
        }
    }

    private var _binding: FragmentProductsBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        productViewModel.loadProducts(args.selectedCategory, FirebaseAuth.getInstance().currentUser!!.uid)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentProductsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity?)?.supportActionBar?.let { it.subtitle = args.selectedCategory }

        val linearLayoutManager = androidx.recyclerview.widget.LinearLayoutManager(activity)
        binding.productRecyclerview.setLayoutManager(linearLayoutManager)
        binding.productRecyclerview.setItemAnimator(DefaultItemAnimator())
        binding.productRecyclerview.setAdapterWithProgress(productAdapter)

        if (savedInstanceState != null) {
            productAdapter.restoreStates(savedInstanceState)
        }

        productAdapter.setActionListener(object : ProductAdapter.ProductActionListener {
            override fun onProductActionListener(event: ProductEvent, fn: () -> Unit) {
                when (event) {
                    is ProductEvent.Edit -> {
                        fn()
                        findNavController(this@ProductFragment).navigate(
                            ProductFragmentDirections.actionProductFragmentToEditProductFragment(
                                event.productModel,
                                args.selectedCategory
                            )
                        )
                    }
                    is ProductEvent.UnPublish -> {
                        productViewModel.deleteProduct().subscribe({
                            fn()
                            Toast.makeText(
                                activity,
                                getString(string.product_successfuly_deleted),
                                Toast.LENGTH_SHORT
                            ).show()
                        }) {
                            Toast.makeText(
                                activity,
                                getString(string.product_could_not_be_deleted),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        })

        binding.productRecyclerview.addOnItemTouchListener(
            RecyclerItemClickListener(
                activity,
                binding.productRecyclerview.recyclerView,
                object : RecyclerItemClickListener.OnItemClickListener {
                    override fun onItemClick(view: View, position: Int) {
                        productViewModel.setSelectedListItem(position)
                    }

                    override fun onItemLongClick(view: View, position: Int) {}
                }
            )
        )

        binding.fab.setOnClickListener {
            findNavController(this@ProductFragment).navigate(
                ProductFragmentDirections.actionProductFragmentToNewProductFragment(
                    args.selectedCategory
                )
            )
        }

        productViewModel.getDataList()
            .observe(
                viewLifecycleOwner,
                { products ->
                    handleDataState(products.status, products.data, products.message)
                }
            )
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        productAdapter.saveStates(outState)
    }

    private fun handleDataState(resourceState: ResourceState, data: List<ProductModel>?, message: String?) {
        when (resourceState) {
            ResourceState.LOADING -> binding.productRecyclerview.showProgress()
            ResourceState.SUCCESS -> {
                binding.productRecyclerview.showRecycler()
                productAdapter.setList(data!!)
            }
            ResourceState.ERROR -> {
                binding.productRecyclerview.showError()
                message?.let { Log.d(OrderFragment::class.java.simpleName, it) }
            }
        }
    }
}
