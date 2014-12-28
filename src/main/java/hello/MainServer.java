package hello;

import static spark.Spark.*;

public class MainServer {
    public static void main(String[] args) {
        get("/hello", (req, res) -> "Hello World");
    }
}
