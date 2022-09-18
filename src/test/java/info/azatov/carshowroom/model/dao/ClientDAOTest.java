package info.azatov.carshowroom.model.dao;

import info.azatov.carshowroom.model.entity.Car;
import info.azatov.carshowroom.model.entity.Client;
import info.azatov.carshowroom.model.entity.Contract;
import info.azatov.carshowroom.model.entity.Model;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import javax.persistence.Query;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestPropertySource(locations="classpath:application.properties")
class ClientDAOTest {
    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private ClientDAO clientDAO;

    @Autowired
    private ModelDAO modelDAO;

    @Autowired
    private ContractDAO contractDAO;

    @Autowired
    private CarDAO carDAO;

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

        Collection<Client> clients = new ArrayList<>();
        clients.add(new Client(null,"Luke Smith", "memes", null, "luke@lukesmith.xyz"));
        clients.add(new Client(null,"John Smith", "USA", "+77777777777", null));
        clients.add(new Client(null,"Richard Stallman", "USA", null, null));
        clients.add(new Client(null,"Linus Torvalds", "USA", null, null));
        clients.add(new Client(null,"Andrey Stolyarov", "Russia, Moscow", null, null));
        clients.add(new Client(null,"Andrey Stolyarov", "Russia", null, null));
        clientDAO.saveCollection(clients);

        Collection<Model> models = new ArrayList<>();
        models.add(new Model("Maxima", "Nissan"));
        models.add(new Model("Qashqai", "Nissan"));
        models.add(new Model("Rio", "Kia"));
        models.add(new Model("Optima", "Kia"));
        models.add(new Model("Forte", "Kia"));
        modelDAO.saveCollection(models);

        Collection<Car> cars = new ArrayList<>();
        cars.add(new Car(
                null,
                modelDAO.getById(1L),
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
                modelDAO.getById(2L),
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
        carDAO.saveCollection(cars);

        Collection<Contract> contracts = new ArrayList<>();
        contracts.add(new Contract(
                clientDAO.getById(1L),
                carDAO.getById(1L),
                Date.valueOf("2022-01-01"),
                true,
                Contract.ContractStatus.WAITING4DELIVERY
        ));
        contracts.add(new Contract(
                clientDAO.getById(2L),
                carDAO.getById(1L),
                Date.valueOf("2022-01-02"),
                true,
                Contract.ContractStatus.PROCESSING
        ));
        contracts.add(new Contract(
                clientDAO.getById(3L),
                carDAO.getById(2L),
                Date.valueOf("2021-01-01"),
                true,
                Contract.ContractStatus.WAITING4DELIVERY
        ));
        contracts.add(new Contract(
                clientDAO.getById(1L),
                carDAO.getById(2L),
                Date.valueOf("2021-01-01"),
                true,
                Contract.ContractStatus.DONE
        ));
        contracts.add(new Contract(
                clientDAO.getById(4L),
                carDAO.getById(2L),
                Date.valueOf("2021-01-01"),
                true,
                Contract.ContractStatus.DONE
        ));
        contractDAO.saveCollection(contracts);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getAllClientByName() {
        List<Client> res = clientDAO.getAllClientByName("Andrey");
        assertEquals(2, res.size());
        assertEquals(5, res.get(0).getId());
    }

    @Test
    void getSingleClientByName() {
        Client res = clientDAO.getSingleClientByName("Andrey");
        assertNull(res);
    }

    @Test
    void getClientByContract() {
        List<Client> res = clientDAO.getClientByContract(Contract.ContractStatus.DONE, null, null, true);
        assertEquals(2, res.size());
    }

    @Test
    void getClientByContract2() {
        List<Client> res = clientDAO.getClientByContract(null, Date.valueOf("2021-01-01"), Date.valueOf("2021-02-02"), null);
        assertEquals(3, res.size());
    }
}