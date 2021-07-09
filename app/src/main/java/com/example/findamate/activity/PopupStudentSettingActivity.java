package com.example.findamate.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.example.findamate.R;
import com.example.findamate.domain.Classroom;
import com.example.findamate.domain.Student;
import com.example.findamate.helper.Util;
import com.example.findamate.manager.PermissionManager;

import java.util.regex.Pattern;

public class PopupStudentSettingActivity extends Activity {
    private static final int POPUP_CONTACT = 1100;

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
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        editTextName = findViewById(R.id.name);
        editTextPhone = findViewById(R.id.phone);
        radioGroupGender = findViewById(R.id.gender);

        Intent intent = getIntent();
        student = Classroom.findStudentById(intent.getIntExtra("id", -1), false);

        if(student == null) resultCode = RESULT_ADD;
        else {
            resultCode = RESULT_MODIFY;

            editTextName.setText(student.getName());
            editTextPhone.setText(student.getPhone());
            radioGroupGender.check(student.isMale() ? R.id.male : R.id.female);
        }

        showButton();
        bindEvents();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case POPUP_CONTACT:
                if(resultCode == PopupContactActivity.RESULT_LOAD) {
                    setResult(PopupContactActivity.RESULT_LOAD);
                    finish();
                }
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == PermissionManager.RC_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                startActivityForResult(new Intent(PopupStudentSettingActivity.this, PopupContactActivity.class), POPUP_CONTACT);
            else Util.toast(PopupStudentSettingActivity.this, "권한 거부로 인해 해당기능이 제한됩니다.", true);
        }
    }

    private void bindEvents() {
        findViewById(R.id.delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_REMOVE);
                finish();
            }
        });

        findViewById(R.id.load).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int permission  = ContextCompat.checkSelfPermission(PopupStudentSettingActivity.this, Manifest.permission.READ_CONTACTS);

                if(permission == PackageManager.PERMISSION_GRANTED)
                    startActivityForResult(new Intent(PopupStudentSettingActivity.this, PopupContactActivity.class), POPUP_CONTACT);
                else if(permission == PackageManager.PERMISSION_DENIED)
                    PermissionManager.requestPermissions(PopupStudentSettingActivity.this);
            }
        });

        findViewById(R.id.ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editTextName.getText().toString().trim();
                String phoneNumber = editTextPhone.getText().toString().trim();
                int genderId = radioGroupGender.getCheckedRadioButtonId();

                if(!checkValidation(name, phoneNumber)) return;

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

    private boolean checkValidation(String name, String phoneNumber) {
        if (name.isEmpty()) {
            Util.toast(this, "학생명을 입력해주세요.", true);
            return false;
        }

        if (phoneNumber.isEmpty()) {
            Util.toast(this, "전화번호를 입력해주세요.", true);
            return false;
        }

        if(!Pattern.matches("^\\d{10,11}$", phoneNumber)) {
            Util.toast(this, "올바른 전화번호 형식이 아닙니다.", true);
            return false;
        }

        return true;
    }

    private void showButton() {
        showDeleteButton(resultCode == RESULT_MODIFY);
        showLoadButton(resultCode == RESULT_ADD);
    }

    private void showDeleteButton(boolean visibility) {
        findViewById(R.id.delete).setVisibility(visibility ? View.VISIBLE : View.GONE);
    }

    private void showLoadButton(boolean visibility) {
        findViewById(R.id.load).setVisibility(visibility ? View.VISIBLE : View.GONE);
    }
}