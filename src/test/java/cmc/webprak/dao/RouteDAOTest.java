package cmc.webprak.dao;

import cmc.webprak.dao.impl.WaypointDAOImpl;
import cmc.webprak.tables.RouteWaypoint;
import cmc.webprak.tables.Routes;
import cmc.webprak.tables.Waypoint;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import cmc.webprak.dao.*;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestPropertySource(locations="classpath:application.properties")
public class RouteDAOTest {

    @Autowired
    private RouteDAO RouteDAO;
    @Autowired
    private WaypointDAO WaypointDAO;
    @Autowired
    private BusDAO BusDAO;
    @Autowired
    private SessionFactory sessionFactory;

    @Test
    void testSimpleManipulations() {
        List<Routes> RouteListAll = (List<Routes>) RouteDAO.getAll();
        assertEquals(1, RouteListAll.size());

        List<Routes> geraltQuery = RouteDAO.searchRoute(Timestamp.valueOf("2023-09-21 23:59:59"), WaypointDAO.getWaypointByName("Москва"), WaypointDAO.getWaypointByName("Санкт-Петербург"));
        assertEquals(1, geraltQuery.size());
        assertEquals(1, geraltQuery.get(0).id);

        Routes RouteId3 = RouteDAO.getById(1L);
        assertEquals(1, RouteId3.getId());

        Routes RouteNotExist = RouteDAO.getById(100L);
        assertNull(RouteNotExist);
    }

    @Test
    void testUpdate() {
        Waypoint arvl = WaypointDAO.getWaypointByName("Великий Новгород");
        Waypoint dprt = WaypointDAO.getWaypointByName("Тверь");

        Routes updateRoute = RouteDAO.getById(1L);
        updateRoute.setArrival(arvl);
        updateRoute.setDeparture(dprt);
        RouteDAO.update(updateRoute);

        Routes route = RouteDAO.getById(1L);
        assertEquals(arvl, route.getArrival());
        assertEquals(dprt, route.getDeparture());
    }

    @Test
    void testDelete() {
        Routes deleteRoute = RouteDAO.getById(1L);
        RouteDAO.delete(deleteRoute);

        Routes route = RouteDAO.getById(1L);
        assertNull(route);
    }

    @BeforeEach
    void beforeEach() {
        List<Routes> RouteList = new ArrayList<>();

        List<RouteWaypoint> waypoints = new ArrayList<RouteWaypoint>();
        waypoints.add(new RouteWaypoint(WaypointDAO.getWaypointByName("Москва").getId(), Timestamp.valueOf("2023-09-21 10:06:01")));
        waypoints.add(new RouteWaypoint(WaypointDAO.getWaypointByName("Тверь").getId(), Timestamp.valueOf("2023-09-21 12:28:02")));
        waypoints.add(new RouteWaypoint(WaypointDAO.getWaypointByName("Великий Новгород").getId(), Timestamp.valueOf("2023-09-21 15:11:03")));
        waypoints.add(new RouteWaypoint(WaypointDAO.getWaypointByName("Санкт-Петербург").getId(), Timestamp.valueOf("2023-09-21 18:50:04")));

        RouteList.add(new Routes(1L, WaypointDAO.getWaypointByName("Москва"), Timestamp.valueOf("2023-09-21 10:06:01"),
                BusDAO.getById(1L), WaypointDAO.getWaypointByName("Санкт-Петербург"), Timestamp.valueOf("2023-09-21 18:50:04")));

        RouteDAO.saveCollection(RouteList);
    }

    @BeforeAll
    @AfterEach
    void annihilation() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.createSQLQuery("TRUNCATE routes RESTART IDENTITY CASCADE;").executeUpdate();
            session.createSQLQuery("ALTER SEQUENCE routes_route_id_seq RESTART WITH 1;").executeUpdate();
            session.getTransaction().commit();
        }
    }
}
