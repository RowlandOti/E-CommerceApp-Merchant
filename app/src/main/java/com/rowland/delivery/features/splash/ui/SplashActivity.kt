package com.rowland.delivery.features.splash.ui

import android.app.Activity
import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import butterknife.ButterKnife
import butterknife.Unbinder
import com.bumptech.glide.Glide
import com.rowland.delivery.features.auth.ui.AuthActivity
import com.rowland.delivery.features.dash.presentation.activities.DashActivity
import com.rowland.delivery.features.splash.di.components.DaggerSplashComponent
import com.rowland.delivery.merchant.R
import com.rowland.delivery.merchant.application.di.modules.ContextModule
import com.rowland.delivery.services.session.SessionManager
import com.rowland.delivery.services.session.di.modules.SessionModule
import com.rowland.delivery.utilities.ScreenUtils
import kotlinx.android.synthetic.main.content_splash.*
import javax.inject.Inject

class SplashActivity : AppCompatActivity() {
    lateinit var unbinder: Unbinder

    @Inject
    lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        unbinder = ButterKnife.bind(this)

        val splashComponent = DaggerSplashComponent.builder()
                .contextModule(ContextModule(this))
                .sessionModule(SessionModule())
                .build()

        splashComponent.injectSplashActivity(this)

        ScreenUtils.makeFullScreen(this)
        ScreenUtils.changeStatusBarColor(this)

        Glide.with(this)
                .load(R.drawable.flash)
                .into(splash_splash_imageview!!)

        val handler = Handler()
        handler.postDelayed({
            if (!sessionManager!!.isLoggedIn) {
                AuthActivity.startActivity(this@SplashActivity)
                finish()
            } else {
                DashActivity.startActivity(this@SplashActivity)
                finish()
            }
            overridePendingTransition(0, 0)
        }, SPLASH_DISPLAY_LENGTH.toLong())
    }

    override fun onDestroy() {
        super.onDestroy()
        unbinder!!.unbind()
    }

    companion object {

        private val SPLASH_DISPLAY_LENGTH = 500

        fun startActivity(context: Context) {
            val intent = Intent(context, SplashActivity::class.java)
            context.startActivity(intent)
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                val bundle = ActivityOptions.makeSceneTransitionAnimation(context as Activity).toBundle()
                context.startActivity(intent, bundle)
            } else {
                context.startActivity(intent)
            }
        }
    }
}