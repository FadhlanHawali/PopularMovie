package hidee.popularmovie.Deskripsi_Film;

/**
 * Created by Iman Satyarno on 19/10/2017.
 */

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hidee.popularmovie.CardView.AppController;
import hidee.popularmovie.R;



public class Activity_Deskripsi_Film extends AppCompatActivity {

    private RecyclerView recyclerView,recyclerViewReview;
    private List<Url_Film_Getter_Setter> albumList;
    private List<Review_Getter_Setter> reviewList;
    private ProgressDialog pDialog;
    private Trailer_Adapter adapter;
    private Review_Adapter adapter_review;
    private String tag_json_obj = "jobj_req", tag_json_arry = "jarray_req";
    private String TAG = Activity_Deskripsi_Film.class.getSimpleName();
    NetworkImageView imgNetWorkView;
    public ImageView thumbnail;
    public ImageButton play;
    public TextView deskripsi_film,rating,judul,rilis_film;
    public String id,url_gambar_poster;
    Button favorite;

    private void showProgressDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideProgressDialog() {
        if (pDialog.isShowing())
            pDialog.hide();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deskripsi_film);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_trailer);
        recyclerViewReview = (RecyclerView) findViewById(R.id.recycler_view_review);
        getSupportActionBar().setTitle(" ");
        Bundle b = getIntent().getExtras();

        favorite = findViewById(R.id.favorite);


        imgNetWorkView = (NetworkImageView) findViewById(R.id.imgNetwork2);
        thumbnail = (ImageView) findViewById(R.id.gambar_poster);
        deskripsi_film = (TextView) findViewById(R.id.deskripsi_film);
        rating = (TextView) findViewById(R.id.rating);
        judul = (TextView) findViewById(R.id.judul_film);
        rilis_film = (TextView) findViewById(R.id.release_date);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        id = b.getCharSequence("id").toString();
        url_gambar_poster = b.getCharSequence("url_gambar_lengkap").toString();
        //Toast.makeText(this, id.toString(), Toast.LENGTH_SHORT).show();
        //Toast.makeText(this, url_gambar_poster.toString(), Toast.LENGTH_SHORT).show();


        makeJsonArryReq();
        getUrl();
        getReview();
        final User user = new User();
        albumList = new ArrayList<>();
        reviewList = new ArrayList<>();
        favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user.setNama(judul.getText().toString());
                user.setHobi(url_gambar_poster);
                //user.setURL(url_gambar_poster);
                DatabaseImp.getInstance(Activity_Deskripsi_Film.this).insertDataUser(user);

                //Toast.makeText(Activity_Deskripsi_Film.this,  DatabaseImp.getInstance(Activity_Deskripsi_Film.this).selectAllData().toString(), Toast.LENGTH_SHORT).show();

                startActivity(new Intent(Activity_Deskripsi_Film.this,MainActivity.class));
                finish();
            }
        });
    }


    private void getReview() {

        showProgressDialog();
        JSONObject duata = new JSONObject();

        //JSONObject id = new JSONObject();


        String url = "https://api.themoviedb.org/3/movie/"+id+"/reviews?api_key=bd2cbac84e5eb94909551b4a20f29037&language=en-US&page=1";
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.GET, url, duata,
                new Response.Listener<JSONObject>() {
                    @Override

                    public void onResponse(JSONObject response) {

                        Log.d(TAG, response.toString());
                        String json_response = response.toString();
                        //Toast.makeText(Activity_Deskripsi_Film.this, response.toString(), Toast.LENGTH_SHORT).show();
                        try {

                            JSONArray featureArray = response.getJSONArray("results");

                            for(int i = 0; i<featureArray.length();i++) {

                                JSONObject firstFeature = featureArray.getJSONObject(i);
                                String author = firstFeature.getString("author");
                                String content = firstFeature.getString("content");

                                Review_Getter_Setter a = new Review_Getter_Setter(author,content);
                                reviewList.add(a);
                            }
                            if(reviewList.isEmpty())
                            {
                                String author = "-";
                                String content = "No current review for this film";

                                Review_Getter_Setter a = new Review_Getter_Setter(author,content);
                                reviewList.add(a);
                            }
                            adapter_review = new Review_Adapter(Activity_Deskripsi_Film.this, reviewList);
                            RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(Activity_Deskripsi_Film.this, 1);
                            recyclerViewReview.setLayoutManager(mLayoutManager);
                            recyclerViewReview.setItemAnimator(new DefaultItemAnimator());
                            recyclerViewReview.setAdapter(adapter_review);

                        } catch (JSONException e) {
                            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
                        }

                        hideProgressDialog();
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                hideProgressDialog();
                if (error instanceof NetworkError) {
                    Toast.makeText(Activity_Deskripsi_Film.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ServerError) {
                    Toast.makeText(Activity_Deskripsi_Film.this, "The server could not be found. Please try again after some time!!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof AuthFailureError) {
                    Toast.makeText(Activity_Deskripsi_Film.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ParseError) {
                    Toast.makeText(Activity_Deskripsi_Film.this, "Parsing error! Please try again after some time!!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NoConnectionError) {
                    Toast.makeText(Activity_Deskripsi_Film.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof TimeoutError) {
                    Toast.makeText(Activity_Deskripsi_Film.this, "Connection TimeOut! Please check your internet connection.", Toast.LENGTH_SHORT).show();
                }
            }
        }) {

            /**
             * Passing some request headers
             * */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };


        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq,
                tag_json_arry);

        // Cancelling request
        // ApplicationController.getInstance().getRequestQueue().cancelAll(tag_json_arry);

    }

    private void getUrl() {

        showProgressDialog();
        JSONObject duata = new JSONObject();

        //JSONObject id = new JSONObject();


        String url = "https://api.themoviedb.org/3/movie/"+id+"/videos?api_key=bd2cbac84e5eb94909551b4a20f29037&language=en-US";
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.GET, url, duata,
                new Response.Listener<JSONObject>() {
                    @Override

                    public void onResponse(JSONObject response) {

                        Log.d(TAG, response.toString());
                        String json_response = response.toString();
                        //Toast.makeText(Activity_Deskripsi_Film.this, response.toString(), Toast.LENGTH_SHORT).show();
                        try {

                            JSONArray featureArray = response.getJSONArray("results");

                            for(int i = 0; i<featureArray.length();i++) {

                                JSONObject firstFeature = featureArray.getJSONObject(i);
                                String key = firstFeature.getString("key");
                                String judul_trailer = firstFeature.getString("name");

                                Url_Film_Getter_Setter a = new Url_Film_Getter_Setter(key,judul_trailer);
                                albumList.add(a);
                            }
                            adapter = new Trailer_Adapter(Activity_Deskripsi_Film.this, albumList);
                            RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(Activity_Deskripsi_Film.this, 1);
                            recyclerView.setLayoutManager(mLayoutManager);
                            recyclerView.setItemAnimator(new DefaultItemAnimator());
                            recyclerView.setAdapter(adapter);

                        } catch (JSONException e) {
                            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
                        }

                        hideProgressDialog();
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                hideProgressDialog();
                if (error instanceof NetworkError) {
                    Toast.makeText(Activity_Deskripsi_Film.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ServerError) {
                    Toast.makeText(Activity_Deskripsi_Film.this, "The server could not be found. Please try again after some time!!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof AuthFailureError) {
                    Toast.makeText(Activity_Deskripsi_Film.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ParseError) {
                    Toast.makeText(Activity_Deskripsi_Film.this, "Parsing error! Please try again after some time!!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NoConnectionError) {
                    Toast.makeText(Activity_Deskripsi_Film.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof TimeoutError) {
                    Toast.makeText(Activity_Deskripsi_Film.this, "Connection TimeOut! Please check your internet connection.", Toast.LENGTH_SHORT).show();
                }
            }
        }) {

            /**
             * Passing some request headers
             * */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };


        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq,
                tag_json_arry);

        // Cancelling request
        // ApplicationController.getInstance().getRequestQueue().cancelAll(tag_json_arry);

    }


    private void makeJsonArryReq() {

        showProgressDialog();
        JSONObject duata = new JSONObject();

        //JSONObject id = new JSONObject();


        String url = "https://api.themoviedb.org/3/movie/"+id+"?api_key=bd2cbac84e5eb94909551b4a20f29037&language=en-US";
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.GET, url, duata,
                new Response.Listener<JSONObject>() {
                    @Override

                    public void onResponse(JSONObject response) {

                        Log.d(TAG, response.toString());
                        String json_response = response.toString();
                        //Toast.makeText(Activity_Deskripsi_Film.this, response.toString(), Toast.LENGTH_SHORT).show();
                        try {

                            String judul_film = response.getString("original_title");
                            String rilis = response.getString("release_date");
                            String rate = response.getString("vote_average");
                            String deskripsi = response.getString("overview");
                            getSupportActionBar().setTitle(judul_film);
                            judul.setText(judul_film);
                            rating.setText(rate+"/10");
                            deskripsi_film.setText(deskripsi);
                            rilis_film.setText(rilis);

                            /**
                            JSONArray genres = response.getJSONArray("genres");
                            String[] genre = new String[genres.length()];
                            //Toast.makeText(Activity_Deskripsi_Film.this, genres.toString(), Toast.LENGTH_SHORT).show();
                            for (int i = 0; i < genres.length(); i++) {
                                JSONObject nama_genre = genres.getJSONObject(i);
                                //String jenis_genre = nama_genre.getString("name");
                                Toast.makeText(Activity_Deskripsi_Film.this, nama_genre.toString(), Toast.LENGTH_SHORT).show();
                            }**/
                            ImageLoader imageLoader = AppController.getInstance().getImageLoader();
                            imgNetWorkView.setImageUrl(url_gambar_poster, imageLoader);
                            imageLoader.get(url_gambar_poster, ImageLoader.getImageListener(
                                    thumbnail, R.drawable.ico_loading, R.drawable.ico_error));

                        } catch (JSONException e) {
                            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
                        }

                        hideProgressDialog();
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                hideProgressDialog();
                if (error instanceof NetworkError) {
                    Toast.makeText(Activity_Deskripsi_Film.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ServerError) {
                    Toast.makeText(Activity_Deskripsi_Film.this, "The server could not be found. Please try again after some time!!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof AuthFailureError) {
                    Toast.makeText(Activity_Deskripsi_Film.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ParseError) {
                    Toast.makeText(Activity_Deskripsi_Film.this, "Parsing error! Please try again after some time!!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NoConnectionError) {
                    Toast.makeText(Activity_Deskripsi_Film.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof TimeoutError) {
                    Toast.makeText(Activity_Deskripsi_Film.this, "Connection TimeOut! Please check your internet connection.", Toast.LENGTH_SHORT).show();
                }
            }
        }) {

            /**
             * Passing some request headers
             * */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };


        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq,
                tag_json_arry);

        // Cancelling request
        // ApplicationController.getInstance().getRequestQueue().cancelAll(tag_json_arry);

    }
}

