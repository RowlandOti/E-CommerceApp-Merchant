/*
 * Copyright (c) Otieno Rowland,  2021. All rights reserved.
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
import android.annotation.SuppressLint
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
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.dekoservidoni.omfm.OneMoreFabMenu
import com.rowland.delivery.merchant.R
import com.rowland.delivery.merchant.R.string
import com.rowland.delivery.merchant.databinding.FragmentOrderItemBinding
import com.rowland.delivery.merchant.features.dash.adapters.OrderItemAdapter
import com.rowland.delivery.presentation.viewmodels.order.OrderViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.HashMap
import javax.inject.Inject

/**
 * Created by Rowland on 5/7/2018.
 */

@AndroidEntryPoint
class OrderItemFragment : Fragment(), OneMoreFabMenu.OptionsClick {

    private val orderViewModel: OrderViewModel by viewModels()
    private val args by navArgs<OrderItemFragmentArgs>()

    @Inject
    lateinit var adapter: OrderItemAdapter

    private var _binding: FragmentOrderItemBinding? = null
    private val binding get() = _binding!!

    companion object {

        private const val CALL_PERMISSION = 100

        fun newInstance(args: Bundle?): OrderItemFragment {
            val frag = OrderItemFragment()
            frag.arguments = args
            return frag
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentOrderItemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ordereditemRecyclerview.setLayoutManager(LinearLayoutManager(activity))
        binding.ordereditemRecyclerview.setItemAnimator(DefaultItemAnimator())
        binding.ordereditemRecyclerview.setAdapterWithProgress(adapter)

        setupData()

        binding.actionCallBtn.setOnClickListener { makeCall() }
        binding.orderitemFab.setOptionsClick(this)
    }

    override fun onOptionClick(optionId: Int?) {
        when (optionId!!) {
            R.id.action_pending -> changeStatus("pending")
            R.id.action_complete -> changeStatus("completed")
            R.id.action_in_progress -> changeStatus("in_progress")
            R.id.action_canceled -> changeStatus("cancelled")
        }
        binding.orderitemFab.performClick()
    }

    private fun makeCall() {
        if (ActivityCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.CALL_PHONE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            val callIntent = Intent(Intent.ACTION_CALL)
            callIntent.data = Uri.parse(String.format("tel:%s", args.orderDataItem.phone))
            startActivity(callIntent)
        } else {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.CALL_PHONE),
                CALL_PERMISSION
            )
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (requestCode == CALL_PERMISSION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                makeCall()
            } else {
                Toast.makeText(activity, getString(string.permision_to_call_msg), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun changeTextStatus(s: String) {
        val status = binding.status
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

    @SuppressLint("CheckResult")
    private fun changeStatus(status: String) {
        val orderUpdateFields = HashMap<String, Any>()
        orderUpdateFields["status"] = status
        orderViewModel.updateOrder(orderUpdateFields, args.orderDataItem.firestoreUid!!)
            .subscribe({
                Toast.makeText(activity, getString(string.order_status_update_success), Toast.LENGTH_SHORT).show()
            }) {
                Toast.makeText(
                    activity,
                    getString(
                        string.order_status_update_failed
                    ),
                    Toast.LENGTH_SHORT
                ).show()
            }
    }

    private fun setupData() {
        (activity as AppCompatActivity?)?.supportActionBar?.let { it.subtitle = args.orderDataItem.orderRef }
        binding.customerName.text = args.orderDataItem.name
        changeTextStatus(args.orderDataItem.status!!)

        binding.phone.text = args.orderDataItem.phone
        binding.email.text = getString(string.msg_email_unprovided)

        if (args.orderDataItem.address!!.isEmpty()) {
            binding.address.text = getString(string.msg_address_unprovided)
        } else {
            binding.address.text = args.orderDataItem.address
        }

        binding.allItems.text = getString(string.items_label_with_value_num, args.orderDataItem.orderItemsQuantity)
        binding.totalItemPrice.text = getString(string.ksh_label_with_value_str, args.orderDataItem.orderSubTotal)
        binding.deliveryFee.text = getString(string.ksh_label_with_value_num, args.orderDataItem.deliveryFee!!)
        binding.totalPrice.text = getString(string.ksh_label_with_value_num, args.orderDataItem.orderTotal!!)

        adapter.setList(args.orderDataItem.items!!)
        adapter.notifyDataSetChanged()
    }
}
