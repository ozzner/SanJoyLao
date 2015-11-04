package rsantillanc.sanjoylao.storage.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import rsantillanc.sanjoylao.model.PlateSizeModel;

/**
 * Created by rsantillanc on 03/11/2015.
 */
public class PlateSizeDao extends BaseDao {

    private static final String SELECT = "Select * from " + Tables.PLATE_SIZE;

    //Columns
    private String objectId = "objectId";
    private String idPlate = "idPlate";
    private String idSize = "idSize";
    private String price = "price";
    private String timeOfPreparation = "timeOfPreparation";


    //Database
    private SQLiteDatabase db;


    public PlateSizeDao(Context c) {
        SJLDatabase mSJLDatabase = new SJLDatabase(c);
        this.db = mSJLDatabase.getWritableDatabase();
    }

    public int count() {
        Cursor cur = db.rawQuery(SELECT, null);
        return cur.getCount();
    }

    public long insert(PlateSizeModel plateSize) {
        ContentValues cv = new ContentValues();
        cv.put(objectId, plateSize.getObjectId());
        cv.put(idPlate, plateSize.getIdPlate().getObjectId());
        cv.put(idSize, plateSize.getSize().getObjectId());
        cv.put(price, plateSize.getPrice());
        cv.put(timeOfPreparation, plateSize.getTimeOfPreparation());
        return db.insert(Tables.PLATE_SIZE, null, cv);
    }

}
