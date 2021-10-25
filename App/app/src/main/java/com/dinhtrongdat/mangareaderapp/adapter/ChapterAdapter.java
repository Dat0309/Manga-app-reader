package com.dinhtrongdat.mangareaderapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dinhtrongdat.mangareaderapp.R;
import com.dinhtrongdat.mangareaderapp.model.Chapter;

import java.util.List;

public class ChapterAdapter extends RecyclerView.Adapter<ChapterAdapter.ViewHolderChapter> {
    Context context;
    List<Chapter> listChapter;

    public ChapterAdapter(Context context, List<Chapter> listChapter) {
        this.context = context;
        this.listChapter = listChapter;
    }

    @Override
    public ViewHolderChapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_chapter,parent, false);
        return new ViewHolderChapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChapterAdapter.ViewHolderChapter holder, int position) {
        Chapter chapter = listChapter.get(position);
        holder.txtChapter.setText(chapter.getName());
    }

    @Override
    public int getItemCount() {
        return listChapter.size();
    }

    public class ViewHolderChapter extends RecyclerView.ViewHolder {
        TextView txtChapter;

        public ViewHolderChapter(@NonNull View itemView) {
            super(itemView);
            txtChapter = itemView.findViewById(R.id.txtItemChapter);
        }
    }
}
