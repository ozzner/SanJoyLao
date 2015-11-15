package rsantillanc.sanjoylao.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by RenzoD on 04/06/2015.
 */
public class CommentModel implements Serializable {
    private static final long serialVersionUID = 0L;

    private String objectId;
    private String comment;
    @SerializedName("idPlate")
    private PlateModel plate;
    @SerializedName("idUser")
    private UserModel user;
    private String createdAt;

    public CommentModel(String objectId, String comment, PlateModel plate, UserModel user, String createdAt) {
        this.objectId = objectId;
        this.comment = comment;
        this.plate = plate;
        this.user = user;
        this.createdAt = createdAt;
    }

    public CommentModel() {
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public PlateModel getPlate() {
        return plate;
    }

    public void setPlate(PlateModel plate) {
        this.plate = plate;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
