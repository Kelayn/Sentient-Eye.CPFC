package com.example.sentient_eyecpfc;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Path;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;

import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sentient_eyecpfc.Data.Product;
import com.example.sentient_eyecpfc.Fragments.EnterProductDialog;
import com.example.sentient_eyecpfc.Network.RetrofitFactory;
import com.example.sentient_eyecpfc.Network.RetrofitSetup;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;
import java.util.Objects;

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
    private String tmp = null;
    private static final int REQUEST_CODE = 1;
    Bundle mBundle;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_barcode_detect, container, false);
        textView = view.findViewById(R.id.textView);
        mCheck = view.findViewById(R.id.textView8);
        btn_scan = view.findViewById(R.id.btn_scan);
        mBtnFind = view.findViewById(R.id.find_API_btn);
        mBtnFind.setVisibility(View.INVISIBLE);
        //textView.setText(OpenCameraScanActivity.getTmp());



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
                    if(!product.getName().equals("Not Found")) {
                        mProduct = product;
                        mCheck.setText(mProduct.getName());
                    }else{
                        addProductDial = new EnterProductDialog();
                        addProductDial.setArguments(mBundle);
                        addProductDial.show(getFragmentManager(), "addProductDial");
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

    public void setTmp(String tmp) {
        this.tmp = tmp;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        verify();
    }
}