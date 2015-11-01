package rsantillanc.sanjoylao.storage.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import rsantillanc.sanjoylao.model.CategoryModel;

/**
 * Created by RenzoD on 31/10/2015.
 */
public class CategoryDao implements BaseColumns{

    private static final String SELECT = "Select * from " + Tables.CATEGORY;

    //Columns
    private String objectId = "objectId";
    private String name = "name";
    private String createdAt = "createdAt";
    private String updatedAt = "updatedAt";

    //Database
    private SQLiteDatabase db;

    //Constructor
    public CategoryDao(Context c) {
        SJLDatabase mSJLDatabase = new SJLDatabase(c);
        this.db = mSJLDatabase.getWritableDatabase();
    }



    /**
     * Inserta una categoria en la base de datos.
     * @param category Modelo con los datos a insertar
     * @return numero de rows afected.
     */
    public long insertCategory(CategoryModel category){
        ContentValues cv = new ContentValues();
        cv.put(objectId,category.getObjectId());
        cv.put(name,category.getName());
        cv.put(createdAt,category.getCreatedAt());
        cv.put(updatedAt,category.getUpdatedAt());
        return db.insert(Tables.CATEGORY,null,cv);
    }


    public int count(){
        Cursor cur = db.rawQuery(SELECT, null);
        return cur.getCount();
    }

}
