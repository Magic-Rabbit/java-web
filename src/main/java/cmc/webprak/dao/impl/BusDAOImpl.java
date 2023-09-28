package cmc.webprak.dao.impl;

import cmc.webprak.dao.BusDAO;
import cmc.webprak.dao.WaypointDAO;
import cmc.webprak.tables.Buses;
import cmc.webprak.tables.Company;
import cmc.webprak.tables.Waypoint;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BusDAOImpl extends CommonDAOImpl<Buses, Long> implements BusDAO {
    public BusDAOImpl(){
        super(Buses.class);
    }
    @Override
    public List<Buses> getBusesByCompany(Company company) {
        try (Session session = sessionFactory.openSession()) {
            Query<Buses> query = session
                    .createQuery("select bus from Buses bus where bus.company = :company", Buses.class)
                    .setParameter("company", company);
            return query.getResultList();
        }
    }
}
