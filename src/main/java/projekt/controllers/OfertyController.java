package projekt.controllers;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpSession;
import projekt.models.Oferty;
import projekt.models.Users;
import projekt.models.Wysylka;
import projekt.services.OfertyService;
import projekt.services.UsersService;
import projekt.services.WysylkaService;

@Controller
public class OfertyController {
	@Autowired
	private OfertyService service;
	@Autowired
	private WysylkaService serviceW;
	@Autowired
	private UsersService serviceU;
	
	public OfertyController() {
	} 
	
	
	@RequestMapping("oferty")
	public String viewOfertyLista(Model model) {
	List<Oferty> lo=service.findAktualne();
	model.addAttribute("lo", lo);
	return "oferty"; 
	}
	
	@RequestMapping("oferty/search")
	public String viewOfertyBySearch(@RequestParam("query") String query, Model model) {
	
	List<Oferty> lo=service.findByName(query);
	model.addAttribute("lo", lo);
	return "oferty"; 
	}
	
	@RequestMapping("oferty/obejrzyj/{id}")
	public ModelAndView viewOfertaPoId(@PathVariable(name = "id") Long id, Model model) {
	    ModelAndView mav = new ModelAndView("obejrzyj");
	    Optional<Oferty> ofertaOptional = service.findById(id);
	    if (ofertaOptional.isPresent()) {
	    	String typOferty = getTypOferty(ofertaOptional.get().getTyp_oferty());
	        Oferty oferta = ofertaOptional.get();
	        mav.addObject("oferta", oferta);
	        mav.addObject("typOferty", typOferty);
	    }
	    return mav;
	}
	
	private String getTypOferty(Integer typOferty) {
	    switch(typOferty) {
	        case 0:
	            return "Sprzedaż";
	        case 1:
	            return "Wymiana";
	        case 2:
	            return "Zapytanie";
	        default:
	            return "Nieznany typ oferty";
	    }
	}
	
	@RequestMapping("oferty/dodaj")
	public String viewFormNewOferta(Model model, HttpSession session) {
		Long currentUserId = (Long) session.getAttribute("userId");
		if (currentUserId == null) {
			return "logowanie";
		}
		Optional<Users> lodUser = serviceU.findById(currentUserId);
		
		if(lodUser.isPresent()) {
			Users user =  lodUser.get();
			if(user.isBlock()) {
				return "zablokowany";
			}
		}
	return "dodaj"; 
	}
	
	@PostMapping("saveOferta")
	public String saveOferta(@RequestParam("oferta_nazwa_ksiazki") String oferta_nazwa_ksiazki, 
							@RequestParam("oferta_typ_oferty") int oferta_typ_oferty,
							@RequestParam("oferta_cena") Double oferta_cena,
							@RequestParam("oferta_opis") String oferta_opis,
							@RequestParam("oferta_stan") String oferta_stan,Model model, HttpSession session) {
		Long currentUserId = (Long) session.getAttribute("userId");
		if (currentUserId == null) {
			return "logowanie";
		}
		
	    Oferty oferta = new Oferty();
	    oferta.setNazwa_ksiazki(oferta_nazwa_ksiazki);
	    oferta.setTyp_oferty(oferta_typ_oferty);
	    oferta.setCena(oferta_cena);
	    oferta.setOpis(oferta_opis);
	    oferta.setStan(oferta_stan);
	    LocalDate today = LocalDate.now();
	    String formattedDate = today.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
	    oferta.setData_dodString(formattedDate);
	    oferta.setSprzedajacy(currentUserId);
	    service.save(oferta);
	    return "redirect:/oferty";
	}
	
	@RequestMapping("oferty/mojeOferty")
	public String viewMojeOferty(Model model, HttpSession session) {
		Long currentUserId = (Long) session.getAttribute("userId");
		if (currentUserId == null) {
			return "logowanie";
		}

		List<Oferty> lo = service.findAllByIdSprzedajacy(currentUserId);
		model.addAttribute("lo", lo);
		return "mojeOferty";
	}
	
	@RequestMapping("oferty/mojeZakupy")
	public String viewMojeZakupy(Model model, HttpSession session) {
		Long currentUserId = (Long) session.getAttribute("userId");
		if (currentUserId == null) {
			return "logowanie";
		}

		List<Oferty> lo = service.findZakonczonePoIdKupujacy(currentUserId); 
		List<Wysylka> lw = serviceW.findByIdKupujacy(currentUserId);
		model.addAttribute("lo", lo);
		model.addAttribute("lw", lw);
		return "mojeZakupy";
	}
	
