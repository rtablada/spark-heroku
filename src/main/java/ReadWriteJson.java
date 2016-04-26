import jodd.json.JsonException;
import jodd.json.JsonParser;
import jodd.json.JsonSerializer;
import spark.*;

import java.io.*;
import java.util.*;

/**
 * Reads information from a JSON, and writes
 */
public class ReadWriteJson {

  public static String writeJson(Response response) throws IOException {
    JsonSerializer js = new JsonSerializer();
    try {
      HashMap<String, Integer> map = Server.getGamesList();
      return js.include("*").serialize(map);
    } catch (NullPointerException e) {
      response.status(500);
      return null;
    }
  }

  static Game readOneGameFromJson(String body, Response response){
    JsonParser parser = new JsonParser();

    try{
      HashMap<String, String> map = parser.parse(body);
      Game game = new Game(map.get("name"), Integer.parseInt(map.get("qty")));
      response.status(201);
      return game;
    }catch(JsonException e){
      response.status(500);
      return null;
    }
  }

  public static ArrayList<Game> readGameJson(String body) throws FileNotFoundException {
    JsonParser parser = new JsonParser();
    ArrayList<Game> games = new ArrayList<>();

    try {
      HashMap<String, ArrayList<HashMap<String, String>>> map = parser.parse(body);
      ArrayList<HashMap<String, String>> list = map.get("games");

      for (HashMap<String, String> h : list) {
        Game game = new Game(h.get("name"), Integer.parseInt(h.get("qty")));
        games.add(game);
      }

      return games;
    } catch (JsonException e) {
      return null;
    }
  }

  public static User readUserJson(String body, Response response){
    JsonParser parser = new JsonParser();
    try{
      HashMap<String, String> map = parser.parse(body);
      User user = new User(map.get("name"), map.get("password"));
      response.status(200);
      return user;
    }catch(JsonException e){
      System.out.println("json messed up");
      response.status(500);
      return null;
    }
  }
}
