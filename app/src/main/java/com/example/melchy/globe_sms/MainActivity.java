package com.example.melchy.globe_sms;

import android.app.ProgressDialog;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Context;

import ph.com.globe.connect.*;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    private Sms sms;
    private TextView tvResult;
    private EditText etMessage;
    public  String TAG = "MainActivityLog";
    private String message;
    ProgressDialog mProgressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sms = new Sms (getString(R.string.SHORT_CODE),getString(R.string.ACCESS_TOKEN));
        tvResult = (TextView)findViewById(R.id.tvResult);
        etMessage = (EditText)findViewById(R.id.etMessage);
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setTitle("Globe API SMS");
    }
    //Check for internet connection
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public void sendMessage (View view) {
        message = etMessage.getText().toString();
        if (TextUtils.isEmpty(message)) {
            etMessage.setError("Please Don't Leave it blank");
        } else {
            mProgressDialog.show();
            mProgressDialog.setMessage("Sending Message");
                if(isNetworkAvailable()){
                    try {
                        sms
                                .setClientCorrelator("1234")
                                .setReceiverAddress("YOUR_SUBSCRIBERS_NUMBER")
                                .setMessage(etMessage.getText().toString())
                                .sendMessage(new AsyncHandler() {
                                    @Override
                                    public void response(HttpResponse response) throws HttpResponseException {
                                        try {
                                            JSONObject json = new JSONObject(response.getJsonResponse().toString());
                                            Log.d(TAG,json.toString());
                                            mProgressDialog.dismiss();
                                            etMessage.setText("");
                                            Toast.makeText(MainActivity.this, "Message has been sent", Toast.LENGTH_SHORT).show();
                                        } catch (JSONException e) {
                                            Log.d(TAG,e.getMessage());
                                        }
                                    }
                                });

                    } catch (ApiException | HttpRequestException | HttpResponseException e) {
                        Log.d(TAG,e.getMessage());
                    }
                }else{
                    mProgressDialog.dismiss();
                    Toast.makeText(MainActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                }

        }

    }




}
