package com.example.findamate.helper;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.findamate.R;

public class StudentView extends LinearLayout {
    private String id;
    private String name;

    public StudentView(Context context, String id, String name) {
        super(context);

        this.id = id;
        this.name = name;
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

        TextView textView = findViewById(R.id.name);
        textView.setText(id + " " + name);
    }
}