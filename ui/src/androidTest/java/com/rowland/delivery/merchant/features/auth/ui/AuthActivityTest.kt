package com.rowland.delivery.merchant.features.auth.ui

import org.junit.Test
import org.junit.Rule

import androidx.test.rule.ActivityTestRule;
import com.squareup.spoon.SpoonRule


class AuthActivityTest {

    @get:Rule
    val spoon = SpoonRule()
    @get:Rule
    val authActivityRule: ActivityTestRule<AuthActivity> = ActivityTestRule(AuthActivity::class.java)

    @Test
    fun onCreate() {
        spoon.screenshot(authActivityRule.getActivity(), "auth_screen");
    }

    @Test
    fun onActivityResult() {
    }

    @Test
    fun onLoginSuccess() {
    }
}