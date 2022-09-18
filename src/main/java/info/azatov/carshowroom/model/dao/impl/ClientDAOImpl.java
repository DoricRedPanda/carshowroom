package info.azatov.carshowroom.model.dao.impl;

import info.azatov.carshowroom.model.dao.ClientDAO;
import info.azatov.carshowroom.model.entity.Car;
import info.azatov.carshowroom.model.entity.Client;
import info.azatov.carshowroom.model.entity.Contract;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class ClientDAOImpl extends BaseDAOImpl<Client, Long> implements ClientDAO {

	public ClientDAOImpl() {
		super(Client.class);
	}

	@Override
	public List<Client> getAllClientByName(String clientName) {
		try (Session session = sessionFactory.openSession()) {
			Query<Client> query = session.createQuery("FROM Client WHERE name LIKE :gotName", Client.class)
					.setParameter("gotName", likeExpr(clientName));
			return query.getResultList().size() == 0 ? null : query.getResultList();
		}
	}

	@Override
	public Client getSingleClientByName(String clientName) {
		List<Client> candidates = this.getAllClientByName(clientName);
		return candidates == null ? null :
				candidates.size() == 1 ? candidates.get(0) : null;
	}

	@Override
	public List<Client> getClientByContract(Contract.ContractStatus status, Date start, Date finish, Boolean test_drive) {
		try (Session session = sessionFactory.openSession()) {
			CriteriaBuilder cb = session.getCriteriaBuilder();
			CriteriaQuery<Contract> criteria = cb.createQuery(Contract.class);
			Root<Contract> root = criteria.from(Contract.class);
			criteria.select(root);

			List<Predicate> predicates = new ArrayList<>();
			if (status != null)
				predicates.add(cb.equal(root.get("status"), status));
			if (start != null)
				predicates.add(cb.greaterThanOrEqualTo(root.get("date"), start));
			if (finish != null)
				predicates.add(cb.lessThanOrEqualTo(root.get("date"), finish));
			if (test_drive != null)
				predicates.add(cb.equal(root.get("test_drive"), test_drive));
			Predicate predicate = cb.and(predicates.toArray(Predicate[]::new));
			criteria.where(predicate);
			List<Contract> contracts = session.createQuery(criteria).getResultList();
			return contracts
					.stream()
					.map(Contract::getClient)
					.distinct()
					.collect(Collectors.toList());
		}
	}

	private Object likeExpr(String string) {
		return "%" + string + "%";
	}


}
