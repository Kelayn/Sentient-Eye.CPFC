package com.example.sentient_eyecpfc;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.IOException;

public class FoodFragment extends Fragment {

    public static DatabaseHelper mDBHelper;    //Обязательные переменные
    public static SQLiteDatabase mDb;          //Обязательные переменные

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //Обязательный блок для работы функций
        mDBHelper = new DatabaseHelper(this.getContext());
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
        //-----------------------------------

        return inflater.inflate(R.layout.fragment_food, container, false);
    }

    // Функция загрузки в профиль КБЖУ по результатам продуктов
    private void updateKBJUinProfile() {

        int total = 0;
        Cursor cursor = mDb.rawQuery("SELECT SUM(" + "Calories" + ") as Total FROM " + "Product", null);
        if (cursor.moveToFirst()) {

            total = cursor.getInt(cursor.getColumnIndex("Total"));
        }

        int total1 = 0;
        Cursor cursor1 = mDb.rawQuery("SELECT SUM(" + "Protein" + ") as Total FROM " + "Product", null);
        if (cursor1.moveToFirst()) {

            total1 = cursor1.getInt(cursor1.getColumnIndex("Total"));
        }

        int total2 = 0;
        Cursor cursor2 = mDb.rawQuery("SELECT SUM(" + "Fat" + ") as Total FROM " + "Product", null);
        if (cursor2.moveToFirst()) {

            total2 = cursor2.getInt(cursor2.getColumnIndex("Total"));
        }

        int total3 = 0;
        Cursor cursor3 = mDb.rawQuery("SELECT SUM(" + "CH" + ") as Total FROM " + "Product", null);
        if (cursor3.moveToFirst()) {

            total3 = cursor3.getInt(cursor3.getColumnIndex("Total"));
        }

        int total4 = 0;
        Cursor cursor4 = mDb.rawQuery("SELECT SUM(" + "dose" + ") as Total FROM " + "Product", null);
        if (cursor4.moveToFirst()) {

            total4 = cursor4.getInt(cursor4.getColumnIndex("Total"));
        }

        ContentValues values1 = new ContentValues();
        values1.put("Calories", total * (total4/100));
        values1.put("Protein", total1 * (total4/100));
        values1.put("Fat", total2 * (total4/100));
        values1.put("CH", total3 * (total4/100));
        mDb.update("User", values1, "id=?", new String[] { String.valueOf(1)});

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

}
