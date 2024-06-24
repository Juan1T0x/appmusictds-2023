package umu.tds.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

@Entity
public class Usuario {

	public static final int MAX = 10; // Numero maximo de canciones recientes

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String email;

	private Date fechaNac;

	private String user;

	private String password;

	private boolean premium;

	@ManyToMany
	private List<Cancion> cancionesRecientes;

	@OneToMany
	private List<Playlist> playlists;

	public Usuario() { // POJO

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getFechaNac() {
		return fechaNac;
	}

	public void setFechaNac(Date fechaNac) {
		this.fechaNac = fechaNac;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isPremium() {
		return premium;
	}

	public void setPremium(boolean premium) {
		this.premium = premium;
	}

	public List<Cancion> getCancionesRecientes() {
		return cancionesRecientes;
	}

	public void setCancionesRecientes(List<Cancion> cancionesRecientes) {
		this.cancionesRecientes = cancionesRecientes;
	}

	public List<Playlist> getPlaylists() {
		return playlists;
	}

	public void setPlaylists(List<Playlist> playlists) {
		this.playlists = playlists;
	}

	public static int getMax() {
		return MAX;
	}

	@Override
	public String toString() {
		return "Usuario [id=" + id + ", email=" + email + ", fechaNac=" + fechaNac + ", user=" + user + ", password="
				+ password + ", premium=" + premium + ", cancionesRecientes=" + cancionesRecientes + ", playlists="
				+ playlists + "]";
	}

}
