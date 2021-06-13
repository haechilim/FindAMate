package com.example.findamate.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.findamate.R;
import com.example.findamate.domain.Classroom;
import com.example.findamate.domain.School;
import com.example.findamate.domain.Student;
import com.example.findamate.manager.ApiManager;
import com.example.findamate.manager.StudentViewManager;

import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private static final int POPUP_CLASS = 0;
    private static final int POPUP_STUDENT = 1;
    private static final int POPUP_MATCHING = 2;
    private static final int POPUP_SIMULATION = 3;
    private static final int AVATAR_COUNT = 54;

    private FrameLayout studentContainer;
    private TextView schoolView;
    private Student targetStudent;
    private View selectedView;

    private List<Rect> studentViewPositions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        studentContainer = findViewById(R.id.studentContainer);
        schoolView = findViewById(R.id.classInformation);

        load();
        bindEvents();

        ApiManager.setMemberId(0);
    }

    private void load() {
        loadSchool();
        loadStudents();
    }

    private void loadSchool() {
        ApiManager.getSchool(new ApiManager.SchoolCallback() {
            @Override
            public void success(School school) {
                Classroom.school = school;
                updateSchool();
            }
        });
    }

    private void loadStudents() {
        ApiManager.getStudents(new ApiManager.StudentListCallback() {
            @Override
            public void success(List<Student> students) {
                Classroom.students = students;
                addStudentViews();
                studentViewPositions = StudentViewManager.randomPositions(MainActivity.this, studentContainer);
                StudentViewManager.startWaveAnimation(MainActivity.this, studentContainer);
            }
        });


        /*addFavoritePartner(Classroom.students.get(0), Classroom.students.get(1), 1);
        addFavoritePartner(Classroom.students.get(0), Classroom.students.get(8), 2);
        addFavoritePartner(Classroom.students.get(0), Classroom.students.get(3), 3);

        addFavoritePartner(Classroom.students.get(1), Classroom.students.get(10), 1);
        addFavoritePartner(Classroom.students.get(1), Classroom.students.get(11), 2);
        addFavoritePartner(Classroom.students.get(1), Classroom.students.get(9), 3);

        addFavoritePartner(Classroom.students.get(2), Classroom.students.get(9), 1);
        addFavoritePartner(Classroom.students.get(2), Classroom.students.get(6), 2);
        addFavoritePartner(Classroom.students.get(2), Classroom.students.get(10), 3);

        addFavoritePartner(Classroom.students.get(3), Classroom.students.get(1), 1);
        addFavoritePartner(Classroom.students.get(3), Classroom.students.get(0), 2);
        addFavoritePartner(Classroom.students.get(3), Classroom.students.get(10), 3);

        addFavoritePartner(Classroom.students.get(4), Classroom.students.get(7), 1);
        addFavoritePartner(Classroom.students.get(4), Classroom.students.get(8), 2);
        addFavoritePartner(Classroom.students.get(4), Classroom.students.get(9), 3);

        addFavoritePartner(Classroom.students.get(5), Classroom.students.get(1), 1);
        addFavoritePartner(Classroom.students.get(5), Classroom.students.get(2), 2);
        addFavoritePartner(Classroom.students.get(5), Classroom.students.get(0), 3);

        addFavoritePartner(Classroom.students.get(6), Classroom.students.get(4), 1);
        addFavoritePartner(Classroom.students.get(6), Classroom.students.get(0), 2);
        addFavoritePartner(Classroom.students.get(6), Classroom.students.get(5), 3);

        addFavoritePartner(Classroom.students.get(7), Classroom.students.get(8), 1);
        addFavoritePartner(Classroom.students.get(7), Classroom.students.get(6), 2);
        addFavoritePartner(Classroom.students.get(7), Classroom.students.get(10), 3);

        addFavoritePartner(Classroom.students.get(8), Classroom.students.get(3), 1);
        addFavoritePartner(Classroom.students.get(8), Classroom.students.get(11), 2);
        addFavoritePartner(Classroom.students.get(8), Classroom.students.get(5), 3);

        addFavoritePartner(Classroom.students.get(9), Classroom.students.get(4), 1);
        addFavoritePartner(Classroom.students.get(9), Classroom.students.get(10), 2);
        addFavoritePartner(Classroom.students.get(9), Classroom.students.get(6), 3);

        addFavoritePartner(Classroom.students.get(10), Classroom.students.get(1), 1);
        addFavoritePartner(Classroom.students.get(10), Classroom.students.get(5), 2);
        addFavoritePartner(Classroom.students.get(10), Classroom.students.get(4), 3);

        addFavoritePartner(Classroom.students.get(11), Classroom.students.get(9), 1);
        addFavoritePartner(Classroom.students.get(11), Classroom.students.get(4), 2);
        addFavoritePartner(Classroom.students.get(11), Classroom.students.get(7), 3);

        addFavoritePartner(Classroom.students.get(12), Classroom.students.get(9), 1);
        addFavoritePartner(Classroom.students.get(12), Classroom.students.get(4), 2);
        addFavoritePartner(Classroom.students.get(12), Classroom.students.get(7), 3);*/

        /*students.add(new Student("임준형",true,"haechilim",10,"카무이!"));
        students.add(new Student("랄로",true,"haechilim",22,"카무이!"));
        students.add(new Student("파카",true,"haechilim",49,"카무이!"));
        students.add(new Student("괴물쥐",false,"haechilim",27,"카무이!"));
        students.add(new Student("로지컬",true,"haechilim",44,"카무이!"));
        students.add(new Student("도파",false,"haechilim",30,"카무이!"));
        students.add(new Student("감스트",true,"haechilim",29,"카무이!"));
        students.add(new Student("진용진",true,"haechilim",19,"카무이!"));
        students.add(new Student("전국진",false,"haechilim",11,"카무이!"));
        students.add(new Student("논리왕 전기",false,"haechilim",33,"카무이!"));
        students.add(new Student("미야",false,"haechilim",32,"카무이!"));
        students.add(new Student("구루루",true,"haechilim",23,"카무이!"));
        students.add(new Student("우주하마",true,"haechilim",43,"카무이!"));*/


    }

    private void addFavoritePartner(Student student, Student mate, int rank) {
        ApiManager.addFavoritePartner(student, mate, rank);
        student.addFavoritePartner(mate, rank);
    }

    private void updateUi() {
        updateSchool();
        addStudentViews();
    }

    private void updateSchool() {
        schoolView.setText(Classroom.getClassInfo());
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

            Student student = new Student(name, male, phone, new Random().nextInt(AVATAR_COUNT) + 1);

            ApiManager.addStudent(student, new ApiManager.StudentCallback() {
                @Override
                public void success(Student student) {
                    View view = addStudentView(student);
                    StudentViewManager.randomPosition(MainActivity.this, studentContainer, view, studentViewPositions);
                    StudentViewManager.startWaveAnimation(MainActivity.this, studentContainer, view);

                    Classroom.students.add(student);
                }
            });
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
                    break;
                }
            }
        }
    }

    private void onResultRun(int resultCode, Intent data, boolean isSimulation) {
        if(resultCode != RESULT_OK) return;

        Intent intent = new Intent(this, isSimulation ? MatchingActivity.class : WaitingActivity.class);
        intent.putExtra("isSimulation", isSimulation);
        intent.putExtra("mode", data.getIntExtra("mode", 0));
        intent.putExtra("duplicate", data.getBooleanExtra("duplicate", false));
        startActivity(intent);
    }

    private void bindEvents() {
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
                Intent intent = new Intent(MainActivity.this, LogActivity.class);
                intent.putExtra("type", LogActivity.TYPE_HISTORY);
                startActivity(intent);
            }
        });

        findViewById(R.id.startButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PopupMatchingSettingActivity.class);
                startActivityForResult(intent, POPUP_MATCHING);
            }
        });

        findViewById(R.id.simulationButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PopupMatchingSettingActivity.class);
                startActivityForResult(intent, POPUP_SIMULATION);
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

    private void hideKeyboard() {
        View view = getCurrentFocus();

        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}