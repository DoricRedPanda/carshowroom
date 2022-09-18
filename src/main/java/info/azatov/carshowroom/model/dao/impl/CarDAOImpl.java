package info.azatov.carshowroom.model.dao.impl;


import info.azatov.carshowroom.model.dao.CarDAO;
import info.azatov.carshowroom.model.entity.Car;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CarDAOImpl extends BaseDAOImpl<Car, Long> implements CarDAO {

    public CarDAOImpl() {
        super(Car.class);
    }

    @Override
    public List<Car> search(Car filter, Double startPrice, Double finishPrice, String name, String make) {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Car> criteria = cb.createQuery(Car.class);
            Root<Car> root = criteria.from(Car.class);
            criteria.select(root);

            List<Predicate> predicates = new ArrayList<>();
            if (filter != null) {
                if (filter.getColor() != null)
                    predicates.add(cb.equal(root.get("color"), filter.getColor()));
                if (filter.getDisplacement() != null)
                    predicates.add(cb.ge(root.get("displacement"), filter.getDisplacement()));
                if (filter.getDevices() != null)
                    predicates.add(cb.like(root.get("devices"), filter.getDevices()));
                if (filter.getPower() != null)
                    predicates.add(cb.ge(root.get("power"), filter.getPower()));
                if (filter.getSaloon() != null)
                    predicates.add(cb.like(root.get("saloon"), filter.getSaloon()));
                if (filter.getSeat_count() != null)
                    predicates.add(cb.equal(root.get("seat_count"), filter.getSeat_count()));
                if (filter.getTop_speed() != null)
                    predicates.add(cb.ge(root.get("top_speed"), filter.getTop_speed()));
                if (filter.getTransmission_type() != null)
                    predicates.add(cb.equal(root.get("transmission_type"), filter.getTransmission_type()));
            }
            if (startPrice != null)
                predicates.add(cb.ge(root.get("price"), startPrice));
            if (finishPrice != null)
                predicates.add(cb.le(root.get("price"), finishPrice));
            if (name != null)
                predicates.add(cb.equal(root.get("model").get("name"), name));
            if (make != null)
                predicates.add(cb.equal(root.get("model").get("make"), make));
            Predicate predicate = cb.and(predicates.toArray(Predicate[]::new));
            criteria.where(predicate);

            return session.createQuery(criteria).getResultList();
        }
    }

}