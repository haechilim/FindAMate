package com.example.findamate.manager;

import android.Manifest;
import android.app.Activity;

import androidx.core.app.ActivityCompat;

public class PermissionManager {
    public static final int READ_CONTACT = 2000;

    public static void requestPermissionReadContact(Activity activity) {
        ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_CONTACTS}, READ_CONTACT);
    }
}
