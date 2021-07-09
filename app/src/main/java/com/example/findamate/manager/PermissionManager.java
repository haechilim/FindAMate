package com.example.findamate.manager;

import android.Manifest;
import android.app.Activity;

import androidx.core.app.ActivityCompat;

public class PermissionManager {
    public static final int RC_PERMISSION = 2000;

    public static void requestPermissions(Activity activity) {
        ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_CONTACTS, Manifest.permission.SEND_SMS}, RC_PERMISSION);
    }
}
