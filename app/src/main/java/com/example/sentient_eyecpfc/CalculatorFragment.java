package com.example.sentient_eyecpfc;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sentient_eyecpfc.Database.DatabaseUsage;

import java.io.IOException;

public class CalculatorFragment extends Fragment {
    private Button mAddSwitch;
    private Button mAddFoodManButton;
    private TextView mFats;
    private TextView mProts;
    private TextView mCBH;
    private TextView mCals;
    private TextView mName;
    private TextView mDose;
    private TextView Gain;
    private TextView Loose;
    private TextView Keep;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_calculator, container, false);
        DatabaseUsage dbUse = new DatabaseUsage(this.getContext());
        Gain = view.findViewById(R.id.textFormGain);
        Loose = view.findViewById(R.id.textFormLoose);
        Keep = view.findViewById(R.id.textFormKeep);
        mAddSwitch = view.findViewById(R.id.addFoodSwitch);
        mCals = view.findViewById(R.id.caloriesText);
        mFats = view.findViewById(R.id.fatText);
        mProts = view.findViewById(R.id.protText);
        mCBH = view.findViewById(R.id.CBHText);
        mName = view.findViewById(R.id.nameText);
        mDose = view.findViewById(R.id.doseText);
        mAddFoodManButton = view.findViewById(R.id.addFoodManButton);
        calcLoader();
        mAddFoodManButton.setOnClickListener(v -> {

            Boolean isFilled = true;
            if(mName.getText().toString().matches("")) {
                mName.setBackgroundColor(Color.parseColor("#B00020"));
                isFilled = false;
            }
            if(mCals.getText().toString().matches("")) {
                mCals.setBackgroundColor(Color.parseColor("#B00020"));
                isFilled = false;
            }
            if(mProts.getText().toString().matches("")) {
                mProts.setBackgroundColor(Color.parseColor("#B00020"));
                isFilled = false;
            }
            if(mCBH.getText().toString().matches("")) {
                mCBH.setBackgroundColor(Color.parseColor("#B00020"));
                isFilled = false;
            }
            if(mFats.getText().toString().matches("")) {
                mFats.setBackgroundColor(Color.parseColor("#B00020"));
                isFilled = false;
            }
            if(mDose.getText().toString().matches("")) {
                mDose.setBackgroundColor(Color.parseColor("#B00020"));
                isFilled = false;
            }
            if (!isFilled) return;
            Long logVar;
            logVar = dbUse.setProduct(
                    mName.getText().toString(),
                    Double.parseDouble(mCals.getText().toString()), Double.parseDouble(mProts.getText().toString()),
                    Double.parseDouble(mFats.getText().toString()), Double.parseDouble(mCBH.getText().toString()),
                    Double.parseDouble(mDose.getText().toString()));
            Log.println(4,"insert", logVar.toString());

            mName.setText("");
            mCals.setText("");
            mProts.setText("");
            mCBH.setText("");
            mFats.setText("");
            mDose.setText("");

        });

        return view;
    }

    public void toggleFields() {
        mFats.setEnabled(!mFats.isEnabled());
        mProts.setEnabled(!mProts.isEnabled());
        mCBH.setEnabled(!mCBH.isEnabled());
        mCals.setEnabled(!mCals.isEnabled());
        mName.setEnabled(!mName.isEnabled());
        mAddFoodManButton.setEnabled(!mAddFoodManButton.isEnabled());
        mDose.setEnabled(!mDose.isEnabled());
    }

    public void calcLoader() {
        String Plan = "";
        String Gender = "";
        int Weight = 0;
        int Height = 0;
        int Age = 0;
        double A = 0;
        double Result = 0;
        Cursor cursor = MainActivity.mDb.rawQuery("SELECT * FROM User", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Plan = cursor.getString(6);
            Age = cursor.getInt(7);
            Gender = cursor.getString(8);
            Weight = cursor.getInt(9);
            Height = cursor.getInt(10);
            cursor.moveToNext();
        }
        cursor.close();
        switch (Plan) {
            case "BX":
                A = 1.2;
                break;
            case "Minimum / lack of physical activity":
                A = 1.375;
                break;
            case "3 times a week":
                A = 1.375;
                break;
            case "5 times a week":
                A = 1.55;
                break;
            case "5 times a week (intensively)":
                A = 1.55;
                break;
            case "Everyday":
                A = 1.725;
                break;
            case "Every day intensively or twice a day":
                A = 1.9;
                break;
            case "Daily exercise + physical work":
                A = 1.9;
                break;
            default:
                Plan = "";
                break;
        }
        if (Plan.equals("") && Gender.equals("")) {
            Toast toast2 = Toast.makeText(getContext(),"Change settings option", Toast.LENGTH_SHORT);
            toast2.show();
        } else {
            if (Gender.equals("1")) {
                Result = (10*Weight + 6.25*Height - 5*Age + 5)*A;
                Keep.setText(String.valueOf(Math.round(Result)));
                Gain.setText(String.valueOf(Math.round(Result) + 311));
                Loose.setText(String.valueOf(Math.round(Result) - 322));
            } else {
                Result = (10*Weight + 6.25*Height - 5*Age - 161)*A;
                Keep.setText(String.valueOf(Math.round(Result)));
                Gain.setText(String.valueOf(Math.round(Result) + 311));
                Loose.setText(String.valueOf(Math.round(Result) - 322));
            }
        }
    }
}
