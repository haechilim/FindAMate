package com.example.findamate.main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.findamate.R;
import com.example.findamate.adapter.HistoryAdapter;
import com.example.findamate.domain.History;
import com.example.findamate.domain.MatchingManager;

import org.w3c.dom.Text;

public class LogActivity extends AppCompatActivity {
    MatchingManager matchingManager = new MatchingManager();
    TextView classInformationOfLog;
    ListView historyList;
    Button closeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);

        String classInformation = getIntent().getStringExtra("classInformation");

        classInformationOfLog = findViewById(R.id.classInformationOfLog);
        historyList = findViewById(R.id.historyList);
        closeButton = findViewById(R.id.closeLog);

        classInformationOfLog.setText(classInformation);

        matchingManager.addHistory(new History());
        matchingManager.addHistory(new History());
        matchingManager.addHistory(new History());
        matchingManager.addHistory(new History());
        matchingManager.addHistory(new History());
        matchingManager.addHistory(new History());
        matchingManager.addHistory(new History());
        matchingManager.addHistory(new History());
        matchingManager.addHistory(new History());
        matchingManager.addHistory(new History());
        matchingManager.addHistory(new History());

        HistoryAdapter historyAdapter = new HistoryAdapter(this, matchingManager.getHistories());
        historyList.setAdapter(historyAdapter);

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}