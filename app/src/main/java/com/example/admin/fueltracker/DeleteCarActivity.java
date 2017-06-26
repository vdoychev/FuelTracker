package com.example.admin.fueltracker;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.admin.fueltracker.db.DatabaseHelper;
import com.example.admin.fueltracker.car.Car;

import java.util.ArrayList;
import java.util.List;

public class DeleteCarActivity extends Activity {

    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_car);

        Spinner spinner = (Spinner) findViewById(R.id.deleteCarSpinner);
        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        List<Car> cars = databaseHelper.getAllCars();
        List<String> values = new ArrayList<>();

        for (Car car : cars) {
            values.add(car.getRegistrationNumber());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, values);
        spinner.setAdapter(adapter);
    }

    public void deleteCar(View view) {
        try {
            Spinner carSpinner = (Spinner) findViewById(R.id.deleteCarSpinner);
            String registrationNumber = carSpinner.getSelectedItem().toString();
            DatabaseHelper databaseHelper = new DatabaseHelper(context);
            int carId = databaseHelper.getCarByRegistrationNumber(registrationNumber);
            databaseHelper.deleteCar(carId);

            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            Toast.makeText(context, "YOU DELETED YOUR CAR(" + registrationNumber + ") SUCCESSFULLY!", Toast.LENGTH_LONG).show();
        } catch (Exception e){
            Toast.makeText(context, "Your input is wrong! Try again!", Toast.LENGTH_LONG).show();
        }
    }
}
