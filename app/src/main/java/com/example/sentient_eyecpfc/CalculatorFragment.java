package com.example.sentient_eyecpfc;

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
import com.example.sentient_eyecpfc.Database.DatabaseUsage;

public class CalculatorFragment extends Fragment {
    private Button mAddSwitch;
    private Button mAddFoodManButton;
    private TextView mFats;
    private TextView mProts;
    private TextView mCBH;
    private TextView mCals;
    private TextView mName;
    private TextView mDose;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_calculator, container, false);
        DatabaseUsage dbUse = new DatabaseUsage(this.getContext());
        mAddSwitch = view.findViewById(R.id.addFoodSwitch);
        mCals = view.findViewById(R.id.caloriesText);
        mFats = view.findViewById(R.id.fatText);
        mProts = view.findViewById(R.id.protText);
        mCBH = view.findViewById(R.id.CBHText);
        mName = view.findViewById(R.id.nameText);
        mDose = view.findViewById(R.id.doseText);
        mAddFoodManButton = view.findViewById(R.id.addFoodManButton);
        mAddSwitch.setOnClickListener(v -> {
            toggleFields();
        });
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
            toggleFields();
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
    }
}
