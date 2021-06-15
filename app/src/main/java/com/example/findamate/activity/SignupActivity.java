package com.example.findamate.activity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.findamate.R;
import com.example.findamate.helper.Logger;
import com.example.findamate.helper.Util;
import com.example.findamate.manager.ApiManager;

public class SignupActivity extends AppCompatActivity {
    private String name;
    private String loginId;
    private String password;
    private String checkPassword;
    private String schoolName;
    private String year;
    private String number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        bindEvents();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Util.hideKeyBoard(ev, this, getCurrentFocus());
        return super.dispatchTouchEvent(ev);
    }

    private void getEditTexts() {
        name = ((TextView)findViewById(R.id.name)).getText().toString().trim();
        loginId = ((TextView)findViewById(R.id.loginId)).getText().toString().trim();
        password = ((TextView)findViewById(R.id.password)).getText().toString().trim();
        checkPassword = ((TextView)findViewById(R.id.checkPassword)).getText().toString().trim();
        schoolName = ((TextView)findViewById(R.id.schoolName)).getText().toString().trim();
        year = ((TextView)findViewById(R.id.year)).getText().toString().trim();
        number = ((TextView)findViewById(R.id.number)).getText().toString().trim();
    }

    private void bindEvents() {
        findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        findViewById(R.id.singup).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getEditTexts();
                if(!checkValidation()) return;

                ApiManager.signup(name, loginId, password, schoolName, Integer.parseInt(year), Integer.parseInt(number), new ApiManager.SignupCallback() {
                    @Override
                    public void success(boolean success) {
                        String message = success ? "회원가입이 정상적으로 완료 되었습니다." : "사용할 수 없는 아이디 입니다.";
                        Util.toast(SignupActivity.this, message);
                        if(success) finish();
                    }
                });
            }
        });
    }

    private boolean checkValidation() {
        if(!password.equals(checkPassword)) Util.toast(SignupActivity.this, "비밀번호를 다시 확인해주세요.");
        return !name.isEmpty() && !loginId.isEmpty() && !password.isEmpty() && !checkPassword.isEmpty() && !schoolName.isEmpty() && !year.isEmpty() && !number.isEmpty() && password.equals(checkPassword);
    }
}