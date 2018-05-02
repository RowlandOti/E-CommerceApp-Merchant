package com.rowland.delivery.services.auth;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.WriteBatch;
import com.rowland.delivery.services.auth.models.Group;
import com.rowland.delivery.services.session.SessionManager;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

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
        final String email = credentials.get(CRED_EMAIL_KEY);
        String password = credentials.get(CRED_PASSWORD_KEY);

        mFirebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(mAuthConfig.getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        task.addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                final FirebaseUser firebaseUser = authResult.getUser();

                                firebaseUser.getIdToken(true).addOnSuccessListener(new OnSuccessListener<GetTokenResult>() {
                                    @Override
                                    public void onSuccess(GetTokenResult getTokenResult) {

                                        mSessionManager.setLogin(getTokenResult.getToken());
                                        mAuthConfig.getCallback().onLoginSuccess();
                                        Toast.makeText(mAuthConfig.getActivity(), "Login Successful", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        });
                    }
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
        final String email = credentials.get(CRED_EMAIL_KEY);
        String password = credentials.get(CRED_PASSWORD_KEY);

        mFirebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(mAuthConfig.getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        task.addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                FirebaseUser firebaseUser = authResult.getUser();
                                configureUserAccount(firebaseUser);
                            }
                        });
                    }
                });
    }

    private void configureUserAccount(final FirebaseUser firebaseUser) {
        WriteBatch batch = mFirebaseFirestone.batch();

        CollectionReference usersCollectionRef = mFirebaseFirestone.collection("users");
        batch.set(usersCollectionRef.document(firebaseUser.getUid()), firebaseUser);

        CollectionReference groupUsersCollectionRef = mFirebaseFirestone.collection("groups");
        Map<String, Object> groupUsers = new HashMap<>();
        groupUsers.put(firebaseUser.getUid(), true);
        batch.set(groupUsersCollectionRef.document(Group.MERCHANT)
                .collection("members")
                .document(firebaseUser.getUid()), groupUsers);

        batch.commit().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    firebaseUser.getIdToken(true).addOnSuccessListener(new OnSuccessListener<GetTokenResult>() {
                        @Override
                        public void onSuccess(GetTokenResult getTokenResult) {
                            mSessionManager.setLogin(getTokenResult.getToken());
                            mAuthConfig.getCallback().onLoginSuccess();
                            Toast.makeText(mAuthConfig.getActivity(), "Account Setup Successful", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }
}
