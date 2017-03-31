import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;

public class StylistTest {

  @Before
  public void setUp() {
    DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/hair_salon_test", null, null);
  }

  @After
  public void tearDown() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "DELETE FROM stylists *;";
      con.createQuery(sql).executeUpdate();
    }
  }

  @Test
  public void Stylist_instantiatesCorrectly_true() {
    Stylist sty = new Stylist("Ryan", "Monday, Friday");
    assertTrue(sty instanceof Stylist);
  }

  @Test
  public void getters_returnsCorrectly_true() {
    Stylist sty = new Stylist("Ryan", "Monday, Friday");
    assertEquals("Ryan", sty.getName());
    assertEquals("Monday, Friday", sty.getWorkDays());
  }

  @Test
  public void save_savesToDatabase_true() {
    Stylist sty = new Stylist("Ryan", "Monday, Friday");
    sty.save();
    assertTrue(Stylist.all().get(0).equals(sty));
  }

}
