package com.example.findamate.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.RadioGroup;

import com.example.findamate.R;

public class PopupMatchingSettingActivity extends Activity {
    public static final int MATCHING_MODE_DIFF = 1;
    public static final int MATCHING_MODE_SAME = 2;
    public static final int MATCHING_MODE_NONE = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_popup_matching_setting);

        findViewById(R.id.ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int mode = MATCHING_MODE_DIFF;
                int modeId = ((RadioGroup)findViewById(R.id.matchingMode)).getCheckedRadioButtonId();
                boolean duplicated = ((CheckBox) findViewById(R.id.duplicated)).isChecked();

                if(modeId == R.id.mode1)  mode = MATCHING_MODE_DIFF;
                else if(modeId == R.id.mode2)  mode = MATCHING_MODE_SAME;
                else if(modeId == R.id.mode3)  mode = MATCHING_MODE_NONE;

                Intent intent = new Intent();
                intent.putExtra("mode", mode);
                intent.putExtra("duplicated", duplicated);
                setResult(RESULT_OK, intent);
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
}