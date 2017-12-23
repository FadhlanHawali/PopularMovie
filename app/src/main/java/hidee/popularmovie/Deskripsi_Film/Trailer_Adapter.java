package hidee.popularmovie.Deskripsi_Film;

/**
 * Created by Iman Satyarno on 19/10/2017.
 */

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.List;


import hidee.popularmovie.CardView.AppController;
import hidee.popularmovie.R;

public class Trailer_Adapter extends RecyclerView.Adapter<Trailer_Adapter.MyViewHolder> {

    private Context mContext;
    private List<Url_Film_Getter_Setter> albumList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView judul_trailer,url_trailer;
        public ImageView thumbnail;
        NetworkImageView imgNetWorkView;



        public MyViewHolder(final View view) {
            super(view);
            judul_trailer = (TextView) view.findViewById(R.id.judul_trailer);
            url_trailer = (TextView) view.findViewById(R.id.url_trailer);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail_trailer);
            imgNetWorkView = (NetworkImageView) itemView.findViewById(R.id.imgNetwork3);
            view.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    Toast.makeText(mContext, url_trailer.getText().toString(), Toast.LENGTH_SHORT).show();
                    view.getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v="+url_trailer.getText().toString())));
                }
            });
        }

    }


    public Trailer_Adapter(Context mContext, List<Url_Film_Getter_Setter> albumList) {
        this.mContext = mContext;
        this.albumList = albumList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_trailer_film, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        Url_Film_Getter_Setter album = albumList.get(position);
        holder.judul_trailer.setText(album.getJudul_trailer());
        holder.url_trailer.setText(album.getUrl_trailer());
        // loading album cover using Glide library
        //Glide.with(mContext).load(album.getThumbnail()).into(holder.thumbnail);

        ImageLoader imageLoader = AppController.getInstance().getImageLoader();
        holder.imgNetWorkView.setImageUrl("http://img.youtube.com/vi/"+album.getUrl_trailer().toString()+"/0.jpg", imageLoader);
        imageLoader.get("http://img.youtube.com/vi/"+album.getUrl_trailer().toString()+"/0.jpg", ImageLoader.getImageListener(
                holder.thumbnail, R.drawable.ico_loading, R.drawable.ico_error));

    }

    @Override
    public int getItemCount() {
        return albumList.size();
    }
}