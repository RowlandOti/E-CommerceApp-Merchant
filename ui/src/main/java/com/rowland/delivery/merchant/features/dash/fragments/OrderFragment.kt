package com.rowland.delivery.merchant.features.dash.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
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

    private val orderViewModel: OrderViewModel by activityViewModels()

    @Inject
    lateinit var orderAdapter: OrderDataAdapter

    private var _binding: FragmentOrdersBinding? = null
    private val binding get() = _binding!!

    companion object {

        fun newInstance(args: Bundle?): Fragment {
            val frag = OrderFragment()
            frag.arguments = args
            return frag
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        orderViewModel.loadOrders(FirebaseAuth.getInstance().currentUser!!.uid)
        Log.d("Hash-${OrderFragment::class.java.simpleName}", orderViewModel.toString())
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

        binding.ordersRecyclerview.addOnItemTouchListener(
            RecyclerItemClickListener(
                activity,
                binding.ordersRecyclerview.recyclerView,
                object : RecyclerItemClickListener.OnItemClickListener {
                    override fun onItemClick(view: View, position: Int) {
                        orderViewModel.setSelectedListItem(position)
                    }

                    override fun onItemLongClick(view: View, position: Int) {}
                })
        )


        orderViewModel.getDataList()
            .observe(
                viewLifecycleOwner,
                { orders -> handleDataState(orders.status, orders.data, orders.message) })

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
