package info.azatov.carshowroom.model.dao.impl;

import info.azatov.carshowroom.model.dao.AutoModelDAO;
import info.azatov.carshowroom.model.entity.AutoModel;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AutoModelDAOImpl extends BaseDAOImpl<AutoModel, Long> implements AutoModelDAO {

    public AutoModelDAOImpl() {
        super(AutoModel.class);
    }

    @Override
    public List<AutoModel> getModelsByName(String name) {
        try (Session session = sessionFactory.openSession()) {
            Query<AutoModel> query = session.createQuery("FROM AutoModel WHERE name LIKE :name",
                        AutoModel.class)
                .setParameter("name", "%" + name + "%");
            return query.getResultList();
        }
    }

    @Override
    public List<AutoModel> getModelsByMake(String make) {
        try (Session session = sessionFactory.openSession()) {
            Query<AutoModel> query = session.createQuery("FROM AutoModel WHERE " +
                        "make LIKE :name", AutoModel.class)
                .setParameter("name", "%" + make + "%");
            return query.getResultList();
        }
    }
}
