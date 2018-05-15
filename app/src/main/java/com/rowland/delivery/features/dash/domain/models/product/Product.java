package com.rowland.delivery.features.dash.domain.models.product;

import android.support.annotation.NonNull;

import com.google.firebase.firestore.Exclude;
import com.rowland.delivery.features.dash.domain.models.FirestoreModel;

import java.util.Date;

/**
 * Created by Rowland on 5/13/2018.
 */

public class Product implements FirestoreModel {

    private Integer id;
    private String name;
    private String description;
    private Integer price;
    private String itemCode;
    private String merchantCode;
    private Integer itemQuantity;
    private Integer unitsOfMeasure;
    private Integer saleQuantity;
    private String imageUrl;
    private Date createdAt;
    private Date updatedAt;
    private Date deletedAt;

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

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Integer getPrice() {
        return price;
    }

    public String getItemCode() {
        return itemCode;
    }

    public String getMerchantCode() {
        return merchantCode;
    }

    public Integer getItemQuantity() {
        return itemQuantity;
    }

    public Integer getUnitsOfMeasure() {
        return unitsOfMeasure;
    }

    public Integer getSaleQuantity() {
        return saleQuantity;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public Date getDeletedAt() {
        return deletedAt;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public void setMerchantCode(String merchantCode) {
        this.merchantCode = merchantCode;
    }

    public void setItemQuantity(Integer itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    public void setUnitsOfMeasure(Integer unitsOfMeasure) {
        this.unitsOfMeasure = unitsOfMeasure;
    }

    public void setSaleQuantity(Integer saleQuantity) {
        this.saleQuantity = saleQuantity;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setDeletedAt(Date deletedAt) {
        this.deletedAt = deletedAt;
    }

    public void setFirestoreUid(String firestoreUid) {
        this.firestoreUid = firestoreUid;
    }
}
