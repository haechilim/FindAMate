package com.example.findamate.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.findamate.R;
import com.example.findamate.domain.Student;
import com.example.findamate.manager.StudentViewManager;

public class StudentView extends LinearLayout {
    private Student student;
    private Context context;

    public StudentView(Context context, Student student) {
        super(context);

        this.context = context;
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

        if(student == null) return;

        StudentViewManager.setStudentView(context, this, student);
    }

    public void hideExtra() {
        findViewById(R.id.statusMessage).setVisibility(INVISIBLE);
        findViewById(R.id.score).setVisibility(INVISIBLE);
    }
}