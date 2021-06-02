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
    private EditText studentSex;
    private EditText studentTalkId;
    private TextView deleteStudent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_popup_student_setting);

        studentName = findViewById(R.id.studentName);
        studentSex = findViewById(R.id.studentSex);
        studentTalkId = findViewById(R.id.studentTalkId);
        deleteStudent = findViewById(R.id.deleteStudent);

        Intent data = getIntent();
        String name = data.getStringExtra("name");
        boolean male = data.getBooleanExtra("male", true);
        String talkId = data.getStringExtra("talkId");

        if(name == null) showDelete(false);
        else {
            studentName.setText(name);
            studentSex.setText(male ? "남" : "여");
            studentTalkId.setText(talkId);
            showDelete(true);
        }

        findViewById(R.id.addStudent).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                intent.putExtra("name", studentName.getText().toString().trim());
                intent.putExtra("male", studentSex.getText().toString().trim());
                intent.putExtra("talkId", studentTalkId.getText().toString().trim());
                setResult(200, intent);
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