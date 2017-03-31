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
    Stylist sty = new Stylist("Ryan", "Monday Friday");
    assertTrue(sty instanceof Stylist);
  }

  @Test
  public void getters_returnsCorrectly_true() {
    Stylist sty = new Stylist("Ryan", "Monday Friday");
    assertEquals("Ryan", sty.getName());
    assertEquals("Monday Friday", sty.getWorkDays());
  }

  @Test
  public void save_savesToDatabase_true() {
    Stylist sty = new Stylist("Ryan", "Monday Friday");
    sty.save();
    assertTrue(Stylist.all().get(0).equals(sty));
  }

  @Test
  public void find_returnsInstanceById_sty2() {
    Stylist sty1 = new Stylist("Ryan", "Monday Friday");
    sty1.save();
    Stylist sty2 = new Stylist("Roberta", "Tuesday Wednesday Thursday");
    sty2.save();
    assertEquals(sty2, Stylist.find(sty2.getId()));
  }

  @Test
  public void updateShifts_changesWorkDays_true() {
    Stylist sty = new Stylist("Ryan", "Monday Friday");
    sty.save();
    sty.updateShifts("Tuesday");
    assertEquals("Tuesday", Stylist.find(sty.getId()).getWorkDays());
  }

  @Test
  public void removeStylist_removesStylist_0() {
    Stylist stylist = new Stylist("Ryan", "Monday Friday");
    stylist.save();
    assertTrue(Stylist.all().get(0).equals(stylist));
    stylist.removeStylist();
    assertEquals(0, Stylist.all().size());
  }
}
