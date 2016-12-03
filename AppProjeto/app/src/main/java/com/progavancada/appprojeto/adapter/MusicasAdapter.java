package com.progavancada.appprojeto.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.progavancada.appprojeto.R;
import com.progavancada.appprojeto.model.Musica;

import java.util.List;

/**
 * Created by gabriel on 19/11/2016.
 */

public class MusicasAdapter extends BaseAdapter {

    private List<Musica> mMusicas;
    private Context mContext;

    public MusicasAdapter(Context context, List<Musica> musicas) {
        mMusicas = musicas;
        mContext = context;
    }

    @Override
    public int getCount() {
        return mMusicas.size();
    }

    @Override
    public Object getItem(int position) {
        return mMusicas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return mMusicas.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Musica musica = mMusicas.get(position);

        LayoutInflater inflater = LayoutInflater.from(mContext);

        ViewHolder holder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_item_musica, null);
            holder = new ViewHolder();

            holder.nomeMusica = (TextView) convertView.findViewById(R.id.txt_nome_musica);
            holder.src = (TextView) convertView.findViewById(R.id.txt_src);

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.nomeMusica.setText(musica.getNome());
        holder.src.setText(musica.getUrlMusica());

        return convertView;
    }

    static class ViewHolder {
        TextView nomeMusica;
        TextView src;
    }

}
