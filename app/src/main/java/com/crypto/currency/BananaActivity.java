package com.crypto.currency;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.crypto.currency.data.VARIABELS;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Objects;

import static java.util.Calendar.DATE;

public class BananaActivity extends AppCompatActivity {
    private String appid;
    private int sizebans;
    private int banyak;
    TextView berhasil,gagal,auto,jumato,jumbanner,categori,size,tanggalan,adopen,rate;

    private static final String DATE="yyasd",GG="gsdag",BB="beqrewb",CIK="casdfsc",CT="crewt",IMP="imasfdpr";

    public int gagalt=0,berhasilt=0,impre=0,cik=0;
    public String ratess;

    public boolean sedang=false,asd;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        asd=false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banana);
        CekDateUP();
        asd=true;
        appid = VARIABELS.getString(SettingAct.BANNERID,this,getString(R.string.TestUnitBannerID));
        String mobid = VARIABELS.getString(SettingAct.APPID,this,getString(R.string.TestMobileAds));

        Button setting =findViewById(R.id.set_banner);
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goban();
            }
        });

        MobileAds.initialize(this, mobid);
        viewBinds();
        data();
        FloatingActionButton floatingActionButton= findViewById(R.id.floatingActionButtonbaner);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadMain();
            }
        });
    }

    @SuppressLint("SetTextI18n")
    public void data(){

        tanggalan.setText("Estimates calculation in :\n"+getString(DATE,this));
        berhasilt = getInteger(BB, this);
        gagalt=getInteger(GG,this);
        impre=getInteger(IMP,this);
        cik=getInteger(CIK,this);
        ratess = getString(CT, this);

        cekRate();

        berhasil.setText("LOAD :"+berhasilt);
        gagal.setText("FAILED :"+gagalt);
        adopen.setText("CLICK :"+cik);
        banyak=VARIABELS.getInteger(SettingAct.TOTALBANNER,this,1);
        jumbanner.setText("Total ad per imprs :"+banyak);
        if(VARIABELS.getBool(SettingAct.AUTORELOADBANNER,this)){
            auto.setText("AUTO RELOAD ACTIVE");
            jumato.setText("in :"+VARIABELS.getInteger(SettingAct.DURATIONRELOADBANNER,this,60)+" second");
        }else{
            auto.setText("AUTO RELOAD DEACTIVE");
            jumato.setText("NULL");
        }
        categori.setText("keyword ad : "+VARIABELS.getString(SettingAct.CATEGORYAD,this,getString(R.string.app_name)));
        //size.setText(BannerSetting.getStringBan(BannerSetting.SIZEBANNER,this));
        sizebans=VARIABELS.getInteger(SettingAct.SIZEBANNER,this,6);
    }

    public void resetResult(){
        saveInteger(GG,0,this);
        saveInteger(CIK,0,this);
        saveInteger(BB,0,this);
        saveInteger(IMP,0,this);
        saveString(CT,"0",this);
    }

    public void viewBinds(){
        berhasil=findViewById(R.id.succsestot);
        gagal=findViewById(R.id.failtot);
        auto=findViewById(R.id.autoReloads);
        adopen=findViewById(R.id.adopenBan);
        jumato=findViewById(R.id.timereload);
        jumbanner=findViewById(R.id.jumlahbanner);
        categori=findViewById(R.id.categoryban);
        size=findViewById(R.id.sizebanner);
        rate=findViewById(R.id.rateSBan);
        tanggalan=findViewById(R.id.tanggal);
    }

    public void CekDateUP(){
        @SuppressLint("SimpleDateFormat") DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy");
        String date = df.format(Calendar.getInstance().getTime());
        if(!date.equals(getString(DATE, this))){
            resetResult();
            saveString(DATE,date,this);
        }else{
            saveString(DATE,date,this);
        }
    }

    public void resetOnClick(View view){
        resetResult();
        data();
    }
    @Override
    protected void onResume() {
        CekDateUP();
        data();
        super.onResume();
    }

    public void goban(){
        SettingAct.startActivity(this);
    }

    public void loadMain(){
        Toast.makeText(this, "please wait for loading..", Toast.LENGTH_SHORT).show();
        if(!sedang){
            loadAd(banyak);
            if (VARIABELS.getBool(SettingAct.AUTORELOADBANNER,this)){
                reloadAuto();
            }
        }
    }

    public void reloadAuto(){
        new CountDownTimer(VARIABELS.getInteger(SettingAct.DURATIONRELOADBANNER,this,60)*1000, 1000) {

            @SuppressLint("SetTextI18n")
            public void onTick(long millisUntilFinished) {
                sedang=true;
                if (VARIABELS.getBool(SettingAct.AUTORELOADBANNER,BananaActivity.this)){
                    jumato.setText("in :"+((millisUntilFinished/1000)-1)+" second");
                }

            }

            public void onFinish() {
                sedang=false;
                loadAd(banyak);
                if (VARIABELS.getBool(SettingAct.AUTORELOADBANNER,BananaActivity.this)&& asd){
                    reloadAuto();
                }
            }
        }.start();
    }

    @SuppressLint("SetTextI18n")
    public void loadAd(int banyak){
        LinearLayout layout = findViewById(R.id.banner_layout);
        layout.removeAllViews();
        for(int a = 1;a<=banyak;a++) {
            AdView mAdView = new AdView(this);
            TextView sixe =new TextView(this);
            sixe.setText("AdView "+a);
            if(sizebans==1){
                mAdView.setAdSize(AdSize.BANNER);
            }else if(sizebans==2){
                mAdView.setAdSize(AdSize.LARGE_BANNER);
            }else if(sizebans==3){
                mAdView.setAdSize(AdSize.MEDIUM_RECTANGLE);
            }else if(sizebans==4){
                mAdView.setAdSize(AdSize.FULL_BANNER);
            }else if(sizebans==5){
                mAdView.setAdSize(AdSize.LEADERBOARD);
            }else{
                mAdView.setAdSize(AdSize.SMART_BANNER);
            }


            mAdView.setAdUnitId(appid);
            mAdView.setAdListener(new AdListener(){
                @Override
                public void onAdFailedToLoad(int i) {
                    super.onAdFailedToLoad(i);
                    if(i==0){
                        Toast.makeText(BananaActivity.this, "error : "+getString(R.string.error0), Toast.LENGTH_SHORT).show();
                    }else if(i==1){
                        Toast.makeText(BananaActivity.this, "error : "+getString(R.string.error1), Toast.LENGTH_SHORT).show();
                    }else if(i==2){
                        Toast.makeText(BananaActivity.this, "error : "+getString(R.string.error2), Toast.LENGTH_SHORT).show();
                    }else if(i==3){
                        Toast.makeText(BananaActivity.this, "error : "+getString(R.string.error3), Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(BananaActivity.this, "error : ad Failed to load, please make your connection stable", Toast.LENGTH_SHORT).show();
                    }
                    gagalt++;
                    saveInteger(GG,gagalt,BananaActivity.this);
                    gagal.setText("FAILED :"+gagalt);
                }

                @Override
                public void onAdOpened() {
                    super.onAdOpened();
                    cik++;
                    saveInteger(CIK,cik,BananaActivity.this);
                    adopen.setText("CLICK :"+cik);
                    cekRate();
                }

                @Override
                public void onAdLoaded() {
                    super.onAdLoaded();
                    berhasilt++;
                    saveInteger(BB,berhasilt,BananaActivity.this);
                    berhasil.setText("LOAD :"+berhasilt);
                    cekRate();
                }

                @Override
                public void onAdImpression() {
                    super.onAdImpression();
                    impre++;
                    saveInteger(IMP,impre,BananaActivity.this);
                }
            });
            AdRequest.Builder adRequestBuilder = new AdRequest.Builder();

            layout.addView(sixe);
            layout.addView(mAdView);
            mAdView.loadAd(adRequestBuilder.addKeyword(VARIABELS.getString(SettingAct.CATEGORYAD,this,getString(R.string.app_name))).build());
        }
    }


    @SuppressLint("SetTextI18n")
    public void cekRate(){

        float total = ((float)cik/(float)berhasilt)*100;
        DecimalFormat df = new DecimalFormat("####.##");
        ratess = df.format(total);
        saveString(CT,ratess,BananaActivity.this);
        rate.setText("CTR :"+ratess+"%");
    }
    @SuppressLint("ApplySharedPref")
    public void saveString(String key, String value, Context context){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String getString(String key, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(key, "empty");
    }

    @SuppressLint("ApplySharedPref")
    public void saveInteger(String key, Integer value, Context context){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(key,value);
        editor.commit();
    }
    public static int getInteger(String key, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getInt(key, 0);
    }

    public static void startActivity(Context context) {

        Intent intent = new Intent(context, BananaActivity.class);
        context.startActivity(intent);
    }
}
