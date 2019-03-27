package com.example.attendance;

import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.Intent;
import me.dm7.barcodescanner.zxing.ZXingScannerView;
import com.google.zxing.Result;

import org.json.JSONException;
import org.json.JSONObject;

import me.dm7.barcodescanner.zxing.ZXingScannerView;
import static android.Manifest.permission.CAMERA;
public class scanner extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    private ZXingScannerView mScannerView;
    String yourPreviousPzl;
    String id,sess,name;
    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        Bundle p = getIntent().getExtras();
        yourPreviousPzl =p.getString("key");
        SharedPreferences sp1=getSharedPreferences("lol1",MODE_PRIVATE);

         id=sp1.getString("id", null);
         sess = sp1.getString("sem", null);
         name = sp1.getString("name",null);

        mScannerView = new ZXingScannerView(this);   // Programmatically initialize the scanner view
        setContentView(mScannerView);
        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentapiVersion >= android.os.Build.VERSION_CODES.M) {
            if (checkPermission()) {
                Toast.makeText(getApplicationContext(), "Permission already granted", Toast.LENGTH_LONG).show();

            } else {
                requestPermission();
            }
        }// Set the scanner view as the content view
    }
    private boolean checkPermission() {
        return ( ContextCompat.checkSelfPermission(getApplicationContext(), CAMERA ) == PackageManager.PERMISSION_GRANTED);
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{CAMERA}, 1);
    }
    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
        mScannerView.startCamera();          // Start camera on resume
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();           // Stop camera on pause
    }


    @Override
    public void handleResult(Result rawResult) {

        final String result = rawResult.getText();
        //result=result.concat(yourPreviousPzl);
       /* TextView txtView = (TextView) ((qrcodescanning)context).findViewById(R.id.text);
        txtView.setText(result);*/
        Log.d("QRCodeScanner", rawResult.getText());
        Log.d("QRCodeScanner", rawResult.getBarcodeFormat().toString());

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        String url1;
        url1="http://";
        url1=url1.concat(yourPreviousPzl);
        url1=url1.concat(":8080/attendance");
        builder.setTitle("Sending Result?");
       // builder.setTitle(unm);
       // builder.setTitle(url);
        builder.setPositiveButton("no", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent displayScreen1 = new Intent(scanner.this, qrcodescanning.class);

                startActivity(displayScreen1);
            }
        });
        builder.setNeutralButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                JSONObject postData = new JSONObject();
                try {
                    postData.put("id",id );
                    postData.put("name", name);
                    postData.put("session", sess);
                    postData.put("qr", result);

                  //  builder.setMessage("Sent");
                    String url;
                    url="http://";
                    url=url.concat(yourPreviousPzl);
                    url=url.concat(":8080/attendance");
                    new SendDeviceDetails().execute(url, postData.toString());
                    Intent displayScreen1 = new Intent(scanner.this, qrcodescanning.class);

                    startActivity(displayScreen1);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

       builder.setMessage(url1);
        AlertDialog alert1 = builder.create();
        alert1.show();
    }


}
