package info.azatov.carshowroom.model.dao.impl;

import info.azatov.carshowroom.model.dao.ModelDAO;
import info.azatov.carshowroom.model.entity.Model;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ModelDAOImpl extends BaseDAOImpl<Model, Long> implements ModelDAO {

    public ModelDAOImpl() {
        super(Model.class);
    }

    @Override
    public List<Model> getModelsByName(String name) {
        try (Session session = sessionFactory.openSession()) {
            Query<Model> query = session.createQuery("FROM Model WHERE name LIKE :name",
                        Model.class)
                .setParameter("name", "%" + name + "%");
            return query.getResultList();
        }
    }

    @Override
    public List<Model> getModelsByMake(String make) {
        try (Session session = sessionFactory.openSession()) {
            Query<Model> query = session.createQuery("FROM Model WHERE " +
                        "make LIKE :name", Model.class)
                .setParameter("name", "%" + make + "%");
            return query.getResultList();
        }
    }
}
