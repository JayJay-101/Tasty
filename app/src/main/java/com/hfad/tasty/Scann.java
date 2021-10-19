package com.hfad.tasty;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.google.zxing.client.android.Intents;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
public class Scann extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        IntentIntegrator intentIntegrator=new IntentIntegrator(MainActivity.this);
        intentIntegrator.setCaptureActivity(re.class);
        intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
        intentIntegrator.setCameraId(0);
        intentIntegrator.setOrientationLocked(true);
        intentIntegrator.setPrompt("Scanning");
        intentIntegrator.setBeepEnabled(false);
        intentIntegrator.setBarcodeImageEnabled(true);
        intentIntegrator.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        IntentResult result=IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if (result!=null && result.getContents()!=null)
        {
            if (result.startsWith("iScanner@")) {
            result = result.substring(8);
            String[] ids = result.split("#");
             resid = ids[1];
             tableid = ids[2];
            Intent intent=new Intent(getApplicationContext(),MainActivity.class);
            intent.putExtra("d",result.getContents());
            Scann.this.startActivity(intent);}
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}
