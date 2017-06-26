package com.example.admin.fueltracker.fuel;

import android.content.Context;

import com.example.admin.fueltracker.db.DatabaseHelper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class FuelValidator {

    private Context context;

    public FuelValidator(Context context) {
        this.context = context;
    }

    public Fuel validate(String registrationNumber, String liters, String kilometers, String price, String gasStation) {
        validateNotNullOrEmpty(registrationNumber);
        validateNotNullOrEmpty(liters);
        validateNotNullOrEmpty(kilometers);
        validateNotNullOrEmpty(price);
        validateNotNullOrEmpty(gasStation);

        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        int carId = databaseHelper.getCarByRegistrationNumber(registrationNumber);
        Double litersNumber = validateNumber(liters);
        Double kilometersNumber = validateNumber(kilometers);
        Double priceNumber = validateNumber(price);
        return new Fuel(carId, litersNumber, kilometersNumber, priceNumber, gasStation, getDate());
    }

    private Double validateNumber(String arg) {
        Double result;

        try {
            result = Double.valueOf(arg);
        } catch (Exception e) {
            throw new IllegalArgumentException(arg + " should be a number");
        }

        return result;
    }

    private void validateNotNullOrEmpty(String arg) {
        if(arg == null || arg.trim().isEmpty()) {
            throw new IllegalArgumentException(arg + " should not be null or empty");
        }
    }

    private String getDate() {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        Date today = Calendar.getInstance().getTime();
        return df.format(today);
    }

}
