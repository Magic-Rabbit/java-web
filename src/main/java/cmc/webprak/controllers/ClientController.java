package cmc.webprak.controllers;

import cmc.webprak.dao.ClientDAO;
import cmc.webprak.dao.CompanyDAO;
import cmc.webprak.dao.RouteDAO;
import cmc.webprak.dao.TicketDAO;
import cmc.webprak.dao.impl.ClientDAOImpl;
import cmc.webprak.dao.impl.CompanyDAOImpl;
import cmc.webprak.dao.impl.RouteDAOImpl;
import cmc.webprak.dao.impl.TicketDAOImpl;
import cmc.webprak.tables.Clients;
import cmc.webprak.tables.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Timestamp;

@Controller
public class ClientController {


    @Autowired
    private final RouteDAO routeDAO = new RouteDAOImpl();

    @Autowired
    private final TicketDAO ticketDAO = new TicketDAOImpl();

    @Autowired
    private final ClientDAO clientDAO = new ClientDAOImpl();

    @GetMapping("/editClient")
    public String editClientPage(@RequestParam(name = "id", required = false) Long id, Model model) {
        if (id == null) {
            model.addAttribute("client", new Clients());
            //model.addAttribute("lecturerService", lecturerDAO);
            return "editClient";
        }

        Clients client = clientDAO.getById(id);

        if (client == null) {
            model.addAttribute("error_msg", "There is no client with this id =" + id);
            return "errorPage";
        }

        model.addAttribute("client", client);
        //model.addAttribute("lecturerService", lecturerDAO);
        return "editClient";
    }

    @PostMapping("/saveClient")
    public String saveClientPage(@RequestParam(name = "clientId") String id,
                                  @RequestParam(name = "clientFullname") String fullname,
                                  @RequestParam(name = "clientPhone") String phone,
                                  @RequestParam(name = "clientEmail") String email,
                                  Model model) {
        Clients client = clientDAO.getById(Long.valueOf(id));

        if (client != null) {
            client.setFullname(fullname);
            client.setPhone(phone);
            client.setEmail(email);
            clientDAO.update(client);
            return "redirect:/clients";
        }

        model.addAttribute("error_msg", "Data not saved");
        return "errorPage";
    }

    @PostMapping("/searchClient")
    public String searchClientPage(@RequestParam(name = "clientFullname") String fullname,
                                 @RequestParam(name = "clientPhone") String phone,
                                 @RequestParam(name = "clientEmail") String email,
                                 Model model) {
        Clients client = clientDAO.searchClientByEmail(email);
        if (client == null)
            client = clientDAO.searchClientByPhone(phone);
        if (client == null)
            client = clientDAO.searchClientByFullname(fullname);

        if (client != null) {
            return "redirect:/client?id=" + client.getId().toString();
        }

        model.addAttribute("error_msg", "Client not found");
        return "errorPage";
    }


    @PostMapping("/removeClient")
    public String removeClientPage(@RequestParam(name = "id") Long id) {
        clientDAO.deleteById(id);
        return "redirect:/clients";
    }

    @GetMapping("/client")
    public String clientPage(@RequestParam(name = "id") Long id, Model model) {
        Clients client = clientDAO.getById(id);

        if (client == null) {
            model.addAttribute("error_msg", "В базе нет клиента с ID = " + id);
            return "errorPage";
        }

        model.addAttribute("client", client);
        model.addAttribute("clientService", clientDAO);
        return "client";
    }

    @GetMapping("/clients")
    public String clientListPage(Model model) {
        model.addAttribute("clientService", clientDAO);
        return "clients";
    }
}
