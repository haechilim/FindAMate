package com.example.findamate.helper;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Rect;
import android.telephony.SmsManager;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.example.findamate.manager.StudentViewManager;

import static android.content.Context.INPUT_METHOD_SERVICE;

public class Util {
    private static PendingIntent sentIntent;
    private static PendingIntent deliveryIntent;

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

    public static void sendSms(Context context, String number, String message) {
        registerSmsReceivers(context);
        SmsManager.getDefault().sendTextMessage(number, null, message, sentIntent, deliveryIntent);
    }

    private static void registerSmsReceivers(Context context) {
        if(sentIntent != null || deliveryIntent != null) return;

        sentIntent = PendingIntent.getBroadcast(context, 0, new Intent("SMS_SENT_ACTION"), 0);
        deliveryIntent = PendingIntent.getBroadcast(context, 0, new Intent("SMS_DELIVERED_ACTION"), 0);

        context.registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                switch(getResultCode()){
                    case Activity.RESULT_OK:
                        Logger.debug("전송 완료");
                        break;
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        Logger.debug("전송 실패  " + intent.toString());
                        break;
                    case SmsManager.RESULT_ERROR_NO_SERVICE:
                        Logger.debug("서비스 지역이 아닙니다");
                        break;
                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                        Logger.debug("무선(Radio)가 꺼져있습니다");
                        break;
                    case SmsManager.RESULT_ERROR_NULL_PDU:
                        Logger.debug("PDU Null");
                        break;
                }
            }
        }, new IntentFilter("SMS_SENT_ACTION"));

        context.registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                switch (getResultCode()){
                    case Activity.RESULT_OK:
                        Logger.debug("SMS 도착 완료");
                        break;
                    case Activity.RESULT_CANCELED:
                        Logger.debug("SMS 도착 실패");
                        break;
                }
            }
        }, new IntentFilter("SMS_DELIVERED_ACTION"));
    }

    public static int getItemPerRow(Activity activity) {
        WindowManager windowManager = activity.getWindowManager();
        DisplayMetrics metrics = activity.getResources().getDisplayMetrics();
        int width = StudentViewManager.MINI_WIDTH + 15;

        return (int)Math.floor(metrics.widthPixels / dpToPx(windowManager, width));
    }
}