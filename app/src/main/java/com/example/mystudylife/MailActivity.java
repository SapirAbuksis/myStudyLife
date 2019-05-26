package com.example.mystudylife;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.EditText;

public class MailActivity extends AppCompatActivity {

    EditText to_text , sub_text , send_text, des_text;
    String to , subject , massage, sender;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mail);

        to_text = findViewById(R.id.text_to);
        sub_text = findViewById(R.id.text_sub);
        send_text = findViewById(R.id.text_sender);
        des_text =findViewById(R.id.text_desc);

        ImageButton sendButton =findViewById(R.id.btn_add_mail);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                GetData();
                Intent intent = new Intent( Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_EMAIL ,new String[]{to});
                intent.putExtra(Intent.EXTRA_SUBJECT,subject);
                intent.putExtra(Intent.EXTRA_TEXT,massage+"\n\n"+ getString(R.string.sender_from) + " " + sender);
                intent.setType("massage/rfc822");
                startActivity(Intent.createChooser(intent,getString(R.string.choose_with)));

            }
        });

    }

    private void GetData() {

        to = to_text.getText().toString();
        subject = sub_text.getText().toString();
        sender = send_text.getText().toString();
        massage = des_text.getText().toString();
    }
}