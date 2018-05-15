package com.rowland.delivery.features.dash.domain.models.order;

import java.util.Date;
import java.util.List;

/**
 * Created by Rowland on 5/8/2018.
 */

public class Order {

    private Integer id;
    private String orderRef;
    private String status;
    private Date createdAt;
    private Date updatedAt;
    private String itemIds;
    private String phone;
    private String name;
    private String address;
    private String lng;
    private String lat;
    private String orderDescription;
    private List<OrderItem> items = null;
    private Integer deliveryFee;
    private Integer orderItemsQuantity;
    private String orderSubTotal;
    private Integer orderTotal;

    public Integer getId() {
        return id;
    }

    public String getOrderRef() {
        return orderRef;
    }

    public String getStatus() {
        return status;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public String getItemIds() {
        return itemIds;
    }

    public String getPhone() {
        return phone;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getLng() {
        return lng;
    }

    public String getLat() {
        return lat;
    }

    public String getOrderDescription() {
        return orderDescription;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public Integer getDeliveryFee() {
        return deliveryFee;
    }

    public Integer getOrderItemsQuantity() {
        return orderItemsQuantity;
    }

    public String getOrderSubTotal() {
        return orderSubTotal;
    }

    public Integer getOrderTotal() {
        return orderTotal;
    }
}
