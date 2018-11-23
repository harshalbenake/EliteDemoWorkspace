package com.elitedemoworkspace.api;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * This class is used fto get and post service response to server using okhttp.
 */
public class OKHTTPService {
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    public static int TYPE_WIFI = 1;
    public static int TYPE_MOBILE = 2;
    public static int TYPE_NOT_CONNECTED = 0;
    // Class For Encryption Decryption :

    public OKHTTPService() {
    }

    /**
     * gets Ok Http Service
     *
     * @param strUrl
     * @return
     */
    public static String requestACallToServer(String strUrl) {
        String strResponse = "";
        try {
            Request request = new Request.Builder()
                    .url(strUrl)
                    .get()
                    .build();
            OkHttpClient.Builder builder = new OkHttpClient.Builder().protocols(Arrays.asList(Protocol.HTTP_1_1));
            builder.connectTimeout(3, TimeUnit.MINUTES)
                    .writeTimeout(3, TimeUnit.MINUTES)
                    .readTimeout(3, TimeUnit.MINUTES);
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                @Override
                public void log(String message) {
                    Log.d("HttpLogging", message);
                }
            });
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .protocols(Arrays.asList(Protocol.HTTP_1_1))
                    .addInterceptor(logging)
                    .connectTimeout(3, TimeUnit.MINUTES)
                    .writeTimeout(3, TimeUnit.MINUTES)
                    .readTimeout(3, TimeUnit.MINUTES)
                    .build();
            Response response = okHttpClient.newCall(request).execute();
            strResponse = response.body().string();
            System.out.println("OkHttp get Response: " + strResponse);
            return strResponse;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strResponse;
    }


    public static int getConnectivityStatus(Context context) {
        try {
            ConnectivityManager cm = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            if (null != activeNetwork) {
                if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI)
                    return TYPE_WIFI;

                if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)
                    return TYPE_MOBILE;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return TYPE_NOT_CONNECTED;
    }

    public static boolean isOnline(Context context) {
        int status = getConnectivityStatus(context);
        if (status == TYPE_NOT_CONNECTED) {
            return false;
        } else {
            return true;
        }
    }
}
