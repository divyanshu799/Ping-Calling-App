package com.ping.calling;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.jitsi.meet.sdk.JitsiMeet;
import org.jitsi.meet.sdk.JitsiMeetActivity;
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions;

import java.net.MalformedURLException;
import java.net.URL;

public class DashboardActivity extends AppCompatActivity {

    EditText secretCodeBox;
    Button joinBtn, shareBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        secretCodeBox = findViewById(R.id.codeBox);
        joinBtn = findViewById(R.id.joinBtn);
        shareBtn = findViewById(R.id.shareBtn);

        final String[] secretcode = {""};

            shareBtn.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    secretcode[0] =secretCodeBox.getText().toString();

                    if(secretcode[0].length()==0){
                        Toast.makeText(DashboardActivity.this,"Please Enter a Valid Secret Code", Toast.LENGTH_SHORT).show();
                    }else {

                        Intent txtIntent = new Intent(android.content.Intent.ACTION_SEND);
                        txtIntent.setType("text/plain");
                        txtIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Join Me on Ping Calling App using this Invite Code:" + secretcode[0]);
                        startActivity(Intent.createChooser(txtIntent, "Share"));
                    }
                }
            });

        URL serverURL;


        try {
            serverURL = new URL("https://meet.jit.si");
            JitsiMeetConferenceOptions defaultOptions =
                    new JitsiMeetConferenceOptions.Builder()
                            .setServerURL(serverURL)
                            .setWelcomePageEnabled(false)
                            .build();
            JitsiMeet.setDefaultConferenceOptions(defaultOptions);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }





        joinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                secretCodeBox=findViewById(R.id.codeBox);
                JitsiMeetConferenceOptions options = new JitsiMeetConferenceOptions.Builder()
                        .setRoom(secretCodeBox.getText().toString())
                        .setWelcomePageEnabled(false)
                        .build();

                JitsiMeetActivity.launch(DashboardActivity.this, options);
            }
        });
    }
}