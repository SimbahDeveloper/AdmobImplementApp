package com.crypto.currency;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.crypto.currency.data.VARIABELS;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class SettingAct extends AppCompatActivity {

    public final static String APPID="a",
            BANNERID="b",
            INTERSTITIALID="c",
            CATEGORYAD="d",
            TOTALBANNER="e",
            AUTORELOADBANNER="f",
            DURATIONRELOADBANNER="h",
            SIZEBANNER="g",
            DURATIONRELOADINTERSTITIAL="i",AUTORELOADINTER="j";

    EditText appID,bannerID,interstialID,categoryAD,totalBanner,editTextBan,editTextInter;
    LinearLayout ads,autoReloadbannerlinear,interstitialLinear;
    CheckBox autoReloadBanner,autoReloadInter;

    Boolean autoReloadBan,autoRelBan,autoReloadInters,autoRelInter;
    int radio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        MobileAds.initialize(this,getString(R.string.MobileAds));
        adsVio();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        viewLayout();
        genneralSetting();
        bannerSetting();
        interSetting();
    }


    public void Save(View view){
        if(!appID.getText().toString().isEmpty()){
            VARIABELS.saveString(APPID,appID.getText().toString(),this);
        }
        if(!bannerID.getText().toString().isEmpty()){
            VARIABELS.saveString(BANNERID,bannerID.getText().toString(),this);
        }
        if(!interstialID.getText().toString().isEmpty()){
            VARIABELS.saveString(INTERSTITIALID,interstialID.getText().toString(),this);
        }
        if(!categoryAD.getText().toString().isEmpty()){
            VARIABELS.saveString(CATEGORYAD,categoryAD.getText().toString(),this);
        }
        if(!totalBanner.getText().toString().isEmpty()){
            VARIABELS.saveInteger(TOTALBANNER, Integer.valueOf(totalBanner.getText().toString()),this);
        }

        VARIABELS.saveBool(AUTORELOADBANNER, autoReloadBan,this);
        if(autoReloadBan){

            if(editTextBan.getText().toString().isEmpty()){
                VARIABELS.saveInteger(DURATIONRELOADBANNER,VARIABELS.getInteger(DURATIONRELOADBANNER,this,60),this);
            }else {
                VARIABELS.saveInteger(DURATIONRELOADBANNER, Integer.valueOf(editTextBan.getText().toString()),this);
            }
        }
        VARIABELS.saveInteger(SIZEBANNER,radio,this);

        VARIABELS.saveBool(AUTORELOADINTER, autoReloadInters,this);
        if(autoReloadInters){
            if(editTextInter.getText().toString().isEmpty()){
                VARIABELS.saveInteger(DURATIONRELOADINTERSTITIAL,VARIABELS.getInteger(DURATIONRELOADINTERSTITIAL,this,60),this);
            }else {
                VARIABELS.saveInteger(DURATIONRELOADINTERSTITIAL, Integer.valueOf(editTextInter.getText().toString()),this);
            }
        }
        Toast.makeText(this, "Updating Datas", Toast.LENGTH_SHORT).show();
    }

    public void genneralSetting(){
        appID.setText(VARIABELS.getString(APPID,this,getString(R.string.TestMobileAds)));
        bannerID.setText(VARIABELS.getString(BANNERID,this,getString(R.string.TestUnitBannerID)));
        interstialID.setText(VARIABELS.getString(INTERSTITIALID,this,getString(R.string.TestIntertitialID)));
        categoryAD.setText(VARIABELS.getString(CATEGORYAD,this,getString(R.string.app_name)));
    }
    public void adsVio(){

        final AdView mAdView = new AdView(this);
        mAdView.setAdSize(AdSize.MEDIUM_RECTANGLE);
        mAdView.setAdUnitId(getString(R.string.UnitBannerID));
        mAdView.setAdListener(new AdListener(){
            boolean load=true;
            @Override
            public void onAdFailedToLoad(int i) {
                super.onAdFailedToLoad(i);
            }

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                if(load){
                ads.addView(mAdView);
                load=false;
                }
            }
        });

        AdRequest adRequest = new AdRequest.Builder()
                .addKeyword("bitcoin")
                .build();
        mAdView.loadAd(adRequest);

    }
    @SuppressLint("ResourceType")
    public void bannerSetting(){
        totalBanner.setHint(String.valueOf(VARIABELS.getInteger(TOTALBANNER,this,1)));
        autoRelBan=VARIABELS.getBool(AUTORELOADBANNER,this);
        autoReloadBanner.setChecked(autoRelBan);
        autoReloadBan=autoRelBan;

        editTextBan=new EditText(this);
        editTextBan.setInputType(InputType.TYPE_CLASS_NUMBER);
        editTextBan.setHint("duration in second : "+VARIABELS.getInteger(DURATIONRELOADBANNER,this,60)+" second");

        if(autoReloadBanner.isChecked()){
            autoReloadbannerlinear.addView(editTextBan);
        }
        autoReloadBanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(autoReloadBanner.isChecked()){
                    autoReloadBan=true;
                    autoReloadbannerlinear.addView(editTextBan);
                    Log.d("TAG", "onClick: "+autoRelBan);
                }else{
                    autoReloadBan=false;
                    autoReloadbannerlinear.removeView(editTextBan);
                    Log.d("TAG", "onClick: "+autoRelBan);
                }
            }
        });

        radio=VARIABELS.getInteger(SIZEBANNER,this,6);

        if(radio==1){
            RadioButton radioButton=findViewById(R.id.radio1);
            radioButton.setChecked(true);
        }else if(radio==2){
            RadioButton radioButton=findViewById(R.id.radio2);
            radioButton.setChecked(true);
        }else if(radio==3){
            RadioButton radioButton=findViewById(R.id.radio3);
            radioButton.setChecked(true);
        }else if(radio==4){
            RadioButton radioButton=findViewById(R.id.radio4);
            radioButton.setChecked(true);
        }else if(radio==5){
            RadioButton radioButton=findViewById(R.id.radio5);
            radioButton.setChecked(true);
        }else{
            RadioButton radioButton=findViewById(R.id.radio6);
            radioButton.setChecked(true);
        }

        RadioGroup rg = findViewById(R.id.sizeRadio);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.radio1:
                        // do operations specific to this selection
                        radio=1;
                        Toast.makeText(SettingAct.this, "set size BANNER", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.radio2:
                        // do operations specific to this selection
                        radio=2;
                        Toast.makeText(SettingAct.this, "set size LARGE_BANNER", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.radio3:
                        // do operations specific to this selection
                        radio=3;
                        Toast.makeText(SettingAct.this, "set size MEDIUM_RECTANGLE", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.radio4:
                        // do operations specific to this selection
                        radio=4;
                        Toast.makeText(SettingAct.this, "set size FULL_BANNER", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.radio5:
                        // do operations specific to this selection
                        radio=5;
                        Toast.makeText(SettingAct.this, "set size LEADERBOARD", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.radio6:
                        // do operations specific to this selection
                        radio=6;
                        Toast.makeText(SettingAct.this, "set size SMART BANNER", Toast.LENGTH_SHORT).show();
                        break;

                }
            }
        });
    }
    public void interSetting(){
        autoRelInter=VARIABELS.getBool(AUTORELOADINTER,this);
        autoReloadInter.setChecked(autoRelInter);
        autoReloadInters=autoRelInter;

        editTextInter=new EditText(this);
        editTextInter.setInputType(InputType.TYPE_CLASS_NUMBER);
        editTextInter.setHint("duration in second : "+VARIABELS.getInteger(DURATIONRELOADINTERSTITIAL,this,60)+" second");

        if(autoReloadInter.isChecked()){
            interstitialLinear.addView(editTextInter);
        }
        autoReloadInter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(autoReloadInter.isChecked()){
                    autoReloadInters=true;
                    interstitialLinear.addView(editTextInter);
                    Log.d("TAG", "onClick: "+autoReloadInters);
                }else{
                    autoReloadInters=false;
                    interstitialLinear.removeView(editTextInter);
                    Log.d("TAG", "onClick: "+autoReloadInters);
                }
            }
        });
    }
    public void viewLayout(){
        //Edit Text Inisialisasi
        appID=findViewById(R.id.ET_appid);
        bannerID=findViewById(R.id.ET_bannerId);
        interstialID=findViewById(R.id.ET_interID);
        categoryAD=findViewById(R.id.ET_catID);
        totalBanner=findViewById(R.id.ET_tottalAd);
        //Linear Layout Inisialisasi
        ads=findViewById(R.id.LT_ads);
        interstitialLinear=findViewById(R.id.chextBoxser);
        autoReloadbannerlinear=findViewById(R.id.auto_relod_banner_linear);
        //checkbox inisialisasi
        autoReloadBanner=findViewById(R.id.auto_relod_cb);
        autoReloadInter=findViewById(R.id.auto_relod_inter);
    }

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, SettingAct.class);
        context.startActivity(intent);
    }

}
