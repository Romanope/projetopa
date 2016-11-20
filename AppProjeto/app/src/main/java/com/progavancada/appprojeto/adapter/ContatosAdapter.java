package com.progavancada.appprojeto.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.progavancada.appprojeto.model.Contato;
import com.progavancada.appprojeto.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by gabriel on 19/11/2016.
 */

public class ContatosAdapter extends BaseAdapter {

    private Context mContext;
    private List<Contato> mContatos;

    public ContatosAdapter(Context context, List<Contato> contatos) {
        mContext = context;
        mContatos = contatos;
    }

    @Override
    public int getCount() {
        return mContatos.size();
    }

    @Override
    public Object getItem(int position) {
        return mContatos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return mContatos.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Contato contato = mContatos.get(position);

        LayoutInflater inflater = LayoutInflater.from(mContext);

        ViewHolder holder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_item_contato, null);

            holder = new ViewHolder();

            holder.nomeContato = (TextView) convertView.findViewById(R.id.txt_nome_contato);
            holder.emailContato = (TextView) convertView.findViewById(R.id.txt_email_contato);
            holder.imagemContato = (ImageView) convertView.findViewById(R.id.imagem_contato);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.nomeContato.setText(contato.getNome());
        holder.emailContato.setText(contato.getEmail());

        if (contato.getUrlFoto() != null) {
            Picasso.with(mContext)
                    .load(contato.getUrlFoto())
                    .resize(64, 64)
                    .into(holder.imagemContato);
        }

        return convertView;
    }

    static class ViewHolder {
        TextView nomeContato;
        TextView emailContato;
        ImageView imagemContato;
    }

}
