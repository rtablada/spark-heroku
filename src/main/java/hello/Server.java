import spark.Request;
import spark.Response;
import spark.Session;

import java.util.HashMap;

/**
 * The server holds the information about the list of games that a user can check out and their availability.
 */
public class Server {

  static HashMap<String, User> users = new HashMap<>();
  static HashMap<String, Integer> gamesList = new HashMap<>();
  static HashMap<String, String> games = new HashMap<>();

  static Boolean isUserLoggedIn(Request request, Response response) {
    Session s = request.session();
    String username = s.attribute("userName");
    User user = users.get(username);

    if (user == null) {
      response.status(401);
      return false;
    }

    return true;
  }

  static void addGame(String name, Integer quantity){
    gamesList.put(name, quantity);
  }

  static HashMap<String, Integer> getGamesList() {
    return gamesList;
  }

  static void checkoutGame(String name){
    gamesList.put(name, gamesList.get(name) - 1);
  }
}
