package com.example.findamate.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.findamate.R;
import com.example.findamate.domain.MatchingManager;
import com.example.findamate.domain.Student;
import com.example.findamate.domain.StudentsPair;
import com.example.findamate.helper.CustomView;

import java.util.ArrayList;
import java.util.List;

public class LogMatchingAdapter extends BaseAdapter {
    Context context;
    List<StudentsPair> studentsPairList = new ArrayList<>();

    public LogMatchingAdapter(Context context, List<StudentsPair> studentsPairList) {
        this.context = context;
        this.studentsPairList = studentsPairList;
    }

    @Override
    public int getCount() {
        return studentsPairList.size();
    }

    @Override
    public Object getItem(int position) {
        return studentsPairList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Student student1 = studentsPairList.get(position).getStudent1();
        Student student2 = studentsPairList.get(position).getStudent2();

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.layout_log_matching_item, parent, false);

        LinearLayout containerOfStudent1 = view.findViewById(R.id.student1);
        LinearLayout containerOfStudent2 = view.findViewById(R.id.student2);

        CustomView profileOfStudent1 = new CustomView(context, student1.getId(), student1.getName());
        CustomView profileOfStudent2 = new CustomView(context, student2.getId(), student2.getName());

        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        containerOfStudent1.addView(profileOfStudent1, layoutParams);
        containerOfStudent2.addView(profileOfStudent2, layoutParams);

        return view;
    }
}
