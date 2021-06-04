package com.example.findamate.helper;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.findamate.R;
import com.example.findamate.domain.Student;

public class CoupleView extends LinearLayout {
    private Student student1;
    private Student student2;

    public CoupleView(Context context, Student student1, Student student2) {
        super(context);
        this.student1 = student1;
        this.student2 = student2;
        init(context);
    }

    public CoupleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CoupleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public CoupleView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutInflater.inflate(R.layout.layout_couple, this, true);

        StudentView nameOfStudent1 = findViewById(R.id.nameOfStudent1);
        StudentView nameOfStudent2 = findViewById(R.id.nameOfStudent2);
        ((TextView) nameOfStudent1.findViewById(R.id.name)).setText(student1.getId() + " " + student1.getName());
        ((TextView) nameOfStudent2.findViewById(R.id.name)).setText(student2.getId() + " " + student2.getName());
    }
}
