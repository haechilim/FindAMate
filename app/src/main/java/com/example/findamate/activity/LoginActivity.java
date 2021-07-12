package com.example.findamate.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.findamate.R;
import com.example.findamate.helper.Logger;
import com.example.findamate.helper.Util;
import com.example.findamate.manager.ApiManager;
import com.example.findamate.manager.PermissionManager;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;

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
        else findViewById(R.id.container).setVisibility(View.VISIBLE);

        init();
        bindEvents();
    }

    @Override
    public void onBackPressed() {}

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
                login(false);
            }
        });

        ((EditText)findViewById(R.id.password)).setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                login(false);
                return false;
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

    private void login(boolean autoLogin) {
        String loginId = this.loginId.getText().toString().trim();
        String password = this.password.getText().toString().trim();

        if(!checkValidation(loginId, password)) return;
        login(loginId, password, autoLogin);
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

                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.setFlags(FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
                else if(!autoLogin) Util.toast(LoginActivity.this, "아이디 또는 비밀번호가 일치하지 않습니다.", true);

                LoginActivity.this.loginId.setText("");
                LoginActivity.this.password.setText("");
            }
        });
    }

    private boolean checkValidation(String loginId, String password) {
        if(loginId.isEmpty()) {
            Util.toast(this, "아이디를 입력해주세요.", true);
            return false;
        }

        if(password.isEmpty()) {
            Util.toast(this, "비밀번호를 입력해주세요.", true);
            return false;
        }

        return true;
    }
}