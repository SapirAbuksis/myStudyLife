package com.example.mystudylife;

import android.app.SearchManager;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.shashank.sony.fancydialoglib.Animation;
import com.shashank.sony.fancydialoglib.FancyAlertDialog;
import com.shashank.sony.fancydialoglib.FancyAlertDialogListener;
import com.shashank.sony.fancydialoglib.Icon;
import com.shashank.sony.fancytoastlib.FancyToast;

public class MenuActivity extends AppCompatActivity {

    String user_name, academic;
    int is_picture;
    String phone_num= "050-2711878";
    String width , length ;
    String phone_academic, web_academic;
    Editable edit_text, edit_text_f ,edit_text_l ,edit_text_phone , edit_text_mail , edit_note;
    long back_dialog;

    public void onBackPressed() {

        new FancyAlertDialog.Builder(MenuActivity.this)

                .setBackgroundColor(Color.parseColor("#DFFF9F1D"))
                .setMessage(getString(R.string.back_dialog))
                .setNegativeBtnText(getString(R.string.back_no))
                .setPositiveBtnBackground(Color.parseColor("#4E495F"))
                .setPositiveBtnText(getString(R.string.back_yes))
                .setNegativeBtnBackground(Color.parseColor("#AECCC6"))
                .setAnimation(Animation.SLIDE)
                .isCancellable(true)
                .setIcon(R.drawable.ic_error_outline_black_24dp, Icon.Visible)
                .OnPositiveClicked(new FancyAlertDialogListener() {
                    @Override
                    public void OnClick() {
                        Intent intent = new Intent(MenuActivity.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.putExtra("EXIT", true);
                        startActivity(intent);
                    }
                })
                .OnNegativeClicked(new FancyAlertDialogListener() {
                    @Override
                    public void OnClick() {
                    }
                })
                .build();
    }

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        TextView user_name_menu = findViewById(R.id.menu_profile_name);
        ImageView user_photo = findViewById(R.id.menu_profile);
        user_name = getIntent().getStringExtra("username");
        academic = getIntent().getStringExtra("academic");
        is_picture = getIntent().getIntExtra("is_picture", 0);
        user_photo.setImageBitmap((Bitmap) getIntent().getParcelableExtra("photo"));
        user_name_menu.setText(user_name);

        final Button btnMail = findViewById(R.id.btn_mail);
        btnMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, MailActivity.class);
                startActivity(intent);
            }
        });

        final Button btnCalendar = findViewById(R.id.btn_calendar);
        btnCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, CalendarActivity.class);
                startActivity(intent);
            }
        });

        final Button btnReminder = findViewById(R.id.btn_reminder);
        btnReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, ReminderActivity.class);
                startActivity(intent);
            }
        });

        Button smsButton = findViewById(R.id.btn_sms);
        smsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LayoutInflater layoutInflaterAndroid = LayoutInflater.from(MenuActivity.this);
                View mView = layoutInflaterAndroid.inflate(R.layout.input_massage_dialog, null);
                AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(MenuActivity.this);
                alertDialogBuilderUserInput.setView(mView);

                final EditText userInputDialogEditText = (EditText) mView.findViewById(R.id.input_dialog);
                alertDialogBuilderUserInput
                        .setCancelable(false)
                        .setPositiveButton(getText(R.string.sms_ok), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogBox, int id) {
                                edit_text = userInputDialogEditText.getText();

                                Uri uri = Uri.parse("smsto:" + phone_num);
                                Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
                                intent.putExtra("sms_body", edit_text.toString());
                                startActivity(intent);
                            }
                        })

                        .setNegativeButton(R.string.sms_no,
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialogBox, int id) {
                                        dialogBox.cancel();
                                    }
                                });

                AlertDialog alertDialogAndroid = alertDialogBuilderUserInput.create();
                alertDialogAndroid.show();
            }
        });


        Button contactButton = findViewById(R.id.btn_contact);
        contactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LayoutInflater layoutInflaterAndroid = LayoutInflater.from(MenuActivity.this);
                View mView = layoutInflaterAndroid.inflate(R.layout.input_contact_dialog, null);
                AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(MenuActivity.this);
                alertDialogBuilderUserInput.setView(mView);

                final EditText userInputDialogEditTextF = (EditText) mView.findViewById(R.id.input_first_name);
                final EditText userInputDialogEditTextL = (EditText) mView.findViewById(R.id.input_last_name);
                final EditText userInputDialogEditTextPhone = (EditText) mView.findViewById(R.id.input_mobile);
                final EditText userInputDialogEditTextEmail = (EditText) mView.findViewById(R.id.input_email);

                alertDialogBuilderUserInput
                        .setCancelable(false)
                        .setPositiveButton(getText(R.string.sms_ok), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogBox, int id) {
                                edit_text_f = userInputDialogEditTextF.getText();
                                edit_text_l = userInputDialogEditTextL.getText();
                                edit_text_phone = userInputDialogEditTextPhone.getText();
                                edit_text_mail = userInputDialogEditTextEmail.getText();

                                Intent intent = new Intent(ContactsContract.Intents.Insert.ACTION);
                                intent.setType(ContactsContract.RawContacts.CONTENT_TYPE);

                                intent.putExtra(ContactsContract.Intents.Insert.NAME, edit_text_f.toString() + " " + edit_text_l.toString());
                                intent.putExtra(ContactsContract.Intents.Insert.PHONE, edit_text_phone.toString());
                                intent.putExtra(ContactsContract.Intents.Insert.EMAIL, edit_text_mail.toString());

                                startActivity(intent);
                            }
                        })

                        .setNegativeButton(R.string.sms_no,
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialogBox, int id) {
                                        dialogBox.cancel();
                                    }
                                });

                AlertDialog alertDialogAndroid = alertDialogBuilderUserInput.create();
                alertDialogAndroid.show();
            }
        });

        final Button btCall = findViewById(R.id.btn_call);
        btCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                switch (academic) {
                    case "המכון הטכנולוגי חולון":
                    case "Holon institute of technology":
                        phone_academic = "03-502-6666";
                        break;
                    case "אוניברסיטת תל אביב":
                    case "Tel aviv university":
                        phone_academic = "03-640-8111";
                        break;
                    case "אוניברסיטת בן גוריון":
                    case "Ben-gurion university":
                        phone_academic = "08-646-1600";
                        break;
                    case "המכללה למנהל ראשון לציון":
                    case "The college of management":
                        phone_academic = "03-9634144";
                        break;
                    case "הטכניון-מכון טכנולוגי לישראל":
                    case "Technion israel institute":
                        phone_academic = "077-887-5555";
                        break;
                    default:
                        phone_academic = "0502711878";
                        break;
                }
                intent.setData(Uri.parse("tel:" + phone_academic));
                startActivity(Intent.createChooser(intent, getString(R.string.call_aca)));
            }
        });

        Button addWebButton = findViewById(R.id.btn_web);
        addWebButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
                    intent.putExtra(SearchManager.QUERY, academic);
                    startActivity(intent);
                    ;
                } catch (ActivityNotFoundException e) {

                    FancyToast.makeText(MenuActivity.this, getString(R.string.no_internet), FancyToast.LENGTH_LONG, FancyToast.ERROR, true).show();
                }

            }
        });

        Button navigateButton = findViewById(R.id.btn_map);
        navigateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                switch (academic) {
                    case "המכון הטכנולוגי חולון":
                    case "Holon institute of technology":
                        width = "32.016093";
                        length = "34.773027";
                        break;
                    case "אוניברסיטת תל אביב":
                    case "Tel aviv university":
                        width = "32.112822";
                        length = "34.803694";
                        break;
                    case "אוניברסיטת בן גוריון":
                    case "Ben-gurion university":
                        width = "31.262180";
                        length = "34.801171";
                        break;
                    case "המכללה למנהל ראשון לציון":
                    case "The college of management":
                        width = "31.969721";
                        length = "34.772789";
                        break;
                    case "הטכניון-מכון טכנולוגי לישראל":
                    case "Technion israel institute":
                        width = "32.776924";
                        length = "35.023246";
                        break;
                    default:
                        width = "31.788126";
                        length = "35.202682";
                        break;
                }
                intent.setData(Uri.parse("geo:" + width + "," +
                        length + "?q=" + academic));
                startActivity(Intent.createChooser(intent, getString(R.string.navi_map)));
            }
        });

        final Button noteButton = findViewById(R.id.btn_note);
        noteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, NodeActivity.class);
                startActivity(intent);
            }
        });

        Button aboutButton = findViewById(R.id.about_btn);
        aboutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LayoutInflater layoutInflaterAndroid = LayoutInflater.from(MenuActivity.this);
                View mView = layoutInflaterAndroid.inflate(R.layout.about_dialog, null);
                AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(MenuActivity.this);
                alertDialogBuilderUserInput.setView(mView);

                alertDialogBuilderUserInput
                        .setCancelable(false)
                        .setNegativeButton(R.string.sms_no,
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialogBox, int id) {
                                        dialogBox.cancel();
                                    }
                                });

                AlertDialog alertDialogAndroid = alertDialogBuilderUserInput.create();
                alertDialogAndroid.show();
            }
        });
    }
}