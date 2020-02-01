package URL_request;

import java.io.Serializable;

public class weiboComment implements Serializable {

    private double weiboid;
    private String weiboidstr;
    private double commentid;
    private String commentidstr;
    private String created_at;
    private String text;
    private String entext;
    private double user_id;
    private String user_idstr;

    public weiboComment(double weiboid, String weiboidstr, double commentid, String commentidstr, String created_at, String text, String entext, double user_id, String user_idstr) {
        this.weiboid = weiboid;
        this.weiboidstr = weiboidstr;
        this.commentid = commentid;
        this.commentidstr = commentidstr;
        this.created_at = created_at;
        this.text = text;
        this.entext = entext;
        this.user_id = user_id;
        this.user_idstr = user_idstr;
    }

    public double getWeiboid() {
        return weiboid;
    }

    public String getEntext() {
        return entext;
    }

    private void setEntext(String entext) {
        this.entext = entext;
    }

    private void setWeiboid(double weiboid) {
        this.weiboid = weiboid;
    }

    public String getWeiboidstr() {
        return weiboidstr;
    }

    public void setWeiboidstr(String weiboidstr) {
        this.weiboidstr = weiboidstr;
    }

    public double getCommentid() {
        return commentid;
    }

    public void setCommentid(double commentid) {
        this.commentid = commentid;
    }

    public String getCommentidstr() {
        return commentidstr;
    }

    public void setCommentidstr(String commentidstr) {
        this.commentidstr = commentidstr;
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

    public double getUser_id() {
        return user_id;
    }

    public void setUser_id(double user_id) {
        this.user_id = user_id;
    }

    public String getUser_idstr() {
        return user_idstr;
    }

    public void setUser_idstr(String user_idstr) {
        this.user_idstr = user_idstr;
    }


}

