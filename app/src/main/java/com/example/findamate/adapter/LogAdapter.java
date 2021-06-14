package com.example.findamate.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.findamate.R;
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
    private int itemsPerRow;

    public LogAdapter(Activity activity, List<History> histories) {
        this.activity = activity;
        this.histories = histories;

        WindowManager windowManager = activity.getWindowManager();
        DisplayMetrics metrics = activity.getResources().getDisplayMetrics();
        int width = StudentViewManager.MINI_WIDTH + 15;
        itemsPerRow = (int)Math.floor(metrics.widthPixels / Util.dpToPx(windowManager, width));
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

        LayoutInflater layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.layout_list_history, parent, false);
        ((TextView) view.findViewById(R.id.date)).setText(formatDate(history));
        LinearLayout container = view.findViewById(R.id.couplesContainer);
        LinearLayout couplesContainer = null;
        LinearLayout.LayoutParams couplesParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        couplesParams.setMargins(0,0,0, Util.dpToPx(activity.getWindowManager(), 30));

        for(int index = 0; index < couples.size(); index++) {
            if(index % itemsPerRow == 0) {
                couplesContainer = new LinearLayout(activity);
                couplesContainer.setOrientation(LinearLayout.HORIZONTAL);
                couplesContainer.setGravity(Gravity.LEFT);
            }

            Couple couple = couples.get(index);
            Student student1 = couple.getStudent1();
            Student student2 = couple.getStudent2();

            CoupleView coupleView = new CoupleView(activity, student1, student2, position != 0);
            couplesContainer.addView(coupleView);

            if(index % itemsPerRow == itemsPerRow - 1 || index >= couples.size() - 1) container.addView(couplesContainer, couplesParams);
        }

        return view;
    }

    private String formatDate(History history) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(History.DATE_FORMAT);
            Date date = simpleDateFormat.parse(history.getDate());
            simpleDateFormat = new SimpleDateFormat("yyyy년 M월 d일 H시 m분");
            return simpleDateFormat.format(date);
        } catch (ParseException e) {
            Logger.debug(e.getMessage());
        }

        return "";
    }
}