package com.example.findamate.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.ListView;

import com.example.findamate.R;
import com.example.findamate.adapter.ContactAdapter;
import com.example.findamate.domain.Contact;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class PopupContactActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup_contact);

        ContactAdapter contactAdapter = new ContactAdapter(this, getContacts());
        ((ListView)findViewById(R.id.list)).setAdapter(contactAdapter);
    }
    
    private List<Contact> getContacts() {
        List<Contact> contacts = new ArrayList<>();
        Cursor cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,null, null, null);

        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

            contacts.add(new Contact(name, getPhoneNumber(number)));
        }
        cursor.close();

        return contacts;
    }

    public static String getPhoneNumber(String number) {
        if(!Pattern.matches("^\\d{10,11}$", number)) return number;

        int index = number.length() == 10 ? 6 : 7;

        return number.substring(0, 3) + "-" + number.substring(3, index) + "-" + number.substring(index, index + 4);
    }
}