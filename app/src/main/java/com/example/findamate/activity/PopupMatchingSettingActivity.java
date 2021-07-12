package com.example.findamate.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.RadioGroup;

import com.example.findamate.R;
import com.example.findamate.manager.MatchingManager;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;

public class PopupMatchingSettingActivity extends Activity {
    private int type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_popup_matching_setting);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        Intent intent = getIntent();
        type = intent.getIntExtra("type", -1);

        findViewById(R.id.ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int mode = MatchingManager.MATCHING_MODE_DIFF;
                int modeId = ((RadioGroup)findViewById(R.id.matchingMode)).getCheckedRadioButtonId();
                boolean duplicated = ((CheckBox) findViewById(R.id.duplicated)).isChecked();

                if(modeId == R.id.mode1)  mode = MatchingManager.MATCHING_MODE_DIFF;
                else if(modeId == R.id.mode2)  mode = MatchingManager.MATCHING_MODE_SAME;
                else if(modeId == R.id.mode3)  mode = MatchingManager.MATCHING_MODE_NONE;

                if (type == LogActivity.TYPE_SIMULATION) {
                    Intent intent = new Intent(PopupMatchingSettingActivity.this, MatchingActivity.class);
                    intent.setFlags(FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra("type", type);
                    intent.putExtra("mode", mode);
                    intent.putExtra("duplicated", duplicated);
                    startActivity(intent);
                }
                else {
                    Intent intent = new Intent();
                    intent.putExtra("mode", mode);
                    intent.putExtra("duplicated", duplicated);
                    setResult(RESULT_OK, intent);
                    finish();
                }
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