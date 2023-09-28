package cmc.webprak.dao.impl;

import cmc.webprak.dao.RouteDAO;
import cmc.webprak.dao.WaypointDAO;
import cmc.webprak.tables.Routes;
import cmc.webprak.tables.Waypoint;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public class WaypointDAOImpl extends CommonDAOImpl<Waypoint, Long> implements WaypointDAO {
    public WaypointDAOImpl(){
        super(Waypoint.class);
    }

    @Override
    public Waypoint getWaypointByName(String name) {
        try (Session session = sessionFactory.openSession()) {
            Query<Waypoint> query = session
                    .createQuery("select wp from Waypoint wp where wp.waypoint_name like :name", Waypoint.class)
                    .setParameter("name", likeExpr(name));
            return query.getResultList().get(0);
        }
    }

    private String likeExpr(String param) {
        return "%" + param + "%";
    }
}
