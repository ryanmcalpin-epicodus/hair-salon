import org.sql2o.*;
import java.util.List;

public class Client {
  private int id;
  private String name;
  private int age;
  private int stylistId;

  public Client(String name, int age, int stylistId) {
    this.name = name;
    this.age = age;
    this.stylistId = stylistId;
  }

  public String getName() {
    return this.name;
  }

  public int getAge() {
    return this.age;
  }

  public int getStylistId() {
    return this.stylistId;
  }

  public int getId() {
    return this.id;
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO clients (name, age, stylist_id) VALUES (:name, :age, :stylist_id)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("name", this.name)
        .addParameter("age", this.age)
        .addParameter("stylist_id", this.stylistId)
        .executeUpdate()
        .getKey();
    }
  }

  public static List<Client> all() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM clients";
      return con.createQuery(sql)
        .addColumnMapping("stylist_id", "stylistId")
        .executeAndFetch(Client.class);
    }
  }

  @Override
  public boolean equals(Object otherClient) {
    if(!(otherClient instanceof Client)) {
      return false;
    } else {
      Client newClient = (Client) otherClient;
      return this.getName().equals(newClient.getName()) && this.getId() == newClient.getId();
    }
  }

  public static Client find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM clients WHERE id = :id";
      Client client = con.createQuery(sql)
        .addParameter("id", id)
        .addColumnMapping("stylist_id", "stylistId")
        .executeAndFetchFirst(Client.class);
      return client;
    }
  }

  public static List<Client> allPerStylist(int stylistId) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM clients WHERE stylist_id = :id";
      return con.createQuery(sql)
        .addParameter("id", stylistId)
        .addColumnMapping("stylist_id", "stylistId")
        .executeAndFetch(Client.class);
    }
  }

  public void updateStylist(int stylistId) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE clients SET stylist_id = :stylist_id WHERE id = :id";
      con.createQuery(sql)
        .addParameter("stylist_id", stylistId)
        .addParameter("id", this.id)
        .executeUpdate();
    }
  }

}
