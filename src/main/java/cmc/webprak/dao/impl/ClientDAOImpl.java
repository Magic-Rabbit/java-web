package cmc.webprak.dao.impl;

import cmc.webprak.dao.BusDAO;
import cmc.webprak.dao.ClientDAO;
import cmc.webprak.tables.Buses;
import cmc.webprak.tables.Clients;
import cmc.webprak.tables.Routes;
import cmc.webprak.tables.Tickets;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ClientDAOImpl extends CommonDAOImpl<Clients, Long> implements ClientDAO {
    public ClientDAOImpl() {
        super(Clients.class);
    }
    @Override
    public Clients searchClientByFullname(String fullname) {
        try (Session session = sessionFactory.openSession()) {
            Query<Clients> query = session
                    .createQuery("select client from Clients client where client.fullname = :fullname", Clients.class)
                    .setParameter("fullname", fullname);
            return query.getResultStream().findFirst().orElse(null);
        }
    }

    @Override
    public Clients searchClientByPhone(String phone) {
        try (Session session = sessionFactory.openSession()) {
            Query<Clients> query = session
                    .createQuery("select client from Clients client where client.phone = :phone", Clients.class)
                    .setParameter("phone", phone);
            return query.getResultStream().findFirst().orElse(null);
        }
    }

    @Override
    public Clients searchClientByEmail(String email) {
        try (Session session = sessionFactory.openSession()) {
            Query<Clients> query = session
                    .createQuery("select client from Clients client where client.email = :email", Clients.class)
                    .setParameter("email", email);
            return query.getResultStream().findFirst().orElse(null);
        }
    }

    @Override
    public List<Clients> getClientsByRoute(Routes route) {
        try (Session session = sessionFactory.openSession()) {
            Query<Clients> query = session
                    .createQuery("select client from Tickets ticket where ticket.route = :route", Clients.class)
                    .setParameter("route", route);
            return query.getResultList();
        }
    }

    @Override
    public List<Tickets> getClientHistory(Clients client) {
        try (Session session = sessionFactory.openSession()) {
            Query<Tickets> query = session
                    .createQuery("select ticket from Tickets ticket where ticket.client = :client", Tickets.class)
                    .setParameter("client", client);
            return query.getResultList();
        }
    }
}
