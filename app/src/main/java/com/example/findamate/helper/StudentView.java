package com.example.findamate.helper;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.findamate.R;
import com.example.findamate.domain.Student;
import com.example.findamate.main.MainActivity;
import com.example.findamate.main.PopupStudentSettingActivity;

public class StudentView extends LinearLayout {
    private Student student;

    public StudentView(Context context, Student student) {
        super(context);

        this.student = student;
        init(context);
    }

    public StudentView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public StudentView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public StudentView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutInflater.inflate(R.layout.layout_profile, this, true);

        ImageView avatar = findViewById(R.id.avatarImage);
        TextView nameView = findViewById(R.id.name);
        TextView statusMessage = findViewById(R.id.statusMessage);
        TextView score = findViewById(R.id.score);

        avatar.setImageResource(student.getAvatarId());
        nameView.setText(student.getName());
        statusMessage.setText(student.getStatusMessage());
        score.setText(student.getScore() + "%");
    }
}