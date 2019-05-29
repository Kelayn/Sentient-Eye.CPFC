package com.example.sentient_eyecpfc;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.util.Objects;

public class ScannerFragment extends Fragment {
    public static TextView textView;
    Button btn_scan;
    private static final String TAG = "Acces";
    private static final int REQUEST_CODE = 1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_barcode_detect, container, false);

        textView = view.findViewById(R.id.textView);
        btn_scan = view.findViewById(R.id.btn_scan);

        btn_scan.setOnClickListener(v -> verify());

        return view;
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