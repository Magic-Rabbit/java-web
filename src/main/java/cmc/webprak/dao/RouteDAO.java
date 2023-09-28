package cmc.webprak.dao;

import cmc.webprak.tables.*;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
public interface RouteDAO extends CommonDAO<Routes, Long> {
    List<Routes> searchRoute(Timestamp date, Waypoint from, Waypoint to);
}
