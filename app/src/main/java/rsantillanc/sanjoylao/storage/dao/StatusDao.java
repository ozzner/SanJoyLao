package rsantillanc.sanjoylao.storage.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import rsantillanc.sanjoylao.model.StatusModel;

/**
 * Created by RenzoD on 06/11/2015.
 */
public class StatusDao {

    private static final String SELECT = "Select * from " + Tables.STATUS;
    private static final String COMPARE = "=?";

    //Columns
    private String objectId = "objectId";
    private String code = "code";
    private String description = "description";
    private String name = "name";

    //Database
    private SQLiteDatabase db;

    //Constructor
    public StatusDao(Context c) {
        SJLDatabase mSJLDatabase = new SJLDatabase(c);
        this.db = mSJLDatabase.getWritableDatabase();
    }


    public int count() {
        Cursor cur = db.rawQuery(SELECT, null);
        return cur.getCount();
    }

    public long insert(StatusModel status) {
        ContentValues cv = new ContentValues();
            cv.put(objectId, status.getObjectId());
            cv.put(code, status.getCode());
            cv.put(description, status.getDescription());
            cv.put(name, status.getName());
        return db.insert(Tables.STATUS, null, cv);
    }




}
