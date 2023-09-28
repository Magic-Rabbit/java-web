package cmc.webprak.dao;

import cmc.webprak.tables.*;
import cmc.webprak.tables.Clients;
import org.checkerframework.checker.units.qual.C;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestPropertySource(locations="classpath:application.properties")
public class ClientDAOTest {

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

    @Test
    void testManipulationsWithTickets() {
        Clients client = ClientDAO.getById(1L);
        List<Tickets> clientTickets = ClientDAO.getClientHistory(client);
        assertEquals(0, clientTickets.size());

        Routes route = RouteDAO.getById(1L);
        Tickets curTicket = TicketDAO.getAvailableTickets(route).get(0);
        TicketDAO.buyTicket(curTicket, client);

        clientTickets = ClientDAO.getClientHistory(client);
        assertEquals(1, clientTickets.size());
        assertEquals(curTicket, clientTickets.get(0));

        List<Clients> clientRoute = ClientDAO.getClientsByRoute(route);
        assertEquals(1, clientRoute.size());
        assertEquals(client, clientRoute.get(0));
    }

    @Test
    void testSimpleManipulations() {
        List<Clients> ClientListAll = (List<Clients>) ClientDAO.getAll();
        assertEquals(2, ClientListAll.size());

        Clients clientEmail = ClientDAO.searchClientByEmail("nekrasov@gmail.com");
        assertEquals(1, clientEmail.getId());

        Clients clientPhone = ClientDAO.searchClientByPhone("+79632501491");
        assertEquals(1, clientPhone.getId());

        Clients clientFullname = ClientDAO.searchClientByFullname("Некрасов Александр Борисович");
        assertEquals(1, clientFullname.getId());

        Clients clientNotExist = ClientDAO.searchClientByFullname("Ананасов Ананас Ананасович");
        assertNull(clientNotExist);
    }


    @Test
    void testDeleteById() {
        Clients deleteClient = ClientDAO.getById(1L);
        ClientDAO.deleteById(1L);

        Clients client = ClientDAO.getById(1L);
        assertNull(client);
    }

    @BeforeEach
    void beforeEach() {
        List<Clients> clients = new ArrayList<Clients>();
        clients.add(new Clients(1L, "Некрасов Александр Борисович", "+79632501491", "nekrasov@gmail.com"));
        clients.add(new Clients(2L, "Кудрявцева Екатерина Юрьевна", "+79635859942", "kudryav2018@mail.ru"));
        ClientDAO.saveCollection(clients);

        Routes route = new Routes(1L, WaypointDAO.getWaypointByName("Москва"), Timestamp.valueOf("2023-09-21 10:06:01"),
                BusDAO.getById(1L), WaypointDAO.getWaypointByName("Санкт-Петербург"), Timestamp.valueOf("2023-09-21 18:50:04"));
        RouteDAO.save(route);

        TicketDAO.createTicketsForRoute(route, 999.99f);
    }

    @BeforeAll
    @AfterEach
    void annihilation() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.createSQLQuery("TRUNCATE clients RESTART IDENTITY CASCADE;").executeUpdate();
            session.createSQLQuery("ALTER SEQUENCE clients_client_id_seq RESTART WITH 1;").executeUpdate();
            session.createSQLQuery("TRUNCATE routes RESTART IDENTITY CASCADE;").executeUpdate();
            session.createSQLQuery("ALTER SEQUENCE routes_route_id_seq RESTART WITH 1;").executeUpdate();
            session.createSQLQuery("TRUNCATE tickets RESTART IDENTITY CASCADE;").executeUpdate();
            session.createSQLQuery("ALTER SEQUENCE tickets_ticket_id_seq RESTART WITH 1;").executeUpdate();
            session.getTransaction().commit();
        }
    }
}
