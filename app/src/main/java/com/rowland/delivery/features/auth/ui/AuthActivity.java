package com.rowland.delivery.features.auth.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.rowland.delivery.features.auth.di.components.AuthComponent;
import com.rowland.delivery.features.auth.di.components.DaggerAuthComponent;
import com.rowland.delivery.merchant.R;
import com.rowland.delivery.merchant.application.di.modules.ContextModule;
import com.rowland.delivery.services.auth.Auth;
import com.rowland.delivery.services.auth.AuthException;
import com.rowland.delivery.services.auth.EmailAuth;
import com.rowland.delivery.services.auth.GoogleAuth;
import com.rowland.delivery.services.auth.di.modules.AuthModule;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.rowland.delivery.services.auth.EmailAuth.CRED_EMAIL_KEY;
import static com.rowland.delivery.services.auth.EmailAuth.CRED_PASSWORD_KEY;

public class AuthActivity extends AppCompatActivity implements Auth.AuthLoginCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private Unbinder unbinder;

    @Inject
    @Named("google_login")
    Auth mGoogleAuth;

    @Inject
    @Named("email_login")
    Auth mEmailAuth;

    @BindView(R.id.splash_imageview)
    ImageView splash;

    @BindView(R.id.input_email)
    EditText input_email;

    @BindView(R.id.email_textinput_layout)
    TextInputLayout email_textinput_layout;

    @BindView(R.id.input_password)
    EditText input_password;

    @BindView(R.id.passord_textinput_layout)
    TextInputLayout passord_textinput_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        unbinder = ButterKnife.bind(this);

        AuthComponent authComponent = DaggerAuthComponent.builder()
                .contextModule(new ContextModule(this))
                .authModule(new AuthModule())
                .build();

        authComponent.injectAuthActivity(this);

        Glide.with(this)
                .load(R.drawable.flash)
                .into(splash);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mGoogleAuth.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onLoginSuccess() {

    }

    @Override
    public void onLoginFailure(AuthException e) {

    }

    @Override
    public Map<String, String> doEmailLogin() {
        Map<String, String> credentialsMap = new HashMap<>();
        credentialsMap.put(CRED_EMAIL_KEY, input_email.getText().toString());
        credentialsMap.put(CRED_PASSWORD_KEY, input_password.getText().toString());
        return credentialsMap;
    }

    @Override
    public Map<String, String> doEmailRegister() {
        Map<String, String> credentialsMap = new HashMap<>();
        credentialsMap.put(CRED_EMAIL_KEY, input_email.getText().toString());
        credentialsMap.put(CRED_PASSWORD_KEY, input_password.getText().toString());
        return credentialsMap;
    }

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, AuthActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @OnClick(R.id.btn_google_login)
    public void onGoogleLogin() {
        mGoogleAuth.login();
    }

    @OnClick(R.id.btn_login)
    public void onLogin() {
        mEmailAuth.login();
    }

    @OnClick(R.id.btn_register)
    public void onRegister() {
        mEmailAuth.register();
    }
}
