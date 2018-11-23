package com.elitedemoworkspace.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.elitedemoworkspace.adapter.CustomAdapter;
import com.elitedemoworkspace.PersonInfo;
import com.elitedemoworkspace.R;

import java.util.ArrayList;

public class BottomFragment extends Fragment {
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.fragment_bottom, container, false);
        ListView lv_personinfo=(ListView)rootView.findViewById(R.id.lv_personinfo);

        PersonInfo personInfo1=new PersonInfo("Name1","name1@gmail.com",18);
        PersonInfo personInfo2=new PersonInfo("Name2","name2@gmail.com",18);
        PersonInfo personInfo3=new PersonInfo("Name3","name3@gmail.com",18);
        PersonInfo personInfo4=new PersonInfo("Name4","name4@gmail.com",18);
        PersonInfo personInfo5=new PersonInfo("Name5","name5@gmail.com",18);

        ArrayList<PersonInfo> arrPersonInfo=new ArrayList<>();
        arrPersonInfo.add(personInfo1);
        arrPersonInfo.add(personInfo2);
        arrPersonInfo.add(personInfo3);
        arrPersonInfo.add(personInfo4);
        arrPersonInfo.add(personInfo5);

        CustomAdapter customAdapter=new CustomAdapter(getActivity(),arrPersonInfo);

        lv_personinfo.setAdapter(customAdapter);

        lv_personinfo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PersonInfo personInfo=(PersonInfo)parent.getItemAtPosition(position);
                Toast.makeText(getActivity(),""+personInfo.strEmail,Toast.LENGTH_SHORT).show();
            }
        });
        return rootView;
    }
}
