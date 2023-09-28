package cmc.webprak.tables;

import javax.persistence.*;
import lombok.*;

import java.util.Objects;

@Entity
@Table(name = "company")
@Getter
@Setter
@ToString
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class Company implements TableEntity<Long>
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "company_id")
    public Long id;

    @Column(nullable = false)
    @NonNull
    public String company_name;

    @Column(nullable = false)
    @NonNull
    public java.sql.Timestamp creation_date;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Company other = (Company) o;
        return Objects.equals(id, other.id);
    }

}