package cmc.webprak.tables;

import lombok.*;

//import jakarta.persistence.*;
import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "buses")
@Getter
@Setter
@ToString
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class Buses implements TableEntity<Long>
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "bus_id")
    public Long id;

    @JoinColumn(name = "company_id")
    @NonNull
    @ManyToOne(fetch = FetchType.EAGER)
    public Company company;

    @Column(nullable = false)
    @NonNull
    public String model;

    @Column(nullable = false)
    @NonNull
    public int year;

    @Column(nullable = false)
    @NonNull
    public int seats_count;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Buses other = (Buses) o;
        return Objects.equals(id, other.id);
    }
}
