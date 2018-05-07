package com.rowland.delivery.services.auth;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.WriteBatch;
import com.rowland.delivery.services.auth.models.Group;
import com.rowland.delivery.services.session.SessionManager;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import durdinapps.rxfirebase2.RxFirebaseAuth;
import durdinapps.rxfirebase2.RxFirebaseUser;
import durdinapps.rxfirebase2.RxFirestore;

/**
 * Created by Rowland on 5/2/2018.
 */

public class EmailAuth extends Auth {

    public static final String CRED_EMAIL_KEY = "email";
    public static final String CRED_PASSWORD_KEY = "password";


    private AuthConfig mAuthConfig;
    private FirebaseAuth mFirebaseAuth;
    private SessionManager mSessionManager;
    private FirebaseFirestore mFirebaseFirestone;

    @Inject
    public EmailAuth(AuthConfig authConfig, FirebaseAuth firebaseAuth, FirebaseFirestore firebaseFirestone, SessionManager sessionManager) {
        this.mAuthConfig = authConfig;
        this.mFirebaseAuth = firebaseAuth;
        this.mSessionManager = sessionManager;
        this.mFirebaseFirestone = firebaseFirestone;
    }

    @Override
    public void login() {
        Map<String, String> credentials = mAuthConfig.getCallback().doEmailLogin();
        String email = credentials.get(CRED_EMAIL_KEY);
        String password = credentials.get(CRED_PASSWORD_KEY);

        RxFirebaseAuth.signInWithEmailAndPassword(mFirebaseAuth, email, password)
                .subscribe(authResult -> {
                    FirebaseUser firebaseUser = authResult.getUser();
                    RxFirebaseUser.getIdToken(firebaseUser, true)
                            .subscribe(getTokenResult -> {
                        mSessionManager.setLogin(getTokenResult.getToken());
                        mAuthConfig.getCallback().onLoginSuccess();
                    });
                }, throwable -> {
                    mAuthConfig.getCallback().onLoginFailure(new AuthException(throwable.getMessage(), throwable));
                });
    }

    @Override
    public boolean logout(Context context) {
        try {
            mSessionManager.logout();
            return true;

        } catch (Exception e) {
            Log.e("Logout Failed", e.getMessage());
            return false;
        }
    }

    @Override
    public void register() {
        Map<String, String> credentials = mAuthConfig.getCallback().doEmailRegister();
        String email = credentials.get(CRED_EMAIL_KEY);
        String password = credentials.get(CRED_PASSWORD_KEY);

        RxFirebaseAuth.createUserWithEmailAndPassword(mFirebaseAuth, email, password)
                .subscribe(authResult -> {
                    FirebaseUser firebaseUser = authResult.getUser();
                    configureUserAccount(firebaseUser);
                }, throwable -> {
                    mAuthConfig.getCallback().onLoginFailure(new AuthException(throwable.getMessage(), throwable));
                });

    }

    private void configureUserAccount(final FirebaseUser firebaseUser) {

        WriteBatch batch = mFirebaseFirestone.batch();

        CollectionReference usersCollectionRef = mFirebaseFirestone.collection("users");
        batch.set(usersCollectionRef.document(firebaseUser.getUid()), firebaseUser);

        CollectionReference groupUsersCollectionRef = mFirebaseFirestone.collection("groups");
        Map<String, Object> member = new HashMap<>();
        member.put(String.format("members.%s", firebaseUser.getUid()), true);
        batch.update(groupUsersCollectionRef.document(Group.MERCHANT), member);

        RxFirestore.atomicOperation(batch).subscribe(() -> {
            RxFirebaseUser.getIdToken(firebaseUser, true).subscribe(getTokenResult -> {
                mSessionManager.setLogin(getTokenResult.getToken());
                mAuthConfig.getCallback().onLoginSuccess();
                Toast.makeText(mAuthConfig.getActivity(), "Account Setup Successful", Toast.LENGTH_SHORT).show();
            });
        }, throwable -> {
            mAuthConfig.getCallback().onLoginFailure(new AuthException(throwable.getMessage(), throwable));
            Toast.makeText(mAuthConfig.getActivity(), "Account Setup Unsuccessful", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    @Override
    public void passwordReset() {

    }

}
