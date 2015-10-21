package rsantillanc.sanjoylao.model;

/**
 * Created by rsantillanc on 19/10/2015.
 */
public class UserModel  {

//    private static final long serialVersionUID = 0L;

    private String objectId;

    private String username;

    private boolean emailVerified;

    private String email;

    private int socialLogin;

    private String createdAt;

    private String updatedAt;

    private boolean haveProfileImage;

    private String urlProfileImage;

    private String fullName;

    private long phoneNumber;

    private boolean isEnabled;

    private String sessionToken;

    private String password;



    //-------------[ Constructors ]

    public UserModel() {
    }

    public UserModel(String objectId, String username, boolean emailVerified, String email, int socialLogin, String createdAt, String updatedAt, boolean haveProfileImage, String urlProfileImage, String fullName, long phoneNumber, boolean isEnabled, String sessionToken, String password) {
        this.objectId = objectId;
        this.username = username;
        this.emailVerified = emailVerified;
        this.email = email;
        this.socialLogin = socialLogin;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.haveProfileImage = haveProfileImage;
        this.urlProfileImage = urlProfileImage;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.isEnabled = isEnabled;
        this.sessionToken = sessionToken;
        this.password = password;
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
}
