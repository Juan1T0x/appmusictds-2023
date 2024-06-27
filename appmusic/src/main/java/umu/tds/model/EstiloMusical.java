package umu.tds.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Representa un estilo musical con atributos como identificador y nombre.
 * 
 * @version 1.0
 */
@Entity
public class EstiloMusical {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String nombre;

	/**
	 * Constructor vacío para la clase EstiloMusical.
	 */
	public EstiloMusical() {
		// Constructor vacío para la creación de un objeto EstiloMusical.
	}

	/**
	 * Obtiene el identificador único del estilo musical.
	 * 
	 * @return el identificador único del estilo musical.
	 */
	public int getId() {
		return id;
	}

	/**
	 * Establece el identificador único del estilo musical.
	 * 
	 * @param id el identificador único del estilo musical.
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Obtiene el nombre del estilo musical.
	 * 
	 * @return el nombre del estilo musical.
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Establece el nombre del estilo musical.
	 * 
	 * @param nombre el nombre del estilo musical.
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * Devuelve una representación en cadena del estilo musical.
	 * 
	 * @return una cadena que representa el estilo musical.
	 */
	@Override
	public String toString() {
		return "EstiloMusical [id=" + id + ", nombre=" + nombre + "]";
	}
}
