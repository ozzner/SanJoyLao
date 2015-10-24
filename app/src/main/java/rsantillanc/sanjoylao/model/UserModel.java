package rsantillanc.sanjoylao.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import rsantillanc.sanjoylao.util.Const;

/**
 * Created by rsantillanc on 19/10/2015.
 */
public class UserModel implements Serializable {

    private static final long serialVersionUID = 0L;

    @SerializedName("objectId")
    private String objectId;

    @SerializedName("username")
    private String username;

    @SerializedName("emailVerified")
    private boolean emailVerified;

    @SerializedName("email")
    private String email;

    @SerializedName("socialLogin")
    private int socialLogin;

    @SerializedName("createdAt")
    private String createdAt;

    @SerializedName("updatedAt")
    private String updatedAt;

    @SerializedName("haveProfileImage")
    private boolean haveProfileImage;

    @SerializedName("urlProfileImage")
    private String urlProfileImage;

    @SerializedName("fullName")
    private String fullName;

    @SerializedName("phoneNumber")
    private long phoneNumber;

    @SerializedName("isEnabled")
    private boolean isEnabled;

    @SerializedName("sessionToken")
    private String sessionToken;

    @SerializedName("password")
    private String password;

    @SerializedName("birthday")
    private String birthday;

    @SerializedName("identificationDocument")
    private long identificationDocument;



    //-------------[ Constructors ]

    public UserModel() {
        this.objectId = Const.EMPTY;
        this.username = Const.EMPTY;
        this.emailVerified = false;
        this.email = Const.EMPTY;
        this.socialLogin = Const.LOGIN_FORM;
        this.createdAt = Const.EMPTY;
        this.updatedAt = Const.EMPTY;
        this.haveProfileImage = false;
        this.urlProfileImage = Const.EMPTY;
        this.fullName = Const.EMPTY;
        this.phoneNumber = Const.PHONE_DEFAULT;
        this.isEnabled = true;
        this.sessionToken = Const.EMPTY;
        this.password = Const.EMPTY;
    }



//-------------[ Custom methods ]

    @Override
    public String toString() {

        String output = "";
        output += "\n+-------------------- UserModel --------------------\n";
        output += "| objectId: " + objectId + "\n";
        output += "| username: " + username + "\n";
        output += "| emailVerified: " + emailVerified + "\n";
        output += "| email: " + email + "\n";
        output += "| socialLogin: " + socialLogin + "\n";
        output += "| createdAt: " + createdAt + "\n";
        output += "| updatedAt: " + updatedAt + "\n";
        output += "| haveProfileImage: " + haveProfileImage + "\n";
        output += "| urlProfileImage: " + urlProfileImage + "\n";
        output += "| fullName: " + fullName + "\n";
        output += "| phoneNumber: " + phoneNumber + "\n";
        output += "| isEnabled: " + isEnabled + "\n";
        output += "| sessionToken: " + sessionToken + "\n";
        output += "| password: " + password + "\n";
        output += "+-------------------- UserModel --------------------\n";
        return output;
    }


    //-------------[ Setter and Getter ]

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified(boolean emailVerified) {
        this.emailVerified = emailVerified;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getSocialLogin() {
        return socialLogin;
    }

    public void setSocialLogin(int socialLogin) {
        this.socialLogin = socialLogin;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public boolean isHaveProfileImage() {
        return haveProfileImage;
    }

    public void setHaveProfileImage(boolean haveProfileImage) {
        this.haveProfileImage = haveProfileImage;
    }

    public String getUrlProfileImage() {
        return urlProfileImage;
    }

    public void setUrlProfileImage(String urlProfileImage) {
        this.urlProfileImage = urlProfileImage;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    public String getSessionToken() {
        return sessionToken;
    }

    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public long getIdentificationDocument() {
        return identificationDocument;
    }

    public void setIdentificationDocument(long identificationDocument) {
        this.identificationDocument = identificationDocument;
    }

    //------------[ Innet class ]


//    public class ParseDate {
//        private String type;
//        private String iso;
//
//        public ParseDate(String type, String iso) {
//            this.type = type;
//            this.iso = iso;
//        }
//
//        public ParseDate() {
//            this.iso = Const.PARSE_DATE;
//        }
//
//        public String getIso() {
//            return iso;
//        }
//
//        public void setIso(String iso) {
//            this.iso = iso;
//        }
//    }
}
