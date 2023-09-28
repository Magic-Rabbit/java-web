package cmc.webprak.controllers;

import cmc.webprak.dao.BusDAO;
import cmc.webprak.dao.CompanyDAO;
import cmc.webprak.dao.RouteDAO;
import cmc.webprak.dao.TicketDAO;
import cmc.webprak.dao.impl.BusDAOImpl;
import cmc.webprak.dao.impl.CompanyDAOImpl;
import cmc.webprak.dao.impl.RouteDAOImpl;
import cmc.webprak.dao.impl.TicketDAOImpl;
import cmc.webprak.tables.Clients;
import cmc.webprak.tables.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Timestamp;

@Controller
public class CompanyController {


    @Autowired
    private final RouteDAO routeDAO = new RouteDAOImpl();

    @Autowired
    private final TicketDAO ticketDAO = new TicketDAOImpl();

    @Autowired
    private final CompanyDAO companyDAO = new CompanyDAOImpl();

    @Autowired
    private final BusDAO busDAO = new BusDAOImpl();

    @PostMapping("/saveCompany")
    public String saveCompanyPage(@RequestParam(name = "companyId") String id,
                                 @RequestParam(name = "companyName") String name,
                                 @RequestParam(name = "creationDate") String date,
                                 Model model) {
        Company company = companyDAO.getById(Long.valueOf(id));

        if (company != null) {
            company.setCompany_name(name);
            company.setCreation_date(Timestamp.valueOf(date));
            companyDAO.update(company);
            return "redirect:/companies";
        }

        model.addAttribute("error_msg", "Data not saved");
        return "errorPage";
    }

    @PostMapping("/removeCompany")
    public String removeCompanyPage(@RequestParam(name = "id") Long id) {
        companyDAO.deleteById(id);
        return "redirect:/companies";
    }

    @GetMapping("/editCompany")
    public String editCompanyPage(@RequestParam(name = "id", required = false) Long id, Model model) {
        if (id == null) {
            model.addAttribute("Company", new Company());
            //model.addAttribute("lecturerService", lecturerDAO);
            return "editCompany";
        }

        Company company = companyDAO.getById(id);

        if (company == null) {
            model.addAttribute("error_msg", "There is no company with this id =" + id);
            return "errorPage";
        }

        model.addAttribute("company", company);
        //model.addAttribute("lecturerService", lecturerDAO);
        return "editCompany";
    }

    @GetMapping("/company")
    public String companyPage(@RequestParam(name = "id") Long id, Model model) {
        Company company = companyDAO.getById(id);

        if (company == null) {
            model.addAttribute("error_msg", "В базе нет компании с ID = " + id);
            return "errorPage";
        }

        model.addAttribute("company", company);
        model.addAttribute("busService", busDAO);
        return "company";
    }

    @GetMapping("/companies")
    public String companyListPage(Model model) {
        model.addAttribute("companyService", companyDAO);
        return "companies";
    }
}
