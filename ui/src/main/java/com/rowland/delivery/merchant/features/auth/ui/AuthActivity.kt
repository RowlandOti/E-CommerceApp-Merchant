package com.rowland.delivery.merchant.features.auth.ui

import android.app.Activity
import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.rowland.delivery.merchant.R
import com.rowland.delivery.merchant.R.id.input_email
import com.rowland.delivery.merchant.R.id.input_password
import com.rowland.delivery.merchant.di.modules.ContextModule
import com.rowland.delivery.merchant.features.auth.Auth
import com.rowland.delivery.merchant.features.auth.AuthException
import com.rowland.delivery.merchant.features.auth.EmailAuth
import com.rowland.delivery.merchant.features.auth.di.components.DaggerAuthComponent
import com.rowland.delivery.merchant.features.auth.di.modules.AuthModule
import com.rowland.delivery.merchant.features.dash.activities.DashActivity
import kotlinx.android.synthetic.main.activity_auth.*
import kotlinx.android.synthetic.main.content_auth.*
import java.util.*
import javax.inject.Inject
import javax.inject.Named


class AuthActivity : AppCompatActivity(), Auth.AuthLoginCallbacks, GoogleApiClient.OnConnectionFailedListener {

    @Inject
    @field:Named("google_login")
    lateinit var mGoogleAuth: Auth

    @Inject
    @field:Named("email_login")
    lateinit var mEmailAuth: Auth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        val authComponent = DaggerAuthComponent.builder()
                .contextModule(ContextModule(this))
                .authModule(AuthModule())
                .build()

        authComponent.injectAuthActivity(this)

        Glide.with(this)
                .load(R.drawable.flash)
                .into(splash_imageview!!)

        btn_google_login.setOnClickListener { mGoogleAuth!!.login() }
        btn_login.setOnClickListener { mEmailAuth!!.login() }
        btn_register.setOnClickListener { mEmailAuth!!.register() }
        txt_reset_password.setOnClickListener { mEmailAuth!!.login() }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        mGoogleAuth!!.onActivityResult(requestCode, resultCode, data!!)
    }

    override fun onLoginSuccess() {
        Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()
        DashActivity.startActivity(this)
    }

    override fun onLoginFailure(e: AuthException) {
        Toast.makeText(this, "Login Unsuccessful", Toast.LENGTH_SHORT).show()
    }

    override fun doEmailLogin(): Map<String, String> {
        val credentialsMap = HashMap<String, String>()
        credentialsMap[EmailAuth.CRED_EMAIL_KEY] = input_email!!.text.toString()
        credentialsMap[EmailAuth.CRED_PASSWORD_KEY] = input_password!!.text.toString()
        return credentialsMap
    }

    override fun doEmailRegister(): Map<String, String> {
        val credentialsMap = HashMap<String, String>()
        credentialsMap[EmailAuth.CRED_EMAIL_KEY] = input_email!!.text.toString()
        credentialsMap[EmailAuth.CRED_PASSWORD_KEY] = input_password!!.text.toString()
        return credentialsMap
    }

    override fun onConnectionFailed(connectionResult: ConnectionResult) {

    }

    companion object {

        fun startActivity(context: Context) {
            val intent = Intent(context, AuthActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NO_HISTORY
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
