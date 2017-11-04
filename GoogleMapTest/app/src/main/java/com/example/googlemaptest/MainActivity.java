package com.example.googlemaptest;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.location.places.ui.PlacePicker;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button googleMap = (Button) findViewById(R.id.googleMap);
        googleMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ContextCompat.checkSelfPermission(MainActivity.this,
                        Manifest.permission.ACCESS_FINE_LOCATION) !=
                        PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[] {
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_NETWORK_STATE
                    }, 1);
                } else {
                    turnActivity();
                }
            }
        });

        Button location_btn = (Button) findViewById(R.id.location);
        location_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int PLACE_PICKER_REQUEST = 1;
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                startActivityForResult(builder.build(MainActivity.this), PLACE_PICKER_REQUEST);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void turnActivity() {
        Intent intent = new Intent(MainActivity.this, MapsActivity.class);
        startActivity(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    turnActivity();
                } else {
                    Toast.makeText(MainActivity.this, "You denied the permission", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
