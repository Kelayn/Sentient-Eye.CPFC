package com.example.sentient_eyecpfc.Fragments;



import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;
import com.example.sentient_eyecpfc.Database.DatabaseUsage;
import com.example.sentient_eyecpfc.R;



public class AddProductToDB extends DialogFragment implements OnClickListener {
    private TextView mCodeTW;
    private TextView mNameTW;
    private TextView mCaloriesTW;
    private TextView mProteinsTW;
    private TextView mCarbohydratesTW;
    private TextView mFatsTW;
    private TextView mDoseTW;
    private Button mSend;
    private CheckBox mCheck;

    private String mName;
    private Double mCalories;
    private Double mProteins;
    private Double mCarbohydrates;
    private Double mFats;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().setTitle("Title!");
        View v = inflater.inflate(R.layout.fragment_enter_product, null);
        mCodeTW = v.findViewById(R.id.codeEdit);
        mCodeTW.setEnabled(false);
        mCodeTW.setText(getArguments().getString("code"));
        mNameTW = v.findViewById(R.id.nameEdit);
        mNameTW.setEnabled(false);
        mNameTW.setText(getArguments().getString("name"));
        mCaloriesTW = v.findViewById(R.id.caloriesEdit);
        mCaloriesTW.setEnabled(false);
        mCaloriesTW.setText(getArguments().getString("cals"));
        mProteinsTW = v.findViewById(R.id.proteinsEdit);
        mProteinsTW.setEnabled(false);
        mProteinsTW.setText(getArguments().getString("prots"));
        mCarbohydratesTW = v.findViewById(R.id.carbohEdits);
        mCarbohydratesTW.setEnabled(false);
        mCarbohydratesTW.setText(getArguments().getString("CBH"));
        mFatsTW = v.findViewById(R.id.fatsEdit);
        mFatsTW.setEnabled(false);
        mFatsTW.setText(getArguments().getString("fats"));
        mDoseTW = v.findViewById(R.id.addDoseText);
        mSend = v.findViewById(R.id.sendProduct);
        mSend.setOnClickListener(this);
        mCheck = v.findViewById(R.id.saveProduct);
        mCheck.setVisibility(View.INVISIBLE);
        mSend.setText("Add to collection");
        return v;
    }

    public void onClick(View v) {
        Boolean isFilled = true;
        DatabaseUsage dBUs = new DatabaseUsage(getContext());
        if (mDoseTW.getText().toString().matches("")) {
            mDoseTW.setBackgroundColor(Color.parseColor("#B00020"));
            isFilled = false;
        }
        if (!isFilled) return;

        mDoseTW.setBackgroundColor(Color.parseColor("#FFFFFF"));


        mName = mNameTW.getText().toString();
        mCalories = Double.parseDouble(mCaloriesTW.getText().toString());
        mProteins = Double.parseDouble(mProteinsTW.getText().toString());
        mCarbohydrates = Double.parseDouble(mCarbohydratesTW.getText().toString());
        mFats = Double.parseDouble(mFatsTW.getText().toString());

        Long log;
        DatabaseUsage dbUse = new DatabaseUsage(this.getContext());
        log = dbUse.setProduct(mName,
                mCalories, mProteins,
                mFats, mCarbohydrates,
                Double.parseDouble(mDoseTW.getText().toString()));
        Log.e("LOGGING", log.toString());
        dBUs.updateKBJUinProfile(getContext());
        dismiss();
    }
}
