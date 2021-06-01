package com.example.findamate.main;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;

import com.example.findamate.R;

public class PopupClassSettingActivity extends Activity {
    private EditText className;
    private EditText classYear;
    private EditText classNumber;
    private String name;
    private String year;
    private String number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_popup_class_setting);

        Intent data = getIntent();
        name = data.getStringExtra("name");
        year = data.getStringExtra("year");
        number = data.getStringExtra("number");

        className = findViewById(R.id.className);
        classYear = findViewById(R.id.classYear);
        classNumber = findViewById(R.id.classNumber);

        className.setText(name);
        classYear.setText(year);
        classNumber.setText(number);

        findViewById(R.id.confirmClassSetting).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                intent.putExtra("name", className.getText().toString().trim());
                intent.putExtra("year", classYear.getText().toString().trim());
                intent.putExtra("number", classNumber.getText().toString().trim());
                setResult(100, intent);
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
}