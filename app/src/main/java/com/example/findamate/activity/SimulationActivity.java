package com.example.findamate.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.findamate.R;
import com.example.findamate.domain.Classroom;
import com.example.findamate.domain.Student;

import java.util.ArrayList;
import java.util.List;

public class SimulationActivity extends AppCompatActivity {
    TextView classInformationOfSimulation;
    ListView simulationList;
    Button closeSimulation;
    Button startSimulation;
    //List<StudentsPair> studentsPairList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simulation);

        Classroom.students.get(0).addFavoritePartner(Classroom.students.get(1));
        Classroom.students.get(0).addFavoritePartner(Classroom.students.get(8));
        Classroom.students.get(0).addFavoritePartner(Classroom.students.get(3));

        Classroom.students.get(1).addFavoritePartner(Classroom.students.get(10));
        Classroom.students.get(1).addFavoritePartner(Classroom.students.get(11));
        Classroom.students.get(1).addFavoritePartner(Classroom.students.get(9));

        Classroom.students.get(2).addFavoritePartner(Classroom.students.get(9));
        Classroom.students.get(2).addFavoritePartner(Classroom.students.get(6));
        Classroom.students.get(2).addFavoritePartner(Classroom.students.get(10));

        Classroom.students.get(3).addFavoritePartner(Classroom.students.get(1));
        Classroom.students.get(3).addFavoritePartner(Classroom.students.get(0));
        Classroom.students.get(3).addFavoritePartner(Classroom.students.get(10));

        Classroom.students.get(4).addFavoritePartner(Classroom.students.get(7));
        Classroom.students.get(4).addFavoritePartner(Classroom.students.get(8));
        Classroom.students.get(4).addFavoritePartner(Classroom.students.get(9));

        Classroom.students.get(5).addFavoritePartner(Classroom.students.get(1));
        Classroom.students.get(5).addFavoritePartner(Classroom.students.get(2));
        Classroom.students.get(5).addFavoritePartner(Classroom.students.get(0));

        Classroom.students.get(6).addFavoritePartner(Classroom.students.get(4));
        Classroom.students.get(6).addFavoritePartner(Classroom.students.get(0));
        Classroom.students.get(6).addFavoritePartner(Classroom.students.get(5));

        Classroom.students.get(7).addFavoritePartner(Classroom.students.get(8));
        Classroom.students.get(7).addFavoritePartner(Classroom.students.get(6));
        Classroom.students.get(7).addFavoritePartner(Classroom.students.get(10));

        Classroom.students.get(8).addFavoritePartner(Classroom.students.get(3));
        Classroom.students.get(8).addFavoritePartner(Classroom.students.get(11));
        Classroom.students.get(8).addFavoritePartner(Classroom.students.get(5));

        Classroom.students.get(9).addFavoritePartner(Classroom.students.get(4));
        Classroom.students.get(9).addFavoritePartner(Classroom.students.get(10));
        Classroom.students.get(9).addFavoritePartner(Classroom.students.get(6));

        Classroom.students.get(10).addFavoritePartner(Classroom.students.get(1));
        Classroom.students.get(10).addFavoritePartner(Classroom.students.get(5));
        Classroom.students.get(10).addFavoritePartner(Classroom.students.get(4));

        Classroom.students.get(11).addFavoritePartner(Classroom.students.get(9));
        Classroom.students.get(11).addFavoritePartner(Classroom.students.get(4));
        Classroom.students.get(11).addFavoritePartner(Classroom.students.get(7));

        Classroom.students.get(12).addFavoritePartner(Classroom.students.get(9));
        Classroom.students.get(12).addFavoritePartner(Classroom.students.get(4));
        Classroom.students.get(12).addFavoritePartner(Classroom.students.get(7));

        Intent intent = getIntent();
        String classInformation = intent.getStringExtra("classInformation");
        int matchingModeId = intent.getIntExtra("matchingModeId", -1);
        boolean overlap = intent.getBooleanExtra("overlap", false);

        classInformationOfSimulation = findViewById(R.id.classInformationOfSimulation);
        simulationList = findViewById(R.id.simulationList);
        closeSimulation = findViewById(R.id.closeSimulation);
        startSimulation = findViewById(R.id.startSimulation);

        /*LogMatchingAdapter resultAdapter = new LogMatchingAdapter(this, studentsPairList);
        simulationList.setAdapter(resultAdapter);

        classInformationOfSimulation.setText(classInformation);

        closeSimulation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        startSimulation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                matchingPartner(overlap);
                resultAdapter.notifyDataSetChanged();
            }
        });
    }

    private boolean matchingPartner(boolean overlap) {
        students.sort(new Comparator<Student>() {
            @Override
            public int compare(Student o1, Student o2) {
                return o2.getScore() - o1.getScore();
            }
        });

        for (int i = 0; i < students.size(); i++) {
            students.get(i).clearHasPartner();
        }

        Student sampleStudent = students.get(0);

        if(sampleStudent == null) return false;

        for (int choice = 0; choice < sampleStudent.favoritePartnersSize(); choice++) {
            for (int i = 0; i < students.size(); i++) {
                Student student = students.get(i);
                Student partner = student.getFavoritePartner(choice);

                if (student.isHasPartner() || partner.isHasPartner()) continue;
                if (!overlap && student.isExpartner(partner)) continue;

                if (partner.getFavoritePartner(0).getId() == student.getId()) {
                    makePartner(student, partner);
                    updateScore(student, partner);
                }
            }
        }

        for (int i = 0; i < students.size(); i++) {
            Student student = students.get(i);

            if(student.isHasPartner()) continue;

            for (int j = i + 1; j < students.size(); j++) {
                Student partner = students.get(j);

                if(!overlap && student.isExpartner(partner)) continue;
                if(!partner.isHasPartner()) {
                    makePartner(student, partner);
                    updateScore(student, partner);
                    break;
                }
            }
        }

        return everyoneHasPartner();
    }

    private void makePartner(Student student, Student partner) {
        student.addPartner(partner);
        partner.addPartner(student);

        student.setHasPartner(true);
        partner.setHasPartner(true);

        studentsPairList.add(new StudentsPair(student, partner));
    }

    private void makePartner(Student student) {
        student.addPartner(student);

        student.setHasPartner(true);

        studentsPairList.add(new StudentsPair(student, student));
    }

    private void updateScore(Student student, Student partner) {
        student.addScore(student.getFavoritePartnerIndex(partner));
        partner.addScore(partner.getFavoritePartnerIndex(student));
    }

    private void updateScore(Student student) {
        student.addScore(-1);
    }

    private boolean everyoneHasPartner() {
        for(int i = 0; i < students.size(); i++) {
            Student student = students.get(i);

            if(!student.isHasPartner()) {
                makePartner(student);
                updateScore(student);
                return false;
            }
        }

        return true;
    }*/
    }
}