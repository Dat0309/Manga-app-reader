package com.dinhtrongdat.mangareaderapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dinhtrongdat.mangareaderapp.R;
import com.dinhtrongdat.mangareaderapp.model.Manga;

import java.util.List;

public class MangaAdapter extends RecyclerView.Adapter<MangaAdapter.ViewHolderMangar> {

    Context context;
    List<Manga> listManga;

    public MangaAdapter(Context context, List<Manga> listManga) {
        this.context = context;
        this.listManga = listManga;
    }

    @Override
    public ViewHolderMangar onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_story, parent, false);
        return new ViewHolderMangar(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MangaAdapter.ViewHolderMangar holder, int position) {
        Manga manga = listManga.get(position);
        holder.txtName.setText(manga.getName());
        Glide.with(context).load(listManga.get(position).getImage()).into(holder.imgManga);
    }

    @Override
    public int getItemCount() {
        return listManga.size();
    }

    public class ViewHolderMangar extends RecyclerView.ViewHolder {
        ImageView imgManga;
        TextView txtName;

        public ViewHolderMangar(@NonNull View itemView) {
            super(itemView);

            imgManga = itemView.findViewById(R.id.img_manga);
            txtName = itemView.findViewById(R.id.txtName);
        }
    }
}
