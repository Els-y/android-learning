package com.example.lbstest;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public LocationClient mLocationClient;

    private TextView positionText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLocationClient = new LocationClient(getApplicationContext());
        mLocationClient.registerLocationListener(new MyLocationListener());
        setContentView(R.layout.activity_main);

        positionText = (TextView) findViewById(R.id.position_text_view);
        List<String> permissionList = new ArrayList<>();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.READ_PHONE_STATE);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (!permissionList.isEmpty()) {
            String[] permissions = permissionList.toArray(new String[permissionList.size()]);
            ActivityCompat.requestPermissions(this, permissions, 1);
        } else {
            requestLocation();
        }
    }

    private void requestLocation() {
        initLocation();
        mLocationClient.start();
    }

    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setScanSpan(5000);
        option.setIsNeedAddress(true);
        option.setLocationMode(LocationClientOption.LocationMode.Device_Sensors);
        mLocationClient.setLocOption(option);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0) {
                    for (int result: grantResults) {
                        if (result != PackageManager.PERMISSION_GRANTED) {
                            Toast.makeText(this, "必须同意所有权限才可以使用本程序", Toast.LENGTH_SHORT).show();
                            finish();
                            return;
                        }
                    }
                    requestLocation();
                } else {
                    Toast.makeText(this, "发生未知错误", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
        }
    }

    public class MyLocationListener implements BDLocationListener {
        private static final String TAG = "MyLocationListener";
        
        @Override
        public void onConnectHotSpotMessage(String s, int i) {
            Log.d(TAG, "onConnectHotSpotMessage: balabala");
        }

        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            Toast.makeText(MainActivity.this, "onReceiveLocation", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "bdLocation.getLatitude(): " + bdLocation.getLatitude());
            Log.d(TAG, "bdLocation.getLongitude(): " + bdLocation.getLongitude());
            Log.d(TAG, "bdLocation.getLocType(): " + bdLocation.getLocType());
            Log.d(TAG, "TypeGpsLocation: " + BDLocation.TypeGpsLocation);
            Log.d(TAG, "TypeNetWorkLocation: " + BDLocation.TypeNetWorkLocation);
            Log.d(TAG, "TypeCacheLocation: " + BDLocation.TypeCacheLocation);
            Log.d(TAG, "TypeOffLineLocation: " + BDLocation.TypeOffLineLocation);

            StringBuilder currentPosition = new StringBuilder();
            currentPosition.append("纬度: ").append(bdLocation.getLatitude()).append('\n');
            currentPosition.append("经度: ").append(bdLocation.getLongitude()).append('\n');
            currentPosition.append("国家: ").append(bdLocation.getCountry()).append('\n');
            currentPosition.append("省: ").append(bdLocation.getProvince()).append('\n');
            currentPosition.append("市: ").append(bdLocation.getCity()).append('\n');
            currentPosition.append("区: ").append(bdLocation.getDistrict()).append('\n');
            currentPosition.append("街道: ").append(bdLocation.getStreet()).append('\n');

            Toast.makeText(MainActivity.this, currentPosition.toString(), Toast.LENGTH_LONG).show();

            currentPosition.append("定位方式: ");
            if (bdLocation.getLocType() == BDLocation.TypeGpsLocation) {
                currentPosition.append("GPS");
            } else if (bdLocation.getLocType() == BDLocation.TypeNetWorkLocation) {
                currentPosition.append("网络");
            }
            positionText.setText(currentPosition.toString());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLocationClient.stop();
    }
}
