package rsantillanc.sanjoylao.storage.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import rsantillanc.sanjoylao.model.ParsePointerModel;
import rsantillanc.sanjoylao.model.PlateSizeModel;
import rsantillanc.sanjoylao.model.SizeModel;
import rsantillanc.sanjoylao.util.Const;

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

    private String plateID;

    //Database
    private SQLiteDatabase db;
    private Context _context;


    public PlateSizeDao(Context c) {
        SJLDatabase mSJLDatabase = new SJLDatabase(c);
        this.db = mSJLDatabase.getWritableDatabase();
        this._context = c;
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


    //{HELPERS}

    private List<PlateSizeModel> loopPlateSize(Cursor cur, List<PlateSizeModel> list) {

        if (cur.moveToFirst())
            do {
                PlateSizeModel plateSize = new PlateSizeModel();

                plateSize.setObjectId(cur.getString(cur.getColumnIndex(objectId)));
                plateSize.setIdPlate(makePlatePointer(plateID));
                plateSize.setPrice(cur.getDouble(cur.getColumnIndex(price)));
                plateSize.setSize(makeSize(cur.getString(cur.getColumnIndex(idSize))));

                list.add(plateSize);
            } while (cur.moveToNext());

        if (!cur.isClosed()) {
            cur.close();
        }
        return list;
    }

    private SizeModel makeSize(String sizeID) {
        return new SizeDao(_context).getSizeByID(sizeID);
    }

    private ParsePointerModel makePlatePointer(String plateID) {
        return new ParsePointerModel(Const.CLASS_PLATE, plateID);
    }

    public List<PlateSizeModel> listByPlateID(String plateID) {
        Cursor cur = db.query(Tables.PLATE_SIZE, null, idPlate + "=?", new String[]{plateID}, null, null, null);
       return loopPlateSize(cur,new ArrayList<PlateSizeModel>());
    }
}
