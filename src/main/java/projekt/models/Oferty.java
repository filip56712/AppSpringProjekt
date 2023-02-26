package projekt.models;

import java.sql.Date;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedNativeQuery;

@Entity
@NamedNativeQuery(name = "Oferty.findAllSQL", 
query = "SELECT * FROM projekt_ksiazki2.oferty",
resultClass = Oferty.class)
@NamedNativeQuery(name = "Oferty.findLast", 
query = "SELECT * FROM projekt_ksiazki2.oferty \r\n"
		+ "WHERE oferty.data_zak IS null \r\n"
		+ "ORDER BY oferty.id DESC \r\n"
		+ "LIMIT 6;",
resultClass = Oferty.class)
public class Oferty {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String Nazwa_ksiazki;
	private int Typ_oferty;
	private Double Cena;
	private String Opis;
	private String Stan;
	private Date data_dod;
	private Date data_zak;
	private Long sprzedajacy;
	private String image;
	private Long kupujacy;
	public Oferty(Long ids, String nazwa_ksiazki, int typ_oferty, Double cena, String opis, String stan, Date data_dod,
			Long sprzedajacy, String image, Long kupujacy) {
		super();
		id = ids;
		Nazwa_ksiazki = nazwa_ksiazki;
		Typ_oferty = typ_oferty;
		Cena = cena;
		Opis = opis;
		Stan = stan;
		this.data_dod = data_dod;
		this.sprzedajacy = sprzedajacy;
		this.image = image;
		this.kupujacy = kupujacy;
	}
	public Oferty() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Long getId() {
		return id;
	}
	public void setId(Long ids) {
		id = ids;
	}
	public String getNazwa_ksiazki() {
		return Nazwa_ksiazki;
	}
	public void setNazwa_ksiazki(String nazwa_ksiazki) {
		Nazwa_ksiazki = nazwa_ksiazki;
	}
	public int getTyp_oferty() {
		return Typ_oferty;
	}
	public void setTyp_oferty(int typ_oferty) {
		Typ_oferty = typ_oferty;
	}
	public Double getCena() {
		return Cena;
	}
	public void setCena(Double cena) {
		Cena = cena;
	}
	public String getOpis() {
		return Opis;
	}
	public void setOpis(String opis) {
		Opis = opis;
	}
	public String getStan() {
		return Stan;
	}
	public void setStan(String stan) {
		Stan = stan;
	}
	public Date getData_dod() {
		return data_dod;
	}
	public void setData_dod(Date data_dod) {
		this.data_dod = data_dod;
	}
	public void setData_dodString(String data_dods) {
		this.data_dod = Date.valueOf(data_dods);
	}
	public Long getSprzedajacy() {
		return sprzedajacy;
	}
	public void setSprzedajacy(Long sprzedajacy) {
		this.sprzedajacy = sprzedajacy;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public Long getKupujacy() {
		return kupujacy;
	}
	public void setKupujacy(Long kupujacy) {
		this.kupujacy = kupujacy;
	}
	public Date getData_zak() {
		return data_zak;
	}
	public void setData_zak(Date data_zak) {
		this.data_zak = data_zak;
	}
	public void setData_zakString(String formattedDate) {
		this.data_zak = Date.valueOf(formattedDate);
		
	}
	
}

