import static spark.Spark.*;

public class main {
    public static void main(String[] args) {
        port(1337);
        get("/hello", (req, res) -> "Simon gillar Battlerite");
    }
}
