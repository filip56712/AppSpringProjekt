package projekt.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Wysylka {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long Id;
	private Long kupujacy;
	private Long sprzedajacy;
	private Long oferta_id;
	private String miasto;
	private String adres_1;
	private String adres_2;
	private String kod_pocztowy;
	private Long telefon;
	private String imie_nazwisko;
	private String stan_wys;
	private String opcja_dost;
	public Long getId() {
		return Id;
	}
	public void setId(Long id) {
		Id = id;
	}
	public Long getKupujacy() {
		return kupujacy;
	}
	public void setKupujacy(Long kupujacy) {
		this.kupujacy = kupujacy;
	}
	public Long getSprzedajacy() {
		return sprzedajacy;
	}
	public void setSprzedajacy(Long sprzedajacy) {
		this.sprzedajacy = sprzedajacy;
	}
	public Long getOferta_id() {
		return oferta_id;
	}
	public void setOferta_id(Long long1) {
		this.oferta_id = long1;
	}
	public String getMiasto() {
		return miasto;
	}
	public void setMiasto(String miasto) {
		this.miasto = miasto;
	}
	public String getAdres_1() {
		return adres_1;
	}
	public void setAdres_1(String adres_1) {
		this.adres_1 = adres_1;
	}
	public String getAdres_2() {
		return adres_2;
	}
	public void setAdres_2(String adres_2) {
		this.adres_2 = adres_2;
	}
	public String getKod_pocztowy() {
		return kod_pocztowy;
	}
	public void setKod_pocztowy(String kod_pocztowy) {
		this.kod_pocztowy = kod_pocztowy;
	}
	public Long getTelefon() {
		return telefon;
	}
	public void setTelefon(Long telefon) {
		this.telefon = telefon;
	}
	public String getImie_nazwisko() {
		return imie_nazwisko;
	}
	public void setImie_nazwisko(String imie_nazwisko) {
		this.imie_nazwisko = imie_nazwisko;
	}
	public String getStan_wys() {
		return stan_wys;
	}
	public void setStan_wys(String stan_wys) {
		this.stan_wys = stan_wys;
	}
	public String getOpcja_dost() {
		return opcja_dost;
	}
	public void setOpcja_dost(String opcja_dost) {
		this.opcja_dost = opcja_dost;
	}
	
}