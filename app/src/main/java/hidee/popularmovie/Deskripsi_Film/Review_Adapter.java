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

public class Review_Adapter extends RecyclerView.Adapter<Review_Adapter.MyViewHolder> {

    private Context mContext;
    private List<Review_Getter_Setter> albumList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView author,content;




        public MyViewHolder(final View view) {
            super(view);
            author = (TextView) view.findViewById(R.id.textview_nama);
            content = (TextView) view.findViewById(R.id.textview_hobi);

        }

    }


    public Review_Adapter(Context mContext, List<Review_Getter_Setter> albumList) {
        this.mContext = mContext;
        this.albumList = albumList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_review_film, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        Review_Getter_Setter album = albumList.get(position);
        holder.author.setText(album.getAuthor());
        holder.content.setText(album.getContent());
        // loading album cover using Glide library
        //Glide.with(mContext).load(album.getThumbnail()).into(holder.thumbnail);

    }

    @Override
    public int getItemCount() {
        return albumList.size();
    }
}