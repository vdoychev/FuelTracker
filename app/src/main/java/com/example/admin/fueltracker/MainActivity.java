package com.example.admin.fueltracker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void goToCarsActivity(View view) {
        Intent intent = new Intent(this, CarsActivity.class);
        startActivity(intent);
    }

    public void goToFuelActivity(View view) {
        Intent intent = new Intent(this, FuelActivity.class);
        startActivity(intent);
    }

    public void goToReportsActivity(View view) {
        Intent intent = new Intent(this, ReportsActivity.class);
        startActivity(intent);
    }
    public void goToMapsActivity(View view) {
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }


}
