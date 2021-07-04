package com.example.findamate.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
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

        ((TextView) view.findViewById(R.id.name)).setText(contact.getName());
        ((TextView) view.findViewById(R.id.number)).setText(contact.getNumber());

        return view;
    }
}
