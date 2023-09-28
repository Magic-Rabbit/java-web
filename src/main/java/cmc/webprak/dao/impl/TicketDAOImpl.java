package cmc.webprak.dao.impl;

import cmc.webprak.dao.BusDAO;
import cmc.webprak.dao.TicketDAO;
import cmc.webprak.tables.*;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class TicketDAOImpl extends CommonDAOImpl<Tickets, Long> implements TicketDAO {

    public TicketDAOImpl(){
        super(Tickets.class);
    }
    @Override
    public List<Tickets> getAvailableTickets(Routes route) {
        try (Session session = sessionFactory.openSession()) {
            Query<Tickets> query = session
                    .createQuery("select ticket from Tickets ticket where ticket.route = :route and ticket.status = 'AVAILABLE'", Tickets.class)
                    .setParameter("route", route);
            return query.getResultList();
        }
    }

    @Override
    public void createTicketsForRoute(Routes route, float price) {
        try (Session session = sessionFactory.openSession()) {
            Query<Seats> query = session
                    .createQuery("select seat from Seats seat where seat.bus = :id", Seats.class)
                    .setParameter("id", route.getBus());

            List<Seats> seats = query.getResultList();
            List<Tickets> tickets = new ArrayList<Tickets>();
            for (Seats seat : seats) {
                Tickets ticket = new Tickets(null, route, seat.getSeat(), null, "AVAILABLE", price);
                tickets.add(ticket);
            }

            this.saveCollection(tickets);
        }
    }

    @Override
    public void buyTicket(Tickets ticket, Clients client) {
        ticket.client = client;
        ticket.status = "SOLD";
        this.save(ticket);
    }
}
