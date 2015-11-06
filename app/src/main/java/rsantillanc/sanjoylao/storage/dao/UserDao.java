package rsantillanc.sanjoylao.storage.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import java.util.ArrayList;
import java.util.List;

import rsantillanc.sanjoylao.model.UserModel;
import rsantillanc.sanjoylao.util.Const;

/**
 * Created by RenzoD on 21/10/2015.
 */
public class UserDao implements BaseColumns {

    //Const
    private static final String SELECT = "Select * from ";
    private static final String SELECT_BY_EMAIL = "SELECT * FROM " + Tables.USER + " WHERE email = ?";
    private static final String SELECT_BY_IS_ENABLED = "SELECT * FROM " + Tables.USER + " WHERE isEnabled = ?";

    //Database
    private SQLiteDatabase db;

    //Columns
    private String objectId = "objectId";
    private String username = "username";
    private String emailVerified = "emailVerified";
    private String email = "email";
    private String socialLogin = "socialLogin";
    private String createdAt = "createdAt";
    private String updatedAt = "updatedAt";
    private String haveProfileImage = "haveProfileImage";
    private String urlProfileImage = "urlProfileImage";
    private String fullName = "fullName";
    private String phoneNumber = "phoneNumber";
    private String isEnabled = "isEnabled";
    private String sessionToken = "sessionToken";
    private String birthday = "birthday";
    private String identificationDocument = "identificationDocument";

    //Constructor
    public UserDao(Context c) {
        SJLDatabase mSJLDatabase = new SJLDatabase(c);
        this.db = mSJLDatabase.getWritableDatabase();
    }

    /**
     * Inserta un usuario en la base de datos.
     *
     * @param user Modelo de datos para el usuario.
     * @return long con el número de filas insertadas.
     */
    public long insertUser(UserModel user) {
        disabledAllUsers();

        ContentValues cv = new ContentValues();
        cv.put(objectId, user.getObjectId());
        cv.put(username, user.getUsername());
        cv.put(emailVerified, user.isEmailVerified());
        cv.put(email, user.getEmail());
        cv.put(socialLogin, user.getSocialLogin());
        cv.put(createdAt, user.getCreatedAt());
        cv.put(updatedAt, user.getUpdatedAt());
        cv.put(haveProfileImage, user.isHaveProfileImage());
        cv.put(urlProfileImage, user.getUrlProfileImage());
        cv.put(fullName, user.getFullName());
        cv.put(phoneNumber, user.getPhoneNumber());
        cv.put(isEnabled, user.isEnabled());
        cv.put(sessionToken, user.getSessionToken());
        cv.put(birthday, user.getBirthday());
        cv.put(identificationDocument, user.getIdentificationDocument());

        long rows = db.insert(Tables.USER, null, cv);
        cv.clear();

        return rows;
    }

    public boolean checkUserByEmail(String email) {
        Cursor cur = db.rawQuery(sqlGetUser(), new String[]{email});
        return cur.moveToFirst();
    }


    public List<Object> getUserByEmail(String email) {
        List<Object> users = new ArrayList<>();
        Cursor cur = db.rawQuery(sqlGetUser(), new String[]{email});
        return loopUsers(cur, users);
    }

    public List<Object> getCurrentUser(int enabled) {
        List<Object> users = new ArrayList<>();
        Cursor cur = db.rawQuery(SELECT_BY_IS_ENABLED, new String[]{String.valueOf(enabled)});
        return loopUsers(cur, users);
    }

    private String sqlGetUser() {
        return SELECT_BY_EMAIL;
    }


    private int disabledAllUsers() {
        ContentValues cv = new ContentValues();
        cv.put(isEnabled, Const.USER_DISABLED);
        return db.update(Tables.USER, cv, null, null);
    }


    private List<Object> loopUsers(Cursor cur, List<Object> listObjects) {

        if (cur.moveToFirst())
            do {

                UserModel user = new UserModel();
                user.setEmailVerified((cur.getInt(cur.getColumnIndex(emailVerified))) == 1 ? true : false);
                user.setEmail(cur.getString(cur.getColumnIndex(this.email)));
                user.setFullName(cur.getString(cur.getColumnIndex(fullName)));
                user.setUsername(cur.getString(cur.getColumnIndex(username)));
                user.setObjectId(cur.getString(cur.getColumnIndex(objectId)));
                user.setSessionToken(cur.getString(cur.getColumnIndex(sessionToken)));
                user.setUrlProfileImage(cur.getString(cur.getColumnIndex(urlProfileImage)));
                user.setCreatedAt(cur.getString(cur.getColumnIndex(createdAt)));
                user.setHaveProfileImage((cur.getInt(cur.getColumnIndex(haveProfileImage))) == 1 ? true : false);
                user.setIsEnabled((cur.getInt(cur.getColumnIndex(isEnabled))) == 1 ? true : false);
                user.setUpdatedAt(cur.getString(cur.getColumnIndex(updatedAt)));
                user.setPhoneNumber(cur.getLong(cur.getColumnIndex(phoneNumber)));
                user.setSocialLogin(cur.getInt(cur.getColumnIndex(socialLogin)));
                user.setBirthday(cur.getString(cur.getColumnIndex(birthday)));
                user.setIdentificationDocument(cur.getLong(cur.getColumnIndex(identificationDocument)));
                listObjects.add(user);

            } while (cur.moveToNext());


        if (!cur.isClosed()) {
            cur.close();
        }

        return listObjects;
    }

    public void logout() {
        disabledAllUsers();
    }

    public void login(String email) {
        ContentValues cv = new ContentValues();
        cv.put(isEnabled, Const.USER_ENABLED);
        db.update(Tables.USER, cv, this.email + "=?", new String[]{email});
    }
}
