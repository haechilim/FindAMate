package com.example.findamate.helper;

import android.util.DisplayMetrics;
import android.view.WindowManager;

public class Util {
    public static int dpToPx(WindowManager windowManager, int dp) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        float density = displayMetrics.density;

        return (int) (dp * density + 0.5);
    }
}