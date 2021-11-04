package com.dinhtrongdat.mangareaderapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.dinhtrongdat.mangareaderapp.model.Chapter;

public class ViewNovelActivity extends AppCompatActivity implements View.OnClickListener {

    TextView txtChapterName,txtNovel;
    View back;
    Chapter chapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_novel);
        chapter = (Chapter) getIntent().getSerializableExtra("novel");
        initUI();
    }

    private void initUI() {
        txtChapterName = findViewById(R.id.txtCurrentChapter);
        txtNovel = findViewById(R.id.txtNovel);
        back = findViewById(R.id.chapter_back);
        txtChapterName.setText(chapter.getName());
        txtNovel.setText(chapter.getLinks().get(0));

        back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case  R.id.chapter_back:
                finish();
                break;
        }
    }
}