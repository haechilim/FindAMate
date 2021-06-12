package com.example.findamate.helper;

import android.util.Log;

public class Logger {
    private static final String TAG = "haechi_wtf";

    public static void debug(String message) {
        Log.d(TAG, message);
    }

    public static void error(String message) {
        Log.e(TAG, message);
    }

    public static void info(String message) {
        Log.i(TAG, message);
    }
}