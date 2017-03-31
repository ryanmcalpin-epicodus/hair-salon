import org.sql2o.*;

public class Client {
  private int id;
  private String name;
  private int stylistId;

  public Client(String name, int stylistId) {
    this.name = name;
    this.stylistId = stylistId;
  }

  public String getName() {
    return this.name;
  }

  public int getStylistId() {
    return this.stylistId;
  }

}
