package info.azatov.carshowroom.model.dao.impl;

import info.azatov.carshowroom.model.dao.ContractDAO;
import info.azatov.carshowroom.model.entity.Client;
import info.azatov.carshowroom.model.entity.Contract;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ContractDAOImpl extends BaseDAOImpl<Contract, Long> implements ContractDAO {

	public ContractDAOImpl() {
		super(Contract.class);
	}

	@Override
	public List<Contract> getContractsByClient(Client client) {
		try (Session session = sessionFactory.openSession()) {
			Query<Contract> query = session.createQuery("FROM Contract WHERE client = :client", Contract.class).
				setParameter("client", client);

			return query.getResultList();
		}
	}

}
