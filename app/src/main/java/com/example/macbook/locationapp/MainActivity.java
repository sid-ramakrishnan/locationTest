package com.example.macbook.locationapp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {


    TextView cityField, detailsField, currentTemperatureField, humidity_field, pressure_field, weatherIcon, updatedField;

    Typeface weatherFont;

    //@SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);


        weatherFont = Typeface.createFromAsset(getAssets(), "fonts/weathericons-regular-webfont.ttf");

        cityField = (TextView) findViewById(R.id.city_field);
        updatedField = (TextView) findViewById(R.id.updated_field);
        detailsField = (TextView) findViewById(R.id.details_field);
        currentTemperatureField = (TextView) findViewById(R.id.current_temperature_field);
        humidity_field = (TextView) findViewById(R.id.humidity_field);
        pressure_field = (TextView) findViewById(R.id.pressure_field);
        weatherIcon = (TextView) findViewById(R.id.weather_icon);
        weatherIcon.setTypeface(weatherFont);

        Function.placeIdTask asyncTask = new Function.placeIdTask(new Function.AsyncResponse() {
            public void processFinish(String weather_city, String weather_description, String weather_temperature, String weather_humidity, String weather_pressure, String weather_updatedOn, String weather_iconText, String sun_rise) {

                cityField.setText(weather_city);
                updatedField.setText(weather_updatedOn);
                detailsField.setText(weather_description);
                currentTemperatureField.setText(weather_temperature);
                humidity_field.setText("Humidity: " + weather_humidity);
                pressure_field.setText("Pressure: " + weather_pressure);
                weatherIcon.setText(Html.fromHtml(weather_iconText));

            }
        });

        LocationManager locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        boolean network_enabled = locManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        Location location;
        double longitude = 0.0;
        double latitude = 0.0;

        if (network_enabled) {


            System.out.println("check self permission 1" + ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION));
            System.out.println("permission granted 1" + PackageManager.PERMISSION_GRANTED);



            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                System.out.println("No permission!");
                //asyncTask.execute(Double.toString(latitude),Double.toString(longitude));
                latitude = 25.000000;
                longitude = 36.00000;
            }
            else if(true)
            {   location = locManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

                if(location!=null) {
                    longitude = location.getLongitude();
                    latitude = location.getLatitude();

                }

            }
        }

        asyncTask.execute(Double.toString(latitude),Double.toString(longitude));
        // asyncTask.execute("Latitude", "Longitude");
    }
}
