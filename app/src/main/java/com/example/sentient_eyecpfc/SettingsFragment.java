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
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.sentient_eyecpfc.Database.DatabaseUsage;

import java.io.IOException;

public class SettingsFragment extends Fragment {

    View view;
    Button btnConf;
    Button btnDel;
    int Gender = 0;
    String Target = "";
    int Weight = 0;
    int Height = 0;
    int Age = 0;
    int checkedRadioButtonId;
    int checkedRadioButtonId1;
    RadioGroup radioGender;
    RadioGroup radioTarget;
    EditText editWeight;
    EditText editHeight;
    EditText editAge;
    public static DatabaseHelper mDBHelper;
    public static SQLiteDatabase mDb;
    String product = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_settings, container, false);

        radioGender = view.findViewById(R.id.radioGender);
        radioTarget = view.findViewById(R.id.radioTarget);
        editWeight = view.findViewById(R.id.editWeight);
        editHeight = view.findViewById(R.id.editHeight);
        editAge = view.findViewById(R.id.editAge);
        btnConf = view.findViewById(R.id.butnConf);
        btnDel = view.findViewById(R.id.btnDelete);

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

        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                product = "";
                DatabaseUsage.btnDelite(getContext());
            }
        });

        btnConf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnConfirm();
            }
        });

        return view;
    }


    private void btnConfirm() {
        if (android.text.TextUtils.isDigitsOnly(editWeight.getText()) && android.text.TextUtils.isDigitsOnly(editHeight.getText()) && android.text.TextUtils.isDigitsOnly(editAge.getText())) {
            checkedRadioButtonId = radioGender.getCheckedRadioButtonId();
            checkedRadioButtonId1 = radioTarget.getCheckedRadioButtonId();
            switch (checkedRadioButtonId) {
                case R.id.radioFemale:
                    Gender = 0;
                    break;
                case R.id.radioMale:
                    Gender = 1;
                    break;
                case -1:
                    Toast toast2 = Toast.makeText(getContext(), "Change gender", Toast.LENGTH_SHORT);
                    toast2.show();
                    break;
            }

            switch (checkedRadioButtonId1) {
                case R.id.radioNochng:
                    Target = "No changes";
                    break;
                case R.id.radioGain:
                    Target = "Gain";
                    break;
                case R.id.radioDiet:
                    Target = "Diet";
                    break;
                case -1:
                    Toast toast2 = Toast.makeText(getContext(), "Change target", Toast.LENGTH_SHORT);
                    toast2.show();
                    break;
            }
            if ((checkedRadioButtonId != -1) && (checkedRadioButtonId1 != -1)) {
                Weight = Integer.parseInt(editWeight.getText().toString());
                Height = Integer.parseInt(editHeight.getText().toString());
                Age = Integer.parseInt(editAge.getText().toString());
            } else {
                Toast toast2 = Toast.makeText(getContext(), "Change target or Gender!", Toast.LENGTH_SHORT);
                toast2.show();
            }
        } else {
            Toast toast2 = Toast.makeText(getContext(), "Incorrect!", Toast.LENGTH_SHORT);
            toast2.show();
        }
        if (FitnessPlanFragment.TrainingPlan.equals("")) {
            Toast toast2 = Toast.makeText(getContext(), "You have not chosen a fitness plan!", Toast.LENGTH_SHORT);
            toast2.show();
        } else {

            Cursor cursor = MainActivity.mDb.rawQuery("SELECT * FROM User", null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                product += cursor.getString(5) + " | ";
                cursor.moveToNext();
            }
            cursor.close();
            Toast toast2 = Toast.makeText(getContext(), "...", Toast.LENGTH_SHORT);
            toast2.show();

            if (product.equals("")) {
                ContentValues values = new ContentValues();
                values.put("id", 1);
                values.put("Calories", 0);
                values.put("Prot", 0);
                values.put("Fat", 0);
                values.put("CH", 0);
                values.put("Target", Target);
                values.put("TrainingPlane", FitnessPlanFragment.TrainingPlan);
                values.put("Age", Age);
                values.put("gender", Gender);
                values.put("weight", Weight);
                values.put("height", Height);
//
                long result = mDb.insert("User", null, values);
                DatabaseUsage dBUs = new DatabaseUsage(getContext());
                dBUs.updateKBJUinProfile(getContext());
                Toast toast = Toast.makeText(getContext(), "Profile created", Toast.LENGTH_SHORT);
                toast.show();
            } else {
                Toast toast = Toast.makeText(getContext(), "Delete previous data", Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    }
}
