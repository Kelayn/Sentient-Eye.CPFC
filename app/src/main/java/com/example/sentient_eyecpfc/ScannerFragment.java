package com.example.sentient_eyecpfc;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.sentient_eyecpfc.Data.Product;
import com.example.sentient_eyecpfc.Database.DatabaseUsage;
import com.example.sentient_eyecpfc.Fragments.EnterProductDialog;
import com.example.sentient_eyecpfc.Network.RetrofitFactory;
import com.example.sentient_eyecpfc.Network.RetrofitSetup;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ScannerFragment extends Fragment {
    public static TextView textView;
    Button btn_scan;
    public static Button mBtnFind;
    TextView mCheck;
    Product mProduct;
    DialogFragment addProductDial;
    private static final String TAG = "Access";
    private static final int REQUEST_CODE = 1;
    Bundle mBundle;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_barcode_detect, container, false);
        textView = view.findViewById(R.id.textView);
        mCheck = view.findViewById(R.id.textFormGain);
        btn_scan = view.findViewById(R.id.btn_scan);
        mBtnFind = view.findViewById(R.id.find_API_btn);
        mBtnFind.setVisibility(View.INVISIBLE);

        mBundle = new Bundle();
        btn_scan.setOnClickListener(v -> verify());
        mBtnFind.setOnClickListener(v -> {
            if (!textView.getText().toString().matches("")) {
                mBundle.putString("code",textView.getText().toString());
                getProduct(textView.getText().toString());
            }
        });
        return view;
    }

    @SuppressLint("CheckResult")
    private void getProduct(String code) {

        RetrofitFactory.getRetrofit().create(RetrofitSetup.class)
                .getProductByCode(code)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(product -> {
                    DatabaseUsage dBUs = new DatabaseUsage(getContext());
                    if(!product.getName().equals("Not Found")) {
                        mProduct = product;
                        mCheck.setText(mProduct.getName());
                        dBUs.updateKBJUinProfile(getContext());
                    }else{
                        addProductDial = new EnterProductDialog();
                        addProductDial.setArguments(mBundle);
                        addProductDial.show(getFragmentManager(), "addProductDial");
                        dBUs.updateKBJUinProfile(getContext());
                    }
                    },
                        error -> mCheck.setText(error.getMessage()));
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


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        verify();
    }
}