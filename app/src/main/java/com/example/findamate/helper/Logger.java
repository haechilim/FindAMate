package com.example.findamate.helper;

import android.util.Log;

public class Logger {
    public static void debug(String message) {
        Log.d("wtf", message);
    }

    public static void error(String message) {
        Log.e("wtf", message);
    }

    public static void info(String message) {
        Log.i("wtf", message);
    }
}