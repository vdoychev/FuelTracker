package com.example.admin.fueltracker.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.admin.fueltracker.car.Car;
import com.example.admin.fueltracker.fuel.Fuel;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    // database name
    private static final String DATABASE_NAME = "FuelTracker";
    // database version
    private static final int DATABASE_VERSION = 1;
    // table name for car class
    private static final String TABLE_CAR = "Cars";

    // car table columns names
    private static final String CAR_KEY_ID = "id";
    private static final String CAR_KEY_BRAND = "brand";
    private static final String CAR_KEY_MARK = "mark";
    private static final String CAR_KEY_REG_NUMBER = "reg_number";
    private static final String CAR_KEY_FUEL_TYPE = "fuel_type";
    private static final String CAR_CREATE_TABLE_SQL = "CREATE TABLE " + TABLE_CAR + " ( " +
                                                        CAR_KEY_ID + " INTEGER PRIMARY KEY, " +
                                                        CAR_KEY_BRAND + " TEXT, " +
                                                        CAR_KEY_MARK + " TEXT, " +
                                                        CAR_KEY_REG_NUMBER + " TEXT, " +
                                                        CAR_KEY_FUEL_TYPE + " TEXT " + " ) ";

    // table name for fuel class
    private static final String TABLE_FUELS = "Fuels";

    // fuel table columns names
    private static final String FUELS_KEY_ID = "id";
    private static final String FUELS_KEY_CAR_ID = "carId";
    private static final String FUELS_KEY_LITERS = "liters";
    private static final String FUELS_KEY_KILOMETERS = "kilometers";
    private static final String FUELS_KEY_PRICE_PER_KILOMETERS = "pricePerKilometer";
    private static final String FUELS_KEY_STATION = "gasStation";
    private static final String FUELS_KEY_DATE = "date";
    private static final String FUELS_CREATE_TABLE_SQL = "CREATE TABLE " + TABLE_FUELS + " ( " +
                                                          FUELS_KEY_ID + " INTEGER PRIMARY KEY, " +
                                                          FUELS_KEY_CAR_ID + " INTEGER, " +
                                                          FUELS_KEY_LITERS + " DOUBLE, " +
                                                          FUELS_KEY_KILOMETERS + " DOUBLE, " +
                                                          FUELS_KEY_PRICE_PER_KILOMETERS + " DOUBLE, " +
                                                          FUELS_KEY_STATION + " TEXT, " +
                                                          FUELS_KEY_DATE + " TEXT " + " ) ";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //creating tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CAR_CREATE_TABLE_SQL);
        db.execSQL(FUELS_CREATE_TABLE_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CAR);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FUELS);

        // create tables again
        onCreate(db);
    }

    public void addCar(Car car) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(CAR_KEY_BRAND, car.getBrand());
        values.put(CAR_KEY_MARK, car.getMark());
        values.put(CAR_KEY_REG_NUMBER, car.getRegistrationNumber());
        values.put(CAR_KEY_FUEL_TYPE, car.getFuelType());

        // insert row
        db.insert(TABLE_CAR, null, values);

        // close database connection
        db.close();
    }

    public List<Car> getAllCars() {
        List<Car> contactList = new ArrayList<>();
        // select all query
        String selectQuery = "SELECT * FROM " + TABLE_CAR;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Car car = new Car();
                car.setId(Integer.parseInt(cursor.getString(0)));
                car.setBrand(cursor.getString(1));
                car.setMark(cursor.getString(2));
                car.setRegistrationNumber(cursor.getString(3));
                car.setFuelType(cursor.getString(4));

                // adding contact to list
                contactList.add(car);
            } while (cursor.moveToNext());
        }

        // return contact list
        return contactList;
    }

    public int getCarByRegistrationNumber(String registrationNumber) {
        SQLiteDatabase db = this.getWritableDatabase();
        // select all query
        String selectQuery = "SELECT * FROM " + TABLE_CAR +
                             " WHERE " + CAR_KEY_REG_NUMBER + " = '" + registrationNumber + "'";
        Cursor cursor = db.rawQuery(selectQuery, null);
        int carId = 0;

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                carId = Integer.parseInt(cursor.getString(0));
            } while (cursor.moveToNext());
        }

        // return contact list
        return carId;
    }

    public void deleteCar(int carId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CAR, CAR_KEY_ID + " = ?", new String[]{String.valueOf(carId) });
        db.close();
    }


    // FUELS

    public void addFuel(Fuel fuel) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(FUELS_KEY_CAR_ID, fuel.getCarId());
        values.put(FUELS_KEY_LITERS, fuel.getLiters());
        values.put(FUELS_KEY_KILOMETERS, fuel.getKilometers());
        values.put(FUELS_KEY_PRICE_PER_KILOMETERS, fuel.getPricePerKilometer());
        values.put(FUELS_KEY_STATION, fuel.getGasStation());
        values.put(FUELS_KEY_DATE, fuel.getDate());

        // insert row
        long result = db.insert(TABLE_FUELS, null, values);

        if (result == -1) {
            throw new SQLException("Error with the inserting.");
        }

        // close database connection
        db.close();
    }

    public List<Fuel> loadByCarId(int carId) throws ParseException {
        SQLiteDatabase db = this.getWritableDatabase();
        List<Fuel> fuelList = new ArrayList<>();
        // select all query
        String selectQuery = "SELECT * FROM " + TABLE_FUELS +
                " WHERE " + FUELS_KEY_CAR_ID + " = '" + carId + "'" ;
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Fuel fuel = new Fuel();
                fuel.setId(Integer.parseInt(cursor.getString(0)));
                fuel.setCarId(Integer.parseInt(cursor.getString(1)));
                fuel.setLiters(cursor.getDouble(2));
                fuel.setKilometers(cursor.getDouble(3));
                fuel.setPricePerKilometer(cursor.getDouble(4));
                fuel.setGasStation(cursor.getString(5));
                fuel.setDate(cursor.getString(6));

                // adding contact to list
                fuelList.add(fuel);
            } while (cursor.moveToNext());
        }

        // return fuel list
        return fuelList;
    }

    public void deleteFuel(Fuel fuel) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_FUELS, FUELS_KEY_ID + " = ?", new String[]{String.valueOf(fuel.getId()) });
        db.close();
    }


}
