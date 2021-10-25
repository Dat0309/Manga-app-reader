package com.dinhtrongdat.mangareaderapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.dinhtrongdat.mangareaderapp.adapter.MyViewPagerAdapter;
import com.dinhtrongdat.mangareaderapp.model.BookFlipPageTransformer;
import com.dinhtrongdat.mangareaderapp.model.Chapter;

public class ViewMangaActivity extends AppCompatActivity implements View.OnClickListener {

    ViewPager viewPager;
    TextView txtChapterName;
    View back, next;
    Chapter chapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_manga);
        chapter = (Chapter) getIntent().getSerializableExtra("chapter");
        
        initUI();
    }

    private void initUI() {
        viewPager = findViewById(R.id.viewPager);
        txtChapterName = findViewById(R.id.txtCurrentChapter);
        back = findViewById(R.id.chapter_back);
        next = findViewById(R.id.chapter_next);
        txtChapterName.setText(chapter.getName());

        fetchhLinks(chapter);

        back.setOnClickListener(this);
        next.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case  R.id.chapter_back:
                break;
            case R.id.chapter_next:
                break;
        }
    }

    private void fetchhLinks(Chapter chap){
        if(chap.getLinks() != null){
            if(chap.getLinks().size() >0){
                MyViewPagerAdapter adapter = new MyViewPagerAdapter(getBaseContext(), chap.getLinks());
                viewPager.setAdapter(adapter);
                BookFlipPageTransformer bookFlipPageTransformer = new BookFlipPageTransformer();
                bookFlipPageTransformer.setScaleAmountPercent(10f);
                viewPager.setPageTransformer(true,bookFlipPageTransformer);
            }
        }
    }
}