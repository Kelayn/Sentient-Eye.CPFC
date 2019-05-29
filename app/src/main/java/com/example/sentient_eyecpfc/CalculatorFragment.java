package com.example.sentient_eyecpfc;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

public class CalculatorFragment extends Fragment {
    private Switch mSwitch;
    private TextView mFats;
    private TextView mProts;
    private TextView mCBH;
    private TextView mCals;
    private TextView mName;
    private Button mButton;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_calculator, container, false);
        mSwitch = view.findViewById(R.id.switch1);
        mCals = view.findViewById(R.id.caloriesText);
        mFats = view.findViewById(R.id.fatText);
        mProts = view.findViewById(R.id.protText);
        mCBH = view.findViewById(R.id.CBHText);
        mName = view.findViewById(R.id.nameText);
        mButton = view.findViewById(R.id.button3);
        mSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    toggleSwitch();
                } else {

                }
            }
        });
        return inflater.inflate(R.layout.fragment_calculator, container, false);
    }

    public void toggleSwitch() {
        mFats.setEnabled(!mFats.isEnabled());
        mProts.setEnabled(!mProts.isEnabled());
        mCBH.setEnabled(!mCBH.isEnabled());
        mCals.setEnabled(!mCals.isEnabled());
        mName.setEnabled(!mName.isEnabled());
        mButton.setEnabled(!mButton.isEnabled());
    }
}
