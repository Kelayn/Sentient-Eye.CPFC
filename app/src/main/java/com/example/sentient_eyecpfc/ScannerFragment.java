package com.example.sentient_eyecpfc;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Path;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;
import java.util.Objects;

public class ScannerFragment extends Fragment {
    public static TextView textView;
    Button btn_scan;
    private String tmp = null;
    private  View view;
    private static final String TAG = "Acces";
    private static final int REQUEST_CODE = 1;
    public static DatabaseHelper mDBHelper;    //Обязательные переменные
    public static SQLiteDatabase mDb;          //Обязательные переменные

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.activity_barcode_detect, container, false);

        textView = view.findViewById(R.id.textView);
        btn_scan = view.findViewById(R.id.btn_scan);
        textView.setText(OpenCameraScanActivity.getTmp());

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

        btn_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verify();
            }
        });

        return view;
    }

    private void verify() {
        Log.d(TAG, "asking user for permission");
        String[] permission = {Manifest.permission.CAMERA};

        if (ContextCompat.checkSelfPermission(this.getContext(), permission[0]) == PackageManager.PERMISSION_GRANTED) {
            startActivity(new Intent(getContext(), OpenCameraScanActivity.class));
        } else {
            requestPermissions(permission, REQUEST_CODE);
        }
    }

    public void setTmp(String tmp) {
        this.tmp = tmp;
    }

    // Функция добавления количества съеденного продуктаю На входе имя и количество
    private void changeProduct(String Name, int value) {

        ContentValues values1 = new ContentValues();
        values1.put("dose", value);
        mDb.update("Product", values1, "Name=?", new String[] {Name});

    }

    // Функция добавления продукта в бд
    private void setProduct(String Name, int Calories, int Protein, int Fat, String Carbohydrates, int dose) {

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

        mDb.insert("Product", null, values);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        verify();
    }
}