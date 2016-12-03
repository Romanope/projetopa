package com.progavancada.appprojeto.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.progavancada.appprojeto.R;
import com.squareup.picasso.Picasso;

import br.com.pa.downloaderpa.downloader.DownloadCompletedListener;
import br.com.pa.downloaderpa.downloader.Downloaderpa;

import static com.progavancada.appprojeto.R.id.imageView;

/**
 * Created by gabriel on 22/11/2016.
 */

public class ImageUtil {

    public static void carregarImagem(final Context c, String url, ImageView imageView) {
        if (url != null) {
            try {

                if (url.startsWith("http") || url.startsWith("www.")) {

                    Downloaderpa.context(c).setImageView(imageView).url(url)
                            .registerListener(new DownloadCompletedListener() {
                        @Override
                        public void completed(String s, String s1) {
                            // =========================
                        }
                    }).startDownload();

                } else {
                    Bitmap bitmap = BitmapFactory.decodeFile(url);
                    Bitmap bitmapReduzido = Bitmap.createScaledBitmap(bitmap, 64, 64, true);
                    imageView.setImageBitmap(bitmapReduzido);
                }

                imageView.setBackgroundResource(0);

            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException(e.getMessage());
            } catch (NullPointerException e) {
                throw new NullPointerException(e.getMessage());
            }

        }
    }

    public static void carregarMusica(Context context, String url) {
        try {
            Downloaderpa.context(context).url(url).startDownload();
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        }

    }


}
