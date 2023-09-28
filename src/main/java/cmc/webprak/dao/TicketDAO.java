package cmc.webprak.dao;

import cmc.webprak.tables.Clients;
import cmc.webprak.tables.Company;
import cmc.webprak.tables.Routes;
import cmc.webprak.tables.Tickets;

import java.util.List;

public interface TicketDAO extends CommonDAO<Tickets, Long> {
    void buyTicket(Tickets ticket, Clients client);
    List<Tickets> getAvailableTickets(Routes route);

    void createTicketsForRoute(Routes route, float price);
}
