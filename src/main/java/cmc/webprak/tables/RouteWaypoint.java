package cmc.webprak.tables;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

@Getter
@AllArgsConstructor
public class RouteWaypoint implements Serializable {

    @NonNull
    private Long waypoint_id;
    @NonNull
    private Timestamp date;

    public void setWaypoint_id(Long id) {
        this.waypoint_id = id;
    }

    public void setDate(String date) {
        this.date = Timestamp.valueOf(date);
    }

    @Override
    public String toString() {
        return "RouteWaypoint'{" +
                "waypoint_id='" + waypoint_id + '\'' +
                ", date='" + date.toString() + '\'' +
                "}'";
    }
}
