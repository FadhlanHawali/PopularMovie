package hidee.popularmovie.Deskripsi_Film;

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
    static final String DATABASE_NAME = "wew.db";
    static final int DATABASE_VERSION = 1;

    static final String KEY_ID = "id";
    static final String NAMA = "nama";
    static final String HOBBY = "hobby";
    static final String URL = "url";

    DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String CREATE_DATABASE_TABLE = "CREATE TABLE " + TAG + " ("
                + KEY_ID + "INTEGER PRIMARY KEY, " +
                NAMA + " TEXT, " +
                HOBBY + " TEXT" + " ) ";

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
            sqLiteDatabase.execSQL("DROP TABLW IF EXIST " + TAG);
            onCreate(db);
        }
    }
}
