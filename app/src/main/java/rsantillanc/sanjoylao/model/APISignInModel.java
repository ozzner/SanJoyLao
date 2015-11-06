package rsantillanc.sanjoylao.model;

import com.google.gson.annotations.SerializedName;

import rsantillanc.sanjoylao.util.Const;

/**
 * Created by RenzoD on 20/10/2015.
 */
public class APISignInModel {

    @SerializedName("username")
    private String username;

    @SerializedName("password")
    private String password;

    @SerializedName("email")
    private String email;

    @SerializedName("fullName")
    private String fullName;

    @SerializedName("socialLogin")
    private int socialLogin;

    @SerializedName("haveProfileImage")
    private boolean haveProfileImage;

    @SerializedName("urlProfileImage")
    private String urlProfileImage;

    @SerializedName("phoneNumber")
    private long phoneNumber;

    @SerializedName("isEnabled")
    private boolean isEnabled;


    public APISignInModel() {
        this.password = Const.EMPTY;
        this.username = Const.EMPTY;
        this.phoneNumber = Const.PHONE_DEFAULT;
        this.isEnabled = true;
    }


    public APISignInModel(String username,
                          String password,
                          String email,
                          String fullName,
                          int socialLogin,
                          boolean haveProfileImage,
                          String urlProfileImage,
                          long phoneNumber,
                          boolean isEnabled) {

        this.username = username;
        this.password = password;
        this.email = email;
        this.fullName = fullName;
        this.socialLogin = socialLogin;
        this.haveProfileImage = haveProfileImage;
        this.urlProfileImage = urlProfileImage;
        this.phoneNumber = phoneNumber;
        this.isEnabled = isEnabled;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getSocialLogin() {
        return socialLogin;
    }

    public void setSocialLogin(int socialLogin) {
        this.socialLogin = socialLogin;
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

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
}
