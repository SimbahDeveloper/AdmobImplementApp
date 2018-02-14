package com.crypto.currency;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.crypto.currency.data.FetchGeoIp;

public class MainActivity extends AppCompatActivity {

    public static TextView data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewLayout();
        cekIp();
    }

    private void cekIp() {
        FetchGeoIp process = new FetchGeoIp();
        process.execute();
    }


    @SuppressLint("SetTextI18n")
    public void viewLayout(){
        TextView packageName= findViewById(R.id.textView);
        data = findViewById(R.id.textView6);
        packageName.setText("this package :"+getPackageName());
    }
    public void Refresh(View view){
        cekIp();
    }
    public void Banner(View view){
        BananaActivity.startActivity(this);
        Toast.makeText(this, "Banner Activity", Toast.LENGTH_SHORT).show();
    }
    public void Inters(View view){
        InataActivity.startActivity(this);
        Toast.makeText(this, "Interstitial Activity", Toast.LENGTH_SHORT).show();
    }
    public void Setting(View view){
        SettingAct.startActivity(this);
        Toast.makeText(this, "Setting Admob", Toast.LENGTH_SHORT).show();
    }
}
