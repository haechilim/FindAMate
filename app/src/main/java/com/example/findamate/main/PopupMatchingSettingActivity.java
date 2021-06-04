package com.example.findamate.main;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.RadioGroup;

import com.example.findamate.R;

public class PopupMatchingSettingActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_popup_matching_setting);

        findViewById(R.id.confirmMatchingSetting).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                intent.putExtra("radioButton", ((RadioGroup)findViewById(R.id.matchingMode)).getCheckedRadioButtonId());
                intent.putExtra("overlap", ((CheckBox)findViewById(R.id.checkbox)).isChecked());
                setResult(300, intent);
                finish();
            }
        });

        findViewById(R.id.cancelMatchingSetting).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}