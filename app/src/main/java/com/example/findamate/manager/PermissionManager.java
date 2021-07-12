package com.example.findamate.manager;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class PermissionManager {
    private static final String readContacts = Manifest.permission.READ_CONTACTS;
    private static final String sendSms = Manifest.permission.SEND_SMS;

    // 권한 요청하기
    public static void requestContactsPermission(Activity activity) {
        requestPermissions(activity, readContacts);
    }

    public static void requestSmsPermission(Activity activity) {
        requestPermissions(activity, sendSms);
    }

    public static void requestPermissions(Activity activity, String permission) {
        ActivityCompat.requestPermissions(activity, new String[] { permission }, 0);
    }

    // 권한을 획득했는지
    public static boolean isContactsGranted(Context context) {
        return isGranted(context, readContacts);
    }

    public static boolean isSmsGranted(Context context) {
        return isGranted(context, sendSms);
    }

    private static boolean isGranted(Context context, String permission) {
        return ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED;
    }

    // 권한 요청 이유를 설명할 필요가 있는지
    public static boolean shouldContactsRequest(Activity activity) {
        return shouldRequest(activity, readContacts);
    }

    public static boolean shouldSmsRequest(Activity activity) {
        return shouldRequest(activity, sendSms);
    }

    private static boolean shouldRequest(Activity activity, String permission) {
        return ActivityCompat.shouldShowRequestPermissionRationale(activity, permission);
    }
}