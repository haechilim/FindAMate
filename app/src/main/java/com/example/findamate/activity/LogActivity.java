package com.example.findamate.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.findamate.R;
import com.example.findamate.adapter.LogAdapter;
import com.example.findamate.domain.Classroom;
import com.example.findamate.domain.Couple;
import com.example.findamate.domain.History;
import com.example.findamate.domain.Student;
import com.example.findamate.helper.Logger;
import com.example.findamate.manager.ApiManager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class LogActivity extends AppCompatActivity {
    public final static int TYPE_HISTORY = 0;
    public final static int TYPE_RESULT = 1;
    public final static int TYPE_SIMULATION = 2;

    private int type;
    private ImageView okButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);

        type = getIntent().getIntExtra("type", TYPE_HISTORY);

        okButton = findViewById(R.id.ok);

        switch (type) {
            case TYPE_HISTORY:
                showOkButton(false);
                break;

            case TYPE_RESULT:
                showOkButton(false);
                break;

            case TYPE_SIMULATION:
                showOkButton(true);

                okButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(LogActivity.this, PopupMatchingSettingActivity.class);
                        intent.putExtra("type", TYPE_SIMULATION);
                        startActivity(intent);
                    }
                });
                break;
        }

        load();
        bindEvents();
    }

    @Override
    public void onBackPressed() {
        if(type == TYPE_HISTORY) super.onBackPressed();
    }

    private void load() {
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

                setAdapter(histories);
            }
        });
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

    private void setAdapter(List<History> histories) {
        LogAdapter logAdapter = new LogAdapter(this, histories);
        ((ListView)findViewById(R.id.logList)).setAdapter(logAdapter);
    }

    private void bindEvents() {
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LogActivity.this, MainActivity.class));
            }
        });
    }

    private void showOkButton(boolean visibility) {
        okButton.setVisibility(visibility ? View.VISIBLE : View.GONE);
    }
}