package com.example.findamate.thread;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.example.findamate.activity.MainActivity;
import com.example.findamate.domain.Couple;
import com.example.findamate.activity.MatchingActivity;

import java.util.ArrayList;
import java.util.List;

public class TimerThread extends Thread {
    private Activity activity;
    private List<Couple> couples;
    private MatchingActivity matchingActivity = new MatchingActivity();
    private Couple couple;


    public TimerThread(Activity activity, List<Couple> couples) {
        this.activity = activity;
        this.couples = couples;
    }

    @Override
    public void run() {
        for(int i = 0; i < couples.size(); i++) {
            couple = couples.get(i);

            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    matchingActivity.animation(couple);
                }
            });

            try {
                sleep(2000);
            } catch (InterruptedException e) {
                Log.d("wtf", e.toString());
            }
        }
    }
}
