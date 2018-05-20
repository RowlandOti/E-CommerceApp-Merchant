package com.rowland.delivery.features.dash.presentation.fragments


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rowland.delivery.features.dash.di.modules.order.OrderModule
import com.rowland.delivery.features.dash.presentation.activities.DashActivity
import com.rowland.delivery.features.dash.presentation.adapters.OrderDataAdapter
import com.rowland.delivery.features.dash.presentation.tools.recylerview.RecyclerItemClickListener
import com.rowland.delivery.features.dash.presentation.viewmodels.ViewEvent
import com.rowland.delivery.features.dash.presentation.viewmodels.order.OrderViewModel
import com.rowland.delivery.merchant.R
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
        fun newInstance(args: Bundle?): Fragment {
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
        (activity as DashActivity).dashComponent.getOrderComponent(OrderModule()).inject(this)
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

        orderViewModel.state.observe(this, Observer {
            when (it!!.event) {
                is ViewEvent.Success -> it.event.handle {
                    /*Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT)
                            .show();*/
                }
                is ViewEvent.Error -> it.event.handle {
                    /*Toast.makeText(getActivity(), "Error Occured", Toast.LENGTH_SHORT)
                            .show();*/
                    orders_recyclerview.showError()
                }
            }
        })


        orderViewModel.getDataList()
                .observe(this, Observer { orders -> orderAdapter.setList(orders) })


/*
        FirebaseFirestore.getInstance()
                .collection("orderdata")
                .add(DummyDataUtility.createOrderData())
                .addOnSuccessListener({ documentReference ->
                    Toast.makeText(getActivity(), "OrderData saved", Toast.LENGTH_SHORT)
                            .show();
                });*/
    }


}
