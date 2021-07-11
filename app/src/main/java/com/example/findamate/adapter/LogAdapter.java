package com.example.findamate.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.findamate.R;
import com.example.findamate.domain.Classroom;
import com.example.findamate.domain.Couple;
import com.example.findamate.domain.History;
import com.example.findamate.domain.Student;
import com.example.findamate.helper.Logger;
import com.example.findamate.helper.Util;
import com.example.findamate.manager.StudentViewManager;
import com.example.findamate.view.CoupleView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class LogAdapter extends BaseAdapter {
    private Activity activity;
    private List<History> histories;
    private boolean isPoll;

    private int itemsPerRow;

    public LogAdapter(Activity activity, List<History> histories, boolean isPoll) {
        this.activity = activity;
        this.histories = histories;
        this.isPoll = isPoll;

        itemsPerRow = Util.getItemPerRow(activity);
    }

    @Override
    public int getCount() {
        return histories.size();
    }

    @Override
    public Object getItem(int position) {
        return histories.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        History history = histories.get(position);
        List<Couple> couples = history.getCouples();
        View rootView = convertView;

        if(convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rootView = layoutInflater.inflate(R.layout.layout_list_history, parent, false);
        }

        if(isPoll) {
            rootView.findViewById(R.id.decoration).setVisibility(View.GONE);
            rootView.findViewById(R.id.date).setVisibility(View.GONE);
        }

        LinearLayout couplesRow = null;
        LinearLayout container = rootView.findViewById(R.id.couplesContainer);

        initView(container);

        for (int index = 0; index < couples.size(); index++) {
            if (index % itemsPerRow == 0) couplesRow = addCouplesRow(container, index / itemsPerRow);
            addCoupleView(couplesRow, index % itemsPerRow, couples.get(index), position != 0);
        }

        ((TextView) rootView.findViewById(R.id.date)).setText(formatDate(history));

        return rootView;
    }

    private void initView(ViewGroup container) {
        for(int i = 0; i < container.getChildCount(); i++) {
            ViewGroup rowContainer = (ViewGroup)container.getChildAt(i);
            rowContainer.setVisibility(View.GONE);

            for(int j = 0; j < rowContainer.getChildCount(); j++) {
                rowContainer.getChildAt(j).setVisibility(View.GONE);
            }
        }
    }

    private LinearLayout addCouplesRow(ViewGroup container, int position) {
        if(position < container.getChildCount()) {
            LinearLayout view = (LinearLayout)container.getChildAt(position);
            view.setVisibility(View.VISIBLE);
            return view;
        }

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0, 0, 0, Util.dpToPx(activity.getWindowManager(), 30));

        LinearLayout view = new LinearLayout(activity);
        view.setOrientation(LinearLayout.HORIZONTAL);
        view.setGravity(Gravity.LEFT);
        container.addView(view, layoutParams);

        return view;
    }

    private View addCoupleView(ViewGroup container, int position, Couple couple, boolean hideExtra) {
        Student student1 = couple.getStudent1();
        Student student2 = couple.getStudent2();

        if(position < container.getChildCount()) {
            LinearLayout view = (LinearLayout)container.getChildAt(position);

            LinearLayout linearLayout = ((LinearLayout)view.getChildAt(0));
            LinearLayout studentView1 = ((LinearLayout)linearLayout.getChildAt(0));
            LinearLayout studentView2 = ((LinearLayout)linearLayout.getChildAt(2));

            StudentViewManager.setStudentView(activity, studentView1, student1);
            StudentViewManager.setStudentView(activity, studentView2, student2);
            view.setVisibility(View.VISIBLE);
            return view;
        }

        CoupleView view = new CoupleView(activity, student1, student2, hideExtra);
        container.addView(view);

        return view;
    }

    private String formatDate(History history) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(History.DATE_FORMAT);
            Date date = simpleDateFormat.parse(history.getDate());
            simpleDateFormat = new SimpleDateFormat("yyyy년 M월 d일 H시 m분 s초");
            return simpleDateFormat.format(date);
        } catch (ParseException e) {
            Logger.debug(e.getMessage());
        }

        return "";
    }
}