package com.example.findamate.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.findamate.R;
import com.example.findamate.helper.Util;
import com.example.findamate.manager.ApiManager;

public class LoginActivity extends AppCompatActivity {
    EditText loginId;
    EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();
        bindEvents();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Util.hideKeyBoard(ev, this, getCurrentFocus());
        return super.dispatchTouchEvent(ev);
    }

    private void init() {
        loginId = findViewById(R.id.loginId);
        password = findViewById(R.id.password);
    }

    private void bindEvents() {
        findViewById(R.id.login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!checkValidation()) return;

                ApiManager.login(loginId.getText().toString().trim(), password.getText().toString().trim(), new ApiManager.LoginCallback() {
                    @Override
                    public void success(boolean success) {
                        if(success) {
                            Intent intent = new Intent(getBaseContext(), MainActivity.class);
                            startActivity(intent);
                        }
                        else {
                            Toast.makeText(LoginActivity.this, "일치하는 회원 정보를 찾을수 없습니다.", Toast.LENGTH_SHORT).show();
                            loginId.setText("");
                            password.setText("");
                        }
                    }
                });
            }
        });

        findViewById(R.id.signup).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), SignupActivity.class);
                startActivity(intent);
            }
        });
    }

    private boolean checkValidation() {
        return !loginId.getText().toString().trim().isEmpty() && !password.getText().toString().trim().isEmpty();
    }
}