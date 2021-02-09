package com.rowland.delivery.merchant

import androidx.test.InstrumentationRegistry.getContext
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.rowland.delivery.merchant.robots.LoginRobot

/**
 * Created by rowlandoti on 31/01/2021
 *
 */

fun login(func: LoginRobot.() -> Unit) = LoginRobot()
    .apply { func() }
