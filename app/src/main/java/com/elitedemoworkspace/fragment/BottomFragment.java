package com.elitedemoworkspace.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.elitedemoworkspace.dbutil.Contact;
import com.elitedemoworkspace.dbutil.DBManager;
import com.elitedemoworkspace.adapter.ContactAdapter;
import com.elitedemoworkspace.R;
import com.elitedemoworkspace.api.asynctask.ContactService;

import java.util.List;

public class BottomFragment extends Fragment {
    private ListView mlv_contacts;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.fragment_bottom, container, false);
        mlv_contacts = (ListView)rootView.findViewById(R.id.lv_contacts);

        new ContactService(BottomFragment.this).execute("");
        return rootView;
    }


    public void setAdapter() {
        List<Contact> contact = new DBManager(getActivity()).getData();
        ContactAdapter contactAdapter = new ContactAdapter(getActivity(),contact);
        mlv_contacts.setAdapter(contactAdapter);
    }
}
