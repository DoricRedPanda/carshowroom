package info.azatov.carshowroom.model.dao;

import info.azatov.carshowroom.model.entity.AutoModel;
import info.azatov.carshowroom.model.entity.Car;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestPropertySource(locations="classpath:application.properties")
class CarDAOTest {

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private AutoModelDAO autoModelDAO;

    @Autowired
    private CarDAO carDAO;

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
        autoModels.add(new AutoModel(null, "Maxima", "Nissan", 2011));
        autoModels.add(new AutoModel("Qashqai", "Nissan"));
        autoModels.add(new AutoModel("Rio", "Kia"));
        autoModels.add(new AutoModel("Optima", "Kia"));
        autoModels.add(new AutoModel("Forte", "Kia"));
        autoModelDAO.insertCollection(autoModels);

        Collection<Car> cars = new ArrayList<>();
        cars.add(new Car(
                null,
                autoModelDAO.getById(1L),
                "qwerty",
                300000.0,
                0.0,
                Date.valueOf("2015-03-31"),
                1995,
                103.0,
                203.0,
                4,
                "AT",
                "radio",
                "black",
                "high"
                ));
        cars.add(new Car(
                null,
                autoModelDAO.getById(2L),
                "qwerty",
                300000.0,
                0.0,
                Date.valueOf("2018-01-01"),
                1332,
                103.0,
                197.0,
                4,
                "AT",
                "radio, gps",
                "blue",
                "high"
        ));
        carDAO.insertCollection(cars);
    }

    @Test
    void search1() {
        List<Car> res = carDAO.search(
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                "Maxima",
                "Nissan",
                2011
        );
        assertEquals(1, res.size());
    }
    @Test
    void search2() {
        List<Car> res = carDAO.search(
			null,
			null,
			null,
			null,
			null,
			null,
			null,
			null,
			null,
			null,
			100000.0,
			400000.0,
                null,
                null,
			null
	);
        assertEquals(2, res.size());
    }
    @Test
    void search3() {
        List<Car> res = carDAO.search(
                "qwerty",
                Date.valueOf("2010-01-01"),
                100,
                100.0,
                150.0,
                4,
                "AT",
                "radio",
                "black",
                "high",
                null,
                null,
                null,
                null,
                null
        );
        assertEquals(1, res.size());
    }

}
