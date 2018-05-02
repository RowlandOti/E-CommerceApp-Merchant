package com.rowland.delivery.services.auth;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.WriteBatch;
import com.rowland.delivery.services.auth.models.GoogleUser;
import com.rowland.delivery.services.auth.models.Group;
import com.rowland.delivery.services.auth.models.utils.UserUtils;
import com.rowland.delivery.services.session.SessionManager;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

/**
 * Created by Rowland on 5/1/2018.
 */

public class GoogleAuth extends Auth {

    private static final int RC_SIGN_IN = 7001;

    private AuthConfig mAuthConfig;
    private FirebaseAuth mFirebaseAuth;
    private SessionManager mSessionManager;
    private FirebaseFirestore mFirebaseFirestone;

    @Inject
    public GoogleAuth(AuthConfig authConfig, FirebaseAuth firebaseAuth, FirebaseFirestore firebaseFirestone, SessionManager sessionManager) {
        this.mAuthConfig = authConfig;
        this.mFirebaseAuth = firebaseAuth;
        this.mSessionManager = sessionManager;
        this.mFirebaseFirestone = firebaseFirestone;
    }

    @Override
    public void login() {
        GoogleApiClient googleApiClient = mAuthConfig.getGoogleApiClient();

        Intent signInIntent = com.google.android.gms.auth.api.Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        mAuthConfig.getActivity().startActivityForResult(signInIntent, RC_SIGN_IN);
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

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RC_SIGN_IN && resultCode == Activity.RESULT_OK) {
            GoogleSignInResult result = com.google.android.gms.auth.api.Auth.GoogleSignInApi.getSignInResultFromIntent(data);

            GoogleSignInAccount acct = result.getSignInAccount();
            GoogleUser googleUser = UserUtils.populateGoogleUser(acct);

            firebaseAuthWithGoogle(googleUser, mAuthConfig.getActivity());
        }
    }

    private void firebaseAuthWithGoogle(final GoogleUser user, Activity activity) {
        AuthCredential credential = GoogleAuthProvider.getCredential(user.getIdToken(), null);
        mFirebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();
                            user.setUserId(firebaseUser.getUid());
                            configureUserAccount(user);
                        }
                    }
                });
    }

    private void configureUserAccount(final GoogleUser user) {
        WriteBatch batch = mFirebaseFirestone.batch();

        CollectionReference usersCollectionRef = mFirebaseFirestone.collection("users");
        batch.set(usersCollectionRef.document(user.getUserId()), user);

        CollectionReference groupUsersCollectionRef = mFirebaseFirestone.collection("groups");
        Map<String, Object> groupUsers = new HashMap<>();
        groupUsers.put(user.getUserId(), true);
        batch.set(groupUsersCollectionRef.document(Group.MERCHANT)
                .collection("members")
                .document(user.getUserId()), groupUsers);

        batch.commit().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    mSessionManager.setLogin(user.getIdToken());
                    mAuthConfig.getCallback().onLoginSuccess();
                    Toast.makeText(mAuthConfig.getActivity(), "Account Setup Successful", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
