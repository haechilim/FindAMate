package com.example.findamate.view;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.example.findamate.R;
import com.example.findamate.domain.Student;
import com.example.findamate.manager.StudentViewManager;

public class CoupleView extends LinearLayout {
    private Activity activity;
    private Student student1;
    private Student student2;
    private boolean hideExtra;

    public CoupleView(Activity activity, Student student1, Student student2, boolean hideExtra) {
        super(activity);
        this.activity = activity;
        this.student1 = student1;
        this.student2 = student2;
        this.hideExtra = hideExtra;
        init(activity);
    }

    public CoupleView(Activity activity, @Nullable AttributeSet attrs) {
        super(activity, attrs);
        init(activity);
    }

    public CoupleView(Activity activity, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(activity, attrs, defStyleAttr);
        init(activity);
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

        StudentView studentView1 = (StudentView)StudentViewManager.newView(activity, student1, true);
        StudentView studentView2 = (StudentView)StudentViewManager.newView(activity, student2, true);

        if(hideExtra) {
            studentView1.hideExtra();
            studentView2.hideExtra();
        }

        container1.addView((View)studentView1.getParent());
        container2.addView((View)studentView2.getParent());
    }
}