package com.example.findamate.activity;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.findamate.R;
import com.example.findamate.adapter.ContactAdapter;
import com.example.findamate.domain.Classroom;
import com.example.findamate.domain.Contact;
import com.example.findamate.domain.Student;
import com.example.findamate.helper.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;

public class PopupContactActivity extends AppCompatActivity {
    public static int RESULT_LOAD = 800;

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
                for(int i = 0; i < contacts.size(); i++) {
                    Contact contact = contacts.get(i);

                    if(contact.getCheckMode() == Contact.CHECK_NONE) continue;

                    Classroom.tempStudents.add(new Student(contact.getName(),
                            contact.getCheckMode() == Contact.CHECK_MALE, contact.getNumber(),
                            new Random().nextInt(MainActivity.AVATAR_COUNT) + 1));
                }

                if(Classroom.tempStudents.isEmpty()) {
                    Util.toast(PopupContactActivity.this, "1명 이상의 학생을 선택해 주세요.", true);
                    return;
                }

                setResult(RESULT_LOAD);
                finish();
            }
        });
    }

    private void getContacts() {
        Cursor cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,null, null, null);

        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

            if(!Pattern.matches("^\\d{10,11}$", number)) continue;

            contacts.add(new Contact(name, number));
        }
        cursor.close();
    }
}