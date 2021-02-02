package com.rowland.delivery.merchant.robots

import com.rowland.delivery.merchant.R
import com.rowland.delivery.merchant.BaseTestRobot

/**
 * Created by rowlandoti on 31/01/2021
 */

class LoginRobot : BaseTestRobot() {

    fun setEmail(email: String) = fillEditText(R.id.input_email, email);

    fun setPassword(pass: String) = fillEditText(R.id.input_password, pass)

    fun clickLogin() = clickButton(R.id.btn_login)

    fun matchErrorText(err: String) = matchText(textView(android.R.id.message), err)

}