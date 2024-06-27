package umu.tds.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

/**
 * Representa un intérprete musical con atributos como identificador, nombre y
 * una lista de canciones interpretadas.
 * 
 * @version 1.0
 */
@Entity
public class Interprete {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String nombre;

	@ManyToMany(mappedBy = "interpretes")
	private List<Cancion> canciones;

	/**
	 * Constructor vacío para la clase Interprete.
	 */
	public Interprete() {
		// Constructor vacío para la creación de un objeto Interprete.
	}

	/**
	 * Obtiene el identificador único del intérprete.
	 * 
	 * @return el identificador único del intérprete.
	 */
	public int getId() {
		return id;
	}

	/**
	 * Establece el identificador único del intérprete.
	 * 
	 * @param id el identificador único del intérprete.
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Obtiene el nombre del intérprete.
	 * 
	 * @return el nombre del intérprete.
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Establece el nombre del intérprete.
	 * 
	 * @param nombre el nombre del intérprete.
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * Obtiene la lista de canciones interpretadas por el intérprete.
	 * 
	 * @return la lista de canciones interpretadas por el intérprete.
	 */
	public List<Cancion> getCanciones() {
		return canciones;
	}

	/**
	 * Establece la lista de canciones interpretadas por el intérprete.
	 * 
	 * @param canciones la lista de canciones interpretadas por el intérprete.
	 */
	public void setCanciones(List<Cancion> canciones) {
		this.canciones = canciones;
	}

	/**
	 * Devuelve una representación en cadena del intérprete.
	 * 
	 * @return una cadena que representa el intérprete.
	 */
	@Override
	public String toString() {
		return "Interprete [id=" + id + ", nombre=" + nombre + "]";
	}
}
