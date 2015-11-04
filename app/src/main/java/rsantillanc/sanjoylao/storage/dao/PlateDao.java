package rsantillanc.sanjoylao.storage.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import rsantillanc.sanjoylao.model.PlateModel;
import rsantillanc.sanjoylao.util.Const;

/**
 * Created by rsantillanc on 03/11/2015.
 */
public class PlateDao {
    private static final String SELECT = "Select * from " + Tables.PLATE;

    //Columns
    private String objectId = "objectId";
    private String idCategory = "idCategory";
    private String image = "image";
    private String name = "name";
    private String atFeast = "atFeast";
    private String available = "available";
    private String ingredients = "ingredients";
    private String qualification = "qualification";
    private String recommended = "recommended";

    //Database
    private SQLiteDatabase db;


    public PlateDao(Context c) {
        SJLDatabase mSJLDatabase = new SJLDatabase(c);
        this.db = mSJLDatabase.getWritableDatabase();
    }

    public int count() {
        Cursor cur = db.rawQuery(SELECT, null);
        return cur.getCount();
    }

    public long insert(PlateModel plate) {
        ContentValues cv = new ContentValues();

        cv.put(objectId, plate.getObjectId());
        cv.put(idCategory, plate.getCategory().getObjectId());
        cv.put(image, getImagePlate(plate));
        cv.put(name, plate.getName());
        cv.put(atFeast, (plate.isAtFeast()) ? 1 : 0);
        cv.put(available, (plate.isAvalible()) ? 1 : 0);
        cv.put(ingredients, plate.getIngredients());
        cv.put(qualification, getQualificationPlate(plate));
        cv.put(recommended, (plate.isRecommendet()) ? 1 : 0);

        return db.insert(Tables.PLATE, null, cv);
    }

    private String getQualificationPlate(PlateModel plate) {
        if (plate.getQualification() == null)
            return Const.EMPTY_JSON;
        else
            return plate.getQualification().toString();
    }

    private String getImagePlate(PlateModel plate) {
        if (plate.getImage() == null)
            return Const.EMPTY;
        else
            return plate.getImage().getUrl();
    }
}
