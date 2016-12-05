package com.progavancada.appprojeto.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.progavancada.appprojeto.R;
import com.progavancada.appprojeto.Video.Video;

import java.util.List;

/**
 * Created by gabriel on 19/11/2016.
 */

public class VideosAdapter extends BaseAdapter {

    private Context mContext;
    private List<Video> mVideos;

    public VideosAdapter(Context context, List<Video> videos) {
        mContext = context;
        mVideos = videos;
    }

    @Override
    public int getCount() {
        return mVideos.size();
    }

    @Override
    public Object getItem(int position) {
        return mVideos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return mVideos.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Video video = mVideos.get(position);

        LayoutInflater inflater = LayoutInflater.from(mContext);

        ViewHolder holder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_item_video, null);
            holder = new ViewHolder();

            holder.nomeVideo = (TextView) convertView.findViewById(R.id.txt_nome_video);
            holder.descricaoVideo = (TextView) convertView.findViewById(R.id.txt_descricao_video);

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.nomeVideo.setText(video.getNome());
        holder.descricaoVideo.setText(video.getDescricao());

        return convertView;
    }

    static class ViewHolder {
        TextView nomeVideo;
        TextView descricaoVideo;
    }

}
