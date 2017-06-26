package com.example.admin.fueltracker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class CarsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cars);
    }

    public void goToAddCarActivity(View view) {
        Intent intent = new Intent(getApplicationContext(), AddCarActivity.class);
        startActivity(intent);
    }

    public void goToDeleteCarActivity(View view) {
        Intent intent = new Intent(getApplicationContext(), DeleteCarActivity.class);
        startActivity(intent);
    }

}
