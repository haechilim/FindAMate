package com.example.findamate.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.icu.text.MessagePattern;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.findamate.R;
import com.example.findamate.domain.Contact;

import java.util.List;

public class ContactAdapter extends BaseAdapter {
    Activity activity;
    List<Contact> contacts;

    public ContactAdapter(Activity activity, List<Contact> contacts) {
        this.activity = activity;
        this.contacts = contacts;
    }

    @Override
    public int getCount() {
        return contacts.size();
    }

    @Override
    public Object getItem(int position) {
        return contacts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Contact contact = contacts.get(position);

        LayoutInflater layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.layout_contact_item, parent, false);

        updateName(view, contact.getName());
        updateNumber(view, contact.getNumber());
        updateCheckBox(view, contact.getCheckMode());

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contact.nextCheckMode();
                updateCheckBox(v, contact.getCheckMode());
                updateCount();
                notifyDataSetChanged();
            }
        });

        return view;
    }

    private void updateName(View view, String name) {
        ((TextView) view.findViewById(R.id.name)).setText(name);
    }

    private void updateNumber(View view, String number) {
        ((TextView) view.findViewById(R.id.number)).setText(number);
    }

    private void updateCheckBox(View view, int checkMode) {
        int drawable;

        if(checkMode == Contact.CHECK_NONE) drawable = R.drawable.layout_check;
        else if(checkMode == Contact.CHECK_MALE) drawable = R.drawable.layout_checked_male;
        else drawable = R.drawable.layout_checked_female;

        view.findViewById(R.id.check).setBackground(activity.getResources().getDrawable(drawable, activity.getTheme()));
    }

    private void updateCount() {
        int maleCount = 0;
        int femaleCount = 0;

        for(int i = 0; i < contacts.size(); i++) {
            Contact contact = contacts.get(i);

            if(contact.getCheckMode() == Contact.CHECK_NONE) continue;
            else if(contact.getCheckMode() == Contact.CHECK_MALE) maleCount++;
            else if(contact.getCheckMode() == Contact.CHECK_FEMALE) femaleCount++;
        }

        ((TextView)activity.findViewById(R.id.selectedMaleCount)).setText(maleCount + " 명");
        ((TextView)activity.findViewById(R.id.selectedFemaleCount)).setText(femaleCount + " 명");
    }
}
