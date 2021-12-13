package com.dinhtrongdat.mangareaderapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.dinhtrongdat.mangareaderapp.adapter.BannerAdapter;
import com.dinhtrongdat.mangareaderapp.adapter.MangaAdapter;
import com.dinhtrongdat.mangareaderapp.model.BannerManga;
import com.dinhtrongdat.mangareaderapp.model.Manga;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import dmax.dialog.SpotsDialog;

public class MainActivity extends AppCompatActivity implements MangaAdapter.OnItemMangaClick {
    BannerAdapter bannerAdapter;
    RecyclerView rcvItem;
    MangaAdapter mangaAdapter;
    ViewPager viewPager;
    TabLayout tabIndicater, tabCategory;
    List<BannerManga> listBanner;
    List<Manga> listAll,listManga, listNovel, listAudio;
    DatabaseReference databaseReference;
    AppBarLayout appBar;
    String TAB = "HOME";
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUI();
    }

    private void initUI() {
        searchView = findViewById(R.id.edtSearch);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        UploadBanner();
        UploadItem();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mangaAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mangaAdapter.getFilter().filter(newText);
                return false;
            }
        });
    }

    private void initToolbarAnimation() {
        appBar = findViewById(R.id.app_bar);
        appBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if(Math.abs(verticalOffset) > 100){
                    invalidateOptionsMenu();
                }
            }
        });
    }

    private void UploadItem() {
        android.app.AlertDialog alertDialog = new SpotsDialog.Builder().setContext(this)
                .setCancelable(false)
                .setMessage("Đang tải")
                .build();

        alertDialog.show();
        listManga = new ArrayList<>();
        listAll = new ArrayList<>();
        listNovel = new ArrayList<>();
        listAudio = new ArrayList<>();

        databaseReference = FirebaseDatabase.getInstance().getReference("Comic");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot data : snapshot.getChildren()){
                    Manga manga = data.getValue(Manga.class);
                    String[] category = manga.getCategory().split("/");
                    listAll.add(manga);
                    if (category[0].compareTo("Manga")==0)
                        listManga.add(manga);
                    if(category[0].compareTo("Novel")==0)
                        listNovel.add(manga);
                    if(category[0].compareTo("AudioBook")==0)
                        listAudio.add(manga);
                }
                setMangaAdapter(listAll);

                tabCategory.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                    @Override
                    public void onTabSelected(TabLayout.Tab tab) {
                        switch (tab.getPosition()){
                            case 0:
                                TAB = "HOME";
                                setMangaAdapter(listAll);
                                break;
                            case 1:
                                TAB = "MANGA";
                                setMangaAdapter(listManga);
                                break;
                            case 2:
                                TAB = "NOVEL";
                                setMangaAdapter(listNovel);
                                break;
                            case 3:
                                TAB = "AUDIO";
                                setMangaAdapter(listAudio);
                                break;
                        }
                    }

                    @Override
                    public void onTabUnselected(TabLayout.Tab tab) {

                    }

                    @Override
                    public void onTabReselected(TabLayout.Tab tab) {

                    }
                });
                alertDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void setMangaAdapter(List<Manga> listManga) {
        rcvItem = findViewById(R.id.rcv_item);
        mangaAdapter = new MangaAdapter(MainActivity.this, listManga, this);
        mangaAdapter.notifyDataSetChanged();

        rcvItem.setLayoutManager(new GridLayoutManager(this,2));
        rcvItem.setAdapter(mangaAdapter);
    }

    private void UploadBanner(){
        tabCategory = findViewById(R.id.tabCategory);
        listBanner = new ArrayList<>();

        databaseReference = FirebaseDatabase.getInstance().getReference("Banners");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot data : snapshot.getChildren()){
                    BannerManga bannerManga = data.getValue(BannerManga.class);
                    String[] category = bannerManga.getCategory().split("/");
                    listBanner.add((bannerManga));
                }
                setBannerAdapter(listBanner);
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



    @Override
    public void onMangaItemClick(int clickedItemIndex) {
        Intent intent = new Intent(MainActivity.this, DetailManga.class);
        if(TAB=="HOME")
            intent.putExtra("manga", listAll.get(clickedItemIndex));
        if(TAB=="MANGA")
            intent.putExtra("manga", listManga.get(clickedItemIndex));
        if(TAB=="NOVEL")
            intent.putExtra("manga", listNovel.get(clickedItemIndex));
        if(TAB=="AUDIO")
            intent.putExtra("manga", listAudio.get(clickedItemIndex));

        startActivity(intent);
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