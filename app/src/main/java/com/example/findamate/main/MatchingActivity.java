package com.example.findamate.main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.example.findamate.R;
import com.example.findamate.adapter.LogMatchingAdapter;
import com.example.findamate.domain.Student;
import com.example.findamate.domain.StudentsPair;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class MatchingActivity extends AppCompatActivity {
    TextView classInformationOfMatching;
    ListView resultList;
    List<Student> students = new ArrayList<>();
    List<StudentsPair> studentsPairList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matching);

        students.add(new Student("20519", "임준형",true));
        students.add(new Student("2400", "랄로",true));
        students.add(new Student("20514", "파카",true));
        students.add(new Student("20516", "괴물쥐",false));
        students.add(new Student("20518", "로지컬",true));
        students.add(new Student("20510", "도파",false));
        students.add(new Student("24519", "감스트",true));
        students.add(new Student("2401", "진용진",true));
        students.add(new Student("2402", "전국진",false));
        students.add(new Student("2403", "논리왕 전기",false));
        students.add(new Student("2404", "미야",false));
        students.add(new Student("2405", "구루루",true));
        students.add(new Student("1105", "우주하마",true));

        students.get(0).addFavoritePartner(students.get(1));
        students.get(0).addFavoritePartner(students.get(8));
        students.get(0).addFavoritePartner(students.get(3));

        students.get(1).addFavoritePartner(students.get(10));
        students.get(1).addFavoritePartner(students.get(11));
        students.get(1).addFavoritePartner(students.get(9));

        students.get(2).addFavoritePartner(students.get(9));
        students.get(2).addFavoritePartner(students.get(6));
        students.get(2).addFavoritePartner(students.get(10));

        students.get(3).addFavoritePartner(students.get(1));
        students.get(3).addFavoritePartner(students.get(0));
        students.get(3).addFavoritePartner(students.get(10));

        students.get(4).addFavoritePartner(students.get(7));
        students.get(4).addFavoritePartner(students.get(8));
        students.get(4).addFavoritePartner(students.get(9));

        students.get(5).addFavoritePartner(students.get(1));
        students.get(5).addFavoritePartner(students.get(2));
        students.get(5).addFavoritePartner(students.get(0));

        students.get(6).addFavoritePartner(students.get(4));
        students.get(6).addFavoritePartner(students.get(0));
        students.get(6).addFavoritePartner(students.get(5));

        students.get(7).addFavoritePartner(students.get(8));
        students.get(7).addFavoritePartner(students.get(6));
        students.get(7).addFavoritePartner(students.get(10));

        students.get(8).addFavoritePartner(students.get(3));
        students.get(8).addFavoritePartner(students.get(11));
        students.get(8).addFavoritePartner(students.get(5));

        students.get(9).addFavoritePartner(students.get(4));
        students.get(9).addFavoritePartner(students.get(10));
        students.get(9).addFavoritePartner(students.get(6));

        students.get(10).addFavoritePartner(students.get(1));
        students.get(10).addFavoritePartner(students.get(5));
        students.get(10).addFavoritePartner(students.get(4));

        students.get(11).addFavoritePartner(students.get(9));
        students.get(11).addFavoritePartner(students.get(4));
        students.get(11).addFavoritePartner(students.get(7));

        students.get(12).addFavoritePartner(students.get(9));
        students.get(12).addFavoritePartner(students.get(4));
        students.get(12).addFavoritePartner(students.get(7));

        Intent intent = getIntent();
        String classInformation = intent.getStringExtra("classInformation");
        int matchingModeId = intent.getIntExtra("matchingModeId", -1);
        boolean overlap = intent.getBooleanExtra("overlap", false);

        classInformationOfMatching = findViewById(R.id.classInformationOfMatching);
        resultList = findViewById(R.id.resultList);

        classInformationOfMatching.setText(classInformation);
        matchingPartner(overlap);

        /*for(int i = 0; i < students.size(); i++) {
            Student student = students.get(i);

            for(int j = 0; j < student.getPartners().size(); j++) {
                Student partner = student.getPartners().get(j);
                Log.d("wtf", student.getName() + "    " + partner.getName() + "    " + student.getScore());
            }
        }*/

        LogMatchingAdapter resultAdapter = new LogMatchingAdapter(this, studentsPairList);
        resultList.setAdapter(resultAdapter);
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
    }
}