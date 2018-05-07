package com.rowland.delivery.features.splash.ui;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.rowland.delivery.features.auth.ui.AuthActivity;
import com.rowland.delivery.features.dash.presentation.activities.DashActivity;
import com.rowland.delivery.features.splash.di.components.DaggerSplashComponent;
import com.rowland.delivery.features.splash.di.components.SplashComponent;
import com.rowland.delivery.merchant.R;
import com.rowland.delivery.merchant.application.di.modules.ContextModule;
import com.rowland.delivery.services.session.SessionManager;
import com.rowland.delivery.services.session.di.modules.SessionModule;
import com.rowland.delivery.utilities.ScreenUtils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class SplashActivity extends AppCompatActivity {

    private static final int SPLASH_DISPLAY_LENGTH = 500;
    private Unbinder unbinder;

    @BindView(R.id.splash_imageview)
    ImageView splash;

    @Inject
    SessionManager sessionManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        unbinder = ButterKnife.bind(this);

        SplashComponent splashComponent = DaggerSplashComponent.builder()
                .contextModule(new ContextModule(this))
                .sessionModule(new SessionModule())
                .build();

        splashComponent.injectSplashActivity(this);

        ScreenUtils.makeFullScreen(this);
        ScreenUtils.changeStatusBarColor(this);

        Glide.with(this)
                .load(R.drawable.flash)
                .into(splash);

        Handler handler = new Handler();
        handler.postDelayed(() -> {
            if (!sessionManager.isLoggedIn()) {
                AuthActivity.startActivity(SplashActivity.this);
                finish();
            } else {
                DashActivity.startActivity(SplashActivity.this);
                finish();
            }
            overridePendingTransition(0, 0);
        }, SPLASH_DISPLAY_LENGTH);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
