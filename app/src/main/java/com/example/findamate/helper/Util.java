package com.example.findamate.helper;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.example.findamate.activity.LoginActivity;

import static android.content.Context.INPUT_METHOD_SERVICE;

public class Util {
    public static int dpToPx(WindowManager windowManager, int dp) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        float density = displayMetrics.density;

        return (int) (dp * density + 0.5);
    }

    public static void hideKeyBoard(MotionEvent ev, Activity activity, View focusView) {
        if (focusView != null) {
            Rect rect = new Rect();

            focusView.getGlobalVisibleRect(rect);

            if (!rect.contains((int)ev.getX(), (int)ev.getY())) {
                InputMethodManager imm = (InputMethodManager) activity.getSystemService(INPUT_METHOD_SERVICE);

                if (imm != null) imm.hideSoftInputFromWindow(focusView.getWindowToken(), 0);

                focusView.clearFocus();
            }
        }
    }

    public static void toast(Context context, String message, boolean isShort) {
        Toast.makeText(context, message, isShort ? Toast.LENGTH_SHORT : Toast.LENGTH_LONG).show();
    }
}