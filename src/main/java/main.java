import spark.Request;

import java.util.Arrays;
import java.util.List;

import static spark.Spark.*;

public class main {


    public static void main(String[] args) {
        port(8080);
        get("/", (req, res) -> "Simon gillar Battlerite");

    }

    private static String preferredResponseType(Request request) {
        // Ibland skickar en klient en lista av format som den önskar.
        // Här splittar vi upp listan och tar bort eventuella mellanslag.
        List<String> types = Arrays.asList(request.headers("Accept").split("\\s*,\\s*"));

        // Gå igenom listan av format och skicka tillbaka det första som vi stöder
        for (String type: types) {
            switch (type) {
                case "application/json":
                case "application/xml":
                case "text/html":
                    return type;
                default:
            }
        }

        // Om vi inte stöder något av formaten, skicka tillbaka det första formatet
        return types.get(0);
    }
}
