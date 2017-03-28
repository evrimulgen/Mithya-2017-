package com.pcce.mithya.mithya2017;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;


import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Sarvesh on 24-03-2017.
 */

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.MyView> {
    private static final int ANIMATED_ITEMS_COUNT = 2;
    private List<ImageUpload> imageUploads;
    private Context context;
    private int lastAnimatedPosition = -1;

    public GalleryAdapter(List<ImageUpload> imageUploads, Context context) {
        this.imageUploads = imageUploads;

        this.context = context;
    }

    public static class MyView extends RecyclerView.ViewHolder {

        ImageView galleryimage;
        TextView caption;

        public MyView(View view) {

            super(view);
            galleryimage = (ImageView) view.findViewById(R.id.galleryimage);
            caption =(TextView)view.findViewById(R.id.caption);

        }

    }

    private void runEnterAnimation(View view, int position) {
        if (position >= ANIMATED_ITEMS_COUNT - 1) {
            return;
        }

        if (position > lastAnimatedPosition) {
            lastAnimatedPosition = position;
            view.setTranslationY(Utils.getScreenHeight(context));

            view.animate()
                    .translationY(0)
                    .setInterpolator(new DecelerateInterpolator(3.f))
                    .setDuration(1000)
                    .start();
        }
    }

    @Override
    public MyView onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_gallery, parent, false);

        return new GalleryAdapter.MyView(itemView);
    }

    @Override
    public void onBindViewHolder(final MyView holder, final int position) {
        runEnterAnimation(holder.itemView, position);

        Picasso.with(context).load(imageUploads.get(position).getUrl()).into(holder.galleryimage);
       holder.caption.setTypeface(Main.myCustomFont);
        holder.caption.setText(imageUploads.get(position).getCaption());
        holder.galleryimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(imageUploads.get(position).getUrl())));
            }
        });

    }

    @Override
    public int getItemCount() {
        return imageUploads.size();
    }
}






