package com.example.findamate.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.findamate.R;
import com.example.findamate.domain.Classroom;

public class SimulationActivity extends AppCompatActivity {
    TextView classInformationOfSimulation;
    ListView simulationList;
    Button closeSimulation;
    Button startSimulation;
    //List<StudentsPair> studentsPairList = new ArrayList<>();

    @Override public void onBackPressed() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simulation);

        Intent intent = getIntent();
        int matchingModeId = intent.getIntExtra("mode", -1);
        boolean overlap = intent.getBooleanExtra("duplicated", false);

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