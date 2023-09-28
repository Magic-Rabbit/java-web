package cmc.webprak.controllers;

import cmc.webprak.dao.*;
import cmc.webprak.dao.impl.*;
import cmc.webprak.tables.Clients;
import cmc.webprak.tables.Routes;
import cmc.webprak.tables.Tickets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Timestamp;
import java.util.List;

@Controller
public class RouteController {


    @Autowired
    private final RouteDAO routeDAO = new RouteDAOImpl();

    @Autowired
    private final TicketDAO ticketDAO = new TicketDAOImpl();

    @Autowired
    private final WaypointDAO waypointDAO = new WaypointDAOImpl();

    @Autowired
    private final BusDAO busesDAO = new BusDAOImpl();

    @Autowired
    private final ClientDAO clientDAO = new ClientDAOImpl();

    @PostMapping("/saveRoute")
    public String saveRoutePage(@RequestParam(name = "routeId") String id,
                                @RequestParam(name = "departure") String departure,
                                @RequestParam(name = "departureDate") String departureDate,
                                @RequestParam(name = "arrival") String arrival,
                                @RequestParam(name = "arrivalDate") String arrivalDate,
                                @RequestParam(name = "busId") String busId,
                                Model model) {
        Routes route = routeDAO.getById(Long.valueOf(id));

        if (route != null) {
            route.setDeparture(waypointDAO.getWaypointByName(departure));
            route.setDeparture_date(Timestamp.valueOf(departureDate));
            route.setArrival(waypointDAO.getWaypointByName(arrival));
            route.setArrival_date(Timestamp.valueOf(arrivalDate));
            route.setBus(busesDAO.getById(Long.valueOf(busId)));
            routeDAO.update(route);
            return "redirect:/routes";
        }

        model.addAttribute("error_msg", "Data not saved");
        return "errorPage";
    }

    @GetMapping("/editRoute")
    public String editRoutePage(@RequestParam(name = "id", required = false) Long id, Model model) {
        if (id == null) {
            model.addAttribute("route", new Routes());
            //model.addAttribute("lecturerService", lecturerDAO);
            return "editRoute";
        }

        Routes route = routeDAO.getById(id);

        if (route == null) {
            model.addAttribute("error_msg", "There is no route with this id =" + id);
            return "errorPage";
        }

        model.addAttribute("route", route);
        //model.addAttribute("lecturerService", lecturerDAO);
        return "editRoute";
    }

    @GetMapping("/route")
    public String routePage(@RequestParam(name = "id") Long id, Model model) {
        Routes route = routeDAO.getById(id);

        if (route == null) {
            model.addAttribute("error_msg", "В базе нет маршрута с ID = " + id);
            return "errorPage";
        }

        model.addAttribute("route", route);
        model.addAttribute("ticketService", ticketDAO);
        return "route";
    }

    @GetMapping("/routes")
    public String routeListPage(Model model) {
        model.addAttribute("routeService", routeDAO);
        model.addAttribute("ticketService", ticketDAO);
        return "routes";
    }

    @GetMapping("/buy")
    public String buyPage(@RequestParam(name = "id") Long id, Model model) {
        model.addAttribute("ticket", ticketDAO.getById(id));
        return "buy";
    }

    @PostMapping("/buy")
    public String buyPostPage(@RequestParam(name = "ticketId") Long id,
                              @RequestParam(name = "clientFullname") String fullname,
                              @RequestParam(name = "clientPhone") String phone,
                              @RequestParam(name = "clientEmail") String email,
                              Model model) {
        Clients client = clientDAO.searchClientByEmail(email);
        if (client == null)
            client = clientDAO.searchClientByPhone(phone);
        if (client == null)
            client = clientDAO.searchClientByFullname(fullname);

        if (client == null) {
            client = new Clients(fullname, phone, email);
            clientDAO.update(client);
        }

        Tickets ticket = ticketDAO.getById(id);
        ticket.status = "SOLD";
        ticket.client = client;
        ticketDAO.update(ticket);
        return "redirect:/route?id=" + ticket.route.getId().toString();

    }

    @PostMapping("/removeRoute")
    public String removeRoutePage(@RequestParam(name = "id") Long id) {
        routeDAO.deleteById(id);
        return "redirect:/routes";
    }
}
