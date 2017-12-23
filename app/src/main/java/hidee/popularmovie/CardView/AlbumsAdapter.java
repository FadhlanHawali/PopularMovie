package hidee.popularmovie.CardView;

/**
 * Created by Iman Satyarno on 19/10/2017.
 */

import android.content.Context;
import android.content.Intent;
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

import hidee.popularmovie.Deskripsi_Film.Activity_Deskripsi_Film;
import hidee.popularmovie.R;

public class AlbumsAdapter extends RecyclerView.Adapter<AlbumsAdapter.MyViewHolder> {

    private Context mContext;
    private List<Album> albumList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, count;
        public TextView id_film,url_gambar_poster;
        public ImageView thumbnail, overflow;
        NetworkImageView imgNetWorkView;


        public MyViewHolder(final View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            count = (TextView) view.findViewById(R.id.count);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
            imgNetWorkView = (NetworkImageView) itemView.findViewById(R.id.imgNetwork);
            id_film = (TextView) view.findViewById(R.id.id);
            url_gambar_poster = (TextView) view.findViewById(R.id.gambar_poster_lengkap);
           // overflow = (ImageView) view.findViewById(R.id.overflow);
            view.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {

                    Intent i = new Intent(view.getContext(), Activity_Deskripsi_Film.class);
                    i.putExtra("id", id_film.getText().toString());
                    i.putExtra("url_gambar_lengkap",url_gambar_poster.getText().toString());
                    view.getContext().startActivity(i);
                }
            });
        }

    }


    public AlbumsAdapter(Context mContext, List<Album> albumList) {
        this.mContext = mContext;
        this.albumList = albumList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.album_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        Album album = albumList.get(position);
        holder.title.setText(album.getName());
        holder.count.setText(album.getNumOfSongs());
        holder.url_gambar_poster.setText(album.getGambar_poster_lengkap());
        holder.id_film.setText(album.getId());
        // loading album cover using Glide library
        //Glide.with(mContext).load(album.getThumbnail()).into(holder.thumbnail);

        //holder.overflow.setOnClickListener(new View.OnClickListener() {
          //  @Override
            //public void onClick(View view) {
             //   showPopupMenu(holder.overflow);
           // }
        //});

        ImageLoader imageLoader = AppController.getInstance().getImageLoader();
        holder.imgNetWorkView.setImageUrl(albumList.get(position).getThumbnail(), imageLoader);
        imageLoader.get(albumList.get(position).getThumbnail(), ImageLoader.getImageListener(
                holder.thumbnail, R.drawable.ico_loading, R.drawable.ico_error));

    }

    /**
     * Showing popup menu when tapping on 3 dots
     */
    private void showPopupMenu(View view) {
        // inflate menu
        PopupMenu popup = new PopupMenu(mContext, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_album, popup.getMenu());
        popup.setOnMenuItemClickListener(new MyMenuItemClickListener());
        popup.show();
    }


    /**
     * Click listener for popup menu items
     */
    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

        public MyMenuItemClickListener() {
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.action_add_favourite:
                    Toast.makeText(mContext, "Add to favourite", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.action_play_next:
                    Toast.makeText(mContext, "Play next", Toast.LENGTH_SHORT).show();
                    return true;
                default:
            }
            return false;
        }
    }

    @Override
    public int getItemCount() {
        return albumList.size();
    }
}