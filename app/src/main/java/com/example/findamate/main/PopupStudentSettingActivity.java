package com.example.findamate.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

import com.example.findamate.R;

public class PopupStudentSettingActivity extends Activity {
    private EditText studentName;
    private EditText isMale;
    private EditText studentTalkId;
    private TextView deleteStudent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_popup_student_setting);

        studentName = findViewById(R.id.studentName);
        isMale = findViewById(R.id.studentSex);
        studentTalkId = findViewById(R.id.studentTalkId);
        deleteStudent = findViewById(R.id.deleteStudent);

        Intent data = getIntent();
        String name = data.getStringExtra("name");
        boolean male = data.getBooleanExtra("male", true);
        String talkId = data.getStringExtra("talkId");

        if(name == null) {
            bindEvents(200);
            showDelete(false);
        }
        else {
            studentName.setText(name);
            isMale.setText(male ? "남" : "여");
            studentTalkId.setText(talkId);
            bindEvents(400);
            showDelete(true);
        }
    }

    private void bindEvents(int resultCode) {
        findViewById(R.id.addStudent).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = studentName.getText().toString().trim();
                String male = isMale.getText().toString().trim();
                String talkId = studentTalkId.getText().toString().trim();

                if(name.equals("") || male.equals("") || talkId.equals("")) return;

                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                intent.putExtra("name", name);
                intent.putExtra("male", male);
                intent.putExtra("talkId", talkId);
                setResult(resultCode, intent);
                finish();
            }
        });

        findViewById(R.id.cancelStudent).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void showDelete(boolean visibility) {
        deleteStudent.setVisibility(visibility ? View.VISIBLE : View.INVISIBLE);
    }
}