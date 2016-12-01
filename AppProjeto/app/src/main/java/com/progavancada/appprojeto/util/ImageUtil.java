package com.progavancada.appprojeto.util;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import br.com.pa.downloaderpa.Util;

/**
 * Created by gabriel on 22/11/2016.
 */

public class ImageUtil {

    public static void carregarImagem(Context c, String url, int alt, int larg, ImageView i) {
        if (url != null) {
            try {
                Util u = new Util();
                u.downloader(url, i, c);
//                Picasso.with(c)
//                        .load(url)
//                        .resize(alt, larg)
//                        .into(i);
                i.setBackgroundResource(0);
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException(e.getMessage());
            }

        }
    }

}
