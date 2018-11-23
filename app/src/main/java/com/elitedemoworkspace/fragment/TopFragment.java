package com.elitedemoworkspace.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.elitedemoworkspace.R;

import java.util.ArrayList;

public class TopFragment extends Fragment {
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.fragment_top, container, false);
        ListView lv_names=(ListView)rootView.findViewById(R.id.lv_names);
        ArrayList<String> arrNames=new ArrayList<>();
        arrNames.add("Name1");
        arrNames.add("Name2");
        arrNames.add("Name3");
        arrNames.add("Name4");
        arrNames.add("Name5");
        ArrayAdapter arrayAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1, arrNames);
        lv_names.setAdapter(arrayAdapter);

        lv_names.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(),""+parent.getItemAtPosition(position),Toast.LENGTH_SHORT).show();
            }
        });
        return rootView;
    }
}
