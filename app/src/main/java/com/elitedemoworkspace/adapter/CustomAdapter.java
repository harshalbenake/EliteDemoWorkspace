package com.elitedemoworkspace.adapter;

import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.elitedemoworkspace.PersonInfo;
import com.elitedemoworkspace.R;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {
    FragmentActivity mActivity;
    ArrayList<PersonInfo> mArrPersonInfo;
    public CustomAdapter(FragmentActivity activity, ArrayList<PersonInfo> arrPersonInfo) {
        this.mActivity=activity;
        this.mArrPersonInfo=arrPersonInfo;
    }

    @Override
    public int getCount() {
        return mArrPersonInfo.size();

    }

    @Override
    public Object getItem(int position) {
        return mArrPersonInfo.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        LayoutInflater mInflater = (LayoutInflater)mActivity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.rowitem_personinfo, null);
            holder = new ViewHolder();
            holder.tv_personinfo_name = (TextView) convertView.findViewById(R.id.tv_personinfo_name);
            holder.tv_personinfo_email = (TextView) convertView.findViewById(R.id.tv_personinfo_email);
            holder.tv_personinfo_age = (TextView) convertView.findViewById(R.id.tv_personinfo_age);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        PersonInfo personInfo = (PersonInfo) getItem(position);

        holder.tv_personinfo_name.setText(personInfo.strName);
        holder.tv_personinfo_email.setText(personInfo.strEmail);
        holder.tv_personinfo_age.setText(""+personInfo.age);

        return convertView;
    }

    private class ViewHolder {
        TextView tv_personinfo_name;
        TextView tv_personinfo_email;
        TextView tv_personinfo_age;
    }
}
