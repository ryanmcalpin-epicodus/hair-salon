import org.sql2o.*;

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



}
