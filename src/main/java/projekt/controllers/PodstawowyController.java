package projekt.controllers;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;
import projekt.models.Oferty;
import projekt.models.Users;
import projekt.models.Kontakt;
import projekt.models.Ksiazki;
import projekt.services.KontaktService;
import projekt.services.KsiazkiService;
import projekt.services.OfertyService;
import projekt.services.UsersService;
@Controller
public class PodstawowyController {
	@Autowired
	private OfertyService service;
	@Autowired
	private KsiazkiService serviceK;
	@Autowired
	private KontaktService serviceKontakt;
	@Autowired
	private UsersService serviceU;
	
	@RequestMapping("index2")
	public String viewLastOferty(Model model) {
	List<Oferty> lo=service.findLast();
	List<Ksiazki> lk=serviceK.findLast();
	model.addAttribute("lo", lo);
	model.addAttribute("lk", lk);
	return "index2"; 
	}
	
	@RequestMapping("glowna")
	public String viewGlowna(Model model) {
	List<Oferty> lo=service.findLast();
	List<Ksiazki> lk=serviceK.findLast();
	model.addAttribute("lo", lo);
	model.addAttribute("lk", lk);
	return "index2"; 
	}
	
	@RequestMapping("kontakt")
	public String viewKontakt(Model model) {
		Kontakt kontakt = new Kontakt();
		model.addAttribute("kontakt", kontakt);
		return "kontakt";
	}
	
	@RequestMapping(value = "saveKontakt", method = RequestMethod.POST)
	public String saveKontakt(Model model, @ModelAttribute("Kontakt") Kontakt kontakt, RedirectAttributes redirAttrs) {	
		boolean success = true;
		serviceKontakt.save(kontakt);
		Kontakt kontakt2 = new Kontakt();
		model.addAttribute("kontakt", kontakt2);
		model.addAttribute("success", success);
		return "kontakt";
	}
	
	@RequestMapping("loguj")
	public String viewLoguj(Model model, HttpSession session) {
		Long currentUserId = (Long) session.getAttribute("userId");
		if(currentUserId!=null) {
			if(currentUserId==1) {
				return "kontoScr";
			}else 
				return "kontoScrUser";
		}
		return "logowanie";
	}
	
	@RequestMapping("wyloguj")
	public String loguj(HttpSession session) {
		Long currentUserId = (Long) session.getAttribute("userId");
		if(currentUserId!=null) {
			session.setAttribute("userId", null);
		}
		
		return "logowanie";
	}
	
	@RequestMapping("logujDane")
	public String loguj(@RequestParam("login") String login, @RequestParam("password") String password, HttpSession session, Model model) {
		Long currentUserId = (Long) session.getAttribute("userId");
		if(currentUserId==null) {
			Users lodUser = serviceU.findByName(login);
			try {
				
				if(lodUser.getPassword().equals(password)) {
					session.setAttribute("userId", lodUser.getId());
					if(lodUser.getId()==1) {
						return "kontoScr";
					}
					return "kontoScrUser";
				}else {
					boolean success = true;
					model.addAttribute("success", success);
					return "logowanie";
				}
			}catch(Exception e) {
				return "logowanie";
			}
		}
		return "logowanie";
	}
	
	@RequestMapping("kontoScr")
	public String viewKontoEkran(Model model,  HttpSession session) {
		Long currentUserId = (Long) session.getAttribute("userId");
		if(currentUserId==null) {
			
			return "logowanie";
			
		}else if(currentUserId==1)
		{
			return "kontoScr";
		}
		/*
		 * ekran uzytkownika 
		 */
		
		return "logowanie";
	}
	
	@RequestMapping("rejestracja")
	public String viewRejestracjaEkran(Model model,  HttpSession session) {
		Long currentUserId = (Long) session.getAttribute("userId");
		if(currentUserId!=null) {
			
			return "kontoScrUser";
			
		}
		return "rejestracja";
	}
	
	@PostMapping("rejestracjaSave")
	public String saveEditOferte(@RequestParam("name") String login, 
										@RequestParam("email") String email,
										@RequestParam("Passwor") String haslo,
										Model model, HttpSession session) {
		 Users nowy = new Users();
		 nowy.setName(login);
		 nowy.setEmail(email);
		 nowy.setPassword(haslo);
		 nowy.setBlock(false);
		 LocalDate today = LocalDate.now();
		 String formattedDate = today.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		 nowy.setCreated_at(Date.valueOf(formattedDate));
		 serviceU.save(nowy);
		 
		 Users lodUser = serviceU.findByName(login);
		 session.setAttribute("userId", lodUser.getId());
		 return "kontoScrUser";
	}
	
	
	
	@RequestMapping("zmienHaslo")
	public String viewZmienHasloEkran(Model model, HttpSession session) {
		Long currentUserId = (Long) session.getAttribute("userId");
		if(currentUserId==null) {
			return "logowanie";
		}
		return "zmienHaslo";
	}
	
	@RequestMapping("kontaktList")
	public String viewKontaktList(Model model, HttpSession session) {
		Long currentUserId = (Long) session.getAttribute("userId");
		if(currentUserId==null||currentUserId!=1) {
			return "logowanie";
		}
		List<Kontakt> lk = serviceKontakt.findAll();
		model.addAttribute("lk", lk);
		return "kontaktList";
	}
	
	@RequestMapping("kontakt/tresc/{id}")
	public String viewKontaktTresc(@PathVariable(name = "id") Long id, Model model, HttpSession session) {
		Long currentUserId = (Long) session.getAttribute("userId");
		if(currentUserId==null||currentUserId!=1) {
			return "logowanie";
		}
		
		Optional<Kontakt> kontaktOptional = serviceKontakt.findById(id);
		if (kontaktOptional.isPresent()) {
			Kontakt kontakt = kontaktOptional.get();
			model.addAttribute("kontakt", kontakt);
		}
		return "kontaktTresc";
	}
	
	@PostMapping("/kontakt/usun/{id}")
	public String usunKontakt(@PathVariable(name = "id") Long id, Model model, HttpSession session) {
		Long currentUserId = (Long) session.getAttribute("userId");
		if(currentUserId==null||currentUserId!=1) {
			return "logowanie";
		}
		
		Optional<Kontakt> kontaktOptional = serviceKontakt.findById(id);
		if (kontaktOptional.isPresent()) {
			Kontakt kontakt = kontaktOptional.get();
			if(kontakt!=null)
				serviceKontakt.deleteById(id);
		}
		List<Kontakt> lk = serviceKontakt.findAll();
		model.addAttribute("lk", lk);
		return "kontaktList";
	}
	
	@PostMapping("zmienHasloSave")
	public String viewZmienHasloPotwierdz(@RequestParam("Passwor") String haslo, Model model, HttpSession session) {
		Long currentUserId = (Long) session.getAttribute("userId");
		if(currentUserId==null) {
			return "logowanie";
		}
		
		Optional<Users> lodUser = serviceU.findById(currentUserId);
		if (lodUser.isPresent()) {
			Users user =  lodUser.get();
			user.setPassword(haslo);
			serviceU.save(user);
		}else return "logowanie";
		boolean success = true;
		model.addAttribute("success", success);
		return "zmienHaslo";
	}
	
	@RequestMapping("uzytkownicy/list")
	public String viewListaUzytkownikow(Model model){
		List<Users> lu = serviceU.findAll();
		model.addAttribute("lu", lu);
		return "listaUzytkownikow";
	}
	
	@PostMapping("user/block/{id}")
	public String blockUzytkownika(@PathVariable(name = "id") Long id,Model model, HttpSession session) {
		Long currentUserId = (Long) session.getAttribute("userId");
		if(currentUserId==null||currentUserId!=1) {
			return "logowanie";
		}
		
		Optional<Users> lodUser = serviceU.findById(id);
		
		if(id!=1&&lodUser.isPresent()) {
			Users user =  lodUser.get();
			user.setBlock(true);
			serviceU.save(user);
			
		}
		
		return "redirect:/uzytkownicy/list";
	}
	
	@PostMapping("user/unblock/{id}")
	public String unblockUzytkownika(@PathVariable(name = "id") Long id,Model model, HttpSession session) {
		Long currentUserId = (Long) session.getAttribute("userId");
		if(currentUserId==null||currentUserId!=1) {
			return "logowanie";
		}
		
		Optional<Users> lodUser = serviceU.findById(id);
		
		if(id!=1&&lodUser.isPresent()) {
			Users user =  lodUser.get();
			user.setBlock(false);
			serviceU.save(user);
			
		}
		
		return "redirect:/uzytkownicy/list";
	}
	
	@RequestMapping("/regulamin")
	public String viewRegulamin(Model model) {
		return "regulamin";
	}
	
}