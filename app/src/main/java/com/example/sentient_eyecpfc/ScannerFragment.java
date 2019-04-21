package com.example.sentient_eyecpfc;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        TextView txtView = Objects.requireNonNull(getView()).findViewById(R.id.txtContent);
        Button btn_scan = getView().findViewById(R.id.button_scan);

        ImageView scanImageView = getView().findViewById(R.id.img_scan);
        Bitmap bitmap = BitmapFactory.decodeResource(Objects.requireNonNull(getActivity()).getApplicationContext().getResources(), R.drawable.puppy);
        scanImageView.setImageBitmap(bitmap);

        BarcodeDetector detector = new BarcodeDetector.Builder(getActivity().getApplicationContext())
                .setBarcodeFormats(Barcode.DATA_MATRIX | Barcode.QR_CODE).build();

        if (!detector.isOperational()) {
            txtView.setText("Couldn't set up detector!");
            return null;
        }

        Frame frame = new Frame.Builder().setBitmap(bitmap).build();
        SparseArray<Barcode> barcodes = detector.detect(frame);

        Barcode thisBarcode = barcodes.valueAt(0);
        txtView.setText(thisBarcode.rawValue);

        btn_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        return inflater.inflate(R.layout.activity_barcode_detect, container, false);
    }
}