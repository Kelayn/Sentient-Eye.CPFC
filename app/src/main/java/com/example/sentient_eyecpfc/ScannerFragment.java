package com.example.sentient_eyecpfc;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
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
        View view = inflater.inflate(R.layout.activity_barcode_detect, container, false);

        TextView txtView = view.findViewById(R.id.txtContent);
        Button btn_scan = view.findViewById(R.id.button_scan);

        ImageView scanImageView = view.findViewById(R.id.img_scan);
        Bitmap bitmap = BitmapFactory.decodeResource(getActivity().getResources(), R.drawable.puppy);
        scanImageView.setImageBitmap(bitmap);

        BarcodeDetector detector = new BarcodeDetector.Builder(getActivity())
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

        return view;
    }
}