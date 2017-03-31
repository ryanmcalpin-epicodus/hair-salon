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
    Client client = new Client("Billi", 12, 1);
    assertTrue(client instanceof Client);
  }

  @Test
  public void getters_returnsCorrectly_true() {
    Client client = new Client("Billi", 12, 1);
    assertEquals("Billi", client.getName());
    assertEquals(12, client.getAge());
    assertEquals(1, client.getStylistId());
  }

  @Test
  public void save_savesToDatabase_true() {
    Client client = new Client("Billi", 12, 1);
    client.save();
    assertTrue(Client.all().get(0).equals(client));
  }

  @Test
  public void find_returnsInstanceById_client2() {
    Client client1 = new Client("Billi", 12, 1);
    client1.save();
    Client client2 = new Client("Tommi", 20, 3);
    client2.save();
    assertEquals(client2, Client.find(client2.getId()));
  }

  @Test
  public void allPerStylist_returnsAllClientsByStylistId() {
    Client client1 = new Client("Billi", 12, 1);
    client1.save();
    Client client2 = new Client("Tommi", 20, 3);
    client2.save();
    assertTrue(Client.allPerStylist(1).contains(client1));
    assertFalse(Client.allPerStylist(1).contains(client2));
  }

}
