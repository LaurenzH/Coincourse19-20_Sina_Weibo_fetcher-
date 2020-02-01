package URL_request;

public class URL_Request {

    public static void main(String[] args){
        /*
        three API Variables are needed in order to run the code
        access_token
        TRANSLATOR_TEXT_SUBSCRIPTION_KEY
        TRANSLATOR_TEXT_ENDPOINT
        saved as environment variables
        see https://gwu-libraries.github.io/sfm-ui/posts/2016-04-26-weibo-api-guide
        for sina Weibo access_token
        see https://docs.microsoft.com/de-de/azure/cognitive-services/translator/quickstart-translate?pivots=programming-language-java#set-up
        for TRANSLATOR_TEXT_SUBSCRIPTION_KEY and TRANSLATOR_TEXT_ENDPOINT
        */
        dataCollector dataCollector = new dataCollector();


        //function which loads the old weibos, comments and users into the program
        dataCollector.loadWeibosandUsersCommentsIntoArraylist();


        /*function, which calls the Sina Weibo API timeline.
        The API return the latest Weibos of the connected developer account as JSON.
        The number of Weibos is limited to 50.
        Repeated calls in a short time will return the same result.
        The weibos and user from the JSON are saved to the arraylists weibos and weibo_users
        The connected developer account needs to follow users in order to use the API timeline.
         */
        dataCollector.collectWeibosviaAPItimeline();

        /*function, which calls the Sina Weivo API comment in order to add a Weibo the arraylist weibos
        Needs a weibo-ID as String and the Weibo needs to be visible to the developer account.
        Developed to overcome the limits of the API timeline and to be able to collect interesting weibos.
        Can be used to collect older weibos which will not be collected via timeline call
        */
        //dataCollector.manualWeiboadd("4439027363357246");


        /*function, which calls the Sina Weibo API comments and processes the received JSON.
        Calls API for all Weibos-IDs were no comments were collected.
        The API call returns up to 50 comments for the called Weibo-ID.
        Unfortunately limited to 50 comments for no paying developers.
        The connected developer account needs to be able to see the comments.
        Which means the account needs to follow the weibo author or the comment writer.
        Calls the azure API translate in order to translate Chinese to English.
        Comments and user which are collected will be saved to the arraylists comments and weibo_users.
        */
        dataCollector.collectComments();


        /*function, which can be called to fix no translations.
        Happens when the free amount of 2 million characters are reached.
         */
        //dataCollector.fixnotranslation();


        /*function, which creates the CSV files for Condor.
        The entries of the arraylists weibos, users and comments are saved to the actor.csv and links.csv.
        Both CSV files are Condor ready.
        function calls the translation functionality in order to translate names and descriptions.
        Unfortunately this makes the function slow for big datasets.
         */
        dataCollector.createCSVactor();
        dataCollector.createCSVLinks();


        /*
        function, which saves the arraylists as objects to the disk.
        It is very important that the function is not interrupted otherwise the files can be damaged and later no be reloaded
         */
        dataCollector.saveWeibos_Users_commentstoFile();

    }
}
