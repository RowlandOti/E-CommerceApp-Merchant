package com.rowland.delivery.merchant.features.dash.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import com.google.firebase.auth.FirebaseAuth
import com.rowland.delivery.merchant.R
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

    private var _binding: FragmentProductsBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        productViewModel = ViewModelProviders.of(requireActivity(), productFactory).get(ProductViewModel::class.java)

        if (arguments != null) {
            val category = requireArguments().getString("selected_category")
            productViewModel.loadProducts(category!!, FirebaseAuth.getInstance().currentUser!!.uid)
        }
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

        (activity as AppCompatActivity).setSupportActionBar(binding.toolbarProduct)

        if (arguments != null) {
            val category = requireArguments().getString("selected_category")
            binding.productCategory.text = category
        }

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

        binding.productRecyclerview.addOnItemTouchListener(
            RecyclerItemClickListener(
                activity,
                binding.productRecyclerview.recyclerView,
                object : RecyclerItemClickListener.OnItemClickListener {
                    override fun onItemClick(view: View, position: Int) {
                        productViewModel.setSelectedListItem(position)
                    }

                    override fun onItemLongClick(view: View, position: Int) {}
                })
        )

        binding.fab.setOnClickListener { _ ->
            var args: Bundle? = null
            if (arguments != null) {
                args = Bundle()
                args.putString("selected_category", requireArguments().getString("selected_category"))
            }
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.dash_container_body, NewProductFragment.Companion.newInstance(args))
                .addToBackStack(NewProductFragment::class.java.simpleName)
                .commit()
        }

        productViewModel.getDataList()
            .observe(viewLifecycleOwner, { products ->
                handleDataState(products.status, products.data, products.message)
            })
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
