package hidee.popularmovie.Deskripsi_Film;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.List;

import hidee.popularmovie.CardView.AppController;
import hidee.popularmovie.R;

/**
 * Created by Iman Satyarno on 18/11/2017.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder> {

    List<User> listUser;
    RecyclerViewAdapter(List<User> list){
        this.listUser = list;
    }


    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_favorite_film,parent,false);
        return new RecyclerViewHolder(view);
    }

    @Override
    //Untuk mengikat view view nya, untuk memanipulasi view nya
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {


        holder.textViewNama.setText(listUser.get(position).getNama());
       //  holder.textViewHobi.setText(listUser.get(position).getHobi());
       ImageLoader imageLoader = AppController.getInstance().getImageLoader();
       holder.imgNetWorkView.setImageUrl(listUser.get(position).getHobi(), imageLoader);
       imageLoader.get(listUser.get(position).getHobi(), ImageLoader.getImageListener(
              holder.thumbnail, R.drawable.ico_loading, R.drawable.ico_error));
    }

    @Override
    public int getItemCount() {

        return listUser.size();
    }


    public class RecyclerViewHolder extends RecyclerView.ViewHolder{

        TextView textViewNama;
        TextView textViewHobi;
        ImageView thumbnail;
        NetworkImageView imgNetWorkView;

        //Mendapatkan constructor parent
        public RecyclerViewHolder(View itemView) {
            super(itemView);
            textViewNama =(TextView) itemView.findViewById(R.id.title_activity_favorite_film);
            textViewHobi = (TextView) itemView.findViewById(R.id.count_activity_favorite_film);
            thumbnail = (ImageView) itemView.findViewById(R.id.thumbnail_activity_favorite_film);
            imgNetWorkView = (NetworkImageView) itemView.findViewById(R.id.imgNetwork_activity_favorite_film);
        }
    }
}
