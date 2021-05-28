package com.example.findamate.main;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.findamate.R;
import com.example.findamate.helper.CustomView;
import com.example.findamate.helper.DataBaseStudentList;

public class MainActivity extends AppCompatActivity {
    Activity activity = this;
    LinearLayout studentContainer;
    TextView classInformation;
    ImageView classSettingButton;
    ImageView classSettingCancelButton;
    LinearLayout classSettingLayout;
    EditText schoolName;
    EditText grade;
    EditText classNumber;
    Button applyButton;
    EditText studentId;
    EditText studentName;
    Button addStudentButton;
    ImageView studentSettingButton;
    ImageView studentSettingCancelButton;
    LinearLayout studentSettingLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        studentContainer = findViewById(R.id.studentContainer);
        classInformation = findViewById(R.id.classInformation);
        classSettingButton = findViewById(R.id.classSettingButton);
        classSettingCancelButton = findViewById(R.id.classSettingCancelButton);
        classSettingLayout = findViewById(R.id.classSettingLayout);
        schoolName = findViewById(R.id.schoolName);
        grade = findViewById(R.id.grade);
        classNumber = findViewById(R.id.classNumber);
        applyButton = findViewById(R.id.applyButton);
        studentId = findViewById(R.id.studentId);
        studentName = findViewById(R.id.studentName);
        addStudentButton = findViewById(R.id.addStudentButton);
        studentSettingButton = findViewById(R.id.studentSettingButton);
        studentSettingCancelButton = findViewById(R.id.studentSettingCancelButton);
        studentSettingLayout = findViewById(R.id.studentSettingLayout);
        //findViewById(R.id.name).setVisibility(View.INVISIBLE);

        bindEvents();
    }

    private void bindEvents() {
        classSettingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showClassSettingButton(false);
                showClassSettingCancelButton(true);
                showClassSettingLayout(false);
            }
        });

        classSettingCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showClassSettingButton(true);
                showClassSettingCancelButton(false);
                showClassSettingLayout(true);
            }
        });

        applyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String editTextSchoolName = schoolName.getText().toString().trim();
                String editTextGrade = grade.getText().toString().trim();
                String editText = classNumber.getText().toString().trim();

                if(!editTextSchoolName.isEmpty() && !editTextGrade.isEmpty() && !editText.isEmpty()) {
                    classInformation.setText("학급 : " + editTextSchoolName + " " + editTextGrade + "학년 " + editText + "반");
                    hideKeyboard();
                }
            }
        });

        addStudentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = studentId.getText().toString().trim();
                String name = studentName.getText().toString().trim();

                if(!id.isEmpty() && !name.isEmpty()) {
                    //DataBaseStudentList dataBaseStudentList = new DataBaseStudentList(activity);
                    newStudentProfile(id, name);
                    studentId.setText("");
                    studentName.setText("");
                    hideKeyboard();
                }
            }
        });

        studentSettingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showStudentSettingButton(false);
                showStudentSettingCancelButton(true);
                showStudentSettingLayout(false);
            }
        });

        studentSettingCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showStudentSettingButton(true);
                showStudentSettingCancelButton(false);
                showStudentSettingLayout(true);
            }
        });
    }

    private void newStudentProfile(String id, String name) {
        CustomView studentProfile = new CustomView(this, id, name);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(300, 400);
        studentContainer.addView(studentProfile, layoutParams);
    }

    private void hideKeyboard() {
        View view = activity.getCurrentFocus();

        if (view != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private void showClassSettingButton(boolean visibility) {
        classSettingButton.setVisibility(visibility ? View.VISIBLE : View.GONE);
    }

    private void showClassSettingCancelButton(boolean visibility) {
        classSettingCancelButton.setVisibility(visibility ? View.VISIBLE : View.GONE);
    }

    private void showClassSettingLayout(boolean visibility) {
        classSettingLayout.setVisibility(visibility ? View.VISIBLE : View.GONE);
    }

    private void showStudentSettingButton(boolean visibility) {
        studentSettingButton.setVisibility(visibility ? View.VISIBLE : View.GONE);
    }

    private void showStudentSettingCancelButton(boolean visibility) {
        studentSettingCancelButton.setVisibility(visibility ? View.VISIBLE : View.GONE);
    }

    private void showStudentSettingLayout(boolean visibility) {
        studentSettingLayout.setVisibility(visibility ? View.VISIBLE : View.GONE);
    }
}