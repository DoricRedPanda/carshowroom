package info.azatov.carshowroom.model.entity;


import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "car")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
public class Car implements BaseEntity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ")
    @SequenceGenerator(name = "SEQ", sequenceName = "CAR_SEQ", allocationSize = 1)
    @Column(nullable = false, name = "vin")
	private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "model_id", nullable = false)
    @NonNull
    private AutoModel model;

    @Column(nullable = false, name = "registration_plate")
    private String registration_plate;

    @NonNull
    @Column(nullable = false, name = "price")
    private Double price;

    @Column(nullable = false, name = "kilometers")
    private Double kilometers;

    @Column(nullable = false, name = "service_date")
    private Date service_date;

    @Column(nullable = false, name = "displacement")
    private Integer displacement;

    @Column(nullable = false, name = "power")
    private Double power;

    @Column(nullable = false, name = "top_speed")
    private Double top_speed;

    @Column(nullable = false, name = "seat_count")
    private Integer seat_count;

    @Column(nullable = false, name = "transmission_type")
    private String transmission_type;

    @Column(nullable = false, name = "devices")
    private String devices;

    @Column(nullable = false, name = "color")
    private String color;

    @Column(nullable = false, name = "saloon")
    private String saloon;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Car car = (Car) o;
        return id != null && Objects.equals(id, car.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
