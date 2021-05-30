package com.example.findamate.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.findamate.R;
import com.example.findamate.domain.MatchingResult;
import com.example.findamate.main.HistoryActivity;

import java.util.ArrayList;
import java.util.List;

public class HistoryAdapter extends BaseAdapter {
    Context context;
    List<MatchingResult> histories = new ArrayList<>();

    public HistoryAdapter(Context context, List<MatchingResult> histories) {
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
        int historyNumber = position + 1;

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.layout_list_history, parent, false);

        ((TextView)view.findViewById(R.id.historyNumber)).setText("기록 " + historyNumber);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, HistoryActivity.class);
                intent.putExtra("history number", historyNumber);
                context.startActivity(intent);
            }
        });
        
        return view;
    }
}
