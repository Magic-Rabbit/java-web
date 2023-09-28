package cmc.webprak.tables;

import javax.persistence.*;
import lombok.*;

import java.util.Objects;

@Entity
@Table(name = "clients")
@Getter
@Setter
@ToString
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class Clients implements TableEntity<Long>
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "client_id")
    public Long id;

    @Column(nullable = false)
    @NonNull
    public String fullname;

    @Column(nullable = false)
    @NonNull
    public String phone;

    @Column(nullable = false)
    @NonNull
    public String email;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Clients other = (Clients) o;
        return Objects.equals(id, other.id);
    }

}