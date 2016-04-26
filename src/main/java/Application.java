/**
 * The main class. Serves as the actual application for the website.
 */

import spark.*;
import static spark.Spark.*;
import java.util.ArrayList;
import java.util.HashMap;


public class Application {

  public static void main(String[] args){
    ProcessBuilder process = new ProcessBuilder();
    Integer port;
    if (process.environment().get("PORT") != null) {
        port = Integer.parseInt(process.environment().get("PORT"));
    } else {
        port = 4567;
    }
    setPort(port);

    Spark.init();

    Spark.post(
      "/login",
      (request, response) -> {

        // TODO: substring limits the characters the user can enter for security (gave error before)
        User login = ReadWriteJson.readUserJson(request.body(), response);
        Session s = request.session();

        if (login != null) {
          s.attribute("userName", login.getName());
          User user = Server.users.get(login.getName());

          if(user == null){
            Server.users.put(login.getName(), new User(login.getName(), login.getPassword()));
            response.status(200);
          }

          else if (login.authenticate(login.getName(), login.getPassword())){
            response.status(200);
          }

          // authentication failed, wrong password
          else response.status(401);
        }

        else response.status(500);

        return "";
      }
    );

    Spark.post(
      "/logout",
      (request, response) -> {
        Session s = request.session();
        s.invalidate();
        response.status(200);
        return "";
      }
    );

    Spark.post(
      "/checkin-game",
      (request, response) -> {
        Server.isUserLoggedIn(request, response);
        Game game = ReadWriteJson.readOneGameFromJson(request.body(), response);

        Session s = request.session();
        String username = s.attribute("userName");

        if (game != null) {
            Server.checkoutGame(game.getName());
            Server.games.put(username, game.getName());
            Server.gamesList.put(game.getName(), Server.gamesList.get(game.getName()) + 1);
            ReadWriteJson.writeJson(response);
            response.status(201);
            return "{\"msg\": \"ok\"}";
        }

          response.status(500);
          return "";

      }
    );

    Spark.get(
      "/games-list",
      (request, response) -> {
        String json = ReadWriteJson.writeJson(response);

       if(json != null) { response.status(201); }
       else { response.status(500); }
        return "{\"msg\": \"ok\"}";
      }
    );

    Spark.post(
      "/add-game",
      (request, response) -> {
        Server.isUserLoggedIn(request, response);

        // need a list of games to be usable, ArrayList<Game>, name and quantity
        ArrayList<Game> games =  ReadWriteJson.readGameJson(request.body());

        if (games != null) {
          for (Game g : games)
              if (g.getClass().equals(Game.class)) {
                Server.addGame(g.getName(), g.getQuantity());
              }
          response.status(201);
        }

        else response.status(500);

        return "{\"msg\": \"ok\"}";
      }
    );

    Spark.post(
      "/checkout-game",
      (request, response) -> {
        Server.isUserLoggedIn(request, response);
        Game game = ReadWriteJson.readOneGameFromJson(request.body(), response);

        Session s = request.session();
        String username = s.attribute("userName");

        HashMap<String, Integer> gamesList = Server.getGamesList();

        if (game != null) {
          Boolean isGameAvailable = !(gamesList.get(game.getName()) == 0);
          if(isGameAvailable) {
            Server.checkoutGame(game.getName());
            Server.games.put(username, game.getName());
            Server.gamesList.put(game.getName(), gamesList.get(game.getName()) - 1);
            ReadWriteJson.writeJson(response);
            response.status(201);
            return "{\"msg\": \"ok\"}";
          }

          else {
            response.status(403);
            return "";
          }
        }

        response.status(500);
        return "";
      }
    );

    // Sends user to 404 page if a page is not found
    Spark.get(
      "*",
      (request, response) -> {
        response.status(404);
        return "";
      }
    );
  }
}
