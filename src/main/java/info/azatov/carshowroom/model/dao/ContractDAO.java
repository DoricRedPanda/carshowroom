package info.azatov.carshowroom.model.dao;

import java.util.List;

import info.azatov.carshowroom.model.entity.Client;
import info.azatov.carshowroom.model.entity.Contract;

public interface ContractDAO extends BaseDAO<Contract, Long> {

    List<Contract> getContractsByClient(Client client);
}
