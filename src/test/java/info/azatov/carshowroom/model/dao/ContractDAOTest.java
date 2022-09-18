package info.azatov.carshowroom.model.dao;

import info.azatov.carshowroom.model.entity.AutoModel;
import info.azatov.carshowroom.model.entity.Car;
import info.azatov.carshowroom.model.entity.Client;
import info.azatov.carshowroom.model.entity.Contract;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterEach;
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
class ContractDAOTest {
    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private ClientDAO clientDAO;

    @Autowired
    private AutoModelDAO autoModelDAO;

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
        session.createQuery("DELETE FROM AutoModel").executeUpdate();
        session.createQuery("DELETE FROM Client").executeUpdate();
        session.createSQLQuery("ALTER SEQUENCE CONTRACT_SEQ RESTART WITH 1;").executeUpdate();
        session.createSQLQuery("ALTER SEQUENCE CLIENT_SEQ RESTART WITH 1;").executeUpdate();
        session.createSQLQuery("ALTER SEQUENCE MODEL_SEQ RESTART WITH 1;").executeUpdate();
        session.createSQLQuery("ALTER SEQUENCE CAR_SEQ RESTART WITH 1;").executeUpdate();
        session.getTransaction().commit();

        Collection<Client> clients = new ArrayList<>();
        clients.add(new Client(null,"Luke Smith", "memes", null, "luke@lukesmith.xyz"));
        clients.add(new Client(null,"Tim Minchin", "Australia", null, null));
        clients.add(new Client(null,"John Smith", "USA", "+77777777777", null));
        clients.add(new Client(null,"Richard Stallman", "USA", null, null));
        clients.add(new Client(null,"Linus Torvalds", "USA", null, null));
        clients.add(new Client(null,"Andrey Stolyarov", "Russia, Moscow", null, null));
        clients.add(new Client(null,"Andrey Stolyarov", "Russia", null, null));
        clientDAO.saveCollection(clients);

        Collection<AutoModel> autoModels = new ArrayList<>();
        autoModels.add(new AutoModel("Maxima", "Nissan"));
        autoModels.add(new AutoModel("Qashqai", "Nissan"));
        autoModels.add(new AutoModel("Rio", "Kia"));
        autoModels.add(new AutoModel("Optima", "Kia"));
        autoModels.add(new AutoModel("Forte", "Kia"));
        autoModelDAO.saveCollection(autoModels);

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
        contractDAO.saveCollection(contracts);

    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testGetContractsByClient() {
        List<Contract> contracts = contractDAO.getContractsByClient(clientDAO.getById(1L));
        assertEquals(2, contracts.size());
    }
}