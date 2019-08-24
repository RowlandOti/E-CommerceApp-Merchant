package com.rowland.delivery.merchant.features.auth.ui

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.rule.ActivityTestRule
import com.rowland.delivery.merchant.R
import com.rowland.delivery.merchant.espressoDaggerMockRule
import com.rowland.delivery.merchant.features.auth.EmailAuth
import com.squareup.spoon.SpoonRule
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito


class AuthActivityTest {

    @get:Rule
    val rule = espressoDaggerMockRule()
    @get:Rule
    val spoon = SpoonRule()
    @get:Rule
    val authActivityRule: ActivityTestRule<AuthActivity> = ActivityTestRule(AuthActivity::class.java)

    @Test
    fun onCreate() {
        spoon.screenshot(authActivityRule.activity, "auth_screen")

        authActivityRule.activity.mEmailAuth = Mockito.mock(EmailAuth::class.java)

        Espresso.onView(withId(R.id.input_email)).perform(replaceText(USER_EMAIL));
        Espresso.onView(withId(R.id.input_password)).perform(replaceText(USER_PASS));
        Espresso.onView(withId(R.id.btn_login)).perform(click())

        Mockito.verify(authActivityRule.activity.mEmailAuth).login()

        spoon.screenshot(authActivityRule.activity, "auth_login");
    }

   /* @Test
    fun onActivityResult() {
    }

    @Test
    fun onLoginSuccess() {
    }

    @Test
    fun onLoginFailure() {

    }*/

    companion object {
        val USER_EMAIL = "test@delivery.com"
        val USER_PASS = "qsWercjdGg"
    }
}