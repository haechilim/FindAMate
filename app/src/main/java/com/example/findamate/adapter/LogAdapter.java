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
import com.example.findamate.domain.Couple;
import com.example.findamate.domain.History;
import com.example.findamate.domain.Student;
import com.example.findamate.helper.Util;
import com.example.findamate.view.CoupleView;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;

public class LogAdapter extends BaseAdapter {
    private final int COUNT = 5; //기록 화면 한줄에 보이는 커플 수

    private Activity activity;
    private List<History> histories;

    public LogAdapter(Activity activity, List<History> histories) {
        this.activity = activity;
        Collections.reverse(histories);
        this.histories = histories;
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
        List<Couple> couples = histories.get(position).getCouples();

        LayoutInflater layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.layout_list_history, parent, false);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy년 MM월 dd일");
        ((TextView) view.findViewById(R.id.date)).setText(simpleDateFormat.format(histories.get(position).getCalendar().getTimeInMillis()));
        LinearLayout container = view.findViewById(R.id.couplesContainer);
        LinearLayout couplesContainer = null;
        LinearLayout.LayoutParams couplesParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        couplesParams.setMargins(0,0,0, Util.dpToPx(activity.getWindowManager(), 30));

        for(int index = 0; index < couples.size(); index++) {
            if(index % COUNT == 0) {
                couplesContainer = new LinearLayout(activity);
                couplesContainer.setOrientation(LinearLayout.HORIZONTAL);
                couplesContainer.setGravity(Gravity.LEFT);
            }

            Couple couple = couples.get(index);
            Student student1 = couple.getStudent1();
            Student student2 = couple.getStudent2();

            CoupleView coupleView = new CoupleView(activity, student1, student2);
            couplesContainer.addView(coupleView);

            if(index % COUNT == COUNT - 1 || index >= couples.size() - 1) container.addView(couplesContainer, couplesParams);
        }

        return view;
    }
}
