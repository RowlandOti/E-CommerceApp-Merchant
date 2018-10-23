package com.rowland.delivery.services.auth.models;

import androidx.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Rowland on 5/2/2018.
 */

public class Group {

    @Retention(RetentionPolicy.SOURCE)
    @StringDef({MERCHANT, CLIENT})
    @interface GroupName {

    }

    public static final String MERCHANT = "merchant";
    public static final String CLIENT = "client";

    @GroupName
    public String name;


}
