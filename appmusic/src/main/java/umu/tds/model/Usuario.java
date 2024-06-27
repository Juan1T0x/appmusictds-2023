package umu.tds.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

/**
 * Representa un usuario con atributos como identificador, correo electrónico,
 * fecha de nacimiento, nombre de usuario, contraseña, estado de premium,
 * canciones recientes y listas de reproducción.
 * 
 * @version 1.0
 */
@Entity
public class Usuario {

	public static final int MAX = 10; // Numero máximo de canciones recientes

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

	/**
	 * Constructor vacío para la clase Usuario.
	 */
	public Usuario() {
		// Constructor vacío para la creación de un objeto Usuario.
	}

	/**
	 * Obtiene el identificador único del usuario.
	 * 
	 * @return el identificador único del usuario.
	 */
	public int getId() {
		return id;
	}

	/**
	 * Establece el identificador único del usuario.
	 * 
	 * @param id el identificador único del usuario.
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Obtiene el correo electrónico del usuario.
	 * 
	 * @return el correo electrónico del usuario.
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Establece el correo electrónico del usuario.
	 * 
	 * @param email el correo electrónico del usuario.
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Obtiene la fecha de nacimiento del usuario.
	 * 
	 * @return la fecha de nacimiento del usuario.
	 */
	public Date getFechaNac() {
		return fechaNac;
	}

	/**
	 * Establece la fecha de nacimiento del usuario.
	 * 
	 * @param fechaNac la fecha de nacimiento del usuario.
	 */
	public void setFechaNac(Date fechaNac) {
		this.fechaNac = fechaNac;
	}

	/**
	 * Obtiene el nombre de usuario.
	 * 
	 * @return el nombre de usuario.
	 */
	public String getUser() {
		return user;
	}

	/**
	 * Establece el nombre de usuario.
	 * 
	 * @param user el nombre de usuario.
	 */
	public void setUser(String user) {
		this.user = user;
	}

	/**
	 * Obtiene la contraseña del usuario.
	 * 
	 * @return la contraseña del usuario.
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Establece la contraseña del usuario.
	 * 
	 * @param password la contraseña del usuario.
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Verifica si el usuario es premium.
	 * 
	 * @return true si el usuario es premium, false en caso contrario.
	 */
	public boolean isPremium() {
		return premium;
	}

	/**
	 * Establece el estado de premium del usuario.
	 * 
	 * @param premium el estado de premium del usuario.
	 */
	public void setPremium(boolean premium) {
		this.premium = premium;
	}

	/**
	 * Obtiene la lista de canciones recientes del usuario.
	 * 
	 * @return la lista de canciones recientes del usuario.
	 */
	public List<Cancion> getCancionesRecientes() {
		return cancionesRecientes;
	}

	/**
	 * Establece la lista de canciones recientes del usuario.
	 * 
	 * @param cancionesRecientes la lista de canciones recientes del usuario.
	 */
	public void setCancionesRecientes(List<Cancion> cancionesRecientes) {
		this.cancionesRecientes = cancionesRecientes;
	}

	/**
	 * Obtiene la lista de playlists del usuario.
	 * 
	 * @return la lista de playlists del usuario.
	 */
	public List<Playlist> getPlaylists() {
		return playlists;
	}

	/**
	 * Establece la lista de playlists del usuario.
	 * 
	 * @param playlists la lista de playlists del usuario.
	 */
	public void setPlaylists(List<Playlist> playlists) {
		this.playlists = playlists;
	}

	/**
	 * Obtiene el número máximo de canciones recientes.
	 * 
	 * @return el número máximo de canciones recientes.
	 */
	public static int getMax() {
		return MAX;
	}

	/**
	 * Devuelve una representación en cadena del usuario.
	 * 
	 * @return una cadena que representa el usuario.
	 */
	@Override
	public String toString() {
		return "Usuario [id=" + id + ", email=" + email + ", fechaNac=" + fechaNac + ", user=" + user + ", password="
				+ password + ", premium=" + premium + ", cancionesRecientes=" + cancionesRecientes + ", playlists="
				+ playlists + "]";
	}
}
