package cmc.webprak.dao;

import cmc.webprak.tables.Buses;
import cmc.webprak.tables.Company;
import cmc.webprak.tables.Waypoint;

import java.util.List;

public interface BusDAO extends CommonDAO<Buses, Long> {
    List<Buses> getBusesByCompany(Company company);
}
