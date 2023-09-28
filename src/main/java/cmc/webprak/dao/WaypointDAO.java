package cmc.webprak.dao;

import cmc.webprak.tables.*;

import java.util.List;

public interface WaypointDAO extends CommonDAO<Waypoint, Long> {
    Waypoint getWaypointByName(String name);
}
