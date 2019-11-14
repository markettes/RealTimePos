package devcom.marcos.realtimepos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.tasks.OnSuccessListener;

import java.text.DecimalFormat;
import java.util.Formatter;

public class MainActivity extends AppCompatActivity {
    //UI
    private TextView locationTF;
    private Button startB, stopB;

    //Location
    private LocationManager locationManager;
    private double longitude, latitude, altitude;

    //Others
    private DecimalFormat f, f1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        //UI
        locationTF = findViewById(R.id.locationTF);
        startB = findViewById(R.id.startB);
        stopB = findViewById(R.id.stopB);

        //Others
        f = new DecimalFormat("##0.00000");
        f1 = new DecimalFormat("##0.00");

        //Aseguramos Permiso
        if ((ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) &&
                (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 0);
        }

        startB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 500, 0, locationListener);
                    System.out.println("TODO BIEN");
                }catch (SecurityException e) {
                    System.out.println(e);
                }
            }
        });

        stopB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("TODO CANCELADO");
                locationManager.removeUpdates(locationListener);
            }
        });


    }




    private final LocationListener locationListener = new LocationListener() {
        public void onLocationChanged(Location location) {
            longitude = location.getLongitude();
            latitude = location.getLatitude();
            altitude = location.getAltitude();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    locationTF.setText(" Latitude: " + f.format(latitude) + " Longitude: " + f.format(longitude) + "\nAltitude = " + f1.format(altitude) + "m");
                    Toast.makeText(MainActivity.this, "Actualizando", Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {
        }

        @Override
        public void onProviderEnabled(String s) {
        }

        @Override
        public void onProviderDisabled(String s) {
        }
    };

}

