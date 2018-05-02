package com.rowland.delivery.services.auth.models.utils;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseUser;
import com.rowland.delivery.services.auth.models.GoogleUser;
import com.rowland.delivery.services.auth.models.User;

/**
 * Created by Rowland on 5/2/2018.
 */

public class UserUtils {
    public static GoogleUser populateGoogleUser(GoogleSignInAccount account) {

        GoogleUser googleUser = new GoogleUser();

        googleUser.setDisplayName(account.getDisplayName());
        if (account.getPhotoUrl() != null) {
            googleUser.setPhotoUrl(account.getPhotoUrl().toString());
        }
        googleUser.setEmail(account.getEmail());
        googleUser.setIdToken(account.getIdToken());
        googleUser.setUserId(account.getId());
        if (account.getAccount() != null) {
            googleUser.setUsername(account.getAccount().name);
        }

        return googleUser;
    }
}
