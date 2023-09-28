package cmc.webprak.dao;

import cmc.webprak.tables.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestPropertySource(locations = "classpath:application.properties")
public class CompanyDAOTest {

    @Autowired
    private ClientDAO ClientDAO;
    @Autowired
    private WaypointDAO WaypointDAO;
    @Autowired
    private BusDAO BusDAO;
    @Autowired
    private SessionFactory sessionFactory;
    @Autowired
    private RouteDAO RouteDAO;
    @Autowired
    private TicketDAO TicketDAO;

    @Autowired
    private CompanyDAO CompanyDAO;

    @Test
    void testManipulationsWithBus() {
        List<Buses> bus = BusDAO.getBusesByCompany(CompanyDAO.getById(1L));
        assertEquals(1, bus.size());

        assertEquals(CompanyDAO.getById(1L), bus.get(0).company);
        assertEquals(2020, bus.get(0).year);
        assertEquals(13, bus.get(0).seats_count);
        assertEquals("ГАЗ 322173", bus.get(0).model);

        bus = BusDAO.getBusesByCompany(CompanyDAO.getById(2L));
        assertEquals(1, bus.size());

        assertEquals(CompanyDAO.getById(2L), bus.get(0).company);
        assertEquals(2021, bus.get(0).year);
        assertEquals(19, bus.get(0).seats_count);
        assertEquals("Ford Transit", bus.get(0).model);
    }

    @Test
    void testDeleteById() {
        Company deleteCompany = CompanyDAO.getById(1L);
        assertNotNull(deleteCompany);
        CompanyDAO.deleteById(1L);

        Company client = CompanyDAO.getById(1L);
        assertNull(client);
    }

    @BeforeEach
    void beforeEach() {
        List<Company> companies = new ArrayList<Company>();
        companies.add(new Company("EUROBUS", Timestamp.valueOf(LocalDateTime.now())));
        companies.add(new Company("Красные автобусы", Timestamp.valueOf(LocalDateTime.now())));
        CompanyDAO.saveCollection(companies);

        List<Buses> buses = new ArrayList<Buses>();
        buses.add(new Buses(CompanyDAO.getById(1L), "ГАЗ 322173", 2020, 13));
        buses.add(new Buses(CompanyDAO.getById(2L), "Ford Transit", 2021, 19));

        BusDAO.saveCollection(buses);
    }

    @BeforeAll
    @AfterEach
    void annihilation() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.createSQLQuery("TRUNCATE buses RESTART IDENTITY CASCADE;").executeUpdate();
            session.createSQLQuery("ALTER SEQUENCE buses_bus_id_seq RESTART WITH 1;").executeUpdate();
            session.createSQLQuery("TRUNCATE company RESTART IDENTITY CASCADE;").executeUpdate();
            session.createSQLQuery("ALTER SEQUENCE company_company_id_seq RESTART WITH 1;").executeUpdate();
            session.getTransaction().commit();
        }
    }
}
