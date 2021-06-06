package com.example.findamate.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

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

        LinearLayout container1 = findViewById(R.id.student1);
        LinearLayout container2 = findViewById(R.id.student2);

        StudentView studentView1 = new StudentView(context, student1);
        StudentView studentView2 = new StudentView(context, student2);

        container1.addView(studentView1);
        container2.addView(studentView2);
    }
}
