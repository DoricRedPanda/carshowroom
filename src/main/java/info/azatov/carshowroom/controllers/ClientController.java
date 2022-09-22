package info.azatov.carshowroom.controllers;

import info.azatov.carshowroom.model.dao.ClientDAO;
import info.azatov.carshowroom.model.dao.ContractDAO;
import info.azatov.carshowroom.model.dao.impl.ClientDAOImpl;
import info.azatov.carshowroom.model.dao.impl.ContractDAOImpl;
import info.azatov.carshowroom.model.entity.Client;
import info.azatov.carshowroom.model.entity.Contract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Date;
import java.util.Collection;

@Controller
public class ClientController {

    @Autowired
    private final ClientDAO clientDAO = new ClientDAOImpl();

    @Autowired
    private final ContractDAO contractDAO = new ContractDAOImpl();

    @GetMapping("/clients")
    public String clientsListPage(Model model) {
        Collection<Client> clients = clientDAO.getAll();
        if (model.containsAttribute("suggestions")) {
            model.addAttribute("clients", model.getAttribute("suggestions"));
        } else {
            model.addAttribute("clients", clients);
        }
        model.addAttribute("contractDAO", contractDAO);
        return "clients";
    }

    @GetMapping("/client")
    public String clientPage(@RequestParam(name = "client_id") Long client_id, Model model) {
        Client client = clientDAO.getById(client_id);
        if (client == null) {
            model.addAttribute(
                    "error_message",
                    String.format("Клиента с номером %d нет в салоне", client_id));
            return "errorPage";
        }
        model.addAttribute("client", client);
        model.addAttribute("clientDAO", clientDAO);
        return "client";
    }

    @PostMapping("/updateClient")
    public String updateClient(
            @RequestParam(name = "client_id") Long client_id,
            @RequestParam(name = "client_name") String client_name,
            @RequestParam(name = "client_address") String client_address,
            @RequestParam(name = "client_phone") String client_phone,
            @RequestParam(name = "client_email") String client_email,
            Model model
    ) {
        Client client = clientDAO.getById(client_id);
        if (client == null) {
            model.addAttribute(
                    "error_message",
                    String.format("Клиента с номером %d нет в салоне", client_id));
            return "errorPage";
        }
        client.setName(client_name);
        client.setAddress(client_address);
        client.setPhone(client_phone);
        client.setEmail(client_email);
        clientDAO.update(client);
        return "redirect:/clients";
    }

    @PostMapping("/deleteClient")
    public String deleteClient(@RequestParam(name = "client_id") Long client_id, Model model) {
        Client client = clientDAO.getById(client_id);
        if (client == null) {
            model.addAttribute(
                    "error_message",
                    String.format("Клиента с номером %d нет в салоне", client_id));
            return "errorPage";
        }
        clientDAO.delete(client);
        return "redirect:/clients";
    }

    @GetMapping("/addClient")
    public String addClientPage(Model model) {
        return "addClient";
    }

    @PostMapping("/insertClient")
    public String insertClient(
            @RequestParam(name = "client_name") String client_name,
            @RequestParam(name = "client_address") String client_address,
            @RequestParam(name = "client_phone") String client_phone,
            @RequestParam(name = "client_email") String client_email,
            Model model
    ) {
        clientDAO.insert(new Client(null, client_name, client_address, client_phone, client_email));
        return "redirect:/clients";
    }

    @GetMapping("/searchClient")
    public String searchClient(
            @RequestParam(name = "vin", required = false) Long vin,
            @RequestParam(name = "startDate", required = false) Date startDate,
            @RequestParam(name = "finishDate", required = false) Date finishDate,
            @RequestParam(name = "test_drive", required = false) Boolean test_drive,
            @RequestParam(name = "status", required = false) Contract.ContractStatus status,
            Model model
    ) {
        Collection<Client> clients = clientDAO.getClientByContract(vin, status, startDate, finishDate, test_drive);
        if (clients.size() == 1) {

            return "redirect:/client?client_id=" + clients.iterator().next().getId();
        }
        if (clients.size() < 1) {
            model.addAttribute(
                    "error_message", "Не нашлось клиентов, удовлетворяющих запросу.");
            return "errorPage";
        }
        model.addAttribute("clients", clients);
        model.addAttribute("clientDAO", clientDAO);
        model.addAttribute("contractDAO", contractDAO);
        return "clients";
    }
}

