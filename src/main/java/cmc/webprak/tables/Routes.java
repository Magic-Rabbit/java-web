package cmc.webprak.tables;

import javax.persistence.*;

import io.hypersistence.utils.hibernate.type.json.JsonBinaryType;
import io.hypersistence.utils.hibernate.type.json.JsonType;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "routes")
@Getter
@Setter
@ToString
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class Routes implements TableEntity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "route_id")
    public Long id;

    @JoinColumn(name = "departure")
    @ManyToOne(fetch = FetchType.EAGER)
    @NonNull
    public Waypoint departure;

    @Column(nullable = false)
    @NonNull
    public Timestamp departure_date;

    @JoinColumn(name = "bus_id")
    @ManyToOne(fetch = FetchType.EAGER)
    @NonNull
    public Buses bus;

    @JoinColumn(name = "arrival")
    @ManyToOne(fetch = FetchType.EAGER)
    @NonNull
    public Waypoint arrival;

    @Column(nullable = false)
    @NonNull
    public Timestamp arrival_date;
}