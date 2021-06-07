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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class LogActivity extends AppCompatActivity {
    public final static int TYPE_HISTORY = 0;
    public final static int TYPE_RESULT = 1;
    public final static int TYPE_SIMULATION = 2;
    private List<History> histories = new ArrayList<>();
    private ImageView okButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);

        okButton = findViewById(R.id.ok);

        Intent intent = getIntent();
        int type = intent.getIntExtra("type", TYPE_HISTORY);

        switch (type) {
            case TYPE_HISTORY:
                histories = Classroom.histories;
                showOkButton(false);
                break;

            case TYPE_RESULT:
                List<History> historyList = Classroom.histories;

                if (historyList.size() == 0) break;

                histories.add(historyList.get(historyList.size() - 1));

                showOkButton(false);
                break;

            case TYPE_SIMULATION:
                histories = Classroom.getClonedHistories();
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

        LogAdapter logAdapter = new LogAdapter(this, histories);
        ((ListView)findViewById(R.id.logList)).setAdapter(logAdapter);

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