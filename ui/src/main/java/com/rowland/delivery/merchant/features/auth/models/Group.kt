package com.rowland.delivery.merchant.features.auth.models

import androidx.annotation.StringDef
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

/**
 * Created by Rowland on 5/2/2018.
 */

class Group {

    @GroupName
    var name: String? = null

    @Retention(RetentionPolicy.SOURCE)
    @StringDef(MERCHANT, CLIENT)
    internal annotation class GroupName

    companion object {

        const val MERCHANT = "merchant"
        const val CLIENT = "client"
    }


}
