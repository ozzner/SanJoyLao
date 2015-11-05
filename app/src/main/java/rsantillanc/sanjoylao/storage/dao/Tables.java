package rsantillanc.sanjoylao.storage.dao;

/**
 * Created by RenzoD on 21/10/2015.
 */
public class Tables {

    //Create
    public static final String CREATE_USER = "CREATE TABLE `USER` (\n" +
            "\t`objectId`\tTEXT NOT NULL,\n" +
            "\t`username`\tTEXT,\n" +
            "\t`emailVerified`\tINTEGER NOT NULL DEFAULT 0,\n" +
            "\t`email`\tTEXT NOT NULL UNIQUE,\n" +
            "\t`socialLogin`\tINTEGER NOT NULL DEFAULT -1,\n" +
            "\t`createdAt`\tTEXT,\n" +
            "\t`updatedAt`\tTEXT,\n" +
            "\t`haveProfileImage`\tINTEGER NOT NULL DEFAULT 0,\n" +
            "\t`urlProfileImage`\tTEXT,\n" +
            "\t`fullName`\tTEXT,\n" +
            "\t`phoneNumber`\tNUMERIC,\n" +
            "\t`isEnabled`\tINTEGER DEFAULT 1,\n" +
            "\t`sessionToken`\tTEXT UNIQUE,\n" +
            "\t`birthday`\tTEXT,\n" +
            "\t`identificationDocument`\tINTEGER,\n" +
            "\tPRIMARY KEY(objectId)\t" +
            ");";


    public static final String CREATE_CATEGORY = "CREATE TABLE `CATEGORY` (\n" +
            "\t`objectId`\tTEXT NOT NULL,\n" +
            "\t`name`\tTEXT NOT NULL UNIQUE,\n" +
            "\t`urlImage`\tTEXT,\n" +
            "\t`createdAt`\tTEXT,\n" +
            "\t`updatedAt`\tTEXT,\n" +
            "\tPRIMARY KEY(objectId)\n" +
            ");";


    public static final String CREATE_PLATE_SIZE = "CREATE TABLE `PLATE_SIZE` (\n" +
            "\t`objectId`\tTEXT NOT NULL,\n" +
            "\t`idPlate`\tTEXT NOT NULL,\n" +
            "\t`idSize`\tTEXT NOT NULL,\n" +
            "\t`price`\tINTEGER DEFAULT 0,\n" +
            "\t`timeOfPreparation`\tINTEGER DEFAULT 5,\n" +
            "\tPRIMARY KEY(objectId)\n" +
            ");";


    public static final String CREATE_SIZE = "CREATE TABLE `SIZE` (\n" +
            "\t`objectId`\tTEXT NOT NULL,\n" +
            "\t`name`\tTEXT NOT NULL DEFAULT 'Normal',\n" +
            "\tPRIMARY KEY(objectId)\n" +
            ");";


    public static final String CREATE_PLATE = "CREATE TABLE `PLATE` (\n" +
            "\t`objectId`\tTEXT NOT NULL,\n" +
            "\t`idCategory`\tTEXT NOT NULL,\n" +
            "\t`image`\tTEXT,\n" +
            "\t`name`\tTEXT NOT NULL,\n" +
            "\t`atFeast`\tINTEGER NOT NULL DEFAULT 0,\n" +
            "\t`available`\tINTEGER NOT NULL DEFAULT 1,\n" +
            "\t`ingredients`\tTEXT,\n" +
            "\t`qualification`\tTEXT NOT NULL DEFAULT '{}',\n" +
            "\t`recommended`\tINTEGER NOT NULL DEFAULT 0,\n" +
            "\tPRIMARY KEY(objectId)\n" +
            ");";


    public static final String CREATE_ORDER_TYPE = "CREATE TABLE `ORDER_TYPE` (\n" +
            "\t`objectId`\tTEXT NOT NULL,\n" +
            "\t`name`\tTEXT NOT NULL,\n" +
            "\tPRIMARY KEY(objectId)\n" +
            ");";

    public static final String CREATE_ORDER = "CREATE TABLE `ORDER` (\n" +
            "\t`objectId`\tTEXT NOT NULL,\n" +
            "\t`idLocationDelivery`\tTEXT NOT NULL,\n" +
            "\t`idOrderType`\tTEXT,\n" +
            "\t`idPago`\tTEXT UNIQUE,\n" +
            "\t`idStatus`\tTEXT,\n" +
            "\t`idUser`\tREAL NOT NULL,\n" +
            "\t`Price`\tNUMERIC NOT NULL DEFAULT 0.0,\n" +
            "\t`createdAt`\tTEXT NOT NULL,\n" +
            "\t`updatedAt`\tINTEGER NOT NULL,\n" +
            "\tPRIMARY KEY(objectId)\n" +
            ");";



    //Drop
    public static final String DROP_TABLE = "DROP TABLE IF EXISTS ";

    //Tables name
    public static final String USER = "USER";
    public static final String CATEGORY = "CATEGORY";
    public static final String PLATE_SIZE = "PLATE_SIZE";
    public static final String SIZE = "SIZE";
    public static final String PLATE = "PLATE";
    public static final String ORDER_TYPE = "ORDER_TYPE";
    public static final String ORDER = "ORDER";
}
