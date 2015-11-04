package rsantillanc.sanjoylao.model;

import com.google.gson.annotations.SerializedName;

import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by rsantillanc on 29/10/2015.
 */
public class PlateModel implements Serializable {
    private static final long serialVersionUID = 0L;

    private String objectId;
    @SerializedName("idCategory")
    private CategoryModel category;
    private ParseFileModel image;
    private String name;
    private String createdAt;
    private String updatedAt;
    private boolean atFeast;
    private boolean avalible;
    private String ingredients;
    private JSONObject qualification;
    private boolean recommendet;
    private PlateSizeModel plateSize;


    public PlateModel() {
    }

    public PlateModel(PlateSizeModel plateSize) {
        this.plateSize = plateSize;
    }


    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public CategoryModel getCategory() {
        return category;
    }

    public void setCategory(CategoryModel category) {
        this.category = category;
    }

    public ParseFileModel getImage() {
        return image;
    }

    public void setImage(ParseFileModel image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public boolean isAtFeast() {
        return atFeast;
    }

    public void setAtFeast(boolean atFeast) {
        this.atFeast = atFeast;
    }

    public boolean isAvalible() {
        return avalible;
    }

    public void setAvalible(boolean avalible) {
        this.avalible = avalible;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public JSONObject getQualification() {
        return qualification;
    }

    public void setQualification(JSONObject qualification) {
        this.qualification = qualification;
    }

    public boolean isRecommendet() {
        return recommendet;
    }

    public void setRecommendet(boolean recommendet) {
        this.recommendet = recommendet;
    }

    public PlateSizeModel getPlateSize() {
        return plateSize;
    }

    public void setPlateSize(PlateSizeModel plateSize) {
        this.plateSize = plateSize;
    }
}
