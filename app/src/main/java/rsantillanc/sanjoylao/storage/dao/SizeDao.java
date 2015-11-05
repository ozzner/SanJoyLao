package rsantillanc.sanjoylao.storage.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import rsantillanc.sanjoylao.model.SizeModel;

/**
 * Created by rsantillanc on 03/11/2015.
 */
public class SizeDao {

    private static final String SELECT = "Select * from " + Tables.SIZE;
    private static final String SELECT_WHERE = "Select * from where ";
    private static final String COMPARE = "=?";

    //Columns
    private String objectId = "objectId";
    private String name = "name";


    //Database
    private SQLiteDatabase db;

    public SizeDao(Context c) {
        SJLDatabase mSJLDatabase = new SJLDatabase(c);
        this.db = mSJLDatabase.getWritableDatabase();
    }

    public int count() {
        Cursor cur = db.rawQuery(SELECT, null);
        return cur.getCount();
    }

    public long insert(SizeModel sizeModel) {
        ContentValues cv = new ContentValues();
        cv.put(objectId, sizeModel.getObjectId());
        cv.put(name, sizeModel.getName());
        return db.insert(Tables.SIZE, null, cv);
    }

    public SizeModel getSizeByID(String sizeID) {
        SizeModel size = new SizeModel();
        Cursor cur = db.query(Tables.SIZE, null, objectId + COMPARE, new String[]{sizeID}, null, null, null);

        if (cur.moveToFirst()) {
            size.setObjectId(cur.getString(cur.getColumnIndex(objectId)));
            size.setName(cur.getString(cur.getColumnIndex(name)));
        }

        return size;
    }
}
