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

import java.util.regex.Pattern;

public class SignupActivity extends AppCompatActivity {
    private String loginId;
    private String password;
    private String checkPassword;

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
        loginId = ((TextView)findViewById(R.id.loginId)).getText().toString().trim();
        password = ((TextView)findViewById(R.id.password)).getText().toString().trim();
        checkPassword = ((TextView)findViewById(R.id.checkPassword)).getText().toString().trim();
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

                ApiManager.signup(loginId, password, new ApiManager.SignupCallback() {
                    @Override
                    public void success(boolean success) {
                        String message = success ? "회원가입이 정상적으로 완료되었습니다." : "사용할 수 없는 아이디 입니다.";
                        Util.toast(SignupActivity.this, message, true);
                        if(success) finish();
                    }
                });
            }
        });
    }

    private boolean checkValidation() {
        if(loginId.isEmpty()) {
            Util.toast(this, "아이디를 입력해주세요.", true);
            return false;
        }

        if(password.isEmpty()) {
            Util.toast(this, "비밀번호를 입력해주세요.", true);
            return false;
        }

        if(checkPassword.isEmpty()) {
            Util.toast(this, "비밀번호를 한번더 입력해주세요.", true);
            return false;
        }

        if(!Pattern.matches("^[a-z]+[a-z0-9]{5,19}$", loginId)) {
            Util.toast(this, "아이디는 6~20자 사이의 소문자나 숫자이어야 합니다.", false);
            return false;
        }

        if(!password.equals(checkPassword)) {
            Util.toast(this, "비밀번호를 다시 확인해주세요.", true);
            return false;
        }

        if(!Pattern.matches("^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{7,19}$", password)) {
            Util.toast(this, "비밀번호는 8~20자, 최소 하나의 문자와 특수문자를 포함해야 합니다.", false);
            return false;
        }

        return true;
    }
}