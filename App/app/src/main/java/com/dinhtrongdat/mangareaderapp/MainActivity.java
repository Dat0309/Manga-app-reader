package com.dinhtrongdat.mangareaderapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Toast;

import com.dinhtrongdat.mangareaderapp.adapter.BannerAdapter;
import com.dinhtrongdat.mangareaderapp.adapter.MangaAdapter;
import com.dinhtrongdat.mangareaderapp.model.BannerManga;
import com.dinhtrongdat.mangareaderapp.model.Manga;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import dmax.dialog.SpotsDialog;

public class MainActivity extends AppCompatActivity {
    BannerAdapter bannerAdapter;
    RecyclerView rcvItem;
    MangaAdapter mangaAdapter;
    ViewPager viewPager;
    TabLayout tabIndicater;
    List<BannerManga> listMangaBanner;
    List<Manga> listManga;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUI();
    }

    private void initUI() {
        UploadBanner();
        UploadItem();
    }

    private void UploadItem() {
        android.app.AlertDialog alertDialog = new SpotsDialog.Builder().setContext(this)
                .setCancelable(false)
                .setMessage("Đang tải")
                .build();

        alertDialog.show();
        listManga = new ArrayList<>();

        databaseReference = FirebaseDatabase.getInstance().getReference("Comic");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot data : snapshot.getChildren()){
                    Manga manga = data.getValue(Manga.class);
                    String[] category = manga.getCategory().split("/");
                    if(category[0].equals("Manga")){
                        listManga.add(manga);
                    }
                }
                setMangaAdapter(listManga);
                alertDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void setMangaAdapter(List<Manga> listManga) {
        rcvItem = findViewById(R.id.rcv_item);
        mangaAdapter = new MangaAdapter(MainActivity.this, listManga);
        mangaAdapter.notifyDataSetChanged();

        rcvItem.setLayoutManager(new GridLayoutManager(this,2));
        rcvItem.setAdapter(mangaAdapter);
    }

    private void UploadBanner(){
        listMangaBanner = new ArrayList<>();

        databaseReference = FirebaseDatabase.getInstance().getReference("Banners");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot data : snapshot.getChildren()){
                    BannerManga bannerManga = data.getValue(BannerManga.class);
                    String[] category = bannerManga.getCategory().split("/");
                    if(category[0].equals("Manga")){
                        listMangaBanner.add(bannerManga);
                    }
                }
                setBannerAdapter(listMangaBanner);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void setBannerAdapter(List<BannerManga> listMangaBanner) {
        viewPager = findViewById(R.id.bannerViewPager);
        tabIndicater = findViewById(R.id.tab_indicator);
        bannerAdapter = new BannerAdapter(MainActivity.this, listMangaBanner);
        bannerAdapter.notifyDataSetChanged();
        viewPager.setAdapter(bannerAdapter);
        tabIndicater.setupWithViewPager(viewPager);

        Timer autoSlider = new Timer();
        autoSlider.schedule(new AutoSlider(listMangaBanner),4000, 6000);
        tabIndicater.setupWithViewPager(viewPager, true);
    }

    public class AutoSlider extends TimerTask {

        List<BannerManga> list;

        public AutoSlider(List<BannerManga> list) {
            this.list = list;
        }

        @Override
        public void run() {

            MainActivity.this.runOnUiThread(() ->{
                if(viewPager.getCurrentItem() < list.size()-1){
                    viewPager.setCurrentItem(viewPager.getCurrentItem() +1);
                }
                else{
                    viewPager.setCurrentItem(0);
                }
            });
        }
    }
}