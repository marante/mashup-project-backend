import api.FeedDAO;
import com.google.gson.Gson;
import news.Feed;
import statistics.Crime;

import java.util.LinkedList;

import static spark.Spark.*;

/**
 * Class som håller reda på alla GET metoder som finns i servern och som kan kallas på.
 * Denna classen startar även tråden för FeedDAO vilket gör att den börjar hämta information från
 * APIerna direkt.
 */
public class Server {

    public static void main(String[] args) {
        Gson gson = new Gson();
        port(8080);
        FeedDAO feedDao = new FeedDAO();
        feedDao.start();

        // Get metod för att se att servern fungerar.
        get("/", (req, res) -> "Working");


        // Get metod för att få tillbaka alla brott i en specifik region eller hela Sverige.
        //Hela Sverige = 'hela-sverige'
        get("/feed/:region", (request, response) -> {

            LinkedList<Feed> feedList = feedDao.getRegionFeed(request.params(":region"));
            response.header("Content-Type", "application/json charset=UTF-8\",\"*/*;charset=UTF-8");
            response.body(gson.toJson(feedList));
            response.status(200);
            return response.body();
        });

        // Get metod för att få tillbaka statistik för en specifik region eller hela Sverige.
        //Hela Sverige = 'hela-sverige'
        get("/brott/:region", (request, response) -> {
            LinkedList<Crime> crimeList = feedDao.getRegionStatistics(request.params(":region"));
            response.header("Content-Type", "application/json charset=UTF-8\",\"*/*;charset=UTF-8");
            response.body(gson.toJson(crimeList));
            response.status(200);
            return response.body();
        });
    }
}
