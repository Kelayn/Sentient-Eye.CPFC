package com.example.sentient_eyecpfc;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class OpenCameraScanActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler{

    private static String tmp = null;
    ZXingScannerView ScannerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ScannerView = new ZXingScannerView(this);
        setContentView(ScannerView);

    }

    @Override
    public void handleResult(Result result) {
        ScannerFragment.textView.setText(result.getText());
        ScannerFragment.mBtnFind.setVisibility(View.VISIBLE);
        tmp = result.getText();
        onBackPressed();
    }

    @Override
    protected void onResume() {
        super.onResume();

        ScannerView.setResultHandler(this);
        ScannerView.startCamera();
    }

    public static String getTmp () {
        return tmp;
    }

    @Override
    protected void onPause() {
        super.onPause();

        ScannerView.stopCamera();
    }

}
