package com.rowland.delivery.merchant.features.splash.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.rowland.delivery.merchant.R
import com.rowland.delivery.merchant.databinding.ActivitySplashBinding
import com.rowland.delivery.merchant.features.auth.ui.AuthActivity
import com.rowland.delivery.merchant.features.dash.activities.DashActivity
import com.rowland.delivery.merchant.services.session.SessionManager
import com.rowland.delivery.merchant.utilities.ScreenUtils
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    @Inject
    lateinit var sessionManager: SessionManager

    private lateinit var binding: ActivitySplashBinding

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ScreenUtils.makeFullScreen(window)
        ScreenUtils.changeStatusBarColor(window)

        Glide.with(this)
            .load(R.drawable.flash)
            .into(binding.splashContent.splashSplashImageview)


        Completable.timer(SPLASH_DISPLAY_LENGTH.toLong(), TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
            .subscribe {
                if (!sessionManager.isLoggedIn) {
                    AuthActivity.startActivity(this@SplashActivity)
                    finish()
                } else {
                    DashActivity.startActivity(this@SplashActivity)
                    finish()
                }
                overridePendingTransition(0, 0)
            }
    }

    companion object {

        private const val SPLASH_DISPLAY_LENGTH = 200

        fun startActivity(context: Context) {
            val intent = Intent(context, SplashActivity::class.java)
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                val bundle = ActivityOptions.makeSceneTransitionAnimation(context as Activity).toBundle()
                context.startActivity(intent, bundle)
            } else {
                context.startActivity(intent)
            }
            (context as Activity).finish()
        }
    }
}
