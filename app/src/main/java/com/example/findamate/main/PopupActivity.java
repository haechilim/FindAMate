package com.example.findamate.main;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.findamate.R;

public class PopupActivity extends Activity {
    RadioGroup matchingMode;
    CheckBox checkbox;
    TextView matchingCancelButton;
    TextView matchingConfirmButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup);

        String data = getIntent().getStringExtra("classInformation");

        matchingMode = findViewById(R.id.matchingMode);
        checkbox = findViewById(R.id.checkbox);
        matchingCancelButton = findViewById(R.id.matchingCancelButton);
        matchingConfirmButton = findViewById(R.id.matchingConfirmButton);

        matchingCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        matchingConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), WaitingActivity.class);
                intent.putExtra("classInformation", data);
                intent.putExtra("matchingMode", matchingMode.getCheckedRadioButtonId());
                intent.putExtra("overlap", checkbox.isChecked());
                startActivity(intent);
                finish();
            }
        });
    }
}