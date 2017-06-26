package com.example.admin.fueltracker;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.admin.fueltracker.db.DatabaseHelper;
import com.example.admin.fueltracker.car.Car;
import com.example.admin.fueltracker.fuel.Fuel;
import com.example.admin.fueltracker.fuel.FuelValidator;

import java.util.ArrayList;
import java.util.List;

public class FuelActivity extends Activity {

    Context context = this;

    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fuel);

        loadSpinnerValues(R.id.carSpinner, loadCars());
        addListenerOnButton();
    }

    private String[] loadCars() {
        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        List<Car> cars = databaseHelper.getAllCars();
        final ArrayList<String> result = new ArrayList<>();

        for (Car car : cars) {
            result.add(car.getRegistrationNumber());
        }

        return result.toArray(new String[result.size()]);
    }

    private void loadSpinnerValues(int id, String[] values) {
        Spinner spinner = (Spinner) findViewById(id);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, values);
        spinner.setAdapter(adapter);
    }

    public void addListenerOnButton() {
        button = (Button) findViewById(R.id.submit);

        button.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
            try {
                Spinner spinner = (Spinner) findViewById(R.id.carSpinner);
                String registrationNumber = spinner.getSelectedItem().toString();
                String liters = ((EditText) findViewById(R.id.litersInput)).getText().toString();
                String kilometers = ((EditText) findViewById(R.id.kmInput)).getText().toString();
                String price = ((EditText) findViewById(R.id.priceInput)).getText().toString();
                String gasStation = ((EditText) findViewById(R.id.gasStationInput)).getText().toString();
                FuelValidator validator = new FuelValidator(context);
                Fuel fuel = validator.validate(registrationNumber, liters, kilometers, price, gasStation);
                DatabaseHelper databaseHelper = new DatabaseHelper(context);
                databaseHelper.addFuel(fuel);
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                Toast.makeText(context, "YOU UPDATE YOUR INFORMATION  SUCCESSFULLY!", Toast.LENGTH_LONG).show();
             } catch (Exception e) {
                Toast.makeText(context, "Your input is wrong! Try again!", Toast.LENGTH_LONG).show();
             }
                }

            });

    }
    public void goToMapssActivity(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}
