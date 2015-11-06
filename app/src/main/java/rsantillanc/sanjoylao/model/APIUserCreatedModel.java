package rsantillanc.sanjoylao.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by RenzoD on 20/10/2015.
 */
public class APIUserCreatedModel {

    @SerializedName("objectId")
    private String objectId;

    @SerializedName("createdAt")
    private String createdAt;

    @SerializedName("sessionToken")
    private String sessionToken;

    public APIUserCreatedModel(String objectId, String createdAt, String sessionToken) {
        this.objectId = objectId;
        this.createdAt = createdAt;
        this.sessionToken = sessionToken;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getSessionToken() {
        return sessionToken;
    }

    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }
}
