package com.example.admin.fueltracker;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.admin.fueltracker.db.DatabaseHelper;
import com.example.admin.fueltracker.car.Car;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class AddCarActivity extends Activity {

    Context context = this;

    private static final String[] FUEL_SPINNER = new String[] {
            "Diesel", "Petrol", "LPG"
    };

    private static final HashMap<String, ArrayList<String>> BRAND_MARK_SPINNER;

    static {
        HashMap<String, ArrayList<String>> spinner = new HashMap<>();
        ArrayList<String> value;
        value = new ArrayList<>(Arrays.asList("GOLF", "JETTA", "PASSAT", "POLO"));
        spinner.put("VW", value);
        value = new ArrayList<>(Arrays.asList("180", "320", "330","520","525", "530", "730", "750" ,"M3", "M5"));
        spinner.put("BMW", value);
        value = new ArrayList<>(Arrays.asList("CIVIC", "HRV", "LOGO", "JAZZ", "PRELUDE", "NSX"));
        spinner.put("HONDA", value);
        value = new ArrayList<>(Arrays.asList("AVENSIS", "COROLLA", "GT86", "MR2"));
        spinner.put("TOYOTA", value);
        value = new ArrayList<>(Arrays.asList("80", "A1", "A2", "A3", "A4", "A5", "A6", "A7", "A8"));
        spinner.put("AUDI", value);
        value = new ArrayList<>(Arrays.asList("A", "B", "C", "E", "ML", "S", "SLK"));
        spinner.put("MERCEDES", value);

        BRAND_MARK_SPINNER = spinner;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_car);

        loadSpinnerValues(R.id.brandSpinner, BRAND_MARK_SPINNER.keySet().toArray(
                new String[BRAND_MARK_SPINNER.keySet().size()]));
        loadSpinnerValues(R.id.fuelSpinner, FUEL_SPINNER);

        Spinner spinner = (Spinner) findViewById(R.id.brandSpinner);
        String brand = spinner.getSelectedItem().toString();

        loadSpinnerValues(R.id.markSpinner, BRAND_MARK_SPINNER.get(brand).toArray(
                new String[BRAND_MARK_SPINNER.get(brand).size()]));

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            //change mark on brand change event
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Spinner spinner = (Spinner) findViewById(R.id.brandSpinner);
                String brand = spinner.getSelectedItem().toString();
                loadSpinnerValues(R.id.markSpinner, BRAND_MARK_SPINNER.get(brand).toArray(
                        new String[BRAND_MARK_SPINNER.get(brand).size()]));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        Button clickButton = (Button) findViewById(R.id.saveCarBtn);
        clickButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    // load information from spinners
                    Spinner brandSpinner = (Spinner) findViewById(R.id.brandSpinner);
                    String brand = brandSpinner.getSelectedItem().toString();
                    Spinner markSpinner = (Spinner) findViewById(R.id.markSpinner);
                    String mark = markSpinner.getSelectedItem().toString();
                    String registrationNumber = ((EditText) findViewById(R.id.regNumberField)).getText().toString();

                    if (registrationNumber == null || registrationNumber.isEmpty()) {
                        throw new IllegalArgumentException("Registration number should not be empty");
                    }

                    Spinner fuelSpinner = (Spinner) findViewById(R.id.fuelSpinner);
                    String fuel = fuelSpinner.getSelectedItem().toString();
                    // add car information in DB
                    Car car = new Car(brand, mark, registrationNumber, fuel);
                    DatabaseHelper databaseHelper = new DatabaseHelper(context);
                    databaseHelper.addCar(car);
                    // show massage that the user add car in db
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    Toast.makeText(context, "YOU ADD YOUR CAR SUCCESSFULLY!", Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    Toast.makeText(context, "Your input is wrong! Try again!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void loadSpinnerValues(int id, String[] values) {
        Spinner spinner = (Spinner) findViewById(id);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, values);
        spinner.setAdapter(adapter);
    }

}
