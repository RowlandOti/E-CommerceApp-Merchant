package com.rowland.delivery.merchant.features.auth.ui

import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.rowland.delivery.merchant.R
import com.rowland.delivery.merchant.features.auth.di.modules.AuthModule
import com.rowland.delivery.merchant.login
import com.squareup.spoon.SpoonRule
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.*
import org.junit.runner.*

@HiltAndroidTest
@UninstallModules(AuthModule::class)
class AuthActivityTest {

    @get:Rule(order=1)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order=2)
    val spoon = SpoonRule()

    @get:Rule(order=3)
    var activityScenarioRule = activityScenarioRule<AuthActivity>()


    @Before
    fun setup() {
        hiltRule.inject()
    }

    @After
    fun tearDown() {
        activityScenarioRule.scenario.close()
    }

    @Test
    fun onLoginMissingEmailAndPasswordFails() {
        activityScenarioRule.scenario.onActivity { activity ->
            login {
                clickLogin()
                spoon.screenshot(activity, "auth_login_missing_credentials");
                matchToastText(activity.getString(R.string.login_unsuccessful), activity.window.decorView)
            }
        }
    }

    @Test
    fun onLoginMissingEmailFails() {
        activityScenarioRule.scenario.onActivity { activity ->
            login {
                setPassword("1234")
                clickLogin()
                spoon.screenshot(activity, "auth_login_missing_email");
                matchToastText(activity.getString(R.string.login_unsuccessful), activity.window.decorView)
            }
        }
    }

    @Test
    fun onLoginMissingPasswordFails() {
        activityScenarioRule.scenario.onActivity { activity ->
            login {
                setEmail("mail@example.com")
                clickLogin()
                spoon.screenshot(activity, "auth_login_missing_password");
                matchToastText(activity.getString(R.string.login_unsuccessful), activity.window.decorView)
            }
        }
    }

    @Test
    fun onLoginWrongPasswordFails() {
        activityScenarioRule.scenario.onActivity { activity ->
            login {
                setEmail("mail@example.com")
                setPassword("wrong")
                clickLogin()
                spoon.screenshot(activity, "auth_login_wrong_password");
                matchToastText(activity.getString(R.string.login_successful), activity.window.decorView)
            }
        }
    }

    @Test
    fun onLoginSuccess() {
        activityScenarioRule.scenario.onActivity { activity ->
            login {
                setEmail(USER_EMAIL)
                setPassword(USER_PASS)
                clickLogin()
                spoon.screenshot(activity, "auth_login_success");
                matchToastText(activity.getString(R.string.login_successful), activity.window.decorView)
            }
        }
    }

    companion object {

        val USER_EMAIL = "test@delivery.com"
        val USER_PASS = "qsWercjdGg"
    }
}