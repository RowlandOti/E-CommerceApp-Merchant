package com.rowland.delivery.merchant

import android.app.Activity
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.rowland.delivery.merchant.robots.auth.GoogleLoginRobot
import com.rowland.delivery.merchant.robots.auth.LoginRobot
import com.rowland.delivery.merchant.robots.auth.RegisterRobot
import java.util.concurrent.atomic.AtomicReference

/**
 * Created by rowlandoti on 31/01/2021
 *
 */

fun login(func: LoginRobot.() -> Unit) = LoginRobot()
    .apply { func() }

fun googleLogin(func: GoogleLoginRobot.() -> Unit) = GoogleLoginRobot()
    .apply { func() }

fun register(func: RegisterRobot.() -> Unit) = RegisterRobot()
    .apply { func() }

fun <T : Activity?> getActivity(activityScenarioRule: ActivityScenarioRule<T>): T {
    val activityRef: AtomicReference<T> = AtomicReference()
    activityScenarioRule.scenario.onActivity(activityRef::set)
    return activityRef.get()
}