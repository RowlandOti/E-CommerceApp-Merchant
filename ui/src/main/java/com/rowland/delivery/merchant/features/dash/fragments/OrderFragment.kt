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
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.rowland.delivery.merchant.databinding.FragmentOrdersBinding
import com.rowland.delivery.merchant.features.dash.adapters.OrderDataAdapter
import com.rowland.delivery.merchant.features.dash.tools.recylerview.RecyclerItemClickListener
import com.rowland.delivery.presentation.data.ResourceState
import com.rowland.delivery.presentation.model.order.OrderDataModel
import com.rowland.delivery.presentation.viewmodels.order.OrderViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * Created by Rowland on 5/7/2018.
 */

@AndroidEntryPoint
class OrderFragment : Fragment() {

    @Inject
    lateinit var orderAdapter: OrderDataAdapter

    private val orderViewModel: OrderViewModel by viewModels({ requireParentFragment() })
    private var _binding: FragmentOrdersBinding? = null
    private val binding get() = _binding!!

    companion object {

        const val REQUEST_ORDER_SELECTED = "order_selected_"
        const val ORDER = "order_"

        fun newInstance(args: Bundle?): Fragment {
            val frag = OrderFragment()
            frag.arguments = args
            return frag
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        orderViewModel.loadOrders(FirebaseAuth.getInstance().currentUser!!.uid)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentOrdersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val linearLayoutManager = LinearLayoutManager(activity)
        binding.ordersRecyclerview.setLayoutManager(linearLayoutManager)
        binding.ordersRecyclerview.setItemAnimator(DefaultItemAnimator())
        binding.ordersRecyclerview.setAdapterWithProgress(orderAdapter)

        binding.ordersRecyclerview.apply {
            addOnItemTouchListener(
                RecyclerItemClickListener(
                    activity,
                    this.recyclerView,
                    object : RecyclerItemClickListener.OnItemClickListener {
                        override fun onItemClick(view: View, position: Int) {
                            orderViewModel.setSelectedListItem(position)

                            setFragmentResult(
                                REQUEST_ORDER_SELECTED,
                                bundleOf(
                                    ORDER to
                                        orderViewModel.getSelectedListItem().value!!
                                )
                            )
                        }

                        override fun onItemLongClick(view: View, position: Int) {}
                    }
                )
            )
        }

        orderViewModel.getDataList()
            .observe(
                viewLifecycleOwner,
                { orders -> handleDataState(orders.status, orders.data, orders.message) }
            )

/*
        FirebaseFirestore.getInstance()
                .collection("orderdata")
                .add(DummyDataUtility.createOrderData())
                .addOnSuccessListener({ documentReference ->
                    Toast.makeText(getActivity(), "OrderData saved", Toast.LENGTH_SHORT)
                            .show();
                });*/
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun handleDataState(resourceState: ResourceState, data: List<OrderDataModel>?, message: String?) {
        when (resourceState) {
            ResourceState.LOADING -> binding.ordersRecyclerview.showProgress()
            ResourceState.SUCCESS -> {
                binding.ordersRecyclerview.showRecycler()
                orderAdapter.setList(data!!)
            }
            ResourceState.ERROR -> {
                binding.ordersRecyclerview.showError()
                message?.let { Log.d(OrderFragment::class.java.simpleName, it) }
            }
        }
    }
}
