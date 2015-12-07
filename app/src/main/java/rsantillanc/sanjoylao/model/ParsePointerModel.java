package rsantillanc.sanjoylao.model;

import java.io.Serializable;

import rsantillanc.sanjoylao.util.Const;

/**
 * Created by rsantillanc on 03/11/2015.
 */
public class ParsePointerModel implements Serializable {
    private static final long serialVersionUID = 0L;

    private String __type = Const.KEY_POINTER;
    private String className;
    private String objectId;

    public ParsePointerModel() {
    }

    public ParsePointerModel(String className, String objectId) {
        this.className = className;
        this.objectId = objectId;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}
