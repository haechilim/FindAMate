package com.example.findamate.thread;

import android.util.Log;

import com.example.findamate.domain.Couple;
import com.example.findamate.activity.MatchingActivity;

import java.util.ArrayList;
import java.util.List;

public class TimerThread extends Thread {
    private List<Couple> couples = new ArrayList<>();
    private MatchingActivity matchingActivity = new MatchingActivity();

    public TimerThread(List<Couple> couples) {
        this.couples = couples;
    }

    @Override
    public void run() {
        for(int i = 0; i < couples.size(); i++) {
            try {
                matchingActivity.animation(couples.get(i));
                sleep(2000);
            } catch (InterruptedException e) {
                Log.d("wtf", e.toString());
            }
        }
    }
}
