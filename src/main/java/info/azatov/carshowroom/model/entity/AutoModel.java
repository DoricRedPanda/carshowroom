package info.azatov.carshowroom.model.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "automodel")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
public class AutoModel implements BaseEntity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ")
    @SequenceGenerator(name = "SEQ", sequenceName = "MODEL_SEQ", allocationSize = 1)
    @Column(nullable = false, name = "model_id")
    private Long id;

    @Column(nullable = false, name = "name")
    @NonNull
    private String name;

    @Column(nullable = false, name = "make")
    @NonNull
    private String make;

    @Column(nullable = false, name = "year")
    private int year;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        AutoModel autoModel = (AutoModel) o;
        return id != null && Objects.equals(id, autoModel.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return String.format("%s %s (%d)", this.make, this.name, this.year);
    }
}

