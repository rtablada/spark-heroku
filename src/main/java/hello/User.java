
import java.util.HashMap;

/**
 * Stores passwords, usernames, and the ID of each user.
 */
public class User {
  public static final Integer MAX_LENGTH = 15;
  private String name;
  private String password;

  public String getName() {
    return name;
  }

  private HashMap<String, String> passwords = new HashMap<>();

  public String getPassword() {
    return password;
  }

  public User(String name, String password) {
    this.name = name;
    this.password = password;
    passwords.put(this.name, this.password);

  }

  final Boolean authenticate(String name, String password) {
    return password.equals(passwords.get(name));
  }

}

