package info.azatov.carshowroom.controllers;

import info.azatov.carshowroom.model.dao.CarDAO;
import info.azatov.carshowroom.model.dao.ClientDAO;
import info.azatov.carshowroom.model.dao.ContractDAO;
import info.azatov.carshowroom.model.dao.impl.CarDAOImpl;
import info.azatov.carshowroom.model.dao.impl.ClientDAOImpl;
import info.azatov.carshowroom.model.dao.impl.ContractDAOImpl;
import info.azatov.carshowroom.model.entity.Car;
import info.azatov.carshowroom.model.entity.Client;
import info.azatov.carshowroom.model.entity.Contract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Date;
import java.util.Collection;

@Controller
public class ContractController {

    @Autowired
    private final ContractDAO contractDAO = new ContractDAOImpl();
    @Autowired
    private final ClientDAO clientDAO = new ClientDAOImpl();
    @Autowired
    private final CarDAO carDAO = new CarDAOImpl();

    @GetMapping("/contracts")
    public String contractsListPage(Model model) {
        Collection<Contract> contracts = contractDAO.getAll();
        model.addAttribute("contracts", contracts);
        return "contracts";
    }

    @GetMapping("/contract")
    public String contractPage(@RequestParam(name = "contract_id") Long contract_id, Model model) {
        Contract contract = contractDAO.getById(contract_id);
        if (contract == null) {
            model.addAttribute(
                    "error_message",
                    String.format("Заказ с номером %d не зарегистрирован.", contract_id));
            return "errorPage";
        }
        model.addAttribute("contract", contract);
        model.addAttribute("contractDAO", contractDAO);
        return "contract";
    }

    @PostMapping("/updateContract")
    public String updateContract(
            @RequestParam(name = "contract_id") Long contract_id,
            @RequestParam(name = "client_id") Long client_id,
            @RequestParam(name = "vin") Long vin,
            @RequestParam(name = "date") Date date,
            @RequestParam(name = "test_drive", required = false) Boolean test_drive,
            @RequestParam(name = "status") Contract.ContractStatus status,
            Model model
    ) {
        Contract contract = contractDAO.getById(contract_id);
        if (contract == null) {
            model.addAttribute(
                    "error_message",
                    String.format("Заказ с номером %d не зарегистрирован.", contract_id));
            return "errorPage";
        }
        if (client_id != null) {
            Client client = clientDAO.getById(client_id);
            if (client == null) {
                model.addAttribute(
                        "error_message",
                        String.format("Клиента с номером %d нет в салоне", client_id));
                return "errorPage";
            }
            contract.setClient(client);
        }
        if (vin != null) {
            Car car = carDAO.getById(vin);
            if (car == null) {
                model.addAttribute("error_message", String.format("Машины с номером %d нет в салоне", vin));
                return "errorPage";
            }
            contract.setCar(car);
        }
        contract.setDate(date);
        contract.setStatus(status);
        if (test_drive == null)
            test_drive = false;
        contract.setTest_drive(test_drive);
        contractDAO.update(contract);
        return "redirect:/clients";
    }

    @GetMapping("/addContract")
    public String addContractPage(Model model) {
        return "addContract";
    }

    @PostMapping("/createContract")
    public String createContract(
            @RequestParam(name = "client_id") Long client_id,
            @RequestParam(name = "vin") Long vin,
            @RequestParam(name = "date") Date date,
            @RequestParam(name = "test_drive", required = false) Boolean test_drive,
            @RequestParam(name = "status") Contract.ContractStatus status,
            Model model
    ) {
        Client client = clientDAO.getById(client_id);
        if (client == null) {
            model.addAttribute(
                    "error_message",
                    String.format("Клиента с номером %d нет в салоне", client_id));
            return "errorPage";
        }
        Car car = carDAO.getById(vin);
        if (car == null) {
            model.addAttribute("error_message", String.format("Машины с номером %d нет в салоне", vin));
            return "errorPage";
        }
        if (test_drive == null)
            test_drive = false;
        Contract contract = new Contract(
                client,
                car,
                date,
                test_drive,
                status
        );
        contractDAO.insert(contract);
        return "redirect:/contracts";
    }

    @PostMapping("/deleteContract")
    public String deleteContract(@RequestParam(name = "contract_id") Long contract_id, Model model) {
        Contract contract = contractDAO.getById(contract_id);
        if (contract == null) {
            model.addAttribute(
                    "error_message",
                    String.format("Заказ с номером %d не зарегистрирован.", contract_id));
            return "errorPage";
        }
        contractDAO.delete(contract);
        return "redirect:/contracts";
    }

}
