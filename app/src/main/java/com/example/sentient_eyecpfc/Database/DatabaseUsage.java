package com.example.sentient_eyecpfc.Database;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;


import com.example.sentient_eyecpfc.DatabaseHelper;
import java.io.IOException;

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

}
