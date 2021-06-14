package com.example.findamate.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.example.findamate.R;
import com.example.findamate.helper.Logger;
import com.example.findamate.helper.Util;
import com.example.findamate.manager.ApiManager;

import java.net.ResponseCache;

import javax.xml.namespace.QName;

public class SignupActivity extends AppCompatActivity {
    private EditText name;
    private EditText loginId;
    private EditText password;
    private EditText checkPassword;
    private EditText schoolName;
    private EditText year;
    private EditText number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        init();
        bindEvents();


    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Util.hideKeyBoard(ev, this, getCurrentFocus());
        return super.dispatchTouchEvent(ev);
    }

    private void init() {
        name = findViewById(R.id.name);
        loginId = findViewById(R.id.loginId);
        password = findViewById(R.id.password);
        checkPassword = findViewById(R.id.checkPassword);
        schoolName = findViewById(R.id.schoolName);
        year = findViewById(R.id.year);
        number = findViewById(R.id.number);
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
                Toast.makeText(SignupActivity.this, "토스트", Toast.LENGTH_SHORT).show();

                if(!checkValidation()) return;

                ApiManager.signup(name.getText().toString().trim(), loginId.getText().toString().trim(), password.getText().toString().trim(), new ApiManager.SignupCallback() {
                    @Override
                    public void success(boolean success) {
                        Logger.debug(success ? "true" : "false");
                        String message = success ? "회원가입이 정상적으로 완료 되었습니다." : "사용할 수 없는 아이디 입니다.";
                        Toast.makeText(SignupActivity.this, message, Toast.LENGTH_SHORT).show();

                        if(success) finish();
                    }
                });
            }
        });
    }

    private boolean checkValidation() {
        String name = this.name.getText().toString().trim();
        String loginId = this.loginId.getText().toString().trim();
        String password = this.password.getText().toString().trim();
        String checkPassword = this.checkPassword.getText().toString().trim();
        String schoolName = this.schoolName.getText().toString().trim();
        String year = this.year.getText().toString().trim();
        String number = this.number.getText().toString().trim();

        return !name.isEmpty() && !loginId.isEmpty() && !password.isEmpty() && !checkPassword.isEmpty() && !schoolName.isEmpty() && !year.isEmpty() && !number.isEmpty() && password.equals(checkPassword);
    }
}