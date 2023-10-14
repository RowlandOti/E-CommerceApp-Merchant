/*
 * Copyright (c) Otieno Rowland,  2021. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.rowland.delivery.merchant

import android.app.Activity
import android.os.Build
import androidx.test.core.app.ActivityScenario
import com.rowland.delivery.merchant.robots.auth.GoogleLoginRobot
import com.rowland.delivery.merchant.robots.auth.LoginRobot
import com.rowland.delivery.merchant.robots.auth.RegisterRobot
import com.squareup.spoon.SpoonRule
import java.util.concurrent.atomic.AtomicReference

/**
 * Created by rowlandoti on 31/01/2021
 *
 */

inline fun login(func: LoginRobot.() -> Unit) = LoginRobot()
    .apply { func() }

inline fun googleLogin(func: GoogleLoginRobot.() -> Unit) = GoogleLoginRobot()
    .apply { func() }

inline fun register(func: RegisterRobot.() -> Unit) = RegisterRobot()
    .apply { func() }

inline fun spoon(spoonRule: SpoonRule, activity: Activity, message: String, func: () -> Unit) {
    func()
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
        spoonRule.screenshot(activity, message)
    }
}

fun <T : Activity?> ActivityScenario<T>.getActivity(): T {
    val activityRef: AtomicReference<T> = AtomicReference()
    onActivity(activityRef::set)
    return activityRef.get()
}
