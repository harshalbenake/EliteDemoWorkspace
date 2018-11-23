package com.elitedemoworkspace.api.asynctask;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.util.ArrayMap;
import android.widget.Toast;


import com.elitedemoworkspace.DBUtil.Contact;
import com.elitedemoworkspace.DBUtil.DBManager;
import com.elitedemoworkspace.activity.DynamicListActivity;
import com.elitedemoworkspace.api.OKHTTPService;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * This async task is used for Contact Service
 */
public class ContactService extends AsyncTask<String, String, String> {
    Context mContext;
    private ProgressDialog mProgressDialog;

    public ContactService(Context context) {
        this.mContext = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (OKHTTPService.isOnline(mContext)) {
            mProgressDialog = new ProgressDialog(mContext);
            mProgressDialog.setMessage("Loading Details...");
            mProgressDialog.setCancelable(false);
            mProgressDialog.setCanceledOnTouchOutside(false);
            mProgressDialog.show();
        } else {
            Toast.makeText(mContext, "Not connected to Internet", Toast.LENGTH_SHORT).show();
            cancel(true);
        }
    }

    @Override
    protected String doInBackground(String... params) {
        if (!isCancelled()) {
            try {
                String strResponse = OKHTTPService.requestACallToServer("https://tinyurl.com/elitetechnologieswebservice");
                System.out.println("strResponse: " + strResponse);
                JSONObject jsonObjectResponse = new JSONObject(strResponse);
                JSONArray contactsArr = jsonObjectResponse.optJSONArray("contacts");
                for (int item = 0; item < contactsArr.length(); item++) {
                    JSONObject contactsObj=contactsArr.getJSONObject(item);
                    String srrName=contactsObj.optString("name");
                    System.out.println("srrName: "+srrName);
                    String srrEmail=contactsObj.optString("email");
                    String srrAddress=contactsObj.optString("address");
                    String srrGender=contactsObj.optString("gender");
                    JSONObject phoneObj=contactsObj.optJSONObject("phone");
                    String strMobile=phoneObj.optString("mobile");
                    Contact contact=new Contact();
                    contact.name=srrName;
                    contact.email=srrEmail;
                    contact.address=srrAddress;
                    contact.gender=srrGender;
                    contact.mobile=strMobile;
                    new DBManager(mContext).addData(contact);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        try {
            if (mProgressDialog != null && mProgressDialog.isShowing()==true) {
                mProgressDialog.dismiss();
            }

            if(mContext instanceof DynamicListActivity){
                ((DynamicListActivity) mContext).setAdapter();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
