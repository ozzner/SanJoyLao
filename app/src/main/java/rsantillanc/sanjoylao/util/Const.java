package rsantillanc.sanjoylao.util;

/**
 * Created by RenzoD on 04/06/2015.
 */
public class Const {

    public static final String BLANK_SPACE = " ";
    public static final String EMPTY = "";
    public static final String EMPTY_JSON = "{}";
    public static final String PRICE_USD = " USD";
    public static final String PRICE_PEN = "S/. ";
    public static final String TAG_BANQUET = "BANQUET";
    public static final String TAG_DETAILS_OPTIONS = "DETAILS_OPTIONS";
    public static final String TAG_EMPTY = "Empty";
    public static final String TAG_OPTIONS = "Opcion";
    public static final float GRID_PADDING = 4;
    public static final String TAG_POR = "x";
    public static final String TAG_FACEBOOK = "facebook";
    public static final String TAG_GOOGLE = "google +";
    public static final String PERCENT_OPERATOR = "%";
    public static final String THREE_DOTS = "...";




    /*Items menu*/
    public static final int INPUT = 0;
    public static final int RICE = 1;
    public static final int SOUP = 2;
    public static final int CHEF = 3;
    public static final int CHICKEN_MEAT = 4;
    public static final int FISH = 5;
    public static final int VEGETARIAN = 6;
    public static final int BANQUETS = 7;
    public static final int DRINKS = 8;
    public static final int CENTRAL = 9;

    //Others menu options
    public static final int COMMENTS = -1;
    public static final int ORDERS = -2;

    //Type screen
    public static final int TABLET_10 = 2;
    public static final int TABLET_7 = 1;
    public static final int PHONE_SCREEN = 0;

    //Type of bookings
    public static final int BOOKING_TABLE = 0;
    public static final int BOOKING_ORDER = 1;
    public static final int BOOKING_DELIVERY = 2;


    //[ DEBUG ]
    public static final String DEBUG = "DEBUGGING";
    public static final String DEBUG_GOOGLE_PLUS = "GOOGLE_PLUS";
    public static final String DEBUG_FACEBOOK = "FACEBOOK";
    public static final String DEBUG_PUSH = "[DEBUG_PUSH]";


    //[ OAUTH ]
    public static final int LOGIN_GOOGLE = 0;
    public static final int LOGIN_FACEBOOK = 1;
    public static final int LOGIN_FORM = -1;
    public static final long PHONE_DEFAULT = 999999999;
    public static final String PARSE_DATE = "Date";


    //Keys sharedPreferences
    public static final String KEY_ORDER_TYPE = "sjl_my_order";
    public static final String KEY_INSTALLATION = "first_time";
    public static final String KEY_PUSH_STATUS = "status_order";
    public static final String KEY_CURRENT_AMOUNT = "current_amount";
    public static final String KEY_COUNTER = "key_counter";


    //[ USER ]
    public static final int USER_ENABLED = 1;
    public static final int USER_DISABLED = 0;
    public static final char CHAR_COMMA = ',';
    public static final char CHAR_DOT = '.';

    //[ PARSE ]
    public static final String PARSE_TYPE_FILE = "File";
    public static final String EXTRA_CATEGORY_ID = "category_id";
    public static final String EXTRA_CATEGORY_NAME = "category_name";
    public static final String EXTRA_PLATE_DETAIL = "plate_detail";
    public static final String EXTRA_USER = "user";
    public static final String SJL_CHANNEL = "SJLChannel";


    public static final String KEY_POINTER = "Pointer";
    public static final String CLASS_USER = "_User";
    public static final String CLASS_PLATE = "Plate";
    public static final String CLASS_STATUS = "Status";
    public static final String CLASS_ORDER = "Order";
    public static final String CLASS_PLATE_SIZE = "PlateSize";

    //[ STATUS ]
    public static final int STATUS_TEMPORAL = 1;
    public static final int STATUS_RECEIVED = 2;
    public static final int STATUS_CONFIRMED = 3;
    public static final int STATUS_ON_ROAD = 4;
    public static final int STATUS_DELIVERED = 5;
    public static final int STATUS_CANCELLED = 6;
    public static final int DEFAULT_COUNTER = 1;
}
