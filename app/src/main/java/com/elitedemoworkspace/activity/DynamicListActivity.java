package com.elitedemoworkspace.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.elitedemoworkspace.DBUtil.Contact;
import com.elitedemoworkspace.DBUtil.DBManager;
import com.elitedemoworkspace.R;
import com.elitedemoworkspace.adapter.ContactAdapter;
import com.elitedemoworkspace.api.asynctask.ContactService;

import java.util.List;

public class DynamicListActivity extends AppCompatActivity {

    private ListView mlv_contacts;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamiclist);
        mlv_contacts = (ListView) findViewById(R.id.lv_contacts);

        new ContactService(DynamicListActivity.this).execute("");
    }

    public void setAdapter() {
        List<Contact> contact = new DBManager(DynamicListActivity.this).getData();
        ContactAdapter contactAdapter = new ContactAdapter(DynamicListActivity.this,contact);
        mlv_contacts.setAdapter(contactAdapter);
    }
}
