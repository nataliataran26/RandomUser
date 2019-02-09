package com.nataliiataran.randomuser;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.nataliiataran.randomuser.api.Result;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UserDetailsActivity extends AppCompatActivity {
    private ImageView ivAvatar;
    private TextView tvName;
    private TextView tvDateOfBirth;
    private TextView tvAge;
    private TextView tvGender;
    private TextView tvId;
    private TextView tvPhone;
    private TextView tvEmail;
    private TextView tvAddress;
    private LinearLayout llId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);
        setTitle(R.string.profile);
        ivAvatar = findViewById(R.id.ivAvatar);
        tvName = findViewById(R.id.tvName);
        tvDateOfBirth = findViewById(R.id.tvDateOfBirth);
        tvAge = findViewById(R.id.tvAge);
        tvGender = findViewById(R.id.tvGender);
        tvId = findViewById(R.id.tvId);
        tvPhone = findViewById(R.id.tvPhone);
        tvEmail = findViewById(R.id.tvEmail);
        tvAddress = findViewById(R.id.tvAddress);
        llId = findViewById(R.id.llId);

        Result result = getIntent().getExtras().getParcelable("user");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        SimpleDateFormat output = new SimpleDateFormat("yyyy-MM-dd");
        Date d = null;
        try {
            d = sdf.parse(result.getDob().getDate().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String formattedTime = output.format(d);

        Glide.with(ivAvatar).load(result.getPicture().getLarge()).into(ivAvatar);
        tvName.setText(result.getName().getTitle() + " " + result.getName().getFirst() + " " + result.getName().getLast());
        tvDateOfBirth.setText(formattedTime);
        tvAge.setText(result.getDob().getAge() + " years old");
        tvGender.setText(result.getGender());
        if (result.getId().getValue() == "null") {
            llId.setVisibility(View.GONE);
        } else {
            llId.setVisibility(View.VISIBLE);
            tvId.setText(result.getId().getName() + " " + result.getId().getValue());
        }
        tvPhone.setText(result.getPhone());
        tvEmail.setText(result.getEmail());
        tvAddress.setText(result.getLocation().getCity() + ", " + result.getLocation().getState());

        tvPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callUserWrapper();
            }
        });
    }

    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;

    private void callUserWrapper(){
        int hasWriteContactsPermission = ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.CALL_PHONE);
        if (hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[] {Manifest.permission.CALL_PHONE},
                    REQUEST_CODE_ASK_PERMISSIONS);
            return;
        }
        callUser();
    }

    private void callUser() {
        String number = ("tel:" + tvPhone.getText());
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse(number));
        if (ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(UserDetailsActivity.this,
                    new String[]{Manifest.permission.CALL_PHONE},
                    1);
        } else {
            try {
                startActivity(intent);
            } catch (SecurityException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted
                    callUser();
                } else {
                    // Permission Denied
                    Toast.makeText(UserDetailsActivity.this, "Need Permission!", Toast.LENGTH_SHORT)
                            .show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}
