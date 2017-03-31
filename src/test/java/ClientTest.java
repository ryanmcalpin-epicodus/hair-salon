import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;

public class ClientTest {

  @Before
  public void setUp() {
    DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/hair_salon_test", null, null);
  }

  @After
  public void tearDown() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "DELETE FROM clients *;";
      con.createQuery(sql).executeUpdate();
    }
  }

  @Test
  public void Client_instantiatesCorrectly_true() {
    Client client = new Client("Billi", 1);
    assertTrue(client instanceof Client);
  }

  @Test
  public void getters_returnsCorrectly_true() {
    Client client = new Client("Billi", 1);
    assertEquals("Billi", client.getName());
    assertEquals(1, client.getStylistId());
  }

  @Test
  public void save_savesToDatabase_true() {
    Client client = new Client("Billi", 1);
    client.save();
    assertTrue(Client.all().get(0).equals(client));
  }

}
