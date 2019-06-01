package com.example.sentient_eyecpfc.Fragments;


import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.fragment.app.DialogFragment;
import com.example.sentient_eyecpfc.Data.Product;
import com.example.sentient_eyecpfc.Network.RetrofitFactory;
import com.example.sentient_eyecpfc.Network.RetrofitSetup;
import com.example.sentient_eyecpfc.R;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import com.example.sentient_eyecpfc.Database.DatabaseUsage;


public class EnterProductDialog extends DialogFragment implements OnClickListener {
    private String mCode;
    private TextView mCodeTW;
    private TextView mNameTW;
    private TextView mCaloriesTW;
    private TextView mProteinsTW;
    private TextView mCarbohydratesTW;
    private TextView mFatsTW;
    private TextView mDoseTW;

    private Product mProduct;
    private String mName;
    private Double mCalories;
    private Double mProteins;
    private Double mCarbohydrates;
    private Double mFats;
    private Boolean mToSave;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().setTitle("Title!");
        View v = inflater.inflate(R.layout.fragment_enter_product, null);
        mCodeTW = v.findViewById(R.id.codeEdit);
        mNameTW = v.findViewById(R.id.nameEdit);
        mCaloriesTW = v.findViewById(R.id.caloriesEdit);
        mProteinsTW = v.findViewById(R.id.proteinsEdit);
        mCarbohydratesTW = v.findViewById(R.id.carbohEdits);
        mFatsTW = v.findViewById(R.id.fatsEdit);
        mDoseTW = v.findViewById(R.id.addDoseText);
        mCode = getArguments().getString("code"); //savedInstanceState.getString("code");
        mCodeTW.setText(mCode);
        v.findViewById(R.id.sendProduct).setOnClickListener(this);
        v.findViewById(R.id.saveProduct).setOnClickListener(v1 ->{
            mToSave = true;
        });
        return v;
    }

    public void onClick(View v) {
        Boolean isFilled = true;
        if(mNameTW.getText().toString().matches("")) {
            mNameTW.setBackgroundColor(Color.parseColor("#B00020"));
            isFilled = false;
        }
        if(mCaloriesTW.getText().toString().matches("")) {
            mCaloriesTW.setBackgroundColor(Color.parseColor("#B00020"));
            isFilled = false;
        }
        if(mProteinsTW.getText().toString().matches("")) {
            mProteinsTW.setBackgroundColor(Color.parseColor("#B00020"));
            isFilled = false;
        }
        if(mCarbohydratesTW.getText().toString().matches("")) {
            mCarbohydratesTW.setBackgroundColor(Color.parseColor("#B00020"));
            isFilled = false;
        }
        if(mFatsTW.getText().toString().matches("")) {
            mFatsTW.setBackgroundColor(Color.parseColor("#B00020"));
            isFilled = false;
        }
        if(mDoseTW.getText().toString().matches("")) {
            mDoseTW.setBackgroundColor(Color.parseColor("#B00020"));
            isFilled = false;
        }
        if (!isFilled) return;

        mNameTW.setBackgroundColor(Color.parseColor("#FFFFFF"));
        mCaloriesTW.setBackgroundColor(Color.parseColor("#FFFFFF"));
        mProteinsTW.setBackgroundColor(Color.parseColor("#FFFFFF"));
        mCarbohydratesTW.setBackgroundColor(Color.parseColor("#FFFFFF"));
        mFatsTW.setBackgroundColor(Color.parseColor("#FFFFFF"));

        mName = mNameTW.getText().toString();
        mCalories = Double.parseDouble(mCaloriesTW.getText().toString());
        mProteins = Double.parseDouble(mProteinsTW.getText().toString());
        mCarbohydrates =Double.parseDouble(mCarbohydratesTW.getText().toString());
        mFats = Double.parseDouble(mFatsTW.getText().toString());
        mProduct = new Product (mCode, mName, mCalories, mProteins, mCarbohydrates, mFats);
        if (mToSave){
            Long log;
            DatabaseUsage dbUse = new DatabaseUsage(this.getContext());
            log = dbUse.setProduct(  mName,
                    mCalories,  mProteins,
                    mFats,  mCarbohydrates,
                    Double.parseDouble(mDoseTW.getText().toString()));
            Log.e("LOGGING", log.toString());
        }
        sendProduct(mProduct);
        mNameTW.setText("");
        mCaloriesTW.setText("");
        mProteinsTW.setText("");
        mCarbohydratesTW.setText("");
        mFatsTW.setText("");
        mDoseTW.setText("");
        dismiss();
    }

    @SuppressLint("CheckResult")
    private void sendProduct(Object _product) {
        RetrofitFactory.getRetrofit().create(RetrofitSetup.class)
                .setProduct(_product)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(product -> {

                        },
                        error ->{} );
    }

}
