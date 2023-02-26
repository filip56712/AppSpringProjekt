package projekt.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Kontakt {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String imie_nazwisko;
	private String email;
	private String temat;
	private String tresc;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getImie_nazwisko() {
		return imie_nazwisko;
	}
	public void setImie_nazwisko(String imie_nazwisko) {
		this.imie_nazwisko = imie_nazwisko;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTemat() {
		return temat;
	}
	public void setTemat(String temat) {
		this.temat = temat;
	}
	public String getTresc() {
		return tresc;
	}
	public void setTresc(String tresc) {
		this.tresc = tresc;
	}
	
}