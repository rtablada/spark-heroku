package hello;

import static spark.Spark.*;
import spark.ModelAndView;
import java.util.*;
import spark.template.mustache.*;

public class MainServer {
    public static void main(String[] args) {

		// Get port config of heroku on environment variable
        ProcessBuilder process = new ProcessBuilder();
        Integer port;
        if (process.environment().get("PORT") != null) {
            port = Integer.parseInt(process.environment().get("PORT"));
        } else {
            port = 8080;
        }
        setPort(port);

		get("/", (req, res) -> {

			Map map = new HashMap();
			map.put("name", "Sam");
            // The hello.ftl file is located in directory:
            // src/test/resources/spark/template/freemarker
            return new ModelAndView(map, "hello.mustache");	
		}, new MustacheTemplateEngine());
		get("/hello", (req, res) -> "Hello World");
    }
}
