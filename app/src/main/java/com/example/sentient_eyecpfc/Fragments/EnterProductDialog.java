package com.example.sentient_eyecpfc.Fragments;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

import com.example.sentient_eyecpfc.Data.Product;
import com.example.sentient_eyecpfc.Network.RetrofitFactory;
import com.example.sentient_eyecpfc.Network.RetrofitSetup;
import com.example.sentient_eyecpfc.R;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class EnterProductDialog extends DialogFragment implements OnClickListener {
    private String mCode;
    private TextView mCodeTW;
    private TextView mNameTW;
    private TextView mCaloriesTW;
    private TextView mProteinsTW;
    private TextView mCarbohydratesTW;
    private TextView mFatsTW;



    private Product mProduct;
    private String mName;
    private Double mCalories;
    private Double mProteins;
    private Double mCarbohydrates;
    private Double mFats;


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().setTitle("Title!");
        View v = inflater.inflate(R.layout.fragment_enter_product, null);
        v.findViewById(R.id.sendProduct).setOnClickListener(this);
        mCodeTW = v.findViewById(R.id.codeEdit);
        mNameTW = v.findViewById(R.id.nameEdit);
        mCaloriesTW = v.findViewById(R.id.caloriesEdit);
        mProteinsTW = v.findViewById(R.id.proteinsEdit);
        mCarbohydratesTW = v.findViewById(R.id.carbohEdits);
        mFatsTW = v.findViewById(R.id.fatsEdit);
        mCode = getArguments().getString("code"); //savedInstanceState.getString("code");
        mCodeTW.setText(mCode);
        return v;
    }

    public void onClick(View v) {
        mName = mNameTW.getText().toString();
        mCalories = Double.parseDouble(mCaloriesTW.getText().toString());
        mProteins = Double.parseDouble(mProteinsTW.getText().toString());
        mCarbohydrates =Double.parseDouble(mCarbohydratesTW.getText().toString());
        mFats = Double.parseDouble(mFatsTW.getText().toString());
        mProduct = new Product (mCode, mName, mCalories, mProteins, mCarbohydrates, mFats);
        sendProduct(mProduct);
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
