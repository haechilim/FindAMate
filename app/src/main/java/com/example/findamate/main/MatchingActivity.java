package com.example.findamate.main;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.example.findamate.R;
import com.example.findamate.adapter.LogMatchingAdapter;
import com.example.findamate.domain.History;
import com.example.findamate.domain.Student;
import com.example.findamate.domain.StudentsPair;

import java.util.ArrayList;
import java.util.List;

public class MatchingActivity extends AppCompatActivity {
    ListView resultList;
    List<Student> students = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matching);

        students.add(new Student("20519", "임준형"));
        students.add(new Student("2400", "랄로"));
        students.add(new Student("20514", "파카"));
        students.add(new Student("20516", "괴물쥐"));
        students.add(new Student("20518", "로지컬"));
        students.add(new Student("20510", "도파"));
        students.add(new Student("24519", "감스트"));
        students.add(new Student("2401", "진용진"));
        students.add(new Student("2402", "전국진"));
        students.add(new Student("2403", "논리왕 전기"));
        students.add(new Student("2404", "미야"));
        students.add(new Student("2405", "구루루"));

        resultList = findViewById(R.id.resultList);

        //LogMatchingAdapter resultAdapter = new LogMatchingAdapter(this, history.getStudentsPairList());
        //resultList.setAdapter(resultAdapter);
    }

    private boolean matchingPartner() {
        for (int choice = 0; choice < students.size() - 1; choice++) {
            for (int i = 0; i < students.size(); i++) {
                Student student = students.get(i);
                Student partner = student.getFavoritePartner(choice);

                if (student.isHasPartner() || partner.isHasPartner()) continue;
                if (student.isExpartner(partner)) continue;

                if (partner.getFavoritePartner(0).getId() == student.getId()) {
                    makePartner(student, partner);
                    updateScore(student, partner);
                }
            }
        }

        for (int i = 0; i < students.size(); i++) {
            Student student = students.get(i);

            if(student.isHasPartner()) continue;

            for (int choice = 0; choice < students.size() - 1; choice++) {
                Student partner = student.getFavoritePartner(choice);

                if(student.isExpartner(partner)) continue;
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
    }

    private void updateScore(Student student, Student partner) {
        student.addScore(student.getFavoritePartnerIndex(partner));
        partner.addScore(partner.getFavoritePartnerIndex(student));
    }

    private boolean everyoneHasPartner() {
        for(int i = 0; i < students.size(); i++) {
            if(!students.get(i).isHasPartner()) return false;
        }

        return true;
    }
}