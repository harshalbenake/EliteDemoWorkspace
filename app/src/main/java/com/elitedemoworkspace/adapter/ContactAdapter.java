package com.elitedemoworkspace.adapter;

import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.elitedemoworkspace.dbutil.Contact;
import com.elitedemoworkspace.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ContactAdapter extends BaseAdapter {
    FragmentActivity mActivity;
    List<Contact> mArrContact;
    public ContactAdapter(FragmentActivity activity, List<Contact> arrContact) {
        this.mActivity=activity;
        this.mArrContact=arrContact;
    }

    @Override
    public int getCount() {
        return mArrContact.size();

    }

    @Override
    public Object getItem(int position) {
        return mArrContact.get(position);
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
            convertView = mInflater.inflate(R.layout.rowitem_contacts, null);
            holder = new ViewHolder();
            holder.iv_contact_profile = (ImageView) convertView.findViewById(R.id.iv_contact_profile);
            holder.tv_contact_name = (TextView) convertView.findViewById(R.id.tv_contact_name);
            holder.tv_contact_email = (TextView) convertView.findViewById(R.id.tv_contact_email);
            holder.tv_contact_address = (TextView) convertView.findViewById(R.id.tv_contact_address);
            holder.tv_contact_gender = (TextView) convertView.findViewById(R.id.tv_contact_gender);
            holder.tv_contact_mobile = (TextView) convertView.findViewById(R.id.tv_contact_mobile);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }
        Contact contact = (Contact) getItem(position);
        holder.tv_contact_name.setText(contact.name);
        holder.tv_contact_email.setText(contact.email);
        holder.tv_contact_address.setText(contact.address);
        holder.tv_contact_gender.setText(contact.gender);
        holder.tv_contact_mobile.setText(contact.mobile);

        Picasso.get().load("https://www.freeiconspng.com/uploads/profile-icon-28.png").into(holder.iv_contact_profile);

        return convertView;
    }

    private class ViewHolder {
        ImageView iv_contact_profile;
        TextView tv_contact_name;
        TextView tv_contact_email;
        TextView tv_contact_address;
        TextView tv_contact_gender;
        TextView tv_contact_mobile;
    }
}
