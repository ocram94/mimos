//package com.mimosSpring.mimos.controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
//
//import com.mimosSpring.mimos.DTO.UtenteDTO;
//import com.mimosSpring.mimos.converter.UtenteConverter;
//import com.mimosSpring.mimos.model.SpecialitaEntity;
//import com.mimosSpring.mimos.model.UtenteEntity;
//import com.mimosSpring.mimos.service.MisuraService;
//import com.mimosSpring.mimos.service.SpecialitaService;
//import com.mimosSpring.mimos.service.UtenteService;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpSession;
//import java.util.*;
//import java.sql.Date;
//
//@Controller
//public class IndexController {
//
//	private UtenteService utenteService;
//	private UtenteConverter utenteConverter;
//	private MisuraService misuraService;
//	private SpecialitaService specialitaService;
//
//	@Autowired
//	public IndexController(UtenteService utenteService, SpecialitaService specialitaService) {
//		this.utenteService = utenteService;
//		this.specialitaService = specialitaService;
//    	this.misuraService = misuraService;
//		this.utenteConverter = new UtenteConverter();
//	}
//
//	@RequestMapping(value = "/", method = RequestMethod.GET)
//	public String index() {
//		return "index";
//	}
//
//	@RequestMapping(value = "/logout", method = RequestMethod.GET)
//	public String logout(HttpServletRequest request) {
//		HttpSession session = request.getSession(true);
//		session.invalidate();
//		return "index";
//	}
//
//	@RequestMapping(value = "/homeDispatcher", method = RequestMethod.GET)
//	public String home(Map<String, Object> model) {
//		return "homePaziente";
//	}
//
//	@RequestMapping(value = "/registerIndex", method = RequestMethod.POST)
//	public String registerIndex(@RequestParam(name = "username", required = true) String username,
//			@RequestParam(name = "password", required = true) String password,
//			@RequestParam(name = "asAdmin", required = false) boolean asAdmin, HttpServletRequest request,
//			Map<String, Object> model) {
//		UtenteEntity retrived = utenteService.findByUsername(username);
//		System.out.println(retrived == null);
//		System.out.println(username + " " + password);
//		String returnString = "";
//		if (retrived == null) {
//			UtenteEntity nuovoUtente = new UtenteEntity();
//			nuovoUtente.setPassword(password);
//			nuovoUtente.setUsername(username);
//			UtenteDTO ud = utenteConverter.convertToDTO(nuovoUtente);
//			//ottengo una stringa che è linkata all'oggetto java
//			//dato che la jsp non può gestire oggetti complessi
//			String udId = UUID.randomUUID().toString();
//			//effetto uno storage dell'oggeto nella session
//			HttpSession session = request.getSession(true);
//			session.setAttribute(udId, ud);
//			//ma nella request gli passo la stringa di riferimento
//			model.put("udId", udId);
//			model.put("utenteIncompleto", ud);
//			model.put("asAdmin", asAdmin);
//			returnString = "register";
//		} else {
//			model.put("messaggio", "già registrato");
//			returnString = "index";
//		}
//		return returnString;
//	}
//
//	@RequestMapping(value = "/registerComplete", method = RequestMethod.POST)
//	public String registerComplete(@RequestParam(name = "udId", required = true) String udId,
//			@RequestParam(name = "nome", required = true) String nome,
//			@RequestParam(name = "cognome", required = true) String cognome,
//			@RequestParam(name = "codiceFiscale", required = true) String codiceFiscale,
//			@RequestParam(name = "citta", required = true) String citta,
//			@RequestParam(name = "dataNascita", required = true) Date dataNascita, HttpServletRequest request,
//			Map<String, Object> model) {
//		//dalla jsp mi prendo il riferimento all'oggetto
//		//mi prendo l'oggetto dalla session
//		//rimuovo l'oggetto dalla session
//		UtenteDTO utenteIncompelto = (UtenteDTO) request.getSession().getAttribute(udId);
//		request.getSession().removeAttribute(udId);
//		utenteIncompelto.setCitta(citta);
//		utenteIncompelto.setCodice_fiscale(codiceFiscale);
//		utenteIncompelto.setCognome(cognome);
//		utenteIncompelto.setData_nascita(dataNascita);
//		utenteIncompelto.setId_ruolo(3);
//		utenteIncompelto.setNome(nome);
//		UtenteEntity utenteCompelto = utenteConverter.convertToEntity(utenteIncompelto);
//		System.out.println(utenteCompelto);
//		utenteService.save(utenteCompelto);
//		return "index";
//	}
//
//	@RequestMapping(value = "/login", method = RequestMethod.POST)
//	public String login(@RequestParam(name = "username", required = true) String username,
//			@RequestParam(name = "password", required = true) String password, HttpServletRequest request,
//			Map<String, Object> model) {
//		UtenteEntity retrived = utenteService.findByUsernameAndPassword(username, password);
//		System.out.println(retrived == null);
//		System.out.println(username + " " + password);
//		String returnString = "";
//		if (retrived != null) {
//			int role = (int) utenteService.findIdRuoloByUsername(username);
//			System.out.println(role);
//			switch (role) {
//			case (0):
//				List<UtenteEntity> utEntities = utenteService.findAll();
//				List<SpecialitaEntity> spEntities = specialitaService.findAll();
//				model.put("listaUtenti", utEntities);
//				model.put("listaSpecialita", spEntities);
//				returnString = "homeAdmin";
//				break;
//			case (1):
//				// dottore
//				break;
//			case (2):
//				// paziente
//				break;
//			case (3):
//                HttpSession session = request.getSession(true);
//                session.setAttribute("username", username);
//                session.setAttribute("utente", retrived);
//        		return "redirect:/paziente/home";
//			}
//			model.put("utente", retrived);
//			HttpSession session = request.getSession(true);
//			session.setAttribute("username", username);
//			session.setAttribute("utente", retrived);
//		} else {
//			returnString = "index";
//		}
//		return returnString;
//	}
//	/*
//	 * public HashMap colorPanel(List<AutoEntity> autoEntitySet) { HashMap color =
//	 * new HashMap(); for (AutoEntity autoEntity:autoEntitySet) { List<DatiEntity>
//	 * listaDati = autoEntity.getDatiEntitySet(); DatiEntity dato =
//	 * listaDati.get(listaDati.size()-1); Scanner scanner = new
//	 * Scanner(autoEntity.getRevisione()).useDelimiter("/"); int gg_revisione =
//	 * scanner.nextInt(); int mm_revisione = scanner.nextInt(); int aa_revisione =
//	 * scanner.nextInt(); scanner = new
//	 * Scanner(autoEntity.getTagliandoData()).useDelimiter("/"); int gg_tagliando =
//	 * scanner.nextInt(); int mm_tagliando = scanner.nextInt(); int aa_tagliando =
//	 * scanner.nextInt(); scanner = new Scanner(dato.getData()).useDelimiter("/");
//	 * int gg_dato = scanner.nextInt(); int mm_dato = scanner.nextInt(); int aa_dato
//	 * = scanner.nextInt(); int revisione = gg_revisione + mm_revisione * 365 / 12 +
//	 * aa_revisione * 365; int tagliando = gg_tagliando + mm_tagliando * 365 / 12 +
//	 * aa_tagliando * 365; int data = gg_dato + mm_dato * 365 / 12 + aa_dato * 365;
//	 * 
//	 * int km = dato.getKm() - autoEntity.getTagliandoKm();
//	 * 
//	 * if (data - revisione > 365 + 365 / 12 * 11) {
//	 * color.put(autoEntity.getCodDispositivo(), "yellow"); } else if (((data -
//	 * tagliando) > 365 + 365 / 12 * 11) || km > 14500) {
//	 * color.put(autoEntity.getCodDispositivo(), "yellow"); } else {
//	 * color.put(autoEntity.getCodDispositivo(), "primary"); } for (DatiEntity
//	 * datiEntity:autoEntity.getDatiEntitySet()) { if(datiEntity.getCodErrore() !=
//	 * null && datiEntity.isStato() == false)
//	 * color.put(autoEntity.getCodDispositivo(), "red"); }
//	 * 
//	 * } return color; }
//	 */
//}