package hidee.popularmovie.Deskripsi_Film;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Iman Satyarno on 18/11/2017.
 */

public class DatabaseImp {
    Context context;
    DatabaseHelper database_helper;
    static DatabaseImp databaseImp;

    static synchronized DatabaseImp getInstance (Context context){
        if (databaseImp == null){
            databaseImp = new DatabaseImp(context);
        }
        return databaseImp;
    }
    DatabaseImp(Context context){
        this.context = context;
        database_helper = new DatabaseHelper(context);
    }

    public void insertDataUser(User user){
        SQLiteDatabase sqLiteDatabase = database_helper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.NAMA,user.getNama());
        contentValues.put(DatabaseHelper.HOBBY,user.getHobi());
        //contentValues.put(DatabaseHelper.URL,user.getURL());
        sqLiteDatabase.insert(DatabaseHelper.TAG,null,contentValues);
        sqLiteDatabase.close();

    }

    public List<User> selectAllData()
    {
        List<User> listUser = new ArrayList<>();

        String query = "SELECT * FROM " + DatabaseHelper.TAG;
        SQLiteDatabase sqLiteDatabase = database_helper.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(query,null);

        if (cursor.moveToNext())
        {
            do{
                User user = new User();
                user.setNama(cursor.getString(1));
                user.setHobi(cursor.getString(2));
                //user.setURL(cursor.getString(3));
                listUser.add(user);
            }while(cursor.moveToNext());
        }
        cursor.close();
        return listUser;
    }
}