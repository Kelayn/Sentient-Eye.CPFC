package com.example.sentient_eyecpfc.Fragments;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.sentient_eyecpfc.R;

public class FitnessPlanFragment extends Fragment {

    int checkedRadioButtonId;
    RadioGroup radioActiv;
    public static String TrainingPlan = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fitness_plan, container, false);

        Button btnCnf = view.findViewById(R.id.btnCnf);
        radioActiv = view.findViewById(R.id.radioGroup);
        btnCnf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkedRadioButtonId = radioActiv.getCheckedRadioButtonId();
                switch (checkedRadioButtonId){
                    case R.id.radioBX:
                        TrainingPlan = "BX";
                        break;
                    case R.id.radioMin:
                        TrainingPlan = "Minimum / lack of physical activity";
                        break;
                    case R.id.radio3tm:
                        TrainingPlan = "3 times a week";
                        break;
                    case R.id.radio5tm:
                        TrainingPlan = "5 times a week";
                        break;
                    case R.id.radio5tmI:
                        TrainingPlan = "5 times a week (intensively)";
                        break;
                    case R.id.radioEvd:
                        TrainingPlan = "Everyday";
                        break;
                    case R.id.radioEvdOr:
                        TrainingPlan = "Every day intensively or twice a day";
                        break;
                    case R.id.radioDly:
                        TrainingPlan = "Daily exercise + physical work";
                        break;
                    case -1:
                        Toast toast2 = Toast.makeText(getContext(), "Change activity", Toast.LENGTH_SHORT);
                        toast2.show();
                        break;
                }
                Toast toast = Toast.makeText(getContext(), "You have chosen '" + TrainingPlan + "' plan", Toast.LENGTH_SHORT);
                toast.show();
            }
        });

        return view;
    }
}
