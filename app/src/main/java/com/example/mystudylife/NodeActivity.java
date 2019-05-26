package com.example.mystudylife;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.shashank.sony.fancytoastlib.FancyToast;

public class NodeActivity extends Activity {

    private int count=0;
    private LinearLayout child;
    private LinearLayout scroll;
    private CheckBox checkBok_done;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        final ImageButton deleteButton = findViewById(R.id.delete_btn);
        final Button addButton = findViewById(R.id.btn_add_note);
        final Button removeAllButton= findViewById(R.id.btn_delete_all_note);

        FancyToast.makeText(this,getString(R.string.first_toast),FancyToast.LENGTH_LONG, FancyToast.INFO,true).show();

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count++;
                scroll = (LinearLayout) findViewById(R.id.in_scroll);
                LayoutInflater inflater = (LayoutInflater)getSystemService(NodeActivity.this.LAYOUT_INFLATER_SERVICE);
                child = (LinearLayout) inflater.inflate(R.layout.row_of_note, null);
                child.setId(count);
                scroll.addView(child);
            }
        });

        removeAllButton.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(NodeActivity.this);

                builder.setTitle(getString(R.string.warning_error));
                builder.setMessage(getString(R.string.delete_all_note));

                builder.setPositiveButton(getString(R.string.ok_note), new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {

                        try {
                            ((ViewGroup)child.getParent()).removeAllViewsInLayout();
                        }
                        catch (Exception e)
                        {
                            Toast.makeText(getBaseContext(),  R.string.no_views, Toast.LENGTH_LONG).show();

                        }
                        dialog.dismiss();
                    }
                });

                builder.setNegativeButton(getString(R.string.no_note), new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // Do nothing
                        dialog.dismiss();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();

            }
        }));


    };


    public void on_delete(final View  v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle(getString(R.string.warning_error));
        builder.setMessage(getString(R.string.delete_note));

        builder.setPositiveButton(getString(R.string.ok_note), new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                ((ViewGroup)v.getParent().getParent()).setVisibility(View.GONE);

                dialog.dismiss();
            }
        });

        builder.setNegativeButton(getString(R.string.no_note), new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                // Do nothing
                dialog.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();


    }


    public void checkBoxToast(View view) {
        checkBok_done = (CheckBox)view;

        if(checkBok_done.isChecked()) {
            FancyToast.makeText(this,getString(R.string.task_toast),FancyToast.LENGTH_LONG, FancyToast.SUCCESS,true).show();
        }

    }

}
