package com.dinhtrongdat.mangareaderapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dinhtrongdat.mangareaderapp.adapter.ChapterAdapter;
import com.dinhtrongdat.mangareaderapp.adapter.TagAdapter;
import com.dinhtrongdat.mangareaderapp.model.BannerManga;
import com.dinhtrongdat.mangareaderapp.model.Chapter;
import com.dinhtrongdat.mangareaderapp.model.Manga;
import com.dinhtrongdat.mangareaderapp.model.Tag;

import java.util.ArrayList;
import java.util.List;

public class DetailManga extends AppCompatActivity implements ChapterAdapter.OnItemChapterClick {

    ImageView imgDetail, imgBack,imgBackground;
    TextView txtName;
    Manga manga;
    BannerManga bannerManga;
    TagAdapter tagAdapter;
    List<Tag> tags;
    ChapterAdapter chapterAdapter;
    List<Chapter> listChapter;
    RecyclerView rcvChapter, rcvTag;
    String TAG;

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
        rcvTag = findViewById(R.id.rcv_tag);
        imgBack = findViewById(R.id.imgBack);
        imgBackground = findViewById(R.id.imgBackground);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        LoadDetail();
    }

    private void LoadDetail() {
        bannerManga = (BannerManga) getIntent().getSerializableExtra("banner");
        manga = (Manga) getIntent().getSerializableExtra("manga");
        tags = new ArrayList<>();
        if(bannerManga != null){
            Glide.with(this).load(bannerManga.getImage()).into(imgDetail);
            Glide.with(this).load(bannerManga.getImage()).into(imgBackground);
            txtName.setText(bannerManga.getName().toString());
            listChapter = bannerManga.getChapters();

            chapterAdapter = new ChapterAdapter(this, listChapter, this);
            chapterAdapter.notifyDataSetChanged();
            rcvChapter.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
            rcvChapter.setAdapter(chapterAdapter);

            String[] tag = bannerManga.getCategory().split("/");
            if(tag[0].compareTo("Manga")==0) {
                TAG = "MANGA";
            }
            else if(tag[0].compareTo("Novel")==0) {
                TAG = "NOVEL";
            }
            for(String cate : tag){
                tags.add(new Tag(cate));
            }
            tagAdapter = new TagAdapter(this, tags);
            tagAdapter.notifyDataSetChanged();
            rcvTag.setLayoutManager(new GridLayoutManager(this,4));
            rcvTag.setAdapter(tagAdapter);
        }
        else if(manga != null){
            Glide.with(this).load(manga.getImage()).into(imgDetail);
            Glide.with(this).load(manga.getImage()).into(imgBackground);
            txtName.setText(manga.getName().toString());
            listChapter = manga.getChapters();

            chapterAdapter = new ChapterAdapter(this, listChapter, this);
            chapterAdapter.notifyDataSetChanged();
            rcvChapter.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
            rcvChapter.setAdapter(chapterAdapter);

            String[] tag = manga.getCategory().split("/");
            if(tag[0].compareTo("Manga")==0) {
                TAG = "MANGA";
            }
            else if(tag[0].compareTo("Novel")==0) {
                TAG = "NOVEL";
            }
            for(String cate : tag){
                tags.add(new Tag(cate));
            }
            tagAdapter = new TagAdapter(this, tags);
            tagAdapter.notifyDataSetChanged();
            rcvTag.setLayoutManager(new GridLayoutManager(this,4));
            rcvTag.setAdapter(tagAdapter);
        }
    }

    @Override
    public void onChapterItemClick(int clickedItemIndex) {
        if(TAG=="MANGA") {
            Intent intent = new Intent(DetailManga.this, ViewMangaActivity.class);
            intent.putExtra("chapter", listChapter.get(clickedItemIndex));
            startActivity(intent);
        }
        else if(TAG=="NOVEL") {
            Intent intent = new Intent(DetailManga.this, ViewNovelActivity.class);
            intent.putExtra("novel", listChapter.get(clickedItemIndex));
            startActivity(intent);
        }
    }
}