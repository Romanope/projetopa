package com.progavancada.appprojeto.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import br.com.pa.downloaderpa.downloader.Downloaderpa;

import static com.progavancada.appprojeto.R.id.imageView;

/**
 * Created by gabriel on 22/11/2016.
 */

public class ImageUtil {

    public static void carregarImagem(Context c, String url, ImageView imageView) {
        if (url != null) {
            try {

                if (url.startsWith("http")) {

                    Downloaderpa.context(c).setImageView(imageView).url(url).startDownload();

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
        Downloaderpa.context(context).url(url).startDownload();
    }

}