	@RequestMapping("oferty/mojaSprzedaz")
	public String viewMojeSprzedaz(Model model, HttpSession session) {
		Long currentUserId = (Long) session.getAttribute("userId");
		if (currentUserId == null) {
			return "logowanie";
		}
		List<Oferty> lo = service.findZakonczonePoIdSprzedajacy(currentUserId); 
		List<Wysylka> lw = serviceW.findByIdSprzedajacy(currentUserId); 
		model.addAttribute("lo", lo);
		model.addAttribute("lw", lw);
		return "mojaSprzedaz";
	}
	
	@RequestMapping("oferty/edytuj/{id}")
	public ModelAndView viewEditOferty(@PathVariable(name = "id") Long id, Model model, HttpSession session) {
		Long currentUserId = (Long) session.getAttribute("userId");
		if (currentUserId == null) {
			ModelAndView mav = new ModelAndView("logowanie");
			return mav;
		}
		 ModelAndView mav = new ModelAndView("edytuj");
		 Optional<Oferty> ofertaOptional = service.findById(id);
		 if (ofertaOptional.isPresent()) {
		    String typOferty = getTypOferty(ofertaOptional.get().getTyp_oferty());
		     Oferty oferta = ofertaOptional.get();
		     
		     //zabezpieczenie przed edytowaniem nie swojej oferty
		     if(oferta.getSprzedajacy()!=currentUserId) {
		    	ModelAndView mav1 = new ModelAndView("logowanie");
				return mav1;
		     }
		    	 
		     mav.addObject("oferta", oferta);
		     mav.addObject("typOferty", typOferty);
		   }
	return mav;
	}
	
	@RequestMapping("oferty/usun/{id}")
	public ModelAndView viewUsunOferte(@PathVariable(name = "id") Long id, Model model) {
		 ModelAndView mav = new ModelAndView("usunConf");
		 Optional<Oferty> ofertaOptional = service.findById(id);
		 if (ofertaOptional.isPresent()) {
		    String typOferty = getTypOferty(ofertaOptional.get().getTyp_oferty());
		     Oferty oferta = ofertaOptional.get();
		     mav.addObject("oferta", oferta);
		     mav.addObject("typOferty", typOferty);
		   }
	return mav;
	}
	
	@PostMapping("usun/{id}")
	public ModelAndView usunOferte(@PathVariable(name = "id") Long id, Model model, HttpSession session) {
		Long currentUserId = (Long) session.getAttribute("userId");
		if (currentUserId == null) {
			ModelAndView mav = new ModelAndView("logowanie");
			return mav;
		}
		
		 ModelAndView mav = new ModelAndView("mojeOferty");
		 Optional<Oferty> ofertaOptional = service.findById(id);
		 if (ofertaOptional.isPresent()) {
			Oferty oferta = ofertaOptional.get();
			if(oferta.getData_zak()==null)
				service.deleteById(id);
			/*
			 * możliwa wiadomość jeżeli jest nullem, czyli jest zakończone, możliwe tez zabezpieczenie czy id użytkownika = id sprzedającego
			 */
		   }
	List<Oferty> lo=service.findAllByIdSprzedajacy(currentUserId);
	model.addAttribute("lo", lo);
	return mav;
	}
	
	@PostMapping("saveEditOferta/{id}")
	public ModelAndView saveEditOferte(@PathVariable(name = "id") Long id,
										@RequestParam("oferta_nazwa_ksiazki") String oferta_nazwa_ksiazki, 
										@RequestParam("oferta_typ_oferty") int oferta_typ_oferty,
										@RequestParam("oferta_cena") Double oferta_cena,
										@RequestParam("oferta_opis") String oferta_opis,
										@RequestParam("oferta_stan") String oferta_stan,Model model, HttpSession session) {
		Long currentUserId = (Long) session.getAttribute("userId");
		if (currentUserId == null) {
			ModelAndView mav = new ModelAndView("logowanie");
			return mav;
		}
		 ModelAndView mav = new ModelAndView("mojeOferty");
		 Optional<Oferty> ofertaOptional = service.findById(id);
		 if (ofertaOptional.isPresent()) {
		    Oferty oferta = ofertaOptional.get();
		    oferta.setNazwa_ksiazki(oferta_nazwa_ksiazki);
		    oferta.setTyp_oferty(oferta_typ_oferty);
		    oferta.setCena(oferta_cena);
		    oferta.setOpis(oferta_opis);
		    oferta.setStan(oferta_stan);
		    service.save(oferta);
		   }
	List<Oferty> lo=service.findAllByIdSprzedajacy(currentUserId);
	model.addAttribute("lo", lo);
	return mav;
	}
	
}