package com.mimosSpring.mimos.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.mimosSpring.mimos.model.UtenteEntity;
import com.mimosSpring.mimos.service.UtenteService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;


@Controller
public class LoginController {

	private UtenteService utenteService;
	
    @Autowired
    public LoginController(UtenteService utenteService) {
    	this.utenteService = utenteService;
    }

    @RequestMapping(value = "/mimos", method = RequestMethod.GET)
    public String index() {
        return "index";
    }
    
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        session.invalidate();
        return "index";
    }

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String home(Map<String, Object> model) {
        return "home";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(@RequestParam(name = "username", required = true) String username,
                        @RequestParam(name = "password", required = true) String password,
                        HttpServletRequest request,
                        Map<String, Object> model) {
        UtenteEntity retrived = utenteService.findByUsernameAndPassword(username, password);
        System.out.println(retrived==null);
        System.out.println(username + " " + password);
        if (retrived != null) {
        	String returnString = "";
            int role = (int) utenteService.findIdRuoloByUsername(username);
            System.out.println(role);
            switch (role) {
            case (1):
            	break;
            case (2):
            	//dottore
            	break;
            case (3):
            	returnString = "homePaziente";
            	//paziente
            	break;
            case (4):
            	break;
            }
            //model.put("user", loginEntity);
            HttpSession session = request.getSession(true);
            session.setAttribute("username", username);
            session.setAttribute("utente", retrived);
            return returnString;
        } else {
            return "index";
        }

    }
    /*
    public HashMap colorPanel(List<AutoEntity> autoEntitySet)
    {
        HashMap color = new HashMap();
        for (AutoEntity autoEntity:autoEntitySet) {
            List<DatiEntity> listaDati = autoEntity.getDatiEntitySet();
            DatiEntity dato = listaDati.get(listaDati.size()-1);
            Scanner scanner = new Scanner(autoEntity.getRevisione()).useDelimiter("/");
            int gg_revisione = scanner.nextInt();
            int mm_revisione = scanner.nextInt();
            int aa_revisione = scanner.nextInt();
            scanner = new Scanner(autoEntity.getTagliandoData()).useDelimiter("/");
            int gg_tagliando = scanner.nextInt();
            int mm_tagliando = scanner.nextInt();
            int aa_tagliando = scanner.nextInt();
            scanner = new Scanner(dato.getData()).useDelimiter("/");
            int gg_dato = scanner.nextInt();
            int mm_dato = scanner.nextInt();
            int aa_dato = scanner.nextInt();
            int revisione = gg_revisione + mm_revisione * 365 / 12 + aa_revisione * 365;
            int tagliando = gg_tagliando + mm_tagliando * 365 / 12 + aa_tagliando * 365;
            int data = gg_dato + mm_dato * 365 / 12 + aa_dato * 365;

            int km = dato.getKm() - autoEntity.getTagliandoKm();

            if (data - revisione > 365 + 365 / 12 * 11) {
                color.put(autoEntity.getCodDispositivo(), "yellow");
            }
            else if (((data - tagliando) > 365 + 365 / 12 * 11) || km > 14500) {
                color.put(autoEntity.getCodDispositivo(), "yellow");
            }
            else {
                color.put(autoEntity.getCodDispositivo(), "primary");
            }
            for (DatiEntity datiEntity:autoEntity.getDatiEntitySet()) {
                if(datiEntity.getCodErrore() != null && datiEntity.isStato() == false)
                    color.put(autoEntity.getCodDispositivo(), "red");
            }

        }
        return color;
	}
     */
}
