package com.example.admin.fueltracker;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;


import com.example.admin.fueltracker.db.DatabaseHelper;
import com.example.admin.fueltracker.car.Car;
import com.example.admin.fueltracker.fuel.Fuel;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class ReportsActivity extends Activity {

    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);

        Spinner spinner = (Spinner) findViewById(R.id.carSpinner);
        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        List<Car> cars = databaseHelper.getAllCars();
        List<String> values = new ArrayList<>();

        for (Car car : cars) {
            values.add(car.getRegistrationNumber());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, values);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                try {
                    createReport();
                } catch (Exception e) {
                    Toast.makeText(context, "Your input is wrong! Try again!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                Toast.makeText(context, "Choose car!", Toast.LENGTH_LONG).show();
            }

        });
    }

    private void createReport() throws ParseException {
        List<Fuel> fuels = getFuels();

        if (fuels.size() == 0) {
            Toast.makeText(context, "This car has no fuels!", Toast.LENGTH_LONG).show();
            return;
        }

        BarChart barChart = (BarChart) findViewById(R.id.bargraph);

        // y -> price per litter
        ArrayList<BarEntry> barEntries = new ArrayList<>();

        // x -> dates to from
        ArrayList<String> dates = new ArrayList<>();

        for (int i = 0; i < fuels.size(); i++) {
            Fuel fuel = fuels.get(i);
            float pricePerKilometer = new Float (fuel.getPricePerKilometer()/fuel.getKilometers());
            barEntries.add(new BarEntry(pricePerKilometer, i));
            dates.add(fuel.getDate());
        }

        BarDataSet barDataSet = new BarDataSet(barEntries, "Price pre kilometer");
        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        BarData data = new BarData(dates, barDataSet);
        barChart.setData(data);
        barChart.setTouchEnabled(true);
        barChart.notifyDataSetChanged(); // let the chart know it's data changed
        barChart.invalidate(); // refresh
    }

    private List<Fuel> getFuels() throws ParseException {
        Spinner carSpinner = (Spinner) findViewById(R.id.carSpinner);
        String registrationNumber = carSpinner.getSelectedItem().toString();
        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        int carId = databaseHelper.getCarByRegistrationNumber(registrationNumber);
        return databaseHelper.loadByCarId(carId);
    }

}
