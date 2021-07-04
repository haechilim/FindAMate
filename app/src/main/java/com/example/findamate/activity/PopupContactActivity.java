package com.example.findamate.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.example.findamate.R;
import com.example.findamate.adapter.ContactAdapter;
import com.example.findamate.domain.Contact;

import java.util.ArrayList;
import java.util.List;

public class PopupContactActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup_contact);

        List<Contact> contacts = new ArrayList<>();
        contacts.add(new Contact("엄준식", "010-1234-5678"));

        ContactAdapter contactAdapter = new ContactAdapter(this, contacts);
        ((ListView)findViewById(R.id.list)).setAdapter(contactAdapter);
    }
}