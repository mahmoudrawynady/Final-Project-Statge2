package com.example.kashasha.theworld.UI;

import android.content.Context;
import android.graphics.drawable.PictureDrawable;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.GenericRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.StreamEncoder;
import com.bumptech.glide.load.resource.file.FileToStreamDecoder;
import com.caverock.androidsvg.SVG;
import com.example.kashasha.theworld.SVG.SvgDecoder;
import com.example.kashasha.theworld.SVG.SvgDrawableTranscoder;
import com.example.kashasha.theworld.SVG.SvgSoftwareLayerSetter;
import com.squareup.picasso.Picasso;
import java.io.IOException;
import java.io.InputStream;



public class CountriesUtitlities {
    public static boolean checkConnection(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isAvailable() && cm.getActiveNetworkInfo().isConnected())
            return true;
        else
            return false;
    }

    public static void loadSvgImage(ImageView imageView,Context context,String url){
        GenericRequestBuilder<Uri, InputStream, SVG, PictureDrawable> requestBuilder= Glide.with(context)
                .using(Glide.buildStreamModelLoader(Uri.class,context), InputStream.class)
                .from(Uri.class)
                .as(SVG.class)
                .transcode(new SvgDrawableTranscoder(), PictureDrawable.class)
                .sourceEncoder(new StreamEncoder())
                .cacheDecoder(new FileToStreamDecoder<SVG>(new SvgDecoder()))
                .decoder(new SvgDecoder())

                .animate(android.R.anim.fade_in)
                .listener(new SvgSoftwareLayerSetter<Uri>());
        requestBuilder
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .load(Uri.parse(url))
                .into(imageView);


    }





}
