package cmc.webprak.dao;

import cmc.webprak.tables.*;

import java.util.Date;
import java.util.List;
public interface ClientDAO extends CommonDAO<Clients, Long> {
    List<Clients> getClientsByRoute(Routes route);
    Clients searchClientByFullname(String fullname);
    Clients searchClientByPhone(String phone);
    Clients searchClientByEmail(String email);
    List<Tickets> getClientHistory(Clients client);
}
