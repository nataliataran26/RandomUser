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
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nataliiataran.randomuser.api.Result;

public class UserDetailsActivity extends AppCompatActivity {
    private ImageView ivAvatar;
    private TextView tvName;
    private TextView tvDateOfBirth;
    private TextView tvGender;
    private TextView tvPhone;
    private TextView tvEmail;
    private TextView tvAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);
        setTitle(R.string.profile);
        ivAvatar = findViewById(R.id.ivAvatar);
        tvName = findViewById(R.id.tvName);
        tvDateOfBirth = findViewById(R.id.tvDateOfBirth);
        tvGender = findViewById(R.id.tvGender);
        tvPhone = findViewById(R.id.tvPhone);
        tvEmail = findViewById(R.id.tvEmail);
        tvAddress = findViewById(R.id.tvAddress);


        Result result = getIntent().getExtras().getParcelable("user");
        Glide.with(ivAvatar).load(result.getPicture().getLarge()).into(ivAvatar);
        tvName.setText(result.getName().getTitle() + " " + result.getName().getFirst() + " " + result.getName().getLast());
        tvDateOfBirth.setText(result.getDob().getAge() + " years old");
        tvGender.setText(result.getGender());
        tvPhone.setText(result.getPhone());
        tvEmail.setText(result.getEmail());
        tvAddress.setText(result.getLocation().getCity() + ", " + result.getLocation().getState());

        tvPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
        });


    }
}
