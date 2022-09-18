package info.azatov.carshowroom.model.dao;

import info.azatov.carshowroom.model.dao.ModelDAO;
import info.azatov.carshowroom.model.entity.Model;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestPropertySource(locations="classpath:application.properties")
public class ModelDAOTest {
    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private ModelDAO modelDAO;

    @Test
    void getModelsByName() {
        List<Model> models = modelDAO.getModelsByName("Rio");
        assertEquals(1, models.size());
        assertEquals("Rio", models.get(0).getName());
        assertEquals("Kia", models.get(0).getMake());
    }

    @Test
    void getModelsByMake() {
        List<Model> models = modelDAO.getModelsByMake("Kia");
        assertEquals(3, models.size());
        assertEquals("Kia", models.get(0).getMake());
    }

    @BeforeEach
    void setUp() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.createQuery("DELETE FROM Contract").executeUpdate();
        session.createQuery("DELETE FROM Car").executeUpdate();
        session.createQuery("DELETE FROM Model").executeUpdate();
        session.createQuery("DELETE FROM Client").executeUpdate();
        session.createSQLQuery("ALTER SEQUENCE CONTRACT_SEQ RESTART WITH 1;").executeUpdate();
        session.createSQLQuery("ALTER SEQUENCE CLIENT_SEQ RESTART WITH 1;").executeUpdate();
        session.createSQLQuery("ALTER SEQUENCE MODEL_SEQ RESTART WITH 1;").executeUpdate();
        session.createSQLQuery("ALTER SEQUENCE CAR_SEQ RESTART WITH 1;").executeUpdate();
        session.getTransaction().commit();

        Collection<Model> initialModels = new ArrayList<>();
        initialModels.add(new Model("Maxima", "Nissan"));
        initialModels.add(new Model("Qashqai", "Nissan"));
        initialModels.add(new Model("Rio", "Kia"));
        initialModels.add(new Model("Optima", "Kia"));
        initialModels.add(new Model("Forte", "Kia"));
        modelDAO.saveCollection(initialModels);
    }


}
