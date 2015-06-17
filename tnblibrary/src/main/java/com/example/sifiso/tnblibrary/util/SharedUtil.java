package com.example.sifiso.tnblibrary.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.preference.PreferenceManager;
import android.util.Log;

import com.example.sifiso.tnblibrary.models.CommunitymemberDTO;
import com.google.gson.Gson;


/**
 * Created by Chris on 2015-02-20.
 */
public class SharedUtil {

    static final Gson gson = new Gson();

    public static final String
            MEMBER_JSON = "communityMember",
            EMAIL_JSON = "email",
            EVALUATION_IMAGE_ID = "evaluationImageID",
            EVALUATION_JSON = ".evaluation",
            EVALUATION_ID = "evaluationID",
            GCM_REGISTRATION_ID = "gcm",
            IMAGE_LOCATION = "imageLocation",
            LOG = "SharedUtil",
            SESSION_ID = "sessionID",
            REMINDER_TIME = "reminderTime",
            APP_VERSION = "appVersion";


    public static CommunitymemberDTO getCommunityMember (Context ctx) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(ctx);
        String st = sp.getString(MEMBER_JSON, null);
        CommunitymemberDTO tm = null;
        if (st != null) {
            tm = gson.fromJson(st, CommunitymemberDTO.class);
        }
        return tm;
    }

    public static void saveCommunityMember(Context ctx, CommunitymemberDTO evi) {

        SharedPreferences sp = PreferenceManager
                .getDefaultSharedPreferences(ctx);
        SharedPreferences.Editor ed = sp.edit();

        ed.putString(MEMBER_JSON, gson.toJson(evi));
        ed.commit();
        Log.e(LOG, "SharedUtil, LOCATION IMAGE: " + evi.getName() + " SAVED IN SharedPreferences");

    }
    public static void logoutCommunityMember(Context ctx) {

        SharedPreferences sp = PreferenceManager
                .getDefaultSharedPreferences(ctx);
        SharedPreferences.Editor ed = sp.edit();

        ed.remove(MEMBER_JSON);
        ed.commit();
        Log.e(LOG, "SharedUtil, LOCATION IMAGE: Log out " );

    }
    public static void storeRegistrationId(Context context, String regId) {
        final SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(context);
        int appVersion = getAppVersion(context);
        Log.i(LOG, "Saving regId on app version " + appVersion);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(GCM_REGISTRATION_ID, regId);
        editor.putInt(APP_VERSION, appVersion);
        editor.commit();
        Log.e(LOG, "GCM registrationId saved in prefs! Yebo!!!");
    }

    /**
     * Gets the current registration ID for application on GCM service.
     * <p/>
     * If result is empty, the app needs to register.
     *
     * @return registration ID, or empty string if there is no existing
     * registration ID.
     */
    public static String getEmail(Context context) {
        final SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(context);
        String email = prefs.getString(EMAIL_JSON, null);
        if (email == null) {
            Log.i(LOG, "GCM Registration ID not found on device.");
            return null;
        }

        return email;
    }

    public static void storeEmail(Context context, String email) {
        final SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(context);
        Log.i(LOG, "Saving regId on app version " + email);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(EMAIL_JSON, email);
        editor.commit();
        Log.e(LOG, "GCM registrationId saved in prefs! Yebo!!!");
    }

    /**
     * Gets the current registration ID for application on GCM service.
     * <p/>
     * If result is empty, the app needs to register.
     *
     * @return registration ID, or empty string if there is no existing
     * registration ID.
     */
    public static String getRegistrationId(Context context) {
        final SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(context);
        String registrationId = prefs.getString(GCM_REGISTRATION_ID, null);
        if (registrationId == null) {
            Log.i(LOG, "GCM Registration ID not found on device.");
            return null;
        }
        // Check if app was updated; if so, it must clear the registration ID
        // since the existing regID is not guaranteed to work with the new
        // app version.
        int registeredVersion = prefs.getInt(APP_VERSION, Integer.MIN_VALUE);
        int currentVersion = SharedUtil.getAppVersion(context);
        if (registeredVersion != currentVersion) {
            Log.i(LOG, "App version changed.");
            return null;
        }
        return registrationId;
    }

    public static void saveSessionID(Context ctx, String sessionID) {

        SharedPreferences sp = PreferenceManager
                .getDefaultSharedPreferences(ctx);
        SharedPreferences.Editor ed = sp.edit();
        ed.putString(SESSION_ID, sessionID);
        ed.commit();
        Log.e("SharedUtil", "%%%%% SessionID: " + sessionID + " saved in SharedPreferences");
    }

    public static int getAppVersion(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            // should never happen
            throw new RuntimeException("Could not get package name: " + e);
        }
    }

    public static String getSessionID(Context ctx) {

        SharedPreferences sp = PreferenceManager
                .getDefaultSharedPreferences(ctx);
        return sp.getString(SESSION_ID, null);
    }

    public static void saveLastEvaluationImageID(Context ctx, Integer evaluationImageID) {
        SharedPreferences sp = PreferenceManager
                .getDefaultSharedPreferences(ctx);
        SharedPreferences.Editor ed = sp.edit();
        ed.putInt(EVALUATION_IMAGE_ID, evaluationImageID);
        ed.commit();
        Log.e("SharedUtil", "evaluationImageID:" + evaluationImageID + "save in SharedPreferences");
    }

    public static Integer getLastEvaluationImageID(Context ctx) {
        SharedPreferences sp = PreferenceManager
                .getDefaultSharedPreferences(ctx);
        int id = sp.getInt(EVALUATION_IMAGE_ID, 0);
        return id;

    }


}
