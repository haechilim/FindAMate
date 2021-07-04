package com.example.findamate.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.findamate.R;
import com.example.findamate.domain.Classroom;
import com.example.findamate.domain.History;
import com.example.findamate.domain.School;
import com.example.findamate.domain.Student;
import com.example.findamate.helper.Logger;
import com.example.findamate.manager.ApiManager;
import com.example.findamate.manager.StudentViewManager;
import com.example.findamate.view.StudentView;

import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private static final int POPUP_CLASS = 0;
    private static final int POPUP_STUDENT = 1;
    private static final int POPUP_MATCHING = 2;
    private static final int POPUP_SIMULATION = 3;
    public static final int AVATAR_COUNT = 54;

    private FrameLayout studentContainer;
    private FrameLayout tip;
    private TextView schoolView;
    private Student targetStudent;
    private View selectedView;
    private List<Rect> studentViewPositions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        load();
        bindEvents();
    }

    @Override
    public void onBackPressed() {}

    private void init() {
        studentContainer = findViewById(R.id.studentContainer);
        tip = findViewById(R.id.tip);
        schoolView = findViewById(R.id.classInformation);

        SharedPreferences sharedPreferences = getSharedPreferences("tip", MODE_PRIVATE);
        showTip(sharedPreferences.getBoolean("showTip", true));
    }

    private void load() {
        loadSchool();
        loadStudents();
        loadHistories();
    }

    private void loadSchool() {
        ApiManager.getSchool(new ApiManager.SchoolCallback() {
            @Override
            public void success(School school) {
                Classroom.school = school;
                updateSchool();
                updateButtons();
            }
        });
    }

    private void loadStudents() {
        ApiManager.getStudents(new ApiManager.StudentListCallback() {
            @Override
            public void success(List<Student> students) {
                Classroom.students = students;
                Classroom.calculateHappiness();
                addStudentViews();
                studentViewPositions = StudentViewManager.randomPositions(MainActivity.this, studentContainer);
                StudentViewManager.startWaveAnimation(MainActivity.this, studentContainer);
                updateButtons();
            }
        });
    }

    private void loadHistories() {
        ApiManager.getRounds(new ApiManager.RoundListCallback() {
            @Override
            public void success(List<History> histories) {
                Classroom.histories = histories;
                updateButtons();
            }
        });
    }

    private void updateSchool() {
        schoolView.setText(Classroom.getClassInfo());
    }

    private void updateButtons() {
        showLogButton(!Classroom.histories.isEmpty());
        showStartButton(!Classroom.students.isEmpty());
        showSimulationButton(!Classroom.students.isEmpty());
    }

    private void addStudentViews() {
        for(int i = 0; i < Classroom.students.size(); i++) {
            addStudentView(Classroom.students.get(i));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case POPUP_CLASS:
                onResultSchool(resultCode);
                break;

            case POPUP_STUDENT:
                onResultStudent(resultCode, data);
                break;

            case POPUP_MATCHING:
                onResultRun(resultCode, data, false);
                break;

            case POPUP_SIMULATION:
                onResultRun(resultCode, data, true);
                break;
        }
    }

    private void onResultSchool(int resultCode) {
        if(resultCode == RESULT_OK) {
            ApiManager.updateSchool(Classroom.school);
            updateSchool();
        }
    }

    private void onResultStudent(int resultCode, Intent data) {
        if(resultCode == PopupStudentSettingActivity.RESULT_ADD) {
            String name = data.getStringExtra("name");
            String phone = data.getStringExtra("phone");
            boolean male = data.getBooleanExtra("male", true);

            addStudent(name, male, phone);
        }
        else if(resultCode == PopupContactActivity.RESULT_LOAD) {
            List<Student> students = Classroom.tempStudents;

            for(int i = 0; i < students.size(); i++) {
                Student student = students.get(i);

                addStudent(student.getName(), student.isMale(), student.getPhone());
            }

            students.clear();
        }
        else if(resultCode == PopupStudentSettingActivity.RESULT_MODIFY) {
            Student student = targetStudent;

            student.setName(data.getStringExtra("name"));
            student.setMale(data.getBooleanExtra("male", true));
            student.setPhone(data.getStringExtra("phone"));

            ApiManager.modifyStudent(targetStudent, new ApiManager.StudentCallback() {
                @Override
                public void success(Student student) {
                    for(int i = 0; i < Classroom.students.size(); i++) {
                        if(Classroom.students.get(i).getId() != student.getId()) continue;

                        Classroom.students.set(i, student);
                    }

                    ((TextView) selectedView.findViewById(R.id.name)).setText(student.getName());
                    ((TextView) selectedView.findViewById(R.id.statusMessage)).setText(student.getStatusMessage());

                    updateButtons();
                }
            });
        }
        else if(resultCode == PopupStudentSettingActivity.RESULT_REMOVE) {
            for(int i = 0; i < Classroom.students.size(); i++) {
                Student student = Classroom.students.get(i);

                if(student == targetStudent) {
                    ApiManager.deleteStudent(student);
                    Classroom.students.remove(i);
                    selectedView.setVisibility(View.GONE);
                    StudentViewManager.stopAnimation(selectedView);
                    break;
                }
            }

            updateButtons();
        }
    }

    private void onResultRun(int resultCode, Intent data, boolean isSimulation) {
        if(resultCode != RESULT_OK) return;

        Intent intent = new Intent(this, MatchingActivity.class);
        intent.putExtra("isSimulation", isSimulation);
        intent.putExtra("mode", data.getIntExtra("mode", 0));
        intent.putExtra("duplicate", data.getBooleanExtra("duplicate", false));
        startActivity(intent);
    }

    private void addStudent(String name, boolean male, String phone) {
        Student student = new Student(name, male, phone, new Random().nextInt(AVATAR_COUNT) + 1);

        ApiManager.addStudent(student, new ApiManager.StudentCallback() {
            @Override
            public void success(Student student) {
                View view = addStudentView(student);
                StudentViewManager.randomPosition(MainActivity.this, studentContainer, view, studentViewPositions);
                StudentViewManager.startWaveAnimation(MainActivity.this, studentContainer, view);
                Classroom.students.add(student);
                updateButtons();
            }
        });
    }

    private void bindEvents() {
        findViewById(R.id.logout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commitTip();

                SharedPreferences sharedPreferences = getSharedPreferences("auto login", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("is auto login", false);
                editor.commit();

                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });

        findViewById(R.id.classSettingButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PopupClassSettingActivity.class);
                startActivityForResult(intent, POPUP_CLASS);
            }
        });

        findViewById(R.id.studentSettingButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PopupStudentSettingActivity.class);
                startActivityForResult(intent, POPUP_STUDENT);
            }
        });

        findViewById(R.id.logButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commitTip();

                Intent intent = new Intent(MainActivity.this, LogActivity.class);
                intent.putExtra("type", LogActivity.TYPE_HISTORY);
                startActivity(intent);
            }
        });

        findViewById(R.id.startButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commitTip();

                Intent intent = new Intent(MainActivity.this, PopupMatchingSettingActivity.class);
                startActivityForResult(intent, POPUP_MATCHING);
            }
        });

        findViewById(R.id.simulationButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commitTip();

                Intent intent = new Intent(MainActivity.this, PopupMatchingSettingActivity.class);
                startActivityForResult(intent, POPUP_SIMULATION);
            }
        });

        tip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commitTip();
                showTip(false);
            }
        });
    }

    private View addStudentView(Student student) {
        View view = StudentViewManager.newView(this, student, false);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                targetStudent = student;
                selectedView = view;

                Intent intent = new Intent(MainActivity.this, PopupStudentSettingActivity.class);
                intent.putExtra("id", student.getId());
                startActivityForResult(intent, POPUP_STUDENT);
            }
        });

        studentContainer.addView((View)view.getParent());

        return view;
    }

    private void commitTip() {
        SharedPreferences sharedPreferences = getSharedPreferences("tip", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("showTip", false);
        editor.commit();
        showTip(false);
    }

    private void showTip(boolean visibility) {
        tip.setVisibility(visibility ? View.VISIBLE : View.GONE);
    }

    private void showLogButton(boolean visibility) {
        findViewById(R.id.logButton).setVisibility(visibility ? View.VISIBLE : View.GONE);
    }

    private void showStartButton(boolean visibility) {
        findViewById(R.id.startButton).setVisibility(visibility ? View.VISIBLE : View.GONE);
    }

    private void showSimulationButton(boolean visibility) {
        findViewById(R.id.simulationButton).setVisibility(visibility ? View.VISIBLE : View.GONE);
    }
}