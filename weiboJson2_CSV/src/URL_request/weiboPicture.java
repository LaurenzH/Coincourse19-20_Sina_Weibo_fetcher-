package URL_request;

public class weiboPicture {

    private int pictureID;
    private String pictureURL;

    public weiboPicture(int pictureID, String pictureURL) {
        this.pictureID = pictureID;
        this.pictureURL = pictureURL;
    }

    public int getPictureID() {
        return pictureID;
    }

    public void setPictureID(int pictureID) {
        this.pictureID = pictureID;
    }

    public String getPictureURL() {
        return pictureURL;
    }

    public void setPictureURL(String pictureURL) {
        this.pictureURL = pictureURL;
    }
}
