import com.google.gson.Gson;
import news.Feed;
import news.FeedReader;
import news.Regions;
import statistics.Crime;
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
        get("/", (req, res) -> "Kemal gillar män");

        // Get method for a specific region
        get("/feed/:region", (request, response) -> {
            String regionUrl = regions.fetchRegion(request.params(":region"));
            LinkedList<Feed> feedList = feedReader.getFeedElements(regionUrl);
            response.header("Content-Type", "application/json charset=UTF-8\",\"*/*;charset=UTF-8");
            response.body(gson.toJson(feedList));
            response.status(200);
            return response.body();
        });

        // Get crimes for specific region
        get("/brott/:region", (request, response) -> {
            LinkedList<Crime> crimeList = crimeReader.getCrimeElements(request.params(":region"));
            response.header("Content-Type", "application/json charset=UTF-8\",\"*/*;charset=UTF-8");
            response.body(gson.toJson(crimeList));
            response.status(200);
            return response.body();
        });
    }
}