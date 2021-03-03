package com.rowland.delivery.merchant.robots.auth

import com.rowland.delivery.merchant.R
import com.rowland.delivery.merchant.robots.BaseScreenRobot

/**
 * Created by rowlandoti on 31/01/2021
 */

class RegisterRobot : BaseScreenRobot<RegisterRobot>() {

    fun setEmail(email: String) = enterTextIntoView(R.id.input_email, email)

    fun setPassword(pass: String) = enterTextIntoView(R.id.input_password, pass)

    fun clickRegister() = performClickOnView(R.id.btn_register)
}
