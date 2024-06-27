package umu.tds.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

/**
 * Representa una canción con varios atributos como título, intérpretes, estilo
 * musical, número de reproducciones, URL y duración.
 * 
 * @version 1.0
 */
@Entity
public class Cancion {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String titulo;

	@ManyToMany
	private List<Interprete> interpretes;

	@ManyToOne
	private EstiloMusical estilo;

	private long numReproducciones;

	private String url;

	private int duracion;

	/**
	 * Constructor vacío para la clase Cancion.
	 */
	public Cancion() {
		// Constructor vacío para la creación de un objeto Cancion.
	}

	/**
	 * Obtiene el identificador único de la canción.
	 * 
	 * @return el identificador único de la canción.
	 */
	public int getId() {
		return id;
	}

	/**
	 * Establece el identificador único de la canción.
	 * 
	 * @param id el identificador único de la canción.
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Obtiene el título de la canción.
	 * 
	 * @return el título de la canción.
	 */
	public String getTitulo() {
		return titulo;
	}

	/**
	 * Establece el título de la canción.
	 * 
	 * @param titulo el título de la canción.
	 */
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	/**
	 * Obtiene la lista de intérpretes de la canción.
	 * 
	 * @return la lista de intérpretes de la canción.
	 */
	public List<Interprete> getInterpretes() {
		return interpretes;
	}

	/**
	 * Establece la lista de intérpretes de la canción.
	 * 
	 * @param interpretes la lista de intérpretes de la canción.
	 */
	public void setInterpretes(List<Interprete> interpretes) {
		this.interpretes = interpretes;
	}

	/**
	 * Obtiene el estilo musical de la canción.
	 * 
	 * @return el estilo musical de la canción.
	 */
	public EstiloMusical getEstilo() {
		return estilo;
	}

	/**
	 * Establece el estilo musical de la canción.
	 * 
	 * @param estilo el estilo musical de la canción.
	 */
	public void setEstilo(EstiloMusical estilo) {
		this.estilo = estilo;
	}

	/**
	 * Obtiene el número de reproducciones de la canción.
	 * 
	 * @return el número de reproducciones de la canción.
	 */
	public long getNumReproducciones() {
		return numReproducciones;
	}

	/**
	 * Establece el número de reproducciones de la canción.
	 * 
	 * @param numReproducciones el número de reproducciones de la canción.
	 */
	public void setNumReproducciones(long numReproducciones) {
		this.numReproducciones = numReproducciones;
	}

	/**
	 * Obtiene la URL de la canción.
	 * 
	 * @return la URL de la canción.
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * Establece la URL de la canción.
	 * 
	 * @param url la URL de la canción.
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * Obtiene la duración de la canción en segundos.
	 * 
	 * @return la duración de la canción en segundos.
	 */
	public int getDuracion() {
		return duracion;
	}

	/**
	 * Establece la duración de la canción en segundos.
	 * 
	 * @param duracion la duración de la canción en segundos.
	 */
	public void setDuracion(int duracion) {
		this.duracion = duracion;
	}

	/**
	 * Devuelve una representación en cadena de la canción.
	 * 
	 * @return una cadena que representa la canción.
	 */
	@Override
	public String toString() {
		return "Cancion [id=" + id + ", titulo=" + titulo + ", interpretes=" + interpretes + ", estilo=" + estilo
				+ ", numReproducciones=" + numReproducciones + ", url=" + url + ", duracion=" + duracion + "]";
	}
}
