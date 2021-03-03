package com.rowland.delivery.merchant.features.auth.ui

import android.app.Activity
import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.rowland.delivery.merchant.R
import com.rowland.delivery.merchant.databinding.ActivityAuthBinding
import com.rowland.delivery.merchant.features.auth.Auth
import com.rowland.delivery.merchant.features.auth.AuthException
import com.rowland.delivery.merchant.features.auth.EmailAuth
import com.rowland.delivery.merchant.features.dash.activities.DashActivity
import dagger.hilt.android.AndroidEntryPoint
import java.util.HashMap
import javax.inject.Inject
import javax.inject.Named

@AndroidEntryPoint
class AuthActivity : AppCompatActivity(), Auth.AuthLoginCallbacks {

    @Inject
    @Named("google_login")
    lateinit var mGoogleAuth: Auth

    @Inject
    @Named("email_login")
    lateinit var mEmailAuth: Auth

    private lateinit var binding: ActivityAuthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Glide.with(this)
            .load(R.drawable.flash)
            .into(binding.splashImageview)

        binding.authContent.apply {
            btnGoogleLogin.setOnClickListener { mGoogleAuth.login() }
            btnLogin.setOnClickListener { mEmailAuth.login() }
            btnRegister.setOnClickListener { mEmailAuth.register() }
            txtResetPassword.setOnClickListener { mEmailAuth.login() }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        mGoogleAuth.onActivityResult(requestCode, resultCode, data!!)
    }

    override fun onLoginSuccess() {
        Toast.makeText(this, getString(R.string.login_successful), Toast.LENGTH_SHORT).show()
        DashActivity.startActivity(this)
    }

    override fun onLoginFailure(e: AuthException) {
        Toast.makeText(this, getString(R.string.login_unsuccessful), Toast.LENGTH_SHORT).show()
    }

    override fun doEmailLogin(): Map<String, String> {
        val credentialsMap = HashMap<String, String>()
        credentialsMap[EmailAuth.CRED_EMAIL_KEY] = binding.authContent.inputEmail.text.toString()
        credentialsMap[EmailAuth.CRED_PASSWORD_KEY] = binding.authContent.inputPassword.text.toString()
        return credentialsMap
    }

    override fun doEmailRegister(): Map<String, String> {
        val credentialsMap = HashMap<String, String>()
        credentialsMap[EmailAuth.CRED_EMAIL_KEY] = binding.authContent.inputEmail.text.toString()
        credentialsMap[EmailAuth.CRED_PASSWORD_KEY] = binding.authContent.inputPassword.text.toString()
        return credentialsMap
    }

    companion object {

        fun startActivity(context: Context) {
            val intent = Intent(context, AuthActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NO_HISTORY
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
