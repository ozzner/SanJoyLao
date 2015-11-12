package rsantillanc.sanjoylao.storage.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import rsantillanc.sanjoylao.model.CategoryModel;
import rsantillanc.sanjoylao.model.ParseFileModel;
import rsantillanc.sanjoylao.model.PlateModel;
import rsantillanc.sanjoylao.model.PlateSizeModel;
import rsantillanc.sanjoylao.model.RelationPlateSizeModel;
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

    //Runtime
    private Context _context;


    public PlateDao(Context c) {
        SJLDatabase mSJLDatabase = new SJLDatabase(c);
        this.db = mSJLDatabase.getWritableDatabase();
        this._context = c;
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
        if (plate.getIngredients() != null)
            cv.put(ingredients, plate.getIngredients().toString());
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

    public List<RelationPlateSizeModel> listByCategory(String categoryID) {
        Cursor query = db.query(Tables.PLATE, null, idCategory + "=?", new String[]{categoryID}, null, null, null);
        return buildRelationPlateSize(query, new ArrayList<RelationPlateSizeModel>());
    }


    private List<PlateModel> loopPlates(Cursor cur, List<PlateModel> plates) {

        if (cur.moveToFirst())
            do {
                PlateModel plate = new PlateModel();

                plate.setObjectId(cur.getString(cur.getColumnIndex(objectId)));
                plate.setCategory(findCategoryByID(cur.getString(cur.getColumnIndex(idCategory))));
                plate.setImage(makeFile(cur));
                plate.setName(cur.getString(cur.getColumnIndex(name)));
                plate.setAtFeast((cur.getInt(cur.getColumnIndex(name))) == 1 ? true : false);
                plate.setAvalible((cur.getInt(cur.getColumnIndex(available))) == 1 ? true : false);
                plate.setIngredients(cur.getString(cur.getColumnIndex(available)));
                plate.setQualification(makeJSONObject(cur.getString(cur.getColumnIndex(qualification))));
                plate.setRecommendet((cur.getInt(cur.getColumnIndex(recommended))) == 1 ? true : false);
//                plate.setPlateSize(makePlateSize(cur.getString(cur.getColumnIndex(objectId))));

                plates.add(plate);
            } while (cur.moveToNext());

        if (!cur.isClosed()) {
            cur.close();
        }
        return plates;
    }


    private List<RelationPlateSizeModel> buildRelationPlateSize(Cursor cur, List<RelationPlateSizeModel> list) {
        RelationPlateSizeModel relation;

        if (cur.moveToFirst()) {
            PlateModel plate;
            do {
                relation = new RelationPlateSizeModel();
                plate = new PlateModel();

                plate.setObjectId(cur.getString(cur.getColumnIndex(objectId)));
                plate.setCategory(findCategoryByID(cur.getString(cur.getColumnIndex(idCategory))));
                plate.setImage(makeFile(cur));
                plate.setName(cur.getString(cur.getColumnIndex(name)));
                plate.setAtFeast((cur.getInt(cur.getColumnIndex(name))) == 1 ? true : false);
                plate.setAvalible((cur.getInt(cur.getColumnIndex(available))) == 1 ? true : false);
                plate.setIngredients(cur.getString(cur.getColumnIndex(available)));
                plate.setQualification(makeJSONObject(cur.getString(cur.getColumnIndex(qualification))));
                plate.setRecommendet((cur.getInt(cur.getColumnIndex(recommended))) == 1 ? true : false);

                //Creating
                relation.setCurrentPlate(plate);
                relation.setListSizes(makePlateSize(cur.getString(cur.getColumnIndex(objectId))));

                //Add
                list.add(relation);

            } while (cur.moveToNext());

            if (!cur.isClosed()) {
                cur.close();
            }
        }

        return list;
    }

    private CategoryModel findCategoryByID(String categoryID) {
        return new CategoryDao(_context).getCategoryByID(categoryID);
    }

    //{HELPERS}
    private ParseFileModel makeFile(Cursor cur) {
        ParseFileModel file = new ParseFileModel();
        file.setUrl(cur.getString(cur.getColumnIndex(image)));
        return file;
    }

    private JSONObject makeJSONObject(String qualification) {
        if (qualification.equals(Const.EMPTY_JSON))
            return null;
        else {
            JSONObject qa = new JSONObject();
            try {
                qa.getJSONObject(qualification);
            } catch (JSONException e) {
                e.printStackTrace();
                return qa;
            }
            return qa;
        }
    }

    private List<PlateSizeModel> makePlateSize(String plateID) {
        return new PlateSizeDao(_context).listByPlateID(plateID);
    }


    public PlateModel getPlateByID(String plateID) {
        Cursor cur = db.query(Tables.PLATE, null, objectId + "=?", new String[]{plateID}, null, null, null);
        return loopPlates(cur, new ArrayList<PlateModel>()).get(0);
    }
}
