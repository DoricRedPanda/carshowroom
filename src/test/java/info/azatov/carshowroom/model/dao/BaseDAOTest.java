package info.azatov.carshowroom.model.dao;

import info.azatov.carshowroom.model.entity.Client;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestPropertySource(locations="classpath:application.properties")
class BaseDAOTest {
    @Autowired
    private SessionFactory sessionFactory;
    @Autowired
    private ClientDAO clientDAO;

    @BeforeEach
    void setUp() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.createQuery("DELETE FROM Contract").executeUpdate();
        session.createQuery("DELETE FROM Car").executeUpdate();
        session.createQuery("DELETE FROM AutoModel").executeUpdate();
        session.createQuery("DELETE FROM Client").executeUpdate();
        session.createSQLQuery("ALTER SEQUENCE CONTRACT_SEQ RESTART WITH 1;").executeUpdate();
        session.createSQLQuery("ALTER SEQUENCE CLIENT_SEQ RESTART WITH 1;").executeUpdate();
        session.createSQLQuery("ALTER SEQUENCE MODEL_SEQ RESTART WITH 1;").executeUpdate();
        session.createSQLQuery("ALTER SEQUENCE CAR_SEQ RESTART WITH 1;").executeUpdate();
        session.getTransaction().commit();

        Collection<Client> initialModels = new ArrayList<>();
        initialModels.add(new Client(null,"Luke Smith", "memes", null, "luke@lukesmith.xyz"));
        initialModels.add(new Client(null,"John Smith", "USA", "+77777777777", null));
        initialModels.add(new Client(null,"Richard Stallman", "USA", null, null));
        initialModels.add(new Client(null,"Linus Torvalds", "USA", null, null));
        initialModels.add(new Client(null,"Andrey Stolyarov", "Russia, Moscow", null, null));
        initialModels.add(new Client(null,"Andrey Stolyarov", "Russia", null, null));
        clientDAO.insertCollection(initialModels);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getAll() {
        assertEquals(6, clientDAO.getAll().size());
    }

    @Test
    void save() {
        clientDAO.insert(new Client(1234L, "TEST", "test space", "+88888", "test@test.test"));
        assertEquals(7, clientDAO.getAll().size());
        assertEquals("TEST", clientDAO.getSingleClientByName("TEST").getName());
        assertEquals("test space", clientDAO.getSingleClientByName("TEST").getAddress());
        assertEquals(7L, clientDAO.getSingleClientByName("TEST").getId());
        assertEquals("+88888", clientDAO.getSingleClientByName("TEST").getPhone());

    }

    @Test
    void delete() {
        clientDAO.delete(clientDAO.getById(1L));
        assertEquals(5, clientDAO.getAll().size());

    }

    @Test
    void deleteById() {
        clientDAO.deleteById(1L);
        assertEquals(5, clientDAO.getAll().size());
    }

    @Test
    void update() {
        Client client = clientDAO.getById(1L);
        client.setEmail("test@test.test");
        clientDAO.update(client);
        assertEquals("test@test.test", clientDAO.getById(1L).getEmail());
    }
}