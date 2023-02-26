package projekt.controllers;
import java.sql.Date;
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
import projekt.models.Ksiazki;
import projekt.services.KsiazkiService;

@Controller
public class KsiazkiController {
	@Autowired
	private KsiazkiService service;
	
	public KsiazkiController() {
	} 
	
	
	@RequestMapping("ksiazki")
	public String viewKsiazkiLista(Model model) {
	List<Ksiazki> lk=service.findAll();
	model.addAttribute("lk", lk);
	return "ksiazki"; 
	}
	
	@RequestMapping("ksiazka/listaAdmin")
	public String viewKsiazkiListaAdmin(Model model, HttpSession session) {
		Long currentUserId = (Long) session.getAttribute("userId");
		if(currentUserId==null||currentUserId!=1) {
			return "logowanie";
		}	
	List<Ksiazki> lk=service.findAll();
	model.addAttribute("lk", lk);
	return "ksiazkiListaAdmin"; 
	}
	
	@RequestMapping("ksiazki/search")
	public String viewOfertyBySearch(@RequestParam("query") String query, Model model) {
	
	List<Ksiazki> lk=service.findByName(query);
	model.addAttribute("lk", lk);
	return "ksiazki"; 
	}
	
	@RequestMapping("ksiazki/obejrzyj/{id}")
	public ModelAndView viewKsiazkiPoId(@PathVariable(name = "id") Long id, Model model) {
	    ModelAndView mav = new ModelAndView("error");
	    Optional<Ksiazki> ofertaOptional = service.findById(id);
	    if (ofertaOptional.isPresent()) {
	    	mav = new ModelAndView("obejrzyjK");
	        Ksiazki ksiazka = ofertaOptional.get();
	        mav.addObject("ksiazka", ksiazka);
	    }
	    return mav;
	}
	
	@RequestMapping("ksiazka/dodaj")
	public String dodajKsiazke(Model model, HttpSession session) {
		Long currentUserId = (Long) session.getAttribute("userId");
		if(currentUserId==null||currentUserId!=1) {
			return "logowanie";
		}
		return "dodajKsiazke";
	}
	
	@PostMapping("ksiazka/save")
    public String saveKsiazkaConf(@RequestParam("ksiazka_Nazwa") String ksiazka_Nazwa, 
								@RequestParam("ksiazka_Gatunek") String ksiazka_Gatunek,
								@RequestParam("ksiazka_Opis") String ksiazka_Opis,
								@RequestParam("ksiazka_Data_wyd") Date ksiazka_Data_wyd,
								@RequestParam("ksiazka_Autorzy") String ksiazka_Autorzy,
								@RequestParam("ksiazka_Wydawnictwo") String ksiazka_Wydawnictwo,
								Model model ,HttpSession session) {
		Long currentUserId = (Long) session.getAttribute("userId");
		if (currentUserId == null || currentUserId !=1) {
			return "logowanie";
		}
		System.out.println("Dodano książke");
        Ksiazki ksiazka = new Ksiazki();
        ksiazka.setNazwa(ksiazka_Nazwa);
        ksiazka.setGatunek(ksiazka_Gatunek);
        ksiazka.setOpis(ksiazka_Opis);
        ksiazka.setData_wyd(ksiazka_Data_wyd);
        ksiazka.setAutorzy(ksiazka_Autorzy);
        ksiazka.setWydawnictwo(ksiazka_Wydawnictwo);
        service.save(ksiazka);

        return "redirect:/ksiazki"; 
    }
	
	@RequestMapping("ksiazka/edytuj/{id}")
	public ModelAndView viewEditKsiazka(@PathVariable(name = "id") Long id, Model model, HttpSession session) {
		Long currentUserId = (Long) session.getAttribute("userId");
		if (currentUserId == null || currentUserId !=1) {
			ModelAndView mav = new ModelAndView("logowanie");
			return mav;
		}
		
		 ModelAndView mav = new ModelAndView("error");
		 Optional<Ksiazki> ksiazkaOptional = service.findById(id);
		 if (ksiazkaOptional.isPresent()) {
			 Ksiazki ksiazka = ksiazkaOptional.get();
			 mav = new ModelAndView("edytujKsiazka");
		     mav.addObject("ksiazka", ksiazka);
		   }
	return mav;
	}
	
	@PostMapping("ksiazka/saveEdit/{id}")
    public String saveKsiazkaEditConf(@PathVariable(name = "id") Long id,
    							@RequestParam("ksiazka_Nazwa") String ksiazka_Nazwa, 
								@RequestParam("ksiazka_Gatunek") String ksiazka_Gatunek,
								@RequestParam("ksiazka_Opis") String ksiazka_Opis,
								@RequestParam("ksiazka_Data_wyd") Date ksiazka_Data_wyd,
								@RequestParam("ksiazka_Autorzy") String ksiazka_Autorzy,
								@RequestParam("ksiazka_Wydawnictwo") String ksiazka_Wydawnictwo,
								Model model){
		//System.out.println("Dodano książke");
        Optional<Ksiazki> ksiazkaOptional = service.findById(id);
        Ksiazki ksiazka = ksiazkaOptional.get();
        
        ksiazka.setNazwa(ksiazka_Nazwa);
        ksiazka.setGatunek(ksiazka_Gatunek);
        ksiazka.setOpis(ksiazka_Opis);
        ksiazka.setData_wyd(ksiazka_Data_wyd);
        ksiazka.setAutorzy(ksiazka_Autorzy);
        ksiazka.setWydawnictwo(ksiazka_Wydawnictwo);
        service.save(ksiazka);

        return "redirect:/ksiazki"; 
    }
	
	@RequestMapping("ksiazka/usun/{id}")
	public ModelAndView viewUsunOferte(@PathVariable(name = "id") Long id, Model model, HttpSession session) {
		Long currentUserId = (Long) session.getAttribute("userId");
		if (currentUserId == null || currentUserId !=1) {
			ModelAndView mav = new ModelAndView("logowanie");
			return mav;
		}
		
		 ModelAndView mav = new ModelAndView("error");
		 Optional<Ksiazki> ksiazkaOptional = service.findById(id);
		 
		 if (ksiazkaOptional.isPresent()) {
			 Ksiazki ksiazka = ksiazkaOptional.get();
			 mav = new ModelAndView("ksiazkaUsunConf");
		     mav.addObject("ksiazka", ksiazka);
		   }
	return mav;
	}
	
	@PostMapping("ksiazka/usunConf/{id}")
	public String usunKsiazke(@PathVariable(name = "id") Long id, Model model, HttpSession session) {
		Long currentUserId = (Long) session.getAttribute("userId");
		if (currentUserId == null || currentUserId !=1) {
			return "logowanie";
		}
		
		 Optional<Ksiazki> ksiazkaOptional = service.findById(id);
		 if (ksiazkaOptional.isPresent()) {
			 service.deleteById(id);
		   }
		 
		 return "redirect:/ksiazki"; 
	}
}