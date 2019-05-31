package com.example.sentient_eyecpfc;

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
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

public class ProfileFragment extends Fragment {
    public static DatabaseHelper mDBHelper;
    public static SQLiteDatabase mDb;
    String Plan = "";
    String Gender = "";
    int Weight = 0;
    int Height = 0;
    int Age = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        TextView textCalories = view.findViewById(R.id.textCalories);
        TextView textProt = view.findViewById(R.id.rexrProtein);
        TextView textFat = view.findViewById(R.id.rexrFat);
        TextView textCH = view.findViewById(R.id.rexrCH);
        TextView textTarget = view.findViewById(R.id.rexrTarget);
        TextView textTraining = view.findViewById(R.id.rexrTrainPlan);
        TextView textAge = view.findViewById(R.id.rexrAge);
        TextView textGender = view.findViewById(R.id.rexrGender);
        TextView textWeight = view.findViewById(R.id.rexrWeight);
        TextView textHeight = view.findViewById(R.id.textHeight);

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

        Cursor cursor = MainActivity.mDb.rawQuery("SELECT * FROM User", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            textCalories.setText("Calories: " + cursor.getString(1));
            textProt.setText("Protein: " + cursor.getString(2));
            textFat.setText("Fat: " + cursor.getString(3));
            textCH.setText("Carbohydrates: " + cursor.getString(4));
            textTarget.setText("Target: " + cursor.getString(5));
            Plan = cursor.getString(6);
            textTraining.setText("Fitness plan: " + Plan);
            Age = cursor.getInt(7);
            textAge.setText("Age: " + String.valueOf(Age));
            Gender = cursor.getString(8);
            if (Gender.equals("1"))
                textGender.setText("Gender: " + "Male");
            else
                textGender.setText("Gender: " + "Female");
            Weight = cursor.getInt(9);
            textWeight.setText("Weight: " + String.valueOf(Weight));
            Height = cursor.getInt(10);
            textHeight.setText("Height: " + String.valueOf(Height));
            cursor.moveToNext();
        }
        cursor.close();
        Toast toast2 = Toast.makeText(getContext(),"...", Toast.LENGTH_SHORT);
        toast2.show();

        return view;
    }
}
