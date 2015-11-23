package rsantillanc.sanjoylao.storage.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import rsantillanc.sanjoylao.model.StatusModel;
import rsantillanc.sanjoylao.util.Const;

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


    public String getIdStatusByCode(int iCode) {
        Cursor cur = db.query(Tables.STATUS, null, code + COMPARE, new String[]{String.valueOf(iCode)}, null, null, null);
        if (cur.moveToFirst()) {
            return cur.getString(cur.getColumnIndex(objectId));
        } else
            return Const.EMPTY;

    }

    public StatusModel getStatusByCode(int statusCode) {
        Cursor cur = db.query(Tables.STATUS, null, code + COMPARE, new String[]{String.valueOf(statusCode)}, null, null, null);
        StatusModel status;
        if (cur.moveToFirst()) {
            status = new StatusModel();
            status.setCode(cur.getInt(cur.getColumnIndex(code)));
            status.setName(cur.getString(cur.getColumnIndex(name)));
            status.setObjectId(cur.getString(cur.getColumnIndex(objectId)));
            status.setDescription(cur.getString(cur.getColumnIndex(description)));
        } else
            status = null;

        return status;
    }

    public StatusModel getStatus(String statusID) {
        Cursor cur = db.query(Tables.STATUS, null, objectId + COMPARE, new String[]{statusID}, null, null, null);
        StatusModel status;

        if (cur.moveToFirst()) {
            status = new StatusModel();
            status.setCode(cur.getInt(cur.getColumnIndex(code)));
            status.setName(cur.getString(cur.getColumnIndex(name)));
            status.setObjectId(cur.getString(cur.getColumnIndex(objectId)));
            status.setDescription(cur.getString(cur.getColumnIndex(description)));
        } else
            status = null;

        return status;
    }
}
