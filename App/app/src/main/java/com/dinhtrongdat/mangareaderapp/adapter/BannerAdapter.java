package com.dinhtrongdat.mangareaderapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.dinhtrongdat.mangareaderapp.R;
import com.dinhtrongdat.mangareaderapp.model.BannerManga;

import java.util.List;

public class BannerAdapter extends PagerAdapter {
    Context context;
    List<BannerManga> listBanner;

    public BannerAdapter(Context context, List<BannerManga> listBanner) {
        this.context = context;
        this.listBanner = listBanner;
    }

    @Override
    public int getCount() {
        return listBanner.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.banner_manga,null);
        ImageView imgBanner = view.findViewById(R.id.img_banner);

        Glide.with(context).load(listBanner.get(position).getImage()).into(imgBanner);
        container.addView(view);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, listBanner.get(position).getName(), Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}
