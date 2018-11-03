package com.rowland.delivery.merchant.features.dash.fragments

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.dekoservidoni.omfm.OneMoreFabMenu
import com.rowland.delivery.merchant.R
import com.rowland.delivery.merchant.features.dash.activities.DashActivity
import com.rowland.delivery.merchant.features.dash.adapters.OrderItemAdapter
import com.rowland.delivery.presentation.model.order.OrderDataModel
import com.rowland.delivery.presentation.viewmodels.order.OrderViewModel
import kotlinx.android.synthetic.main.fragment_order_item.*
import java.util.*
import javax.inject.Inject

/**
 * Created by Rowland on 5/7/2018.
 */
class OrderItemFragment : Fragment(), OneMoreFabMenu.OptionsClick {

    private lateinit var orderViewModel: OrderViewModel
    private var orderData: OrderDataModel? = null

    @Inject
    lateinit var adapter: OrderItemAdapter

    @Inject
    lateinit var orderFactory: ViewModelProvider.Factory

    companion object {
        private val CALL_PERMISSION = 100

        fun newInstance(args: Bundle?): OrderItemFragment {
            val frag = OrderItemFragment()
            frag.arguments = args
            return frag
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        orderViewModel = ViewModelProviders.of(activity!!, orderFactory).get(OrderViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_order_item, container, false)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        (activity as DashActivity).dashComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity).setSupportActionBar(orderitem_toolbar)

        ordereditem_recyclerview.setLayoutManager(androidx.recyclerview.widget.LinearLayoutManager(activity))
        ordereditem_recyclerview.setItemAnimator(androidx.recyclerview.widget.DefaultItemAnimator())
        ordereditem_recyclerview.setAdapterWithProgress(adapter)

        action_call_btn.setOnClickListener({ view ->
            makeCall()
        })

        orderitem_fab.setOptionsClick(this)

        orderViewModel.getSelectedListItem()
                .observe(this, Observer { orderData ->
                    this.orderData = orderData
                    setupData()
                })
    }

    override fun onOptionClick(optionId: Int?) {
        when (optionId!!) {
            R.id.action_pending -> changeStatus("pending")
            R.id.action_complete -> changeStatus("completed")
            R.id.action_in_progress -> changeStatus("in_progress")
            R.id.action_canceled -> changeStatus("cancelled")
        }
        orderitem_fab.performClick()
    }


    fun makeCall() {
        if (ActivityCompat.checkSelfPermission(activity!!, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            val callIntent = Intent(Intent.ACTION_CALL)
            callIntent.data = Uri.parse(String.format("tel:%s", orderData!!.phone))
            startActivity(callIntent)
        } else {
            ActivityCompat.requestPermissions(activity!!, arrayOf(Manifest.permission.CALL_PHONE), CALL_PERMISSION)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (requestCode == CALL_PERMISSION) {
            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                makeCall()
            } else {
                Toast.makeText(activity, "Please grant permission to call", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun changeTextStatus(s: String) {
        status.text = s
        when (s) {
            "active" -> status.setTextColor(Color.parseColor("#378E3D"))
            "delivered" -> status.setTextColor(Color.parseColor("#FF6D00"))
            "failed" -> status.setTextColor(Color.parseColor("#B94464"))
            "missed" -> status.setTextColor(Color.parseColor("#B94464"))
            "completed" -> status.setTextColor(Color.parseColor("#AAAAAA"))
            "cancelled" -> status.setTextColor(Color.parseColor("#B94464"))
            "in_progress" -> status.setTextColor(Color.parseColor("#378E3D"))
            "pending" -> status.setTextColor(Color.parseColor("#B94464"))
            else -> status.setTextColor(Color.parseColor("#AAAAAA"))
        }
    }

    fun changeStatus(status: String) {
        val orderUpdateFields = HashMap<String, Any>()
        orderUpdateFields.put("status", status)
        orderViewModel.updateOrder(orderUpdateFields)
                .subscribe({ Toast.makeText(activity, "Order Status Updated Successfull", Toast.LENGTH_SHORT).show() }) { throwable -> Toast.makeText(activity, "Update Failed", Toast.LENGTH_SHORT).show() }
    }


    fun setupData() {
        customer_name.text = orderData!!.name
        changeTextStatus(orderData!!.status!!)

        phone.text = orderData!!.phone
        email.text = "Email not provided"

        if (orderData!!.address!!.isEmpty()) {
            address.text = "Address not provided"
        } else {
            address.text = orderData!!.address
        }

        order_ref.text = orderData!!.orderRef
        all_items.text = "items (" + orderData!!.orderItemsQuantity + ")"
        total_item_price.text = "Ksh " + orderData!!.orderSubTotal
        delivery_fee.text = "Ksh " + orderData!!.deliveryFee!!
        total_price.text = "Ksh " + orderData!!.orderTotal!!

        adapter.setList(orderData!!.items!!)
        adapter.notifyDataSetChanged()
    }
}
