package com.example.findamate.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckBox;
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

        SharedPreferences sharedPreferences = getSharedPreferences("auto login", MODE_PRIVATE);
        boolean isAutoLogin = sharedPreferences.getBoolean("is auto login", false);
        String id = sharedPreferences.getString("id", "");
        String password = sharedPreferences.getString("password", "");

        if(isAutoLogin) login(id, password, true);

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

                login(loginId.getText().toString().trim(), password.getText().toString().trim(), false);
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

    private void login(String loginId, String password, boolean autoLogin) {
        ApiManager.login(loginId, password, new ApiManager.LoginCallback() {
            @Override
            public void success(boolean success) {
                if(success) {
                    SharedPreferences sharedPreferences = getSharedPreferences("auto login", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();

                    if(autoLogin || ((CheckBox)findViewById(R.id.autoLogin)).isChecked()) {
                        editor.putBoolean("is auto login", true);
                        editor.putString("id", loginId);
                        editor.putString("password", password);
                    }
                    else editor.putBoolean("is auto login", false);

                    editor.commit();

                    Intent intent = new Intent(getBaseContext(), MainActivity.class);
                    startActivity(intent);
                }
                else if(!autoLogin) Toast.makeText(LoginActivity.this, "아이디 또는 비밀번호가 정확하지 않습니다.", Toast.LENGTH_SHORT).show();

                LoginActivity.this.loginId.setText("");
                LoginActivity.this.password.setText("");
            }
        });
    }

    private boolean checkValidation() {
        return !loginId.getText().toString().trim().isEmpty() && !password.getText().toString().trim().isEmpty();
    }
}