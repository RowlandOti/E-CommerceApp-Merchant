package com.rowland.delivery.merchant.features.auth.models;

/**
 * Created by Rowland on 5/2/2018.
 */

public class GoogleUser extends User {

    private String displayName;
    private String photoUrl;

    public GoogleUser() {
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }
}
