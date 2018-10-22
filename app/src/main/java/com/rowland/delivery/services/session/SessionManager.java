package com.rowland.delivery.services.session;

import com.rowland.delivery.services.sharedpreferences.SharedPreferencesManager;
import com.rowland.delivery.utilities.AppConstants;

import java.util.Date;

import javax.inject.Inject;

/**
 * Created by Rowland on 9/11/17.
 */

public class SessionManager {

    private SharedPreferencesManager preferencesManager;

    @Inject
    public SessionManager(SharedPreferencesManager preferencesManager) {
        this.preferencesManager = preferencesManager;
    }

    public void setLogin(String token) {
        long time_log = new Date().getTime();

        preferencesManager.putBoolean(AppConstants.INSTANCE.getKEY_IS_LOGGEDIN(), true)
                .putString(AppConstants.INSTANCE.getKEY_TOKEN(), token)
                .putLong(AppConstants.INSTANCE.getKEY_TIME(), time_log);
    }

    public boolean isLoggedIn() {
        return preferencesManager.getBoolean(AppConstants.INSTANCE.getKEY_IS_LOGGEDIN());
    }

    public String getAuthToken() {
        return preferencesManager.getString(AppConstants.INSTANCE.getKEY_TOKEN());
    }

    public void logout() {
        preferencesManager.remove(AppConstants.INSTANCE.getKEY_IS_LOGGEDIN())
                .remove(AppConstants.INSTANCE.getKEY_TOKEN())
                .remove(AppConstants.INSTANCE.getKEY_TIME());
    }

    public boolean shouldLogout() {
        long diffMSec = new Date().getTime() - preferencesManager.getLong(AppConstants.INSTANCE.getKEY_TIME());
        int diffHours = (int) (diffMSec / (1000 * 60 * 60));

        if (diffHours >= 23) {
            preferencesManager.putBoolean(AppConstants.INSTANCE.getKEY_IS_LOGGEDIN(), false)
                    .putString(AppConstants.INSTANCE.getKEY_TOKEN(), "")
                    .putLong(AppConstants.INSTANCE.getKEY_TIME(), 0);

            return true;
        } else {
            return false;
        }
    }
}
