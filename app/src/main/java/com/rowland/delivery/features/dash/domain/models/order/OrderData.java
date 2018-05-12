package com.rowland.delivery.features.dash.domain.models.order;

import android.support.annotation.NonNull;

import com.google.firebase.firestore.Exclude;
import com.rowland.delivery.features.dash.domain.models.FirestoreModel;

import java.util.List;

/**
 * Created by Rowland on 5/9/2018.
 */

public class OrderData implements FirestoreModel {

    private Integer id;
    private String orderRef;
    private String status;
    private String createdAt;
    private String updatedAt;
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

    @Exclude
    private String firestoreUid;

    @Override
    public <T extends FirestoreModel> T withFirestoreId(@NonNull String firestoreUid) {
        this.firestoreUid = firestoreUid;
        return (T) this;
    }

    public String getFirestoreUid() {
        return firestoreUid;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderRef() {
        return orderRef;
    }

    public void setOrderRef(String orderRef) {
        this.orderRef = orderRef;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getItemIds() {
        return itemIds;
    }

    public void setItemIds(String itemIds) {
        this.itemIds = itemIds;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getOrderDescription() {
        return orderDescription;
    }

    public void setOrderDescription(String orderDescription) {
        this.orderDescription = orderDescription;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }

    public Integer getDeliveryFee() {
        return deliveryFee;
    }

    public void setDeliveryFee(Integer deliveryFee) {
        this.deliveryFee = deliveryFee;
    }

    public Integer getOrderItemsQuantity() {
        return orderItemsQuantity;
    }

    public void setOrderItemsQuantity(Integer orderItemsQuantity) {
        this.orderItemsQuantity = orderItemsQuantity;
    }

    public String getOrderSubTotal() {
        return orderSubTotal;
    }

    public void setOrderSubTotal(String orderSubTotal) {
        this.orderSubTotal = orderSubTotal;
    }

    public Integer getOrderTotal() {
        return orderTotal;
    }

    public void setOrderTotal(Integer orderTotal) {
        this.orderTotal = orderTotal;
    }

    @Override
    public String toString() {
        return "OrderData{" +
                "id=" + id +
                ", orderRef='" + orderRef + '\'' +
                ", status='" + status + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", updatedAt='" + updatedAt + '\'' +
                ", itemIds='" + itemIds + '\'' +
                ", phone='" + phone + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", lng='" + lng + '\'' +
                ", lat='" + lat + '\'' +
                ", orderDescription='" + orderDescription + '\'' +
                ", items=" + items +
                ", deliveryFee=" + deliveryFee +
                ", orderItemsQuantity=" + orderItemsQuantity +
                ", orderSubTotal='" + orderSubTotal + '\'' +
                ", orderTotal=" + orderTotal +
                ", firestoreUid='" + firestoreUid + '\'' +
                '}';
    }
}
