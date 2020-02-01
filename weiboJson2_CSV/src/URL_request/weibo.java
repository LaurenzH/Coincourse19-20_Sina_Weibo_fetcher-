package URL_request;

import java.io.Serializable;
import java.util.ArrayList;

public class weibo implements Serializable {

    private double id;
    private String idstr;
    private String created_at;
    private String text;
    private String device_source;
    private ArrayList<weiboPicture> pictures = new ArrayList<weiboPicture>();
    private double user_id;
    private String user_idstr;

    public weibo(double id, String idstr, String created_at, String text, String device_source, double user_id, String user_idstr) {
        this.id = id;
        this.idstr = idstr;
        this.created_at = created_at;
        this.text = text;
        this.device_source = device_source;
        this.user_id = user_id;
        this.user_idstr = user_idstr;

    }

    public double getId() {
        return id;
    }

    public String getIdstr() {
        return idstr;
    }

    public void setIdstr(String idstr) {
        this.idstr = idstr;
    }

    public String getUser_idstr() {
        return user_idstr;
    }

    public void setUser_idstr(String user_idstr) {
        this.user_idstr = user_idstr;
    }

    public void setId(double id) {
        this.id = id;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDevice_source() {
        return device_source;
    }

    public void setDevice_source(String device_source) {
        this.device_source = device_source;
    }

    public ArrayList<weiboPicture> getPictures() {
        return pictures;
    }

    public void setPictures(ArrayList<weiboPicture> pictures) {
        this.pictures = pictures;
    }

    public double getUser_id() {
        return user_id;
    }

    public void setUser_id(double user_id) {
        this.user_id = user_id;
    }


}
