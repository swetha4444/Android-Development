//Pixel 2 -API 22
package com.example.gpslocation;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
public class MainActivity extends AppCompatActivity implements LocationListener{
    TextView tv_lat,tv_log;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_lat = findViewById(R.id.lat);
        tv_log = findViewById(R.id.lon);
        EditText et_name = findViewById(R.id.name);
        Button bt_get = findViewById(R.id.gpsButton);

        if(ActivityCompat.checkSelfPermission(MainActivity.this,ACCESS_COARSE_LOCATION)!=PackageManager.PERMISSION_GRANTED
        && ActivityCompat.checkSelfPermission(MainActivity.this,ACCESS_FINE_LOCATION)!=PackageManager.PERMISSION_GRANTED)
            return;

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,(LocationListener) this);

        bt_get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Geocoder geocoder = new Geocoder(MainActivity.this);
                try {
                    List<Address> list = geocoder.getFromLocationName(et_name.getEditableText().toString(),1);
                    if(list.size() != 0)
                    {
                        Address adr = list.get(0);
                        tv_lat.setText(Double.toString(adr.getLatitude()));
                        tv_log.setText(Double.toString(adr.getLongitude()));
                        et_name.setText("");
                    }
                    else
                    {
                        tv_lat.setText("");
                        tv_log.setText("");
                        et_name.setText("");
                        Toast.makeText(getBaseContext(),"No such place found",Toast.LENGTH_SHORT).show();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {

    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {

    }
}
