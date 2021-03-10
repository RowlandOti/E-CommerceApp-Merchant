package com.rowland.delivery.merchant.robots.auth

import com.rowland.delivery.merchant.R
import com.rowland.delivery.merchant.robots.BaseScreenRobot

/**
 * Created by rowlandoti on 31/01/2021
 */

class GoogleLoginRobot : BaseScreenRobot<GoogleLoginRobot>() {

    fun clickGoogleLogin() = performClickOnView(R.id.btn_google_login)
}
