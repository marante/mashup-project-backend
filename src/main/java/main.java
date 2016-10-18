import com.google.gson.Gson;
import news.Feed;
import news.FeedReader;
import news.Regions;
import statistics.CrimeReader;

import java.util.LinkedList;

import static spark.Spark.*;

public class main {

    public static void main(String[] args) {
        Gson gson = new Gson();
        Regions regions = new Regions();
        FeedReader feedReader = new FeedReader();
        CrimeReader crimeReader = new CrimeReader();
        port(8080);
        // Get method for index page.
        get("/", (req, res) -> "Simon gillar Battlerite");

        // Get method for a specific region
        get("/feed/:region", (request, response) -> {
            String regionUrl = regions.fetchRegion(request.params(":region"));
            LinkedList<Feed> feedList = feedReader.getFeedElements(regionUrl);
            response.header("Content-Type", "application/json charset=UTF-8\",\"*/*;charset=UTF-8");
            response.body(gson.toJson(feedList));
            response.status(200);
            return response.body();
        });

        get("/crime/:region", (request, response) -> {
            //String region = crimeReader.getCrimeElements();
            response.header("Content-Type", "application/json charset=UTF-8\",\"*/*;charset=UTF-8");
            //response.body(region);
            response.status(200);
            return response.body();
        });
    }
}
