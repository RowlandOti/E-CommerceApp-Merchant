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

package com.rowland.delivery.merchant.features.auth.ui

import android.Manifest
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.filters.MediumTest
import androidx.test.rule.GrantPermissionRule
import com.google.firebase.auth.FirebaseAuth
import com.rowland.delivery.merchant.R
import com.rowland.delivery.merchant.features.auth.di.modules.AuthModule
import com.rowland.delivery.merchant.features.auth.di.modules.FakeAuthModule
import com.rowland.delivery.merchant.getActivity
import com.rowland.delivery.merchant.googleLogin
import com.rowland.delivery.merchant.login
import com.rowland.delivery.merchant.register
import com.rowland.delivery.merchant.spoon
import com.squareup.spoon.SpoonRule
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import io.mockk.*
import org.junit.*

@HiltAndroidTest
@UninstallModules(AuthModule::class)
@MediumTest
class AuthActivityTest {

    @get:Rule(order = 1)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 2)
    val spoonRule = SpoonRule()

    @get:Rule(order = 3)
    var activityScenarioRule: ActivityScenarioRule<AuthActivity> = activityScenarioRule()

    @get:Rule(order = 4)
    var runtimePermissionRule: GrantPermissionRule = GrantPermissionRule.grant(
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE
    )

    private lateinit var activity: AuthActivity

    @Before
    fun setup() {
        hiltRule.inject()
        activity = activityScenarioRule.scenario.getActivity()
    }

    @After
    fun tearDown() {
        activityScenarioRule.scenario.close()
    }

    @Test
    fun onLoginEmptyEmailAndPasswordFails() {
        login {
            spoon(spoonRule, activity, "auth_login_empty_credentials") {
                clickLogin()
                matchToastText(activity.getString(R.string.login_unsuccessful), activity.window.decorView)
            }
        }
    }

    @Test
    fun onLoginEmptyEmailFails() {
        login {
            spoon(spoonRule, activity, "auth_login_empty_email") {
                setPassword(FakeAuthModule.USER_PASS)
                clickLogin()
                matchToastText(activity.getString(R.string.login_unsuccessful), activity.window.decorView)
            }
        }
    }

    @Test
    fun onLoginEmptyPasswordFails() {
        login {
            spoon(spoonRule, activity, "auth_login_empty_password") {
                setEmail(FakeAuthModule.USER_EMAIL)
                clickLogin()
                matchToastText(activity.getString(R.string.login_unsuccessful), activity.window.decorView)
            }
        }
    }

    @Test
    fun onLoginWrongPasswordFails() {
        login {
            spoon(spoonRule, activity, "auth_login_wrong_password") {
                setEmail(FakeAuthModule.USER_EMAIL)
                setPassword(FakeAuthModule.WRONG_USER_PASS)
                clickLogin()
                matchToastText(activity.getString(R.string.login_successful), activity.window.decorView)
            }
        }
    }

    @Test
    fun onLoginWrongEmailFails() {
        login {
            spoon(spoonRule, activity, "auth_login_wrong_email") {
                setEmail(FakeAuthModule.WRONG_USER_EMAIL)
                setPassword(FakeAuthModule.USER_PASS)
                clickLogin()
                matchToastText(activity.getString(R.string.login_successful), activity.window.decorView)
            }
        }
    }

    @Test
    fun onLoginSuccess() {
        mockkStatic(FirebaseAuth::class)
        every { FirebaseAuth.getInstance() } returns mockk(relaxed = true)

        login {
            spoon(spoonRule, activity, "auth_login_success") {
                setEmail(FakeAuthModule.USER_EMAIL)
                setPassword(FakeAuthModule.USER_PASS)
                clickLogin()
                matchToastText(activity.getString(R.string.login_successful), activity.window.decorView)
            }
        }
    }

    @Test
    fun onGoogleLoginSuccess() {
        googleLogin {
            spoon(spoonRule, activity, "auth_google_login_success") {
                clickGoogleLogin()
                matchToastText(activity.getString(R.string.login_successful), activity.window.decorView)
            }
        }
    }

    @Test
    fun onRegisterEmptyEmailFails() {
        register {
            spoon(spoonRule, activity, "auth_register_empty_password") {
                setPassword(FakeAuthModule.USER_PASS)
                clickRegister()
                matchToastText(activity.getString(R.string.login_unsuccessful), activity.window.decorView)
            }
        }
    }

    @Test
    fun onRegisterEmptyPasswordFails() {
        register {
            spoon(spoonRule, activity, "auth_register_empty_password") {
                setEmail(FakeAuthModule.USER_EMAIL)
                clickRegister()
                matchToastText(activity.getString(R.string.login_unsuccessful), activity.window.decorView)
            }
        }
    }
}