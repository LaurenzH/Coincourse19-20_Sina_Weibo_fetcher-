package URL_request;

import java.io.Serializable;
import java.util.Date;

public class weibo_user implements Serializable {

    private double userID;
    private String userIDstr;
    private String name;
    private String location;
    private String description;
    private String gender;
    private double follower;
    private double friends;
    private double weibos;
    private String created_at;

    public weibo_user(double userID, String userIDstr, String name, String location, String description, String gender, double follower, double friends, double weibos, String created_at) {
        this.userID = userID;
        this.userIDstr = userIDstr;
        this.name = name;
        this.location = location;
        this.description = description;
        this.gender = gender;
        this.follower = follower;
        this.friends = friends;
        this.weibos = weibos;
        this.created_at = created_at;
    }

    public String getUserIDstr() {
        return userIDstr;
    }

    public void setUserIDstr(String userIDstr) {
        this.userIDstr = userIDstr;
    }

    public double getUserID() {
        return userID;
    }

    public void setUserID(double userID) {
        this.userID = userID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public double getFollower() {
        return follower;
    }

    public void setFollower(double follower) {
        this.follower = follower;
    }

    public double getFriends() {
        return friends;
    }

    public void setFriends(double friends) {
        this.friends = friends;
    }

    public double getWeibos() {
        return weibos;
    }

    public void setWeibos(double weibos) {
        this.weibos = weibos;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
}

