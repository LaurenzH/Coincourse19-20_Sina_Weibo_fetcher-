package URL_request;

import com.opencsv.CSVWriter;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.DoubleStream;

public class dataCollector {

    private String access_token = System.getenv("access_token");

    public ArrayList<weibo> weibos = new ArrayList<weibo>();
    public ArrayList<weibo_user> users = new ArrayList<weibo_user>();
    public ArrayList<weiboComment> comments = new ArrayList<weiboComment>();
    public ArrayList<weiboComment> comments2 = new ArrayList<weiboComment>();


    public dataCollector() {
    }

    public ArrayList<weibo> getWeibos() {
        return weibos;
    }

    public void setWeibos(ArrayList<weibo> weibos) {
        this.weibos = weibos;
    }

    public ArrayList<weibo_user> getUsers() {
        return users;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public ArrayList<weiboComment> getComments() {
        return comments;
    }

    public void setComments(ArrayList<weiboComment> comments) {
        this.comments = comments;
    }

    public void setUsers(ArrayList<weibo_user> users) {
        this.users = users;
    }

    private void addWeibo(double id, String idstr,String created_at, String text, String device_source, double user_id, String user_idstr) {
        weibo weibo = new weibo(id,idstr, created_at, text, device_source, user_id, user_idstr);
        // create array with Weibo IDs
        int sizeWeibos = weibos.size();
        double [] weiboIDs;
        weiboIDs = new double[sizeWeibos];
        for (int i = 0; i < sizeWeibos; i++) {
            double weiboID = weibos.get(i).getId();
            weiboIDs[i] = weiboID;
        }
        //check if user is already in user list
        boolean isinList = true;
        for (int i = 0; i < weiboIDs.length ; i++) {
            if (weiboIDs[i]==id){
                isinList = true;
                break;
            }else {
                isinList = false;
            }
        }
        if (isinList==false||sizeWeibos==0){
            weibos.add(weibo);
        }

    }

    private void addcomment(double weiboid, String weiboidstr, double commentid, String commentidstr,String created_at, String text, String entext, double user_id, String user_idstr){
        weiboComment comment = new weiboComment( weiboid,  weiboidstr,  commentid,  commentidstr, created_at,  text, entext,  user_id,  user_idstr);
        //create array with comment IDs
        double [] commentIDs;
        commentIDs = new double[comments.size()];
        for (int i = 0; i < commentIDs.length; i++) {
            commentIDs[i] = comments.get(i).getCommentid();
        }
        boolean isinList = true;
        for (int i = 0; i < commentIDs.length; i++) {
            double commentIDi =commentIDs[i];
            if (commentIDi==commentid){
                isinList = true;
                break;
            }else {
                isinList = false;
            }
        }
        if(isinList==false||comments.size()==0){
            comments.add(comment);
        }
    }

    private void addWeiboUser(double userID,String userIDstr, String name, String location, String description, String gender, double follower, double friends, double weibos, String created_at) {
        weibo_user weibo_user = new weibo_user(userID, userIDstr,name, location, description, gender, follower, friends, weibos, created_at);
        // create array with wUser IDs
        int sizewUser = users.size();
        double [] wUserIDs;
        wUserIDs = new double[sizewUser];
        for (int i = 0; i < sizewUser; i++) {
            double wUserID = users.get(i).getUserID();
            wUserIDs[i] = wUserID;
        }
        //check if user is already in user list
        boolean isinList = true;
        for (int i = 0; i < wUserIDs.length ; i++) {
            if (wUserIDs[i]==userID){
                isinList = true;
                break;
            }else {
                isinList = false;
            }
        }
        if (isinList==false||sizewUser==0){
            users.add(weibo_user);
        }
    }

    public void saveWeibos_Users_commentstoFile(){

        this.writeArraylistToFile(weibos,"fileWeibos.ser");
        this.writeArraylistToFile(users,"fileWeibousers.ser");
        this.writeArraylistToFile(comments,"comments.ser");
        System.out.print("all ArrayList are saved to the disk");
    }

    public void loadWeibosandUsersCommentsIntoArraylist(){
        this.readWeibosFromFile("fileWeibos.ser");
        this.readWeiboUsersFromFile("fileWeibousers.ser");
        this.readWeiboCommentsFromFile("comments.ser");
    }

    private void readWeiboCommentsFromFile(String filename) {
        try {
            // read file to import Array
            FileInputStream readData = new FileInputStream(filename);
            ObjectInputStream readStream = new ObjectInputStream(readData);
            ArrayList<weiboComment> weibosimport = (ArrayList<weiboComment>) readStream.readObject();
            readStream.close();
            // get values from file and pass to function addcomment for check
            for (int i = 0; i < weibosimport.size(); i++) {
                double weiboid = weibosimport.get(i).getWeiboid();
                String weiboidstr = weibosimport.get(i).getWeiboidstr();
                double commentid = weibosimport.get(i).getCommentid();
                String commentidstr = weibosimport.get(i).getCommentidstr();
                String created_at = weibosimport.get(i).getCreated_at();
                String text = weibosimport.get(i).getText();
                String entext = weibosimport.get(i).getEntext();
                double user_id = weibosimport.get(i).getUser_id();
                String user_idstr = weibosimport.get(i).getUser_idstr();
                this.addcomment(weiboid,weiboidstr,commentid,commentidstr,created_at,text,entext,user_id,user_idstr);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void writeArraylistToFile(ArrayList nameArraylist, String filename) {
        try {
            FileOutputStream writeData = new FileOutputStream(filename);
            ObjectOutputStream writeStream = new ObjectOutputStream(writeData);
            writeStream.writeObject(nameArraylist);
            writeStream.flush();
            writeStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readWeibosFromFile(String filename) {
        try {
            // read file to import Array
            FileInputStream readData = new FileInputStream(filename);
            ObjectInputStream readStream = new ObjectInputStream(readData);
            ArrayList<weibo> weibosimport = (ArrayList<weibo>) readStream.readObject();
            readStream.close();
            // get values from file and pass to function addWeibo for check
            for (int i = 0; i < weibosimport.size(); i++) {
                double id = weibosimport.get(i).getId();
                String idstr = weibosimport.get(i).getIdstr();
                String created_at = weibosimport.get(i).getCreated_at();
                String text = weibosimport.get(i).getText();
                String device_source = weibosimport.get(i).getDevice_source();
                double User_id = weibosimport.get(i).getUser_id();
                String User_idstr = weibosimport.get(i).getUser_idstr();
                // pass values to next function
                this.addWeibo(id,idstr,created_at,text,device_source,User_id,User_idstr);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private  void readWeiboUsersFromFile(String filename){
        try {
            // read file to import Array
            FileInputStream readData = new FileInputStream(filename);
            ObjectInputStream readStream = new ObjectInputStream(readData);
            ArrayList<weibo_user> wuserimport = (ArrayList<weibo_user>) readStream.readObject();
            readStream.close();
            //get values from file and pass to function addWeiboUser for check
            for (int i = 0; i < wuserimport.size(); i++) {
                double userID = wuserimport.get(i).getUserID();
                String userIDstr = wuserimport.get(i).getUserIDstr();
                String name = wuserimport.get(i).getName();
                String location = wuserimport.get(i).getLocation();
                String description = wuserimport.get(i).getDescription();
                String gender = wuserimport.get(i).getGender();
                double follower = wuserimport.get(i).getFollower();
                double friends = wuserimport.get(i).getFriends();
                double weibos = wuserimport.get(i).getWeibos();
                String created_at = wuserimport.get(i).getCreated_at();
                // pass values to next function
                this.addWeiboUser(userID,userIDstr,name,location,description,gender,follower,friends,weibos,created_at);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void collectWeibosviaAPItimeline(){
        try {
            String url = "https://api.weibo.com/2/statuses/friends_timeline.json?access_token="+access_token;
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            int responseCode = con.getResponseCode();
            System.out.println("\nSending 'Get' request to URL : " + url);
            System.out.println("Response Code : " + responseCode);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            //System.out.println(response.toString());
            JSONObject weibojson = new JSONObject(response.toString());
            JSONArray jweibo = weibojson.getJSONArray("statuses");
            int length = jweibo.length();
            for (int i = 0; i < length; i++) {
                JSONObject objweibox = jweibo.getJSONObject(i);
                Double weiboID = objweibox.getDouble("id");
                String weiboIDstr = objweibox.getString("idstr");
                String weibocreated_at = objweibox.getString("created_at");
                String weiboText = objweibox.getString("text");
                String weiboSource =objweibox.getString("source");
                //Get Weibo Userdata and create Weibo User
                JSONObject weiboUser = objweibox.getJSONObject("user");
                Double UserID = weiboUser.getDouble("id");
                String UserIDstr = weiboUser.getString("idstr");
                String UserName = weiboUser.getString("name");
                String UserLocation = "Province: " + weiboUser.getString("province") + " City: " + weiboUser.getString("city") + " Location: " + weiboUser.getString("location");
                String description = weiboUser.getString("description");
                String gender = weiboUser.getString("gender");
                double follower = weiboUser.getDouble("followers_count");
                double friends = weiboUser.getDouble("friends_count");
                double statuses = weiboUser.getDouble("statuses_count");
                String UserCreated_at = weiboUser.getString("created_at");
                // add User and Weibo to Lists
                this.addWeiboUser(UserID,UserIDstr,UserName,UserLocation,description,gender,follower,friends,statuses,UserCreated_at);
                this.addWeibo(weiboID,weiboIDstr,weibocreated_at,weiboText,weiboSource,UserID,UserIDstr);
            }
        } catch (Exception e){
            System.out.println(e);
        }
    }

    public void manualWeiboadd(String wIDstr) {
        System.out.print("get Weibo with the IDstr" + wIDstr);
        int responseCode = 0;
        try {
            String url = "https://api.weibo.com/2/comments/show.json?access_token=" + access_token + "&id=" + wIDstr;
            String urls = url;
            URL obj = new URL(urls);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            responseCode = con.getResponseCode();
            System.out.println("\nSending 'Get' request to URL : " + urls);
            System.out.println("Response Code : " + responseCode);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            //System.out.println(response.toString());
            JSONObject weibojson = new JSONObject(response.toString());

            JSONObject oldweibojson = weibojson.getJSONObject("status");

            int length = oldweibojson.length();
            for (int i = 0; i < length; i++) {

                Double weiboID = oldweibojson.getDouble("id");
                String weiboIDstr = oldweibojson.getString("idstr");
                String weibocreated_at = oldweibojson.getString("created_at");
                String weiboText = oldweibojson.getString("text");
                String weiboSource = oldweibojson.getString("source");
                //Get Weibo Userdata and create Weibo User
                JSONObject weiboUser = oldweibojson.getJSONObject("user");
                Double UserID = weiboUser.getDouble("id");
                String UserIDstr = weiboUser.getString("idstr");
                String UserName = weiboUser.getString("name");
                String UserLocation = "Province: " + weiboUser.getString("province") + " City: " + weiboUser.getString("city") + " Location: " + weiboUser.getString("location");
                String description = weiboUser.getString("description");
                String gender = weiboUser.getString("gender");
                double follower = weiboUser.getDouble("followers_count");
                double friends = weiboUser.getDouble("friends_count");
                double statuses = weiboUser.getDouble("statuses_count");
                String UserCreated_at = weiboUser.getString("created_at");
                // add User and Weibo to Lists
                this.addWeiboUser(UserID, UserIDstr, UserName, UserLocation, description, gender, follower, friends, statuses, UserCreated_at);
                this.addWeibo(weiboID, weiboIDstr, weibocreated_at, weiboText, weiboSource, UserID, UserIDstr);
                }
        } catch (Exception e) {
            System.out.println(e);
        }

    }


    public void collectComments(){
        //check if comments are already collected
        double[] weiboIDcollected;
        weiboIDcollected = new double[comments.size()];
        for (int i = 0; i < comments.size(); i++) {
            weiboIDcollected[i] = comments.get(i).getWeiboid();
        }
        //get a arraylist of all weibo IDs
        ArrayList<Double> weiboIDs = new ArrayList<Double>();
        ArrayList<String> weiboIDs_str = new ArrayList<String>();
        for (int i = 0; i < weibos.size(); i++) {
            double toFind = weibos.get(i).getId();
            boolean isCollected = DoubleStream.of(weiboIDcollected).anyMatch(n-> n == toFind);
            //isCollected==false
            if (isCollected==false){
                weiboIDs.add(weibos.get(i).getId());
                weiboIDs_str.add(weibos.get(i).getIdstr());
            }
        }


        //call API comments
        Random random = new Random();
        int responseCode = 200;
        for (int i = 0 ; i < weiboIDs.size()  & responseCode!=403 ; i++) {
            int n = 0;
            double WeiboID = weiboIDs.get(i);
            String WeiboIDstr = weiboIDs_str.get(i);
            System.out.print("java will wait "+ n + " seconds\n");
            try {
                TimeUnit.SECONDS.sleep(n);
                responseCode = this.callAPIcomments(WeiboID,WeiboIDstr);
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.out.print("Stopped with the WeiboID "+ WeiboIDstr);
            }
        }
    }

    private int callAPIcomments(double WeiboID,String WeiboIDstr){
        System.out.print("get comments of Weibo with the ID " + WeiboID +" IDstr "+ WeiboIDstr+"\n");
        Translate Translate = new Translate();
        int responseCode = 0;
        try {
            String url = "https://api.weibo.com/2/comments/show.json?access_token="+access_token+"&id="+WeiboIDstr;
            String urls = url;
            URL obj = new URL(urls);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            responseCode = con.getResponseCode();
            System.out.println("\nSending 'Get' request to URL : " + urls);
            System.out.println("Response Code : " + responseCode);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            //System.out.println(response.toString());
            JSONObject weibojson = new JSONObject(response.toString());
            JSONArray jweibo = weibojson.getJSONArray("comments");
            int length = jweibo.length();
            for (int i = 0; i < length; i++){
                JSONObject objweibox = jweibo.getJSONObject(i);
                Double commentID = objweibox.getDouble("id");
                String commentIDstr = objweibox.getString("idstr");
                String created_at = objweibox.getString("created_at");
                String commentText = objweibox.getString("text");
                String commentenText = Translate.translateText(commentText);
                //Get Weibo Userdata and create Weibo User from comment
                JSONObject weiboUser = objweibox.getJSONObject("user");
                Double user_id = weiboUser.getDouble("id");
                String user_idstr = weiboUser.getString("idstr");
                String UserName = weiboUser.getString("name");
                String UserLocation = "Province: " + weiboUser.getString("province") + " City: " + weiboUser.getString("city") + " Location: " + weiboUser.getString("location");
                String description = weiboUser.getString("description");
                String gender = weiboUser.getString("gender");
                double follower = weiboUser.getDouble("followers_count");
                double friends = weiboUser.getDouble("friends_count");
                double statuses = weiboUser.getDouble("statuses_count");
                String UserCreated_at = weiboUser.getString("created_at");
                //add comment and User to Lists
                this.addcomment(WeiboID,WeiboIDstr,commentID,commentIDstr,created_at,commentText,commentenText,user_id,user_idstr);
                this.addWeiboUser(user_id,user_idstr,UserName,UserLocation,description,gender,follower,friends,statuses,UserCreated_at);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.print("comment API not working. Maybe out of request");
        }
        return responseCode;
    }

    public void createCSVactor() {
        Translate translate = new Translate();
        List<String[]> actors = new LinkedList<String[]>();
        actors.add(new String[]{"id","uuid","name","enname","starttime","endtime","location","description","ENdescription","gender","follower","friends","weibos"});
        for (int i = 0; i < users.size(); i++) {
            String id = users.get(i).getUserIDstr();
            String uuid = users.get(i).getUserIDstr();
            String name = users.get(i).getName();
            String enname = translate.translateText(name);
            //use first comment datetime
            SimpleDateFormat DateFormat  = new SimpleDateFormat("EE MMM d HH:mm:ss Z yyyy",Locale.ENGLISH);

            ArrayList<Date> commentdates = new ArrayList<Date>();
            for (int j = 0; j < comments.size(); j++) {
                if (id.equals(comments.get(j).getUser_idstr())) {
                    String commentDatestr = comments.get(j).getCreated_at();
                    try {
                        Date commentDate = DateFormat.parse(commentDatestr);
                        commentdates.add(commentDate);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }
            Date oldestComment = new Date();
            for (int j = 0; j < commentdates.size(); j++) {
                if(commentdates.get(j).before(oldestComment)){
                    oldestComment = commentdates.get(j);
                }
            }
            String starttime = oldestComment.toString();
            String endtime = starttime;
            String location = users.get(i).getLocation();
            String description = users.get(i).getDescription();
            String endescription = translate.translateText(description);
            String gender = users.get(i).getGender();
            String follower = Double.toString(users.get(i).getFollower());
            String friends = Double.toString(users.get(i).getFriends());
            String weibos = Double.toString(users.get(i).getWeibos());
            actors.add(new String[]{id,uuid,name,enname,starttime,endtime,location,description,endescription,gender,follower,friends,weibos});
        }
        try {
            CSVWriter csvWriter = new CSVWriter(new FileWriter("actor.csv"));
            csvWriter.writeAll(actors);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("CSV actor ready");
    }

    public  void createCSVLinks(){
        List<String[]> links = new LinkedList<String[]>();
        links.add(new String[]{"uuid","name","source id","target id","starttime","endtime","text","entext"});
        for (int i = 0; i < comments.size() ; i++) {
            String uuid = comments.get(i).getCommentidstr();
            String name = comments.get(i).getUser_idstr();
            String sourceid = comments.get(i).getUser_idstr();
            double weiboid = comments.get(i).getWeiboid();
            String targetid = "null";
            for (int j = 0; j < weibos.size(); j++) {
                double weiboIDloop = weibos.get(j).getId();
                String Userid = weibos.get(j).getUser_idstr();
                if (weiboid==weiboIDloop){
                    targetid = Userid;
                    break;
                }
            }
            String starttime = comments.get(i).getCreated_at();
            String endtime = comments.get(i).getCreated_at();
            String text = comments.get(i).getText();
            String entext = comments.get(i).getEntext();
            links.add(new String[]{uuid,name,sourceid,targetid,starttime,endtime,text,entext});
        }
        Translate translate = new Translate();
        for (int i = 0; i < weibos.size(); i++) {
            String uuid = weibos.get(i).getIdstr();
            String name = weibos.get(i).getUser_idstr();
            String sourceid = weibos.get(i).getUser_idstr();
            String targetid = weibos.get(i).getUser_idstr();
            String starttime = weibos.get(i).getCreated_at();
            String endtime = weibos.get(i).getCreated_at();
            String text = weibos.get(i).getText();
            String entext = translate.translateText(text);
            links.add(new String[]{uuid,name,sourceid,targetid,starttime,endtime,text,entext});
        }
        try {
            CSVWriter csvWriter = new CSVWriter(new FileWriter("links.csv"));
            csvWriter.writeAll(links);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("CSV links ready");
    }

    public void saveWeibostoCSV(){
        List<String[]> weiboscsv = new LinkedList<String[]>();
        weiboscsv.add(new String[]{"id","idstr","created_at","text","device_source","user_id","user_idstr"});
        for (int i = 0; i < weibos.size(); i++) {
            String id = Double.toString(weibos.get(i).getId());
            String idstr = weibos.get(i).getIdstr();
            String created_at = weibos.get(i).getCreated_at();
            String text = weibos.get(i).getText();
            String device_source = weibos.get(i).getDevice_source();
            String user_id = Double.toString(weibos.get(i).getUser_id());
            String user_idstr = weibos.get(i).getUser_idstr();
            weiboscsv.add(new String[]{id,idstr,created_at,text,device_source,user_id,user_idstr});
            try {
                CSVWriter csvWriter = new CSVWriter(new FileWriter("weibo-IDs.csv"));
                csvWriter.writeAll(weiboscsv);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void printWeibosfromcomments() {
        for (int i = 0; i < comments.size(); i++) {
            String WeiboID = comments.get(i).getWeiboidstr();
            System.out.print(WeiboID+"\n");
        }
    }

    public void printWeiboIDs() {
        for (int i = 0; i < weibos.size(); i++) {
            System.out.print(weibos.get(i).getIdstr()+"\n");
        }
    }

    public void printComments() {
        for (int i = 0; i < comments.size(); i++) {
            System.out.println(i);
            System.out.println(comments.get(i).getEntext());

        }
    }

    public void fixnotranslation() {

        for (int i = 0; i < comments.size(); i++) {
            double weiboid = comments.get(i).getWeiboid();
            String weiboidstr = comments.get(i).getWeiboidstr();
            double commentid = comments.get(i).getCommentid();
            String commentidstr = comments.get(i).getCommentidstr();
            String created_at = comments.get(i).getCreated_at();
            String text = comments.get(i).getText();
            String entext = comments.get(i).getEntext();
            double user_id = comments.get(i).getUser_id();
            String user_idstr = comments.get(i).getUser_idstr();


            if (entext.equals("not translated")==true){
                Translate translate = new Translate();
                entext = translate.translateText(text);
            }
            weiboComment com2 = new weiboComment(weiboid,weiboidstr,commentid,commentidstr,created_at,text,entext,user_id,user_idstr);
            comments2.add(com2);

        }
        comments.clear();
        comments.addAll(comments2);

        }
    }
