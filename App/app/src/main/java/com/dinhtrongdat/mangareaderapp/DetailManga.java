package com.dinhtrongdat.mangareaderapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dinhtrongdat.mangareaderapp.adapter.ChapterAdapter;
import com.dinhtrongdat.mangareaderapp.model.BannerManga;
import com.dinhtrongdat.mangareaderapp.model.Chapter;
import com.dinhtrongdat.mangareaderapp.model.Manga;

import java.util.List;

public class DetailManga extends AppCompatActivity {

    ImageView imgDetail;
    TextView txtName;
    Manga manga;
    BannerManga bannerManga;
    ChapterAdapter chapterAdapter;
    List<Chapter> listChapter;
    RecyclerView rcvChapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_manga);

        initUI();
    }

    private void initUI() {
        imgDetail = findViewById(R.id.imgDetail);
        txtName = findViewById(R.id.txtNameDetail);
        rcvChapter = findViewById(R.id.rcv_chapter);

        LoadDetail();
    }

    private void LoadDetail() {
        bannerManga = (BannerManga) getIntent().getSerializableExtra("banner");
        if(bannerManga != null){
            Glide.with(this).load(bannerManga.getImage()).into(imgDetail);
            txtName.setText(bannerManga.getName().toString());
            listChapter = bannerManga.getChapters();

            chapterAdapter = new ChapterAdapter(this, listChapter);
            chapterAdapter.notifyDataSetChanged();
            rcvChapter.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
            rcvChapter.setAdapter(chapterAdapter);
        }
    }
}