package com.example.findamate.activity;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.findamate.R;
import com.example.findamate.adapter.LogAdapter;
import com.example.findamate.domain.Classroom;
import com.example.findamate.domain.Couple;
import com.example.findamate.domain.History;
import com.example.findamate.helper.Util;
import com.example.findamate.manager.ApiManager;
import com.example.findamate.manager.PermissionManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class PollActivity extends AppCompatActivity {
    public final static int TYPE_RESULT = 1;
    public final static int TYPE_SIMULATION = 2;

    private int type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poll);

        type = getIntent().getIntExtra("type", TYPE_SIMULATION);

        ApiManager.getRounds(new ApiManager.RoundListCallback() {
            @Override
            public void success(List<History> histories) {
                refineHistories(histories);

                if(type == TYPE_SIMULATION) {
                    List<History> simulationHistories = Classroom.getClonedHistories();

                    for(int i = 0; i < simulationHistories.size(); i++) {
                        histories.add(0, simulationHistories.get(i));
                    }
                }

                List<History> historyList = new ArrayList<>();
                historyList.add(histories.get(0));

                LogAdapter logAdapter = new LogAdapter(PollActivity.this, historyList, true);
                ((ListView)findViewById(R.id.list)).setAdapter(logAdapter);
            }
        });

        Util.sendSms(this, "01034993068", "test");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == PermissionManager.RC_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) Util.sendSms(this, "01034993068", "test");
            else Util.toast(PollActivity.this, "권한 거부로 인해 설문조사 기능이 제한됩니다.", true);
        }
    }

    private void startRequest() {
        Timer timer = new Timer();

        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                    }
                });
            }
        };

        timer.schedule(timerTask, 0, 5000);
    }

    private void refineHistories(List<History> histories) {
        for(int i = 0; i < histories.size(); i++) {
            History history = histories.get(i);
            List<Couple> couples = history.getCouples();

            for(int j = 0; j < couples.size(); j++) {
                Couple couple = couples.get(j);
                couple.setStudent1(Classroom.findStudentById(couple.getStudentId1(), false));
                couple.setStudent2(Classroom.findStudentById(couple.getStudentId2(), false));
            }
        }
    }
}