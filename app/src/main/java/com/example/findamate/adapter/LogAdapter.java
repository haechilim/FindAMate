package com.example.findamate.adapter;

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
import com.example.findamate.view.CoupleView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class LogAdapter extends BaseAdapter {
    private Context context;
    private List<History> histories = new ArrayList<>();

    public LogAdapter(Context context, List<History> histories) {
        this.context = context;
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
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.layout_list_history, parent, false);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy년 MM월 dd일");

        ((TextView) view.findViewById(R.id.date)).setText(simpleDateFormat.format(histories.get(position).getCalendar().getTimeInMillis()));
        LinearLayout container = view.findViewById(R.id.couplesContainer);
        LinearLayout couplesContainer = new LinearLayout(context);
        LinearLayout.LayoutParams couplesContainerParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        History history = histories.get(position);

        container.setOrientation(LinearLayout.VERTICAL);
        couplesContainer.setOrientation(LinearLayout.HORIZONTAL);
        couplesContainer.setGravity(Gravity.CENTER);

        for(int i = 0; i < 5/*history.getCouples().size()*/; i++) {
            Couple couple = history.getCouples().get(i);
            Student student1 = couple.getStudent1();
            Student student2 = couple.getStudent2();

            CoupleView coupleView = new CoupleView(context, student1, student2);
            couplesContainer.addView(coupleView);
        }

        container.addView(couplesContainer, couplesContainerParams);

        return view;
    }
}
