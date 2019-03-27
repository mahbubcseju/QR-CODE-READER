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


public class MainActivity extends AppCompatActivity {
    private  EditText name,semister,id,pass;
    Button submitBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if(getSharedPreferences("lol1",MODE_PRIVATE ).getBoolean("logged_in", false)==true)
        {
            Intent displayScreen = new Intent(MainActivity.this, qrcodescanning.class);
            startActivity(displayScreen);
        }
        else
        {
            setContentView(R.layout.activity_main);
            name = findViewById(R.id.name);
            semister = findViewById(R.id.Semister);
            id = findViewById(R.id.ID);
            pass = findViewById(R.id.password);
            submitBtn =findViewById(R.id.login);

            submitBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    SharedPreferences preferences = getSharedPreferences("lol1",MODE_PRIVATE);
                    String name1 = name.getText().toString();
                    String semister1 = semister.getText().toString();
                    String id1 = id.getText().toString();
                    String pass1  = pass.getText().toString();
                    SharedPreferences.Editor editor = preferences.edit();

                    //stores 3 new instances of sharedprefs. Both the user and password's keys are the same as the input.
                    //Must be done this way because sharedprefs is stupid and inefficient. You cannot store Arrays easily
                    //so I use strings instead.
                    editor.putBoolean("logged_in", true);
                    editor.apply();
                    editor.putString("name",name1);
                    editor.commit();
                    editor.putString("sem", semister1);
                    editor.commit();
                    editor.putString("id", id1);
                    editor.commit();
                    editor.putString("pass",pass1);
                    editor.commit();

                    Intent displayScreen1 = new Intent(MainActivity.this, qrcodescanning.class);
                    startActivity(displayScreen1);
                }
            });

        }




    }
}
