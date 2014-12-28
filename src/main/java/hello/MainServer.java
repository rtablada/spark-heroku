package hello;

import static spark.Spark.*;

public class MainServer {
    public static void main(String[] args) {
        ProcessBuilder process = new ProcessBuilder();
        Integer port;
        if (process.environment().get("PORT") != null) {
            port = Integer.parseInt(process.environment().get("PORT"));
        } else {
            port = 8080;
        }
        setPort(port);

		get("/", (req, res) -> "Home page");
		get("/hello", (req, res) -> "Hello World");
    }
}
