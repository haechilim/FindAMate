package com.example.findamate.main;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.findamate.R;
import com.example.findamate.adapter.HistoryAdapter;
import com.example.findamate.domain.MatchingResult;
import com.example.findamate.domain.History;

public class HistoryListActivity extends AppCompatActivity {
    History history = new History();
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

        history.addResult(new MatchingResult());
        history.addResult(new MatchingResult());
        history.addResult(new MatchingResult());
        history.addResult(new MatchingResult());
        history.addResult(new MatchingResult());
        history.addResult(new MatchingResult());
        history.addResult(new MatchingResult());
        history.addResult(new MatchingResult());
        history.addResult(new MatchingResult());
        history.addResult(new MatchingResult());
        history.addResult(new MatchingResult());

        HistoryAdapter historyAdapter = new HistoryAdapter(this, history.getResults());
        historyList.setAdapter(historyAdapter);

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}