package cmc.webprak.dao.impl;

import cmc.webprak.dao.CommonDAO;
import cmc.webprak.dao.RouteDAO;
import cmc.webprak.tables.Routes;
import cmc.webprak.tables.Waypoint;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

@Repository
public class RouteDAOImpl extends CommonDAOImpl<Routes, Long> implements RouteDAO {
    public RouteDAOImpl(){
        super(Routes.class);
    }
    @Override
    public List<Routes> searchRoute(Timestamp date, Waypoint from, Waypoint to) {
        try (Session session = sessionFactory.openSession()) {
            Query<Routes> query = session
                    .createQuery("select route from Routes route where route.departure_date <= :date and route.departure = :from and route.arrival = :to", Routes.class)
                    .setParameter("date", date)
                    .setParameter("from", from)
                    .setParameter("to", to);
            return query.getResultList();
        }
    }
}
