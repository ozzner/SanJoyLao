package rsantillanc.sanjoylao.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

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

    @SerializedName("profileImage")
    private Object profileImage;

    @SerializedName("fullName")
    private String fullName;

    @SerializedName("lastLogin")
    private String lastLogin;

    @SerializedName("phoneNumber")
    private long phoneNumber;

    @SerializedName("isEnabled")
    private boolean isEnabled;

    @SerializedName("sessionToken")
    private String sessionToken;

    //-------------[ Constructors ]

    public UserModel() {
    }


    //-------------[ Custom methods ]

    @Override
    public String toString() {

        String output = "\n\n";
        output += "+-------------------- UserModel --------------------\n";
        output += "| objectId: " + objectId + "\n";
        output += "| username: " + username + "\n";
        output += "| emailVerified: " + emailVerified + "\n";
        output += "| email: " + email + "\n";
        output += "| socialLogin: " + socialLogin + "\n";
        output += "| createdAt: " + createdAt + "\n";
        output += "| updatedAt: " + updatedAt + "\n";
        output += "| haveProfileImage: " + haveProfileImage + "\n";
        output += "| urlProfileImage: " + urlProfileImage + "\n";
        output += "| profileImage: " + profileImage + "\n";
        output += "| fullName: " + fullName + "\n";
        output += "| lastLogin: " + lastLogin + "\n";
        output += "| phoneNumber: " + phoneNumber + "\n";
        output += "| isEnabled: " + isEnabled + "\n";
        output += "| sessionToken: " + sessionToken + "\n";
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

    public Object getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(Object profileImage) {
        this.profileImage = profileImage;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(String lastLogin) {
        this.lastLogin = lastLogin;
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
}
