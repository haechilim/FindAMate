package com.example.findamate.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.findamate.R;
import com.example.findamate.adapter.LogAdapter;
import com.example.findamate.domain.Classroom;
import com.example.findamate.domain.Couple;
import com.example.findamate.domain.History;
import com.example.findamate.domain.Poll;
import com.example.findamate.helper.Logger;
import com.example.findamate.helper.Util;
import com.example.findamate.manager.ApiManager;
import com.example.findamate.manager.MatchingManager;
import com.example.findamate.manager.PermissionManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class PollActivity extends AppCompatActivity {
    public final static int TYPE_RESULT = 1;
    public final static int TYPE_SIMULATION = 2;

    private int type;
    private int mode;
    private boolean duplicated;
    private TextView agreeView;
    private TextView disagreeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poll);

        Intent intent = getIntent();
        type = intent.getIntExtra("type", TYPE_SIMULATION);
        mode = intent.getIntExtra("mode", 1);
        duplicated = intent.getBooleanExtra("duplicated", false);

        agreeView = findViewById(R.id.agree);
        disagreeView = findViewById(R.id.disagree);

        List<History> histories = new ArrayList<>();
        histories.add(new History(Classroom.couples));
        LogAdapter logAdapter = new LogAdapter(PollActivity.this, histories, true);
        ((ListView)findViewById(R.id.list)).setAdapter(logAdapter);

        poll(false, () -> {
            poll(true, () -> {
                moniterPoll();
            });
        });

        bindEvents();

        //Util.sendSms(this, "01034993068", "test");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == PermissionManager.RC_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) Util.sendSms(this, "01034993068", "test");
            else Util.toast(PollActivity.this, "권한 거부로 인해 설문조사 기능이 제한됩니다.", true);
        }
    }

    private void bindEvents() {
        findViewById(R.id.again).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PollActivity.this, MatchingActivity.class);
                intent.putExtra("mode", mode);
                intent.putExtra("duplicated", duplicated);
                startActivity(intent);
            }
        });
    }

    // 설문 시작 or 종료
    private void poll(boolean begin, PollCallback callback) {
        ApiManager.poll(begin, (success) -> {
            callback.complete();
        });
    }

    // 타이머로 주기 적으로 데이터 요청 후 화면 갱신
     private void moniterPoll() {
         Timer timer = new Timer();
         TimerTask timerTask = new TimerTask() {
             @Override
             public void run() {
                 runOnUiThread(() -> {
                     pollStatus();
                 });
             }
         };

         timer.schedule(timerTask, 0, 1000);
     }

    // 학생들의 설문결과를 가져옴
    private void pollStatus() {
        ApiManager.pollStatus(new ApiManager.PollListCallback() {
            @Override
            public void success(List<Poll> polls) {
                updatePollResult(polls);
            }
        });
    }

    //좋아요 다시해요 갱신
    private void updatePollResult(List<Poll> polls) {
        Logger.debug(polls.toString());

        if(polls.isEmpty()) return;

        int agree = 0;
        int sum = 0;

        for(int i = 0; i < polls.size(); i++) {
            if(polls.get(i).isAgree()) agree++;
            sum++;
        }

        if(sum != 0) {
            int agreePercent = Math.round((float) agree / sum * 100);
            int disagreePercent = 100 - agreePercent;

            agreeView.setText(String.format("좋아요 %d%%", agreePercent));
            disagreeView.setText(String.format("다시해요 %d%%", disagreePercent));
        }
    }

    // 아이디를 객체와 연결
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

    private interface PollCallback {
        void complete();
    }
}