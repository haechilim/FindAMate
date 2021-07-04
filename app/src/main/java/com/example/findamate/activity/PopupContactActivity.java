package com.example.findamate.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.ListView;

import com.example.findamate.R;
import com.example.findamate.adapter.ContactAdapter;
import com.example.findamate.domain.Classroom;
import com.example.findamate.domain.Contact;
import com.example.findamate.domain.School;
import com.example.findamate.domain.Student;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class PopupContactActivity extends AppCompatActivity {
    List<Contact> contacts = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup_contact);

        getContacts();

        ContactAdapter contactAdapter = new ContactAdapter(this, contacts);
        ((ListView)findViewById(R.id.list)).setAdapter(contactAdapter);

        bindEvents();
    }

    private void bindEvents() {
        findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        findViewById(R.id.confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void getContacts() {
        Cursor cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,null, null, null);

        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

            contacts.add(new Contact(name, getPhoneNumber(number)));
        }
        cursor.close();
    }

    public static String getPhoneNumber(String number) {
        if(!Pattern.matches("^\\d{10,11}$", number)) return number;

        int index = number.length() == 10 ? 6 : 7;

        return number.substring(0, 3) + "-" + number.substring(3, index) + "-" + number.substring(index, index + 4);
    }
}