package URL_request;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.squareup.okhttp.*;

import java.io.IOException;

public class Translate {
    private static String subscriptionKey = System.getenv("TRANSLATOR_TEXT_SUBSCRIPTION_KEY");
    private static String endpoint = System.getenv("TRANSLATOR_TEXT_ENDPOINT");
    String url = endpoint + "/translate?api-version=3.0&to=en";


    // Instantiates the OkHttpClient.
    OkHttpClient client = new OkHttpClient();

    // This function performs a POST request.
    private String Post(String text) throws IOException {
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType,
                "[{\n\t\"Text\": \""+text+"\"\n}]");
        Request request = new Request.Builder()
                .url(url).post(body)
                .addHeader("Ocp-Apim-Subscription-Key", subscriptionKey)
                .addHeader("Content-type", "application/json").build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    // This function prettifies the json response.
    private static String prettify(String json_text) {
        JsonParser parser = new JsonParser();
        JsonElement json = parser.parse(json_text);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(json);
    }

    public String translateText(String chinesetext){
        String chText = chinesetext;
        String enText = "not translated";

        try {
            Translate translateRequest = new Translate();
            String response = translateRequest.Post(chText);
            enText = response;
            String[] output = enText.split("\"");
            if (output.length>9){
                enText = output[13];
            }else if (output.length<=9) {
                enText = "not translated";
            }
        } catch (Exception e) {
            System.out.println(e);
        }


        return enText;
    }





}
