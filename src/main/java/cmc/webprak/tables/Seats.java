package cmc.webprak.tables;

import javax.persistence.*;

import cmc.webprak.tables.composites.SeatCompositeId;
import lombok.*;
import javax.persistence.*;

@Entity
@Table(name = "seats",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"bus_id", "seat_number"})
        })
@Getter
@Setter
@ToString
@NoArgsConstructor
//@RequiredArgsConstructor
@AllArgsConstructor
@IdClass(SeatCompositeId.class)
public class Seats
{
    @Id
    @JoinColumn(name = "bus_id", nullable = false)
    @ManyToOne(fetch = FetchType.EAGER)
    public Buses bus;

    @Id
    @Column(nullable = false, name = "seat_number")
    public String seat;
}