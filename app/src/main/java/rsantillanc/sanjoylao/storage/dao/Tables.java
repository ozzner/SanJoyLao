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
            "\t`identificationDocument`\tINTEGER\n" +
            "\tPRIMARY KEY(objectId)\n" +

            ");";

    //Drop
    public static final String DROP_TABLE = "DROP TABLE IF EXISTS ";

    //Tables name
    public static final String USER = "USER";
}
