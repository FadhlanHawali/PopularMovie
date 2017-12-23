package hidee.popularmovie.CardView;

import android.content.Context;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Iman Satyarno on 18/11/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    SQLiteDatabase sqLiteDatabase;
    Resources resources;

    static final String TAG = DatabaseHelper.class.getSimpleName();
    static final String DATABASE_NAME = "user.db";
    static final int DATABASE_VERSION = 1;

    static final String KEY_ID = "id";
    static final String JUDUL_FILM = "nama";
    static final String TANGGAL_RILIS = "2017-08-09";
    static final String GAMBAR_POSTER = "qwerqwer";


    DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String CREATE_DATABASE_TABLE = "CREATE TABLE " + TAG + " (" + KEY_ID + "INTEGER PRIMARY KEY, " + JUDUL_FILM + " TEXT, " +TANGGAL_RILIS+ "DATE," + GAMBAR_POSTER + "TEXT" + " ) ";

        Log.e("Query", CREATE_DATABASE_TABLE);
        db.execSQL(CREATE_DATABASE_TABLE);

        try{
            this.sqLiteDatabase = db;
        } catch (Exception e) {
            Log.e("error create query", e.getMessage());        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion!=newVersion){
            sqLiteDatabase.execSQL("DROP TABLE IF EXIST " + TAG);
            onCreate(db);
        }
    }
}
