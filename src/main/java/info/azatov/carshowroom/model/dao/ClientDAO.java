package info.azatov.carshowroom.model.dao;

import info.azatov.carshowroom.model.entity.Client;
import info.azatov.carshowroom.model.entity.Contract;

import java.sql.Date;
import java.util.List;

public interface ClientDAO extends BaseDAO<Client, Long> {

    List<Client> getAllClientByName(String clientName);
    Client getSingleClientByName(String clientName);

    List<Client> getClientByContract(Long vin, Contract.ContractStatus status, Date start, Date finish, Boolean test_drive);
}
