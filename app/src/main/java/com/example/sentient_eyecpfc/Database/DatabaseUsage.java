package com.example.sentient_eyecpfc.Database;
import android.annotation.TargetApi;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.icu.util.Calendar;
import android.os.Build;
import android.widget.Toast;


import com.example.sentient_eyecpfc.Data.DBProduct;
import com.example.sentient_eyecpfc.DatabaseHelper;
import com.example.sentient_eyecpfc.MainActivity;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DatabaseUsage {
    public static DatabaseHelper mDBHelper;    //Обязательные переменные
    public static SQLiteDatabase mDb;          //Обязательные переменные
    public DatabaseUsage(Context context){
        //Обязательный блок для работы функций
        mDBHelper = new DatabaseHelper(context);
        try {
            mDBHelper.updateDataBase();
        } catch (IOException mIOException) {
            throw new Error("UnableToUpdateDatabase");
        }
        try {
            mDb = mDBHelper.getWritableDatabase();
        } catch (SQLException mSQLException) {
            throw mSQLException;
        }
    }
    // Функция добавления количества съеденного продуктаю На входе имя и количество
    public void changeProduct(String Name, int value) {

        ContentValues values1 = new ContentValues();
        values1.put("dose", value);
        mDb.update("Product", values1, "Name=?", new String[] {Name});

    }
    // Функция добавления продукта в бд
    public long setProduct(String Name, Double Calories, Double Protein, Double Fat, Double Carbohydrates, Double dose) {
        int tmp = 0;
        Cursor cursor = mDb.rawQuery("SELECT * FROM Product", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            tmp = cursor.getInt(0);
            cursor.moveToNext();
        }
        cursor.close();

        ContentValues values = new ContentValues();
        values.put("id_prod", tmp + 1);
        values.put("Name", Name);
        values.put("Calories", Calories);
        values.put("Protein", Protein);
        values.put("Fat", Fat);
        values.put("CH", Carbohydrates);
        values.put("dose", dose);
        values.put("date", String.valueOf(android.text.format.DateFormat.format("dd-MM-yyyy", new java.util.Date())));

        return mDb.insert("Product", null, values);
    }

    // Функция загрузки в профиль КБЖУ по результатам продуктов
    public void updateKBJUinProfile(Context context) {

        int total = 0;
        Cursor cursor = MainActivity.mDb.rawQuery("SELECT SUM(" + "Calories" + ") as Total FROM " + "Product WHERE date = '" +
                android.text.format.DateFormat.format("dd-MM-yyyy", new java.util.Date()) + "'", null);
        if (cursor.moveToFirst()) {

            total = cursor.getInt(cursor.getColumnIndex("Total"));
        }

        int total1 = 0;
        Cursor cursor1 = MainActivity.mDb.rawQuery("SELECT SUM(" + "Protein" + ") as Total FROM " + "Product WHERE date = '" +
                android.text.format.DateFormat.format("dd-MM-yyyy", new java.util.Date()) + "'", null);
        if (cursor1.moveToFirst()) {

            total1 = cursor1.getInt(cursor1.getColumnIndex("Total"));
        }

        int total2 = 0;
        Cursor cursor2 = MainActivity.mDb.rawQuery("SELECT SUM(" + "Fat" + ") as Total FROM " + "Product WHERE date = '" +
                android.text.format.DateFormat.format("dd-MM-yyyy", new java.util.Date()) + "'", null);
        if (cursor2.moveToFirst()) {

            total2 = cursor2.getInt(cursor2.getColumnIndex("Total"));
        }

        int total3 = 0;
        Cursor cursor3 = MainActivity.mDb.rawQuery("SELECT SUM(" + "CH" + ") as Total FROM " + "Product WHERE date = '" +
                android.text.format.DateFormat.format("dd-MM-yyyy", new java.util.Date()) + "'", null);
        if (cursor3.moveToFirst()) {

            total3 = cursor3.getInt(cursor3.getColumnIndex("Total"));
        }

        int total4 = 0;
        Cursor cursor4 = MainActivity.mDb.rawQuery("SELECT SUM(" + "dose" + ") as Total FROM " + "Product WHERE date = '" +
                android.text.format.DateFormat.format("dd-MM-yyyy", new java.util.Date()) + "'", null);
        if (cursor4.moveToFirst()) {

            total4 = cursor4.getInt(cursor4.getColumnIndex("Total"));
        }

        ContentValues values1 = new ContentValues();
        values1.put("Calories", total * (total4/100.0));
         values1.put("Fat", total2 * (total4/100.0));
        values1.put("CH", total3 * (total4/100.0));
        MainActivity.mDb.update("User", values1, "id=?", new String[] { String.valueOf(1)});
    }

    // Вункция установки значений итемов для листа с едой, выгрузка из бд
    private void setListViewItems() {

        Cursor cursor = mDb.rawQuery("SELECT * FROM Product", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            // Заглушки для листа
            //ListViewItem.setName(cursor.getString(0));
            //ListViewItem.setCalories(cursor.getInt(0));
            //ListViewItem.setProtein(cursor.getInt(0));
            //ListViewItem.setFat(cursor.getInt(0));
            //ListViewItem.setCarboHyd(cursor.getInt(0));
            // ListViewItem.setDose(cursor.getInt(0));
            cursor.moveToNext();
        }
        cursor.close();

    }

    // Функция для даты ДД_ММ_ГГГГ
    private void getDataAddProduct() {

        Cursor cursor = mDb.rawQuery("SELECT * FROM Product", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            // Здесь берётся дата, можно положить в переменную
            cursor.getString(7);
            cursor.moveToNext();
        }
        cursor.close();
    }

    // Функция для калорий для графика
    private void getDoseProductSum() {

        Cursor cursor = mDb.rawQuery("SELECT * FROM Product", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            // Здесь калории, можно положить в переменную
            cursor.getInt(2);
            cursor.moveToNext();
        }
        cursor.close();
    }

    @TargetApi(Build.VERSION_CODES.N)
    public static ArrayList<Double> setData(Context context) {
        ArrayList<Double> prod1 = null;
        try {
            DatabaseHelper mDBHelper;
            SQLiteDatabase mDb;
            mDBHelper = new DatabaseHelper(context);
            try {
                mDBHelper.updateDataBase();
            } catch (IOException mIOException) {
                throw new Error("UnableToUpdateDatabase");
            }

            try {
                mDb = mDBHelper.getWritableDatabase();
            } catch (SQLException mSQLException) {
                throw mSQLException;
            }
            prod1 = new ArrayList<>();
            for (int i = 0; i < 31; i++) {
                double total = 0;
                int month = Calendar.getInstance().get(Calendar.MONTH) + 1;
                String date = i + "-" + month + "-" + Calendar.getInstance().get(Calendar.YEAR);
                DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
                Date dt = formatter.parse(date);
                Cursor cursor = mDb.rawQuery("SELECT SUM(" + "Calories" + ") as Total FROM " + "Product WHERE date = '" + String.valueOf(formatter.format(dt)) + "'", null);
                if (cursor.moveToFirst()) {
                    total = cursor.getInt(cursor.getColumnIndex("Total"));
                }
                prod1.add(total);
            }
            String view = "";
            for (double elem : prod1) {
                view += String.valueOf(elem) + " | ";
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return (prod1);
    }

    public static ArrayList<DBProduct> setList(Context context) {
        ArrayList<DBProduct> prod = null;
        try {
            prod = new ArrayList<>();
            mDBHelper = new DatabaseHelper(context);
            try {
                mDBHelper.updateDataBase();
            } catch (IOException mIOException) {
                throw new Error("UnableToUpdateDatabase");
            }

            try {
                mDb = mDBHelper.getWritableDatabase();
            } catch (SQLException mSQLException) {
                throw mSQLException;
            }
            DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
            Cursor cursor1 = mDb.rawQuery("SELECT * FROM Product", null);
            cursor1.moveToFirst();
            while (!cursor1.isAfterLast()) {
                DBProduct product = new DBProduct(formatter.parse(cursor1.getString(7)), cursor1.getDouble(6),
                        cursor1.getString(7), cursor1.getString(1), cursor1.getDouble(2),
                        cursor1.getDouble(3), cursor1.getDouble(5), cursor1.getDouble(4));
                prod.add(product);
                cursor1.moveToNext();
            }
            cursor1.close();
            String view = "";
            for (DBProduct elem : prod) {
                view = elem.getCode() + " | " + elem.getName() + " | " + elem.getCalories() + " | " +
                        elem.getCarbohydrates() + " | " + elem.getFats() + " | " + elem.getProteins() + " | " +
                        String.valueOf(android.text.format.DateFormat.format("dd-MM-yyyy", elem.getmDate())) + " | " + elem.getmDose();
                view = "";
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return (prod);
    }

    public static void btnDelite(Context context) {
        mDb.delete("User", "id=?", new String[]{String.valueOf(1)});
        mDb.delete("Product", null, null);
        Toast toast2 = Toast.makeText(context, "Profile delited", Toast.LENGTH_SHORT);
        toast2.show();
    }

}
