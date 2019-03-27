package com.example.attendance;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.Intent;


public class qrcodescanning extends AppCompatActivity {
    Button logout,sent;
    EditText result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qrcodescanning);
        logout =findViewById(R.id.logout);
        sent =findViewById(R.id.sent);
        result=findViewById(R.id.result);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences preferences = getSharedPreferences("lol1",MODE_PRIVATE);

                SharedPreferences.Editor editor = preferences.edit();

                //stores 3 new instances of sharedprefs. Both the user and password's keys are the same as the input.
                //Must be done this way because sharedprefs is stupid and inefficient. You cannot store Arrays easily
                //so I use strings instead.
                editor.clear();
                editor.commit();

                Intent displayScreen1 = new Intent(qrcodescanning.this, MainActivity.class);
                startActivity(displayScreen1);
            }
        });
        sent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle ePzl= new Bundle();
                ePzl.putString("key", result.getText().toString());
                Intent displayScreen1 = new Intent(qrcodescanning.this, scanner.class);
                displayScreen1.putExtras(ePzl);
                startActivity(displayScreen1);
            }
        });


    }
   /* public TextView getTextView()
    {

        TextView txtView = (TextView)findViewById(R.id.result);
        return txtView;
    }*/
}
