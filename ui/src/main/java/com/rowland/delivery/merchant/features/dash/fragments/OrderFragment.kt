package com.rowland.delivery.merchant.features.dash.fragments


import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.rowland.delivery.merchant.R
import com.rowland.delivery.merchant.features.dash.activities.DashActivity
import com.rowland.delivery.merchant.features.dash.adapters.OrderDataAdapter
import com.rowland.delivery.merchant.features.dash.tools.recylerview.RecyclerItemClickListener
import com.rowland.delivery.presentation.data.ResourceState
import com.rowland.delivery.presentation.model.order.OrderDataModel
import com.rowland.delivery.presentation.viewmodels.order.OrderViewModel
import kotlinx.android.synthetic.main.fragment_orders.*
import javax.inject.Inject

/**
 * Created by Rowland on 5/7/2018.
 */
class OrderFragment : Fragment() {

    private lateinit var orderViewModel: OrderViewModel

    @Inject
    lateinit var orderFactory: ViewModelProvider.Factory

    @Inject
    lateinit var orderAdapter: OrderDataAdapter

    companion object {
        fun newInstance(args: Bundle?): androidx.fragment.app.Fragment {
            val frag = OrderFragment()
            frag.arguments = args
            return frag
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        orderViewModel = ViewModelProviders.of(activity!!, orderFactory).get(OrderViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_orders, container, false)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        (activity as DashActivity).dashComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val linearLayoutManager = LinearLayoutManager(activity)
        orders_recyclerview.setLayoutManager(linearLayoutManager)
        orders_recyclerview.setItemAnimator(DefaultItemAnimator())
        orders_recyclerview.setAdapterWithProgress(orderAdapter)

        orders_recyclerview.addOnItemTouchListener(RecyclerItemClickListener(activity, orders_recyclerview.recyclerView, object : RecyclerItemClickListener.OnItemClickListener {
            override fun onItemClick(view: View, position: Int) {
                orderViewModel.setSelectedListItem(position)
            }

            override fun onItemLongClick(view: View, position: Int) {}
        }))


        orderViewModel.getDataList()
                .observe(this, Observer { orders -> handleDataState(orders.status, orders.data, orders.message) })


/*
        FirebaseFirestore.getInstance()
                .collection("orderdata")
                .add(DummyDataUtility.createOrderData())
                .addOnSuccessListener({ documentReference ->
                    Toast.makeText(getActivity(), "OrderData saved", Toast.LENGTH_SHORT)
                            .show();
                });*/
    }

    private fun handleDataState(resourceState: ResourceState, data: List<OrderDataModel>?, message: String?) {
        when (resourceState) {
            ResourceState.LOADING -> orders_recyclerview.showProgress()
            ResourceState.SUCCESS -> {
                orders_recyclerview.showRecycler()
                orderAdapter.setList(data!!)
            }
            ResourceState.ERROR -> {
                orders_recyclerview.showError()
                Log.d(OrderFragment::class.java.simpleName, message)
            }
        }
    }


}
