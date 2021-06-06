package com.example.findamate.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.example.findamate.R;
import com.example.findamate.domain.Classroom;
import com.example.findamate.domain.Student;

public class PopupStudentSettingActivity extends Activity {
    public static final int RESULT_ADD = 100;
    public static final int RESULT_MODIFY = 200;
    public static final int RESULT_REMOVE = 300;

    private Student student;
    private int resultCode;
    private EditText editTextName;
    private EditText editTextPhone;
    private RadioGroup radioGroupGender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_popup_student_setting);

        editTextName = findViewById(R.id.name);
        editTextPhone = findViewById(R.id.phone);
        radioGroupGender = findViewById(R.id.gender);

        Intent intent = getIntent();
        student = Classroom.findStudentById(intent.getIntExtra("id", -1));

        if(student == null) resultCode = RESULT_ADD;
        else {
            resultCode = RESULT_MODIFY;

            editTextName.setText(student.getName());
            editTextPhone.setText(student.getPhone());
            radioGroupGender.check(student.isMale() ? R.id.male : R.id.female);
        }

        showDeleteButton(resultCode == RESULT_MODIFY);
        bindEvents(resultCode);
    }

    private void bindEvents(int resultCode) {
        findViewById(R.id.delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_REMOVE);
                finish();
            }
        });

        findViewById(R.id.ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editTextName.getText().toString().trim();
                String phoneNumber = editTextPhone.getText().toString().trim();
                int genderId = radioGroupGender.getCheckedRadioButtonId();

                if(name.isEmpty() || phoneNumber.isEmpty() || genderId < 0) return;

                Intent intent = new Intent();
                intent.putExtra("name", name);
                intent.putExtra("male", genderId == R.id.male);
                intent.putExtra("phone", phoneNumber);
                setResult(resultCode, intent);
                finish();
            }
        });

        findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void showDeleteButton(boolean visibility) {
        findViewById(R.id.delete).setVisibility(visibility ? View.VISIBLE : View.INVISIBLE);
    }
}