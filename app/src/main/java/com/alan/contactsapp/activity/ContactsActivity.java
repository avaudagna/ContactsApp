package com.alan.contactsapp.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.alan.contactsapp.R;

/**
 * @author alan
 */
public class ContactsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        setTitle(R.string.title);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
