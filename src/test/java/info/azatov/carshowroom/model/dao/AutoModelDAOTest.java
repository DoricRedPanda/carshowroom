package info.azatov.carshowroom.model.dao;

import info.azatov.carshowroom.model.entity.AutoModel;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestPropertySource(locations="classpath:application.properties")
public class AutoModelDAOTest {
    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private AutoModelDAO autoModelDAO;

    @Test
    void getModelsByName() {
        List<AutoModel> autoModels = autoModelDAO.getModelsByName("Rio");
        assertEquals(1, autoModels.size());
        assertEquals("Rio", autoModels.get(0).getName());
        assertEquals("Kia", autoModels.get(0).getMake());
    }

    @Test
    void getModelsByMake() {
        List<AutoModel> autoModels = autoModelDAO.getModelsByMake("Kia");
        assertEquals(3, autoModels.size());
        assertEquals("Kia", autoModels.get(0).getMake());
    }

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

        Collection<AutoModel> autoModels = new ArrayList<>();
        autoModels.add(new AutoModel("Maxima", "Nissan"));
        autoModels.add(new AutoModel("Qashqai", "Nissan"));
        autoModels.add(new AutoModel("Rio", "Kia"));
        autoModels.add(new AutoModel("Optima", "Kia"));
        autoModels.add(new AutoModel("Forte", "Kia"));
        autoModelDAO.insertCollection(autoModels);
    }


}
