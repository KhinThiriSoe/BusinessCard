package com.example.khinthirisoe.businesscard;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.text_email)
    TextView textEmail;
    @BindView(R.id.text_phone)
    TextView textPhone;

    public static final String PLACE_LATITUDE = "16.775886";
    public static final String PLACE_LONGITUDE = "96.139460";
    public static final String PLACE_NAME = "Yangon";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

    }

    @OnClick(R.id.text_location)
    public void onLocation(View view) {
        showMap();
    }

    protected void showMap() {

        boolean installedMaps = false;

        // CHECK IF GOOGLE MAPS IS INSTALLED
        PackageManager pkManager = getPackageManager();
        try {
            @SuppressWarnings("unused")
            PackageInfo pkInfo = pkManager.getPackageInfo("com.google.android.apps.maps", 0);
            installedMaps = true;
        } catch (Exception e) {
            e.printStackTrace();
            installedMaps = false;
        }

        // SHOW THE MAP USING CO-ORDINATES FROM THE CHECKIN
        if (installedMaps == true) {
            String geoCode = "geo:0,0?q=" + PLACE_LATITUDE + ","
                    + PLACE_LONGITUDE + "(" + PLACE_NAME + ")";
            Intent sendLocationToMap = new Intent(Intent.ACTION_VIEW,
                    Uri.parse(geoCode));
            startActivity(sendLocationToMap);
        } else if (installedMaps == false) {

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    MainActivity.this);

            // SET THE ICON
            alertDialogBuilder.setIcon(R.drawable.ic_location);

            // SET THE TITLE
            alertDialogBuilder.setTitle("Google Maps Not Found");

            // SET THE MESSAGE
            alertDialogBuilder
                    .setMessage("google map not installed")
                    .setCancelable(false)
                    .setNeutralButton("Got It",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int id) {
                                    dialog.dismiss();
                                }
                            });

            // CREATE THE ALERT DIALOG
            AlertDialog alertDialog = alertDialogBuilder.create();

            // SHOW THE ALERT DIALOG
            alertDialog.show();
        }
    }

    public void onPhone(View view) {
        String phone = "+959970392284";
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
        startActivity(intent);
    }

    public void onEmail(View view) {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto", "khinthirisoe.mdy@gmail.com", null));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Body");
        startActivity(Intent.createChooser(emailIntent, "Send email..."));
    }
}
