/**
 * Stores information about each game, including the name and quantity. Allows checking out games (removing one of them)
 * and adding new games.
 */
public class Game extends Server{
  private String name;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Game(String name, Integer quantity) {
    this.name = name;
    this.quantity = quantity;
  }

  public Integer getQuantity() {
    return quantity;
  }

  public void setQuantity(Integer quantity) {
    this.quantity = quantity;
  }

  private Integer quantity;

}
