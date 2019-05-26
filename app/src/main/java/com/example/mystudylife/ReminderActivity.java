package com.example.mystudylife;

import android.Manifest;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TimePicker;

import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.Calendar;

public class ReminderActivity extends AppCompatActivity {

    int hour_rem, minute_rem;
    String message;
    final int ALARM_PERMISSION_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);

        EditText messageET =findViewById(R.id.text_description);
        message   = messageET.getText().toString();

        ImageButton imageButton = findViewById(R.id.btn_add_remider);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(Build.VERSION.SDK_INT>=23) {

                    int hasCallPermission = checkSelfPermission(Manifest.permission.SET_ALARM);
                    if(hasCallPermission == PackageManager.PERMISSION_GRANTED) {
                        SetAlarm();
                    }
                    else {

                        requestPermissions(new String[] {Manifest.permission.SET_ALARM},ALARM_PERMISSION_REQUEST);
                    }
                }
                else {
                    SetAlarm();
                }
            }
        });

        Button hourButton = findViewById(R.id.btn_time);
        hourButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showHourPicker();
            }
        });
    }

    private void showHourPicker() {

        final Calendar myCalender = Calendar.getInstance();
        int hour = myCalender.get(Calendar.HOUR_OF_DAY);
        int minute = myCalender.get(Calendar.MINUTE);


        TimePickerDialog.OnTimeSetListener myTimeListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                if (view.isShown()) {
                    myCalender.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    myCalender.set(Calendar.MINUTE, minute);

                    minute_rem= minute;
                    hour_rem= hourOfDay;

                }
            }
        };
        TimePickerDialog timePickerDialog = new TimePickerDialog(ReminderActivity.this, android.R.style.Theme_Holo_Light_Dialog_NoActionBar, myTimeListener, hour, minute, true);
        timePickerDialog.setTitle(getString(R.string.time_choose));
        timePickerDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        timePickerDialog.show();
    }

    private void SetAlarm() {

        EditText msg_rem= findViewById(R.id.text_description);
        message= msg_rem.getText().toString();

        Intent intent = new Intent(AlarmClock.ACTION_SET_ALARM)
                .putExtra(AlarmClock.EXTRA_MESSAGE, message)
                .putExtra(AlarmClock.EXTRA_HOUR, hour_rem)
                .putExtra(AlarmClock.EXTRA_MINUTES, minute_rem);

        startActivity(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == ALARM_PERMISSION_REQUEST) {

            if(grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                SetAlarm();
            }
            else {
                FancyToast.makeText(this,getString(R.string.premission_call),FancyToast.LENGTH_LONG,FancyToast.ERROR,true).show();

            }
        }
    }
}
