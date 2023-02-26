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
import projekt.models.Wysylka;
import projekt.services.OfertyService;
import projekt.services.WysylkaService;
@Controller
public class WysylkaController {
	@Autowired
	private WysylkaService service;
	@Autowired
	private OfertyService serviceO;
	
	
	public WysylkaController() {
	} 
	
	
	@RequestMapping("wysylka/wyswietlDane/{id}")
	public ModelAndView viewDaneWysylki(@PathVariable(name = "id") Long id, Model model, HttpSession session) {
		Long currentUserId = (Long) session.getAttribute("userId");
		if(currentUserId==null) {
			ModelAndView mav = new ModelAndView("logowanie");
			return mav;
		}
		
		 ModelAndView mav = new ModelAndView("wyswietlDane");
		 Wysylka wysylka = service.findByIdOferty(id);
		 if(wysylka.getSprzedajacy()!=currentUserId) {
			ModelAndView mav1 = new ModelAndView("kontoScrUser");
			return mav1;
		 }
		 
		 Optional<Oferty> ofertaOptional = serviceO.findById(id);
		 if (ofertaOptional.isPresent()) {
			 String typOferty = getTypOferty(ofertaOptional.get().getTyp_oferty());
			 Oferty oferta = ofertaOptional.get();
			 model.addAttribute("wysylka", wysylka);
			 model.addAttribute("oferta", oferta);
			 mav.addObject("typOferty", typOferty);
		 }
		 return mav;
	}
	
	@RequestMapping("wysylka/zmienStan/{id}")
	public ModelAndView viewZmienStanWysylki(@PathVariable(name = "id") Long id, Model model, HttpSession session) {
		Long currentUserId = (Long) session.getAttribute("userId");
		if(currentUserId==null) {
			ModelAndView mav = new ModelAndView("logowanie");
			return mav;
		}
		
		 ModelAndView mav = new ModelAndView("zmienStan");
		 Wysylka wysylka = service.findByIdOferty(id);
		 if(wysylka.getSprzedajacy()!=currentUserId) {
				ModelAndView mav1 = new ModelAndView("kontoScrUser");
				return mav1;
			 }
		 Optional<Oferty> ofertaOptional = serviceO.findById(id);
		 if (ofertaOptional.isPresent()) {
			 String typOferty = getTypOferty(ofertaOptional.get().getTyp_oferty());
			 Oferty oferta = ofertaOptional.get();
			 model.addAttribute("wysylka", wysylka);
			 model.addAttribute("oferta", oferta);
			 mav.addObject("typOferty", typOferty);
		 }
		 return mav;
	}
	
	@PostMapping("wysylka/zaaktualizuj/{id}")
	public ModelAndView saveStanWysylki(@PathVariable(name = "id") Long id, @RequestParam("wysylka_stan_wys") String wysylka_stan_wys, Model model, HttpSession session) {
		Long currentUserId = (Long) session.getAttribute("userId");
		if(currentUserId==null) {
			ModelAndView mav = new ModelAndView("logowanie");
			return mav;
		}
		
		ModelAndView mav = new ModelAndView("mojaSprzedaz");
		Optional<Wysylka> wysylkaOptional = service.findById(id);
		Wysylka wysylka = wysylkaOptional.get();
		if (wysylkaOptional.isPresent()) {
			
			if(wysylka.getSprzedajacy()!=currentUserId) {
				ModelAndView mav1 = new ModelAndView("kontoScrUser");
				return mav1;
			 }
			
			wysylka.setStan_wys(wysylka_stan_wys);
			service.save(wysylka);
		}
		List<Oferty> lo=serviceO.findZakonczonePoIdSprzedajacy(currentUserId); 
		List<Wysylka> lw=service.findByIdSprzedajacy(currentUserId); 
		model.addAttribute("lo", lo);
		model.addAttribute("lw", lw);
		return mav;
	}
	
	@RequestMapping("/wysylka/nowa/{id}")
	public ModelAndView nowaWysylka(@PathVariable(name = "id") Long id, Model model, HttpSession session) {
		Long currentUserId = (Long) session.getAttribute("userId");
		if(currentUserId==null) {
			ModelAndView mav = new ModelAndView("logowanie");
			return mav;
		}
		
		ModelAndView mav = new ModelAndView("nowaWys");
		Optional<Oferty> ofertaOptional = serviceO.findById(id);
		if (ofertaOptional.isPresent()) {
			Oferty oferta = ofertaOptional.get();
			model.addAttribute("oferta", oferta);
		}
		return mav;
	}
	
	@PostMapping("wysylka/save/{id}")
	public String saveWysylka(@PathVariable(name = "id") Long id,
							@RequestParam("wysylka_imie_nazwisko") String wysylka_imie_nazwisko, 
							@RequestParam("wysylka_telefon") Long wysylka_telefon,
							@RequestParam("wysylka_adres_1") String wysylka_adres_1,
							@RequestParam("wysylka_adres_2") String wysylka_adres_2,
							@RequestParam("wysylka_kod_pocztowy") String wysylka_kod_pocztowy,
							@RequestParam("wysylka_miasto") String wysylka_miasto,
							@RequestParam("wysylka_opcja_dost") String wysylka_opcja_dost,
							Model model, HttpSession session) {
		Long currentUserId = (Long) session.getAttribute("userId");
		if(currentUserId==null) {
			return "logowanie";
		}
		
		Optional<Oferty> ofertaOptional = serviceO.findById(id);
		Wysylka newWysylka = new Wysylka();
		if (ofertaOptional.isPresent()) {
			Oferty oferta = ofertaOptional.get();
			LocalDate today = LocalDate.now();
		    String formattedDate = today.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		    oferta.setData_zakString(formattedDate);
		    
		    newWysylka.setSprzedajacy(oferta.getSprzedajacy());
		    newWysylka.setOferta_id(oferta.getId());
		    oferta.setKupujacy(currentUserId);
		    serviceO.save(oferta);
		}else {
			String message = "Nie znaleziono oferty, 404 X_X";
			model.addAttribute(message);
			return "error";
		}
		newWysylka.setKupujacy(currentUserId);
		newWysylka.setImie_nazwisko(wysylka_imie_nazwisko);
		newWysylka.setTelefon(wysylka_telefon);
		newWysylka.setAdres_1(wysylka_adres_1);
		newWysylka.setAdres_2(wysylka_adres_2);
		newWysylka.setKod_pocztowy(wysylka_kod_pocztowy);
		newWysylka.setMiasto(wysylka_miasto);
		newWysylka.setOpcja_dost(wysylka_opcja_dost);
		newWysylka.setStan_wys("Sprzedający otrzymal zamowienie");
		service.save(newWysylka);
		
		List<Oferty> lo=serviceO.findZakonczonePoIdKupujacy(currentUserId); 
		List<Wysylka> lw=service.findByIdKupujacy(currentUserId);
		model.addAttribute("lo", lo);
		model.addAttribute("lw", lw);
		return "mojeZakupy"; 
		
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
	
}