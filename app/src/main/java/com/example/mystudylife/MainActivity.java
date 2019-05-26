package com.example.mystudylife;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Bitmap bitmap;
    ImageView profile_image;
    final int CAMERA_REQ = 1;
    int isPicture = 0;

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAMERA_REQ && resultCode == RESULT_OK) {
            bitmap = (Bitmap) data.getExtras().get("data");
            profile_image.setImageBitmap(bitmap);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText editText = findViewById(R.id.profile_name);
        final Spinner spinner_academic = (Spinner) findViewById(R.id.spinner_institute);

        Button button = findViewById(R.id.submit_btn);

        if (getIntent().getBooleanExtra("EXIT", false)) {
            finish();
        }
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.name_institute, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_academic.setAdapter(adapter);
        spinner_academic.setOnItemSelectedListener(this);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = editText.getText().toString();
                String academic_name = spinner_academic.getSelectedItem().toString();

                if (TextUtils.isEmpty(userName))
                    Toast.makeText(MainActivity.this, R.string.textToast, Toast.LENGTH_SHORT).show();
                else {
                    Intent intent = new Intent(MainActivity.this, MenuActivity.class);
                    intent.putExtra("username", userName);
                    intent.putExtra("academic", academic_name);
                    intent.putExtra("photo", bitmap);
                    intent.putExtra("is_picture", isPicture);

                    startActivity(intent);
                }
            }
        });

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        profile_image = findViewById(R.id.profile_image);
        profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, CAMERA_REQ);
                isPicture = 1;
            }
        });

    }

    public void InfoClick(View view) {
        com.geniusforapp.fancydialog.FancyAlertDialog.Builder alert = new com.geniusforapp.fancydialog.FancyAlertDialog.Builder(MainActivity.this)
                .setimageResource(R.drawable.icon_app_info)
                .setTextTitle(getString(R.string.info_tittle))
                .setBody(getString(R.string.info_con))
                .setNegativeColor(R.color.colorAccent)
                .setPositiveButtonText(getString(R.string.sms_no))
                .setPositiveColor(R.color.colorBtn)
                .setOnPositiveClicked(new com.geniusforapp.fancydialog.FancyAlertDialog.OnPositiveClicked() {
                    @Override
                    public void OnClick(View view, Dialog dialog) {
                        dialog.dismiss();

                    }
                }).setButtonsGravity(com.geniusforapp.fancydialog.FancyAlertDialog.PanelGravity.CENTER)
                .setBodyGravity(com.geniusforapp.fancydialog.FancyAlertDialog.TextGravity.CENTER)
                .setTitleGravity(com.geniusforapp.fancydialog.FancyAlertDialog.TextGravity.CENTER)

                .setCancelable(false)
                .build();
        alert.show();

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }
}
