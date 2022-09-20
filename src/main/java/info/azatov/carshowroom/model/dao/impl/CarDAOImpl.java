package info.azatov.carshowroom.model.dao.impl;


import info.azatov.carshowroom.model.dao.CarDAO;
import info.azatov.carshowroom.model.entity.Car;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CarDAOImpl extends BaseDAOImpl<Car, Long> implements CarDAO {

    public CarDAOImpl() {
        super(Car.class);
    }

    @Override
    public List<Car> search(
            String registration_plate,
            Date from_service_date,
            Integer displacement,
            Double power,
            Double top_speed,
            Integer seat_count,
            String transmission_type,
            String devices,
            String color,
            String saloon,
            Double startPrice,
            Double finishPrice,
            String name,
            String make,
            Integer year
            ) {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Car> criteria = cb.createQuery(Car.class);
            Root<Car> root = criteria.from(Car.class);
            criteria.select(root);

            List<Predicate> predicates = new ArrayList<>();
            if (registration_plate != null)
                predicates.add(cb.equal(root.get("registration_plate"), registration_plate));
            if (color != null)
               predicates.add(cb.equal(root.get("color"), color));
            if (from_service_date != null)
                predicates.add(cb.greaterThanOrEqualTo(root.get("service_date"), from_service_date));
            if (displacement != null)
                predicates.add(cb.ge(root.get("displacement"), displacement));
            if (devices != null)
                predicates.add(cb.like(root.get("devices"), devices));
            if (power != null)
                predicates.add(cb.ge(root.get("power"), power));
            if (saloon != null)
                predicates.add(cb.like(root.get("saloon"), saloon));
            if (seat_count != null)
                predicates.add(cb.equal(root.get("seat_count"), seat_count));
            if (top_speed != null)
                predicates.add(cb.ge(root.get("top_speed"), top_speed));
            if (transmission_type != null)
                predicates.add(cb.equal(root.get("transmission_type"), transmission_type));
            if (startPrice != null)
                predicates.add(cb.ge(root.get("price"), startPrice));
            if (finishPrice != null)
                predicates.add(cb.le(root.get("price"), finishPrice));
            if (name != null)
                predicates.add(cb.equal(root.get("model").get("name"), name));
            if (make != null)
                predicates.add(cb.equal(root.get("model").get("make"), make));
            if (year != null)
                predicates.add(cb.equal(root.get("model").get("year"), year));
            Predicate predicate = cb.and(predicates.toArray(Predicate[]::new));
            criteria.where(predicate);

            return session.createQuery(criteria).getResultList();
        }
    }

}
