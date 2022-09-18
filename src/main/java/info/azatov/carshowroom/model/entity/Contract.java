package info.azatov.carshowroom.model.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "contract")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@NoArgsConstructor
public class Contract implements BaseEntity<Long> {

    public enum ContractStatus {
        PROCESSING,
        WAITING4DELIVERY,
        IN_SALOON,
        IN_TEST_DRIVE,
        DONE
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ")
    @SequenceGenerator(name = "SEQ", sequenceName = "CONTRACT_SEQ", allocationSize = 1)
    @Column(nullable = false, name = "contract_id")
    private Long id;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id", nullable = false)
    @NonNull
    private Client client;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "vin", nullable = false)
    @NonNull
    private Car car;
    
    @Column(nullable = false, name = "date")
    @NonNull
    private Date date;

    @Column(nullable = false, name = "test_drive")
    @NonNull
    private boolean test_drive;

    @Enumerated
    @Column(nullable = false, name = "status")
    @NonNull
    private Contract.ContractStatus status;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Contract contract = (Contract) o;
        return id != null && Objects.equals(id, contract.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}