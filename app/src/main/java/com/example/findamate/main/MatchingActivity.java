package com.example.findamate.main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.findamate.R;
import com.example.findamate.domain.Couple;
import com.example.findamate.domain.Student;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class MatchingActivity extends AppCompatActivity {
    TextView classInformationOfMatching;
    LinearLayout resultContainer;
    List<Student> students = new ArrayList<>();
    List<Couple> couples = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matching);

        students.add(new Student("20519", "임준형",true,"haechilim", R.drawable.avatar10, "카무이!"));
        students.add(new Student("2400", "랄로",true,"haechilim", R.drawable.avatar22, "카무이!"));
        students.add(new Student("20514", "파카",true,"haechilim", R.drawable.avatar49, "카무이!"));
        students.add(new Student("20516", "괴물쥐",false,"haechilim", R.drawable.avatar27, "카무이!"));
        students.add(new Student("20518", "로지컬",true,"haechilim", R.drawable.avatar44, "카무이!"));
        students.add(new Student("20510", "도파",false,"haechilim", R.drawable.avatar30, "카무이!"));
        students.add(new Student("24519", "감스트",true,"haechilim", R.drawable.avatar29, "카무이!"));
        students.add(new Student("2401", "진용진",true,"haechilim", R.drawable.avatar19, "카무이!"));
        students.add(new Student("2402", "전국진",false,"haechilim", R.drawable.avatar11, "카무이!"));
        students.add(new Student("2403", "논리왕 전기",false,"haechilim", R.drawable.avatar33, "카무이!"));
        students.add(new Student("2404", "미야",false,"haechilim", R.drawable.avatar32, "카무이!"));
        students.add(new Student("2405", "구루루",true,"haechilim", R.drawable.avatar23, "카무이!"));
        students.add(new Student("1105", "우주하마",true,"haechilim", R.drawable.avatar43, "카무이!"));

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
        boolean isSimulation = intent.getBooleanExtra("isSimulation", false);
        int matchingModeId = intent.getIntExtra("matchingModeId", -1);
        boolean overlap = intent.getBooleanExtra("overlap", false);

        classInformationOfMatching = findViewById(R.id.classInformationOfMatching);
        resultContainer = findViewById(R.id.resultContainer);

        init(classInformation);

        matchingPartner(matchingModeId, overlap);

        /*for(int i = 0; i < students.size(); i++) {
            Student student = students.get(i);

            for(int j = 0; j < student.getPartners().size(); j++) {
                Student partner = student.getPartners().get(j);
                Log.d("wtf", student.getName() + "    " + partner.getName() + "    " + student.getScore());
            }
        }*/

      /*  LogMatchingAdapter resultAdapter = new LogMatchingAdapter(this, studentsPairList);
        resultList.setAdapter(resultAdapter);*/
    }

    private void init(String classInformation) {
        classInformationOfMatching.setText(classInformation);
        LayoutInflater layoutInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutInflater.inflate(R.layout.layout_list_history, resultContainer, true);
    }

    private boolean matchingPartner(int matchingModeId, boolean overlap) {
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

        couples.add(new Couple(student, partner));
    }

    private void makePartner(Student student) {
        student.addPartner(student);

        student.setHasPartner(true);

        couples.add(new Couple(student, student));
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