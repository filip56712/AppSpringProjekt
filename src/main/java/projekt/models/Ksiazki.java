package projekt.models;

import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedNativeQuery;

@Entity
@NamedNativeQuery(name = "Ksiazki.findAllSQL", 
query = "SELECT * FROM projekt_ksiazki2.ksiazki",
resultClass = Ksiazki.class)
@NamedNativeQuery(name = "Ksiazki.findLast", 
query = "SELECT * FROM projekt_ksiazki2.ksiazki \r\n"
		+ "ORDER BY ksiazki.id DESC \r\n"
		+ "LIMIT 6;",
resultClass = Ksiazki.class)
public class Ksiazki {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String nazwa;
	private String Opis;
	private String Gatunek;
	private Date Data_wyd;
	private String Autorzy;
	private String Wydawnictwo;
	private String image;
	public Long getId() {
		return id;
	}
	public void setId(Long vid) {
		id = vid;
	}
	public String getNazwa() {
		return nazwa;
	}
	public void setNazwa(String nazwas) {
		nazwa = nazwas;
	}
	public String getOpis() {
		return Opis;
	}
	public void setOpis(String opis) {
		Opis = opis;
	}
	public String getGatunek() {
		return Gatunek;
	}
	public void setGatunek(String gatunek) {
		Gatunek = gatunek;
	}
	public Date getData_wyd() {
		return Data_wyd;
	}
	public void setData_wyd(Date data_wyd) {
		Data_wyd = data_wyd;
	}
	public String getAutorzy() {
		return Autorzy;
	}
	public void setAutorzy(String autorzy) {
		Autorzy = autorzy;
	}
	public String getWydawnictwo() {
		return Wydawnictwo;
	}
	public void setWydawnictwo(String wydawnictwo) {
		Wydawnictwo = wydawnictwo;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	
}