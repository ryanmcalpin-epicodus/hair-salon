import org.sql2o.*;
import java.util.List;

public class Stylist {
  private int id;
  private String name;
  private String workDays;

  public Stylist(String name, String workDays) {
    this.name = name;
    this.workDays = workDays;
  }

  public String getName() {
    return this.name;
  }

  public String getWorkDays() {
    return this.workDays;
  }

  public int getId() {
    return this.id;
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO stylists (name, work_days) VALUES (:name, :work_days)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("name", this.name)
        .addParameter("work_days", this.workDays)
        .executeUpdate()
        .getKey();
    }
  }

  public static List<Stylist> all() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM stylists";
      return con.createQuery(sql)
        .addColumnMapping("work_days", "workDays")
        .executeAndFetch(Stylist.class);
    }
  }

  @Override
  public boolean equals(Object otherStylist) {
    if (!(otherStylist instanceof Stylist)) {
      return false;
    } else {
      Stylist newStylist = (Stylist) otherStylist;
      return this.getName().equals(newStylist.getName()) && this.getId() == newStylist.getId();
    }
  }

  public static Stylist find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM stylists WHERE id = :id";
      Stylist sty = con.createQuery(sql)
        .addParameter("id", id)
        .addColumnMapping("work_days", "workDays")
        .executeAndFetchFirst(Stylist.class);
      return sty;
    }
  }

}
