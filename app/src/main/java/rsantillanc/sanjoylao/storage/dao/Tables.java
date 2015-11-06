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

    public static final String CREATE_ORDER = "CREATE TABLE `ORDERS` (\n" +
            "\t`objectId`\tTEXT NOT NULL,\n" +
            "\t`idLocationDelivery`\tTEXT NOT NULL,\n" +
            "\t`idOrderType`\tTEXT,\n" +
            "\t`idPayment`\tTEXT UNIQUE,\n" +
            "\t`idStatus`\tTEXT,\n" +
            "\t`idUser`\tREAL NOT NULL,\n" +
            "\t`Price`\tNUMERIC NOT NULL DEFAULT 0.0,\n" +
            "\t`createdAt`\tTEXT NOT NULL,\n" +
            "\t`updatedAt`\tINTEGER NOT NULL,\n" +
            "\tPRIMARY KEY(objectId)\n" +
            ");";

    public static final String CREATE_CALCULATION_TIME = "CREATE TABLE `CALCULATION_TIME` (\n" +
            "\t`objectId`\tTEXT NOT NULL,\n" +
            "\t`alteredTime`\tINTEGER DEFAULT 0,\n" +
            "\t`calculatedTime`\tINTEGER DEFAULT 0,\n" +
            "\t`idOrder`\tTEXT,\n" +
            "\t`createdAt`\tTEXT,\n" +
            "\t`updatedAt`\tTEXT,\n" +
            "\tPRIMARY KEY(objectId)\n" +
            ");";


  public static final String CREATE_FEAST = "CREATE TABLE `FEAST` (\n" +
          "\t`objectId`\tTEXT NOT NULL,\n" +
          "\t`image`\tTEXT,\n" +
          "\t`included`\tTEXT,\n" +
          "\t`name`\tTEXT,\n" +
          "\t`flagOption`\tINTEGER DEFAULT 0,\n" +
          "\t`price`\tNUMERIC DEFAULT 0.0,\n" +
          "\t`quantityPerson`\tINTEGER DEFAULT 0,\n" +
          "\t`flagStatus`\tINTEGER DEFAULT 1,\n" +
          "\t`createdAt`\tTEXT,\n" +
          "\t`updatedAt`\tTEXT,\n" +
          "\tPRIMARY KEY(objectId)\n" +
          ");";


  public static final String CREATE_FEAST_PLATE = "CREATE TABLE `FEAST_PLATE` (\n" +
          "\t`objectId`\tTEXT NOT NULL,\n" +
          "\t`idFeast`\tTEXT,\n" +
          "\t`idPlate`\tTEXT,\n" +
          "\t`timeOfPreparation`\tINTEGER DEFAULT 0,\n" +
          "\t`createdAt`\tTEXT,\n" +
          "\t`updatedAt`\tTEXT,\n" +
          "\tPRIMARY KEY(objectId)\n" +
          ");";


  public static final String CREATE_LOCAL_RESTAURANT = "CREATE TABLE `LOCAL_RESTAURANT` (\n" +
          "\t`objectId`\tTEXT NOT NULL,\n" +
          "\t`Field2`\tINTEGER,\n" +
          "\t`deliveriAvailable`\tINTEGER DEFAULT 0,\n" +
          "\t`idRestaurant`\tTEXT,\n" +
          "\t`location`\tTEXT,\n" +
          "\t`reservationAvailable`\tINTEGER DEFAULT 0,\n" +
          "\t`createdAt`\tTEXT,\n" +
          "\t`updatedAt`\tTEXT,\n" +
          "\tPRIMARY KEY(objectId)\n" +
          ");";


  public static final String CREATE_LOCATION_DELIVERY = "CREATE TABLE `LOCATION_DELIVERY` (\n" +
          "\t`objectId`\tTEXT NOT NULL,\n" +
          "\t`location`\tTEXT,\n" +
          "\tPRIMARY KEY(objectId)\n" +
          ");";


  public static final String CREATE_ORDER_DETAIL = "CREATE TABLE `ORDER_DETAIL` (\n" +
          "\t`objectId`\tTEXT NOT NULL,\n" +
          "\t`idFeastPlate`\tTEXT,\n" +
          "\t`idOrder`\tTEXT,\n" +
          "\t`idPlateSize`\tTEXT,\n" +
          "\t`createdAt`\tTEXT,\n" +
          "\t`updatedAt`\tTEXT,\n" +
          "\tPRIMARY KEY(objectId)\n" +
          ");";


  public static final String CREATE_PAYMENT = "CREATE TABLE `PAYMENT` (\n" +
          "\t`objectId`\tTEXT NOT NULL,\n" +
          "\t`createdAt`\tTEXT,\n" +
          "\t`updatedAt`\tTEXT,\n" +
          "\tPRIMARY KEY(objectId)\n" +
          ");";


  public static final String CREATE_RESTAURANT = "CREATE TABLE `RESTAURANT` (\n" +
          "\t`objectId`\tTEXT NOT NULL,\n" +
          "\t`about`\tTEXT,\n" +
          "\t`centralCallNumber`\tINTEGER,\n" +
          "\t`name`\tTEXT,\n" +
          "\t`ruc`\tINTEGER,\n" +
          "\t`slogan`\tTEXT,\n" +
          "\t`createdAt`\tTEXT,\n" +
          "\t`updatedAt`\tTEXT,\n" +
          "\tPRIMARY KEY(objectId)\n" +
          ");";


  public static final String CREATE_RUSH_HOUR = "CREATE TABLE `RUSH_HOUR` (\n" +
          "\t`objectId`\tTEXT NOT NULL,\n" +
          "\t`aditionalTime`\tINTEGER DEFAULT 0,\n" +
          "\t`day`\tTEXT,\n" +
          "\t`fromHour`\tTEXT DEFAULT '00:00',\n" +
          "\t`toHour`\tTEXT DEFAULT '23:59',\n" +
          "\t`createdAt`\tTEXT,\n" +
          "\t`updatedAt`\tTEXT,\n" +
          "\tPRIMARY KEY(objectId)\n" +
          ");";


  public static final String CREATE_STATUS = "CREATE TABLE `STATUS` (\n" +
          "\t`objectId`\tTEXT NOT NULL,\n" +
          "\t`code`\tINTEGER DEFAULT 0,\n" +
          "\t`name`\tINTEGER,\n" +
          "\t`description`\tINTEGER,\n" +
          "\tPRIMARY KEY(objectId)\n" +
          ");";


  public static final String CREATE_SUGGESTION = "CREATE TABLE `SUGGESTION` (\n" +
          "\t`objectId`\tTEXT NOT NULL,\n" +
          "\t`code`\tTEXT,\n" +
          "\t`idFeastPlate`\tTEXT,\n" +
          "\t`name`\tTEXT,\n" +
          "\t`createdAt`\tTEXT,\n" +
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
    public static final String ORDERS = "ORDERS";
    public static final String ORDER_DETAIL = "ORDER_DETAIL";
    public static final String CALCULATION_TIME = "CALCULATION_TIME";
    public static final String FEAST = "FEAST";
    public static final String FEAST_PLATE = "FEAST_PLATE";
    public static final String LOCAL_RESTAURANT = "LOCAL_RESTAURANT";
    public static final String LOCATION_DELIVERY = "LOCATION_DELIVERY";
    public static final String PAYMENT = "PAYMENT";
    public static final String RESTAURANT = "RESTAURANT";
    public static final String RUSH_HOUR = "RUSH_HOUR";
    public static final String STATUS = "STATUS";
    public static final String SUGGESTION = "SUGGESTION";

}
