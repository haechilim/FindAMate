package com.example.findamate.main;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.RadioButton;
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
                int id = 0;
                int selectedId = ((RadioGroup)findViewById(R.id.matchingMode)).getCheckedRadioButtonId();

                if(selectedId == R.id.radioButton1)  id = 1;
                else if(selectedId == R.id.radioButton2)  id = 2;
                else if(selectedId == R.id.radioButton3)  id = 3;

                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                intent.putExtra("matchingModeId", id);
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