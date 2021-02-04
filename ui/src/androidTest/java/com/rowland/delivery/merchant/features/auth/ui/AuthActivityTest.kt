package com.rowland.delivery.merchant.features.auth.ui

import androidx.test.rule.ActivityTestRule
import com.google.firebase.auth.FirebaseAuth
import com.rowland.delivery.merchant.R
import com.rowland.delivery.merchant.getString
import com.rowland.delivery.merchant.login
import com.squareup.spoon.SpoonRule
import org.junit.*

class AuthActivityTest {

    @get:Rule
    val spoon = SpoonRule()

    @get:Rule
    val authActivityRule: ActivityTestRule<AuthActivity> = ActivityTestRule(AuthActivity::class.java)

    @After
    fun tearDown() {

    }

    @Test
    fun onLoginMissingEmailAndPasswordFails() {
        login {
            clickLogin()
            spoon.screenshot(authActivityRule.activity, "auth_login_missing_credentials");
            matchToastText(getString(R.string.login_unsuccessful), authActivityRule.activity.window.decorView)
        }
    }

    @Test
    fun onLoginMissingEmailFails() {
        login {
            setPassword("1234")
            clickLogin()
            spoon.screenshot(authActivityRule.activity, "auth_login_missing_email");
            matchToastText(getString(R.string.login_unsuccessful), authActivityRule.activity.window.decorView)
        }
    }

    @Test
    fun onLoginMissingPasswordFails() {
        login {
            setEmail("mail@example.com")
            clickLogin()
            spoon.screenshot(authActivityRule.activity, "auth_login_missing_password");
            matchToastText(getString(R.string.login_unsuccessful), authActivityRule.activity.window.decorView)
        }
    }

    @Test
    fun onLoginWrongPasswordFails() {
        login {
            setEmail("mail@example.com")
            setPassword("wrong")
            clickLogin()
            spoon.screenshot(authActivityRule.activity, "auth_login_wrong_password");
            matchToastText(getString(R.string.login_successful), authActivityRule.activity.window.decorView)
        }
    }

    @Test
    fun onLoginSuccess() {
        login {
            setEmail(USER_EMAIL)
            setPassword(USER_PASS)
            clickLogin()
            spoon.screenshot(authActivityRule.activity, "auth_login_success");
            matchToastText(getString(R.string.login_successful), authActivityRule.activity.window.decorView)
        }
    }

    companion object {

        val USER_EMAIL = "test@delivery.com"
        val USER_PASS = "qsWercjdGg"
    }
}