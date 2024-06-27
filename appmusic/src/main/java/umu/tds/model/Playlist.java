package umu.tds.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

/**
 * Representa una lista de reproducción (playlist) con atributos como
 * identificador, nombre y una lista de canciones.
 * 
 * @version 1.0
 */
@Entity
public class Playlist {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String nombre;

	@ManyToMany
	private List<Cancion> canciones;

	/**
	 * Constructor vacío para la clase Playlist.
	 */
	public Playlist() {
		// Constructor vacío para la creación de un objeto Playlist.
	}

	/**
	 * Obtiene el identificador único de la playlist.
	 * 
	 * @return el identificador único de la playlist.
	 */
	public int getId() {
		return id;
	}

	/**
	 * Establece el identificador único de la playlist.
	 * 
	 * @param id el identificador único de la playlist.
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Obtiene el nombre de la playlist.
	 * 
	 * @return el nombre de la playlist.
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Establece el nombre de la playlist.
	 * 
	 * @param nombre el nombre de la playlist.
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * Obtiene la lista de canciones de la playlist.
	 * 
	 * @return la lista de canciones de la playlist.
	 */
	public List<Cancion> getCanciones() {
		return canciones;
	}

	/**
	 * Establece la lista de canciones de la playlist.
	 * 
	 * @param canciones la lista de canciones de la playlist.
	 */
	public void setCanciones(List<Cancion> canciones) {
		this.canciones = canciones;
	}

	/**
	 * Devuelve una representación en cadena de la playlist.
	 * 
	 * @return una cadena que representa la playlist.
	 */
	@Override
	public String toString() {
		return "Playlist [id=" + id + ", nombre=" + nombre + ", canciones=" + canciones + "]";
	}
}
