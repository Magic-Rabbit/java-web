package cmc.webprak.tables;

import javax.persistence.*;
import lombok.*;

import java.util.Objects;

@Entity
@Table(name = "tickets")
@Getter
@Setter
@ToString
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class Tickets implements TableEntity<Long>
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "ticket_id")
    public Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "route_id")
    @NonNull
    public Routes route;

    @Column(nullable = false)
    public String seat_number;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id")
    public Clients client;

    @Column(nullable = false)
    public String status;

    @Column(nullable = false)
    public Float price;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tickets other = (Tickets) o;
        return Objects.equals(id, other.id);
    }
}