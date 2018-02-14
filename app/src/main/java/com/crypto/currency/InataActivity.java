package com.crypto.currency;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.crypto.currency.data.InterstialMe;
import com.crypto.currency.data.VARIABELS;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;
import static android.content.Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP;
import static com.crypto.currency.data.InterstialMe.AUTOCLOSEAD;
import static com.crypto.currency.data.InterstialMe.AUTORELOAD;
import static com.crypto.currency.data.InterstialMe.BERHASIL;
import static com.crypto.currency.data.InterstialMe.DATE;
import static com.crypto.currency.data.InterstialMe.GAGAL;
import static com.crypto.currency.data.InterstialMe.OPEN;
import static com.crypto.currency.data.InterstialMe.RATE;
import static com.crypto.currency.data.InterstialMe.SHOW;
import static com.crypto.currency.data.InterstialMe.saveString;

public class InataActivity extends AppCompatActivity {

    private InterstitialAd mInterstitialAd;
    public final String RELOADE="reload";

    public boolean reload=false,autoclose,autoreload;


    public int gagalt=0,berhasilt=0,cik=0,show=0;
    public String ratess;

    Button sett;
    TextView berhasil,gagal,auto,categori,close,tanggalan,adopen,rate,showon,times;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        saveBool(RELOADE,false,this);
        reload = false;
        autoclose = false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inata);
        viewBinds();

        CekDateUP();

        data();
        sett.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SettingAct.startActivity(InataActivity.this);
            }
        });

        MobileAds.initialize(this, VARIABELS.getString(SettingAct.BANNERID,this,getString(R.string.TestUnitBannerID)));
        DODO();
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("mamud", "onClick: "+reload);
                if(!reload){
                    Toast.makeText(InataActivity.this, "Loading...", Toast.LENGTH_SHORT).show();
                    LoadedAD();
                }else if(!autoreload){
                    if(mInterstitialAd.isLoaded()){
                        LoadAd(VARIABELS.getString(SettingAct.CATEGORYAD,InataActivity.this,getString(R.string.app_name)));
                        LoadedAD();
                    }else {
                        DODO();
                    }
                    Toast.makeText(InataActivity.this, "Loading...", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        saveBool(RELOADE,false,this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        reload=false;
        saveBool(RELOADE, false,this);
        autoclose=false;

    }
    @Override
    protected void onResume() {
        super.onResume();
        reload=true;
        saveBool(RELOADE, true,this);
        CekDateUP();
        data();
        autoclose=true;
    }

    public void viewBinds(){
        berhasil=findViewById(R.id.succsestotint);
        gagal=findViewById(R.id.failtotint);
        adopen=findViewById(R.id.adopenBanint);
        tanggalan=findViewById(R.id.tanggalint);
        rate=findViewById(R.id.rateSBanint);
        auto=findViewById(R.id.A11);
        close=findViewById(R.id.timeautoReloadINTERTV);
        categori=findViewById(R.id.keywordInter);
        sett=findViewById(R.id.set_interes);
        showon=findViewById(R.id.shoewint);
        times=findViewById(R.id.timede);
    }
    @SuppressLint("SetTextI18n")
    public void data(){
        gagalt= InterstialMe.getInteger(GAGAL,this);
        berhasilt=InterstialMe.getInteger(BERHASIL,this);
        cik=InterstialMe.getInteger(OPEN,this);
        ratess=InterstialMe.getString(RATE,this);
        show=InterstialMe.getInteger(SHOW,this);

        autoclose=VARIABELS.getBool(SettingAct.AUTORELOADINTER,this);
        autoreload=VARIABELS.getBool(SettingAct.AUTORELOADINTER,this);
        reload=getBool(RELOADE,this);

        tanggalan.setText("Estimates calculation in :\n"+InterstialMe.getString(DATE,this));
        if(VARIABELS.getBool(SettingAct.AUTORELOADINTER,this)){
            auto.setText("AUTO RELOAD ACTIVE");
        }else {
            auto.setText("AUTO RELOAD OFF");
            times.setText("");
        }
        if(VARIABELS.getBool(SettingAct.AUTORELOADINTER,this)){
            close.setText("AUTO CLOSE AD ACTIVE ");
        }else{
            close.setText("AUTO CLOSE AD OFF");
        }
        categori.setText("Keyword : "+VARIABELS.getString(SettingAct.CATEGORYAD,InataActivity.this,getString(R.string.app_name)));
        berhasil.setText("LOAD :"+berhasilt);
        gagal.setText("FAILED :"+gagalt);
        adopen.setText("CLICK :"+cik);
        rate.setText("CTR :"+ratess+"%");
        showon.setText("SHOW :"+show);
    }
    public void LoadAd(String kategory){
        mInterstitialAd.loadAd(new AdRequest.Builder().addKeyword(kategory).build());
    }
    public void DODO(){
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(VARIABELS.getString(SettingAct.INTERSTITIALID,this,getString(R.string.TestIntertitialID)));
        mInterstitialAd.setAdListener(new AdListener(){
            @Override
            public void onAdClosed() {
                if(!autoclose) {
                    LoadAd(VARIABELS.getString(SettingAct.CATEGORYAD,InataActivity.this,getString(R.string.app_name)));
                }
            }

            @Override
            public void onAdFailedToLoad(int i) {
                if(i==0){
                    Toast.makeText(InataActivity.this, "error : "+getString(R.string.error0), Toast.LENGTH_SHORT).show();
                }else if(i==1){
                    Toast.makeText(InataActivity.this, "error : "+getString(R.string.error1), Toast.LENGTH_SHORT).show();
                }else if(i==2){
                    Toast.makeText(InataActivity.this, "error : "+getString(R.string.error2), Toast.LENGTH_SHORT).show();
                }else if(i==3){
                    Toast.makeText(InataActivity.this, "error : "+getString(R.string.error3), Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(InataActivity.this, "error : ad Failed to load, please make your connection stable", Toast.LENGTH_SHORT).show();
                }
                gagalt++;
                LoadAd(VARIABELS.getString(SettingAct.CATEGORYAD,InataActivity.this,getString(R.string.app_name)));
                InterstialMe.saveInteger(GAGAL,gagalt,InataActivity.this);
                dataC();
            }

            @Override
            public void onAdLeftApplication() {
                super.onAdLeftApplication();
                cik++;
                InterstialMe.saveInteger(OPEN,cik,InataActivity.this);
                dataC();
            }

            @Override
            public void onAdOpened() {
                show++;
                InterstialMe.saveInteger(SHOW,show,InataActivity.this);
                dataC();
                super.onAdOpened();
                if(autoclose) {
                    countDownTimeAR();
                }
            }

            @Override
            public void onAdLoaded() {
                Toast.makeText(InataActivity.this, "Ad Ready", Toast.LENGTH_SHORT).show();
                if(autoreload&&reload){
                    mInterstitialAd.show();
                }
                berhasilt++;
                InterstialMe.saveInteger(BERHASIL,berhasilt,InataActivity.this);
                dataC();
            }

            @Override
            public void onAdClicked() {
                super.onAdClicked();

            }

            @Override
            public void onAdImpression() {
                super.onAdImpression();
            }

        });
        if(autoclose){
            LoadAd(VARIABELS.getString(SettingAct.CATEGORYAD,InataActivity.this,getString(R.string.app_name)));
        }else if(autoreload){
            LoadAd(VARIABELS.getString(SettingAct.CATEGORYAD,InataActivity.this,getString(R.string.app_name)));
        }
    }
    public static void startActivity(Context context) {

        Intent intent = new Intent(context, InataActivity.class);
        context.startActivity(intent);
    }
    public static void startActivity(Context context, int flags) {
        Intent intent = new Intent(context, InataActivity.class);
        intent.setFlags(flags);
        context.startActivity(intent);
    }
    public void dataC(){
        float total = ((float)cik/(float)show)*100;
        DecimalFormat df = new DecimalFormat("####.##");
        ratess = df.format(total);
        saveString(RATE,ratess,this);
        CekDateUP();
        data();
    }
    public void CekDateUP(){
        @SuppressLint("SimpleDateFormat") DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy");
        String date = df.format(Calendar.getInstance().getTime());
        if(!date.equals(InterstialMe.getString(DATE,this))){
            saveString(DATE,date,this);
            resetResult();

        }else{
            saveString(DATE,date,this);
        }
    }
    public void resetResult(){
        InterstialMe.saveInteger(SHOW,0,InataActivity.this);
        InterstialMe.saveInteger(GAGAL,0,InataActivity.this);
        InterstialMe.saveInteger(BERHASIL,0,InataActivity.this);
        InterstialMe.saveInteger(OPEN,0,InataActivity.this);
        saveString(RATE,"0",this);
        data();
        CekDateUP();
    }
    public void ResetOnClick(View view){
        resetResult();
    }
    public void LoadedAD(){
        mInterstitialAd.show();
    }
    public void countDownTimeAR(){
        new CountDownTimer(VARIABELS.getInteger(SettingAct.DURATIONRELOADINTERSTITIAL,this,60)*1000, 1000) {

            @SuppressLint("SetTextI18n")
            public void onTick(long millisUntilFinished) {
                times.setText(millisUntilFinished/1000+" s");
            }

            public void onFinish() {
                if(autoclose){
                    InataActivity.startActivity(InataActivity.this,
                            Intent.FLAG_ACTIVITY_NEW_TASK | FLAG_ACTIVITY_PREVIOUS_IS_TOP | FLAG_ACTIVITY_CLEAR_TOP);
                }
            }
        }.start();
    }
    @SuppressLint("ApplySharedPref")
    public static void saveBool(String key, Boolean value, Context context){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }
    @NonNull
    public static Boolean getBool(String key, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getBoolean(key, false);
    }
}
