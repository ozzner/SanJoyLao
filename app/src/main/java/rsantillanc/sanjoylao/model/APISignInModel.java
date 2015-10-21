package rsantillanc.sanjoylao.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by rsantillanc on 20/10/2015.
 */
public class APISignInModel implements Serializable{
    private static final long serialVersionUID = 0L;


    @SerializedName("username")
    private String username;

    @SerializedName("email")
    private String email;

    @SerializedName("socialLogin")
    private int socialLogin;


    @SerializedName("password")
    private String password;


//    @SerializedName("haveProfileImage")
//    private boolean haveProfileImage;
//
//    @SerializedName("urlProfileImage")
//    private String urlProfileImage;
//
//    @SerializedName("fullName")
//    private String fullName;
//
//    @SerializedName("lastLogin")
//    private String lastLogin;
//
//    @SerializedName("isEnabled")
//    private boolean isEnabled;


    public APISignInModel() {
    }

    public APISignInModel(String username,
                          String password,
                          int socialLogin,
//                          boolean haveProfileImage,
//                          String urlProfileImage,
                          String email
//                          String lastLogin,
//                          boolean isEnabled
    ) {

        this.username = username;
        this.password = password;
        this.socialLogin = socialLogin;
//        this.haveProfileImage = haveProfileImage;
//        this.urlProfileImage = urlProfileImage;
        this.email = email;
//        this.lastLogin = lastLogin;
//        this.isEnabled = isEnabled;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }

    public int getSocialLogin() {
        return socialLogin;
    }

    public void setSocialLogin(int socialLogin) {
        this.socialLogin = socialLogin;
    }

//    public boolean isHaveProfileImage() {
//        return haveProfileImage;
//    }
//
//    public void setHaveProfileImage(boolean haveProfileImage) {
//        this.haveProfileImage = haveProfileImage;
//    }
//
//    public String getUrlProfileImage() {
//        return urlProfileImage;
//    }
//
//    public void setUrlProfileImage(String urlProfileImage) {
//        this.urlProfileImage = urlProfileImage;
//    }
//
//    public String getFullName() {
//        return fullName;
//    }
//
//    public void setFullName(String fullName) {
//        this.fullName = fullName;
//    }
//
//    public String getLastLogin() {
//        return lastLogin;
//    }
//
//    public void setLastLogin(String lastLogin) {
//        this.lastLogin = lastLogin;
//    }
//
//    public boolean isEnabled() {
//        return isEnabled;
//    }
//
//    public void setIsEnabled(boolean isEnabled) {
//        this.isEnabled = isEnabled;
//    }
}
