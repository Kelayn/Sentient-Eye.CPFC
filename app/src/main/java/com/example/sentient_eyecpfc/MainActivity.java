package com.example.sentient_eyecpfc;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    ImageButton scannerImageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scannerImageButton = (ImageButton) findViewById(R.id.scanner_image_button);

        scannerImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View e) {
                Intent intentLoadScannerActivity =
                        new Intent(MainActivity.this, ScannerActivity.class);
                startActivity(intentLoadScannerActivity);
            }
        });
    }
}
