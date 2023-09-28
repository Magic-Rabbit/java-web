package cmc.webprak.tables;

import javax.persistence.*;
import lombok.*;

import java.util.Objects;

@Entity
@Table(name = "waypoint")
@Getter
@Setter
@ToString
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class Waypoint implements TableEntity<Long>
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "waypoint_id")
    public Long id;

    @Column(nullable = false)
    @NonNull
    public String waypoint_name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Waypoint other = (Waypoint) o;
        return Objects.equals(id, other.id);
    }
}
