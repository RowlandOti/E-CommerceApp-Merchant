package com.rowland.delivery.merchant.features.auth.ui

import androidx.test.ext.junit.rules.activityScenarioRule
import com.rowland.delivery.merchant.R
import com.rowland.delivery.merchant.features.auth.FakeAuthModule
import com.rowland.delivery.merchant.features.auth.di.modules.AuthModule
import com.rowland.delivery.merchant.getActivity
import com.rowland.delivery.merchant.googleLogin
import com.rowland.delivery.merchant.login
import com.rowland.delivery.merchant.register
import com.squareup.spoon.SpoonRule
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.*

@HiltAndroidTest
@UninstallModules(AuthModule::class)
class AuthActivityTest {

    @get:Rule(order = 1)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 2)
    val spoon = SpoonRule()

    @get:Rule(order = 3)
    var activityScenarioRule = activityScenarioRule<AuthActivity>()

    private lateinit var activity: AuthActivity

    @Before
    fun setup() {
        hiltRule.inject()
        activity = getActivity(activityScenarioRule)
    }

    @Test
    fun onLoginEmptyEmailAndPasswordFails() {
        login {
            dismissKeyboard()
            clickLogin()
            spoon.screenshot(activity, "auth_login_Empty_credentials");
            matchToastText(activity.getString(R.string.login_unsuccessful), activity.window.decorView)
        }
    }

    @Test
    fun onLoginEmptyEmailFails() {
        login {
            setPassword(FakeAuthModule.USER_PASS)
            clickLogin()
            spoon.screenshot(activity, "auth_login_Empty_email");
            matchToastText(activity.getString(R.string.login_unsuccessful), activity.window.decorView)
        }
    }

    @Test
    fun onLoginEmptyPasswordFails() {
        login {
            setEmail(FakeAuthModule.USER_EMAIL)
            clickLogin()
            spoon.screenshot(activity, "auth_login_empty_password");
            matchToastText(activity.getString(R.string.login_unsuccessful), activity.window.decorView)
        }
    }

    @Test
    fun onLoginWrongPasswordFails() {
        login {
            setEmail(FakeAuthModule.USER_EMAIL)
            setPassword(FakeAuthModule.WRONG_USER_PASS)
            clickLogin()
            spoon.screenshot(activity, "auth_login_wrong_password")
            matchToastText(activity.getString(R.string.login_successful), activity.window.decorView)
        }
    }

    @Test
    fun onLoginWrongEmailFails() {
        login {
            setEmail(FakeAuthModule.WRONG_USER_EMAIL)
            setPassword(FakeAuthModule.USER_PASS)
            clickLogin()
            spoon.screenshot(activity, "auth_login_wrong_password")
            matchToastText(activity.getString(R.string.login_successful), activity.window.decorView)
        }
    }

    @Test
    fun onLoginSuccess() {
        login {
            setEmail(FakeAuthModule.USER_EMAIL)
            setPassword(FakeAuthModule.USER_PASS)
            clickLogin()
            spoon.screenshot(activity, "auth_login_success");
            matchToastText(activity.getString(R.string.login_successful), activity.window.decorView)
        }
    }

    @Test
    fun onGoogleLoginSuccess() {
        googleLogin {
            clickGoogleLogin()
            spoon.screenshot(activity, "auth_google_login_success");
            matchToastText(activity.getString(R.string.login_successful), activity.window.decorView)
        }
    }

    @Test
    fun onRegisterEmptyEmailFails() {
        register {
            setPassword(FakeAuthModule.USER_PASS)
            clickRegister()
            spoon.screenshot(activity, "auth_register_empty_password");
            matchToastText(activity.getString(R.string.login_unsuccessful), activity.window.decorView)
        }
    }

    @Test
    fun onRegisterEmptyPasswordFails() {
        register {
            setEmail(FakeAuthModule.USER_EMAIL)
            clickRegister()
            spoon.screenshot(activity, "auth_register_empty_password");
            matchToastText(activity.getString(R.string.login_unsuccessful), activity.window.decorView)
        }
    }
}