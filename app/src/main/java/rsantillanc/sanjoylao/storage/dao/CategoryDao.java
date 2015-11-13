package rsantillanc.sanjoylao.storage.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import java.util.ArrayList;
import java.util.List;

import rsantillanc.sanjoylao.model.CategoryModel;
import rsantillanc.sanjoylao.model.ParseFileModel;

/**
 * Created by RenzoD on 31/10/2015.
 */
public class CategoryDao implements BaseColumns {

    private static final String SELECT = "Select * from " + Tables.CATEGORY;

    //Columns
    private String objectId = "objectId";
    private String name = "name";
    private String urlImage = "urlImage";
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
     *
     * @param category Modelo con los datos a insertar
     * @return numero de rows afected.
     */
    public long insert(CategoryModel category) {
        ContentValues cv = new ContentValues();
        cv.put(objectId, category.getObjectId());
        cv.put(name, category.getName());
        if (category.getImage() != null)
            cv.put(urlImage, category.getImage().getUrl());
        cv.put(createdAt, category.getCreatedAt());
        cv.put(updatedAt, category.getUpdatedAt());
        return db.insert(Tables.CATEGORY, null, cv);
    }


    public int count() {
        Cursor cur = db.rawQuery(SELECT, null);
        return cur.getCount();
    }

    public List<CategoryModel> list() {
        Cursor cur = db.rawQuery(SELECT, null);
        return loopCategories(cur, new ArrayList<CategoryModel>());
    }


    private List<CategoryModel> loopCategories(Cursor cur, List<CategoryModel> categories) {

        if (cur.moveToFirst())
            do {

                CategoryModel category = new CategoryModel();
                category.setObjectId(cur.getString(cur.getColumnIndex(objectId)));
                category.setName(cur.getString(cur.getColumnIndex(name)));
                category.setImage(makeFile(cur));
                category.setCreatedAt(cur.getString(cur.getColumnIndex(createdAt)));
                category.setUpdatedAt(cur.getString(cur.getColumnIndex(updatedAt)));
                categories.add(category);

            } while (cur.moveToNext());

        if (!cur.isClosed()) {
            cur.close();
        }

        return categories;
    }

    private ParseFileModel makeFile(Cursor cur) {
        ParseFileModel file = new ParseFileModel();
        file.setUrl(cur.getString(cur.getColumnIndex(urlImage)));
        return file;
    }

    public void listByPlateID(String plateID) {
//        db.query(Tables.CATEGORY, null, );
    }

    public CategoryModel getCategoryByID(String categoryID) {
        Cursor cur = db.query(Tables.CATEGORY, null, objectId + "=?", new String[]{categoryID}, null, null, null);
        return makeCategory(cur);
    }

    private CategoryModel makeCategory(Cursor cur) {
        CategoryModel category = new CategoryModel();

        if (cur.moveToFirst()) {
            category.setObjectId(cur.getString(cur.getColumnIndex(objectId)));
            category.setName(cur.getString(cur.getColumnIndex(name)));
            category.setImage(makeFile(cur));
            category.setCreatedAt(cur.getString(cur.getColumnIndex(createdAt)));
            category.setUpdatedAt(cur.getString(cur.getColumnIndex(updatedAt)));
        }

        return category;
    }
}
