import api.FeedDAO;
import com.google.gson.Gson;
import news.Feed;
import statistics.Crime;

import java.util.LinkedList;

import static spark.Spark.*;

/**
 * Created by kemkoi on 10/25/16.
 */
public class Server {
    
    public static void main(String[] args) {
        Gson gson = new Gson();
        port(8080);
        FeedDAO feedDao = new FeedDAO();
        feedDao.start();

        // Get method for index page.
        get("/", (req, res) -> "Working");


        // Get method for a specific region
        get("/feed/:region", (request, response) -> {

            LinkedList<Feed> feedList = feedDao.getRegionFeed(request.params(":region"));
            response.header("Content-Type", "application/json charset=UTF-8\",\"*/*;charset=UTF-8");
            response.body(gson.toJson(feedList));
            response.status(200);
            return response.body();
        });

        // Get crimes for specific region
        get("/brott/:region", (request, response) -> {
            LinkedList<Crime> crimeList = feedDao.getRegionStatistics(request.params(":region"));
            response.header("Content-Type", "application/json charset=UTF-8\",\"*/*;charset=UTF-8");
            response.body(gson.toJson(crimeList));
            response.status(200);
            return response.body();
        });
    }
}
