package cmc.webprak.dao.impl;

import cmc.webprak.dao.BusDAO;
import cmc.webprak.dao.CompanyDAO;
import cmc.webprak.tables.Buses;
import cmc.webprak.tables.Company;
import org.springframework.stereotype.Repository;

@Repository
public class CompanyDAOImpl extends CommonDAOImpl<Company, Long> implements CompanyDAO {
    public CompanyDAOImpl(){
        super(Company.class);
    }
//    @Override
//    public Buses getBusById(Long id) {
//        try (Session session = sessionFactory.openSession()) {
//            Query<Buses> query = session
//                    .createQuery("select bus from Buses bus where bus.id = :id", Buses.class)
//                    .setParameter("id", id);
//            return query.getResultList().get(0);
//        }
//    }
}
