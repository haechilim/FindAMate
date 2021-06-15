package com.example.findamate.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;

import com.example.findamate.R;
import com.example.findamate.domain.Classroom;
import com.example.findamate.helper.Util;

public class PopupClassSettingActivity extends Activity {
    private EditText editTextName;
    private EditText editTextYear;
    private EditText editTextNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_popup_class_setting);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        editTextName = findViewById(R.id.className);
        editTextYear = findViewById(R.id.classYear);
        editTextNumber = findViewById(R.id.classNumber);

        editTextName.setText(Classroom.school.getName());
        editTextYear.setText(Classroom.school.getYear());
        editTextNumber.setText(Classroom.school.getNumber());

        findViewById(R.id.confirmClassSetting).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editTextName.getText().toString().trim();
                String year = editTextYear.getText().toString().trim();
                String number = editTextNumber.getText().toString().trim();

                if(!checkValidation(name, year, number)) return;

                Classroom.school.setName(name);
                Classroom.school.setYear(year);
                Classroom.school.setNumber(number);

                setResult(RESULT_OK);
                finish();
            }
        });

        findViewById(R.id.cancelClassSetting).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    private boolean checkValidation(String name, String year, String number) {
        if (name.isEmpty()) {
            Util.toast(this, "학교명을 입력해주세요.", true);
            return false;
        }

        if (year.isEmpty()) {
            Util.toast(this, "학년을 입력해주세요.", true);
            return false;
        }

        if (number.isEmpty()) {
            Util.toast(this, "학급명을 입력해주세요.", true);
            return false;
        }

        return true;
    }
}