package com.elitedemoworkspace.api.asynctask;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.Toast;


import com.elitedemoworkspace.dbutil.Contact;
import com.elitedemoworkspace.dbutil.DBManager;
import com.elitedemoworkspace.api.OKHTTPService;
import com.elitedemoworkspace.fragment.BottomFragment;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * This async task is used for Contact Service
 */
public class ContactService extends AsyncTask<String, String, String> {
    BottomFragment mContext;
    private ProgressDialog mProgressDialog;

    public ContactService(BottomFragment context) {
        this.mContext = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (OKHTTPService.isOnline(mContext.getActivity())) {
            mProgressDialog = new ProgressDialog(mContext.getActivity());
            mProgressDialog.setMessage("Loading Details...");
            mProgressDialog.setCancelable(false);
            mProgressDialog.setCanceledOnTouchOutside(false);
            mProgressDialog.show();
        } else {
            Toast.makeText(mContext.getActivity(), "Not connected to Internet", Toast.LENGTH_SHORT).show();
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
                    new DBManager(mContext.getActivity()).addData(contact);
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

            if(mContext instanceof BottomFragment){
                ((BottomFragment) mContext).setAdapter();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
