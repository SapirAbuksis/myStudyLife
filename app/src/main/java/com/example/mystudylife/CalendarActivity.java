package com.example.mystudylife;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TimePicker;

import java.util.Calendar;

public class CalendarActivity extends AppCompatActivity {

    EditText sub_text , desc_text;
    String  subject1 , description1;
    int hour_time, min_time, year_time, month_time, day_time;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        Button hourButton = findViewById(R.id.btn_calender_time);
        hourButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showHourPicker();
            }
        });

        ImageButton imageButton = findViewById(R.id.btn_add_calendar);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                GetData();

                Calendar beginTime = Calendar.getInstance();
                beginTime.set(year_time, month_time, day_time, hour_time, min_time);

                Calendar endTime = Calendar.getInstance();
                endTime.set(year_time, month_time, day_time, hour_time, min_time+1);

                Intent intent = new Intent(Intent.ACTION_INSERT)
                        .setData(CalendarContract.Events.CONTENT_URI)
                        .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME,
                                beginTime.getTimeInMillis())
                        .putExtra(CalendarContract.EXTRA_EVENT_END_TIME,
                                endTime.getTimeInMillis())
                        .putExtra(CalendarContract.Events.TITLE, subject1)
                        .putExtra(CalendarContract.Events.DESCRIPTION, description1)
                        .putExtra(CalendarContract.Events.AVAILABILITY, CalendarContract.Events.AVAILABILITY_BUSY);
                startActivity(intent);
            }
        });
        sub_text =findViewById(R.id.sub_text);
        desc_text =findViewById(R.id.desc_text);

        CalendarView calendarView =findViewById(R.id.cal_card);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange( CalendarView view, int year, int month, int dayOfMonth) {

                year_time = year;
                day_time = dayOfMonth;
                month_time = month;
            }
        });
    }

    private void GetData() {
        subject1 = sub_text.getText().toString();
        description1 = desc_text.getText().toString();
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

                    min_time= minute;
                    hour_time= hourOfDay;

                }
            }
        };
        TimePickerDialog timePickerDialog = new TimePickerDialog(CalendarActivity.this, android.R.style.Theme_Holo_Light_Dialog_NoActionBar, myTimeListener, hour, minute, true);
        timePickerDialog.setTitle(getString(R.string.time_choose));
        timePickerDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        timePickerDialog.show();
    }
}
