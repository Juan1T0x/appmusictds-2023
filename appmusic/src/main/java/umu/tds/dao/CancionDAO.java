package umu.tds.dao;

import java.util.List;

import umu.tds.model.Cancion;

/**
 * Interfaz para la gestión de canciones en el sistema.
 * Define los métodos necesarios para agregar, actualizar, eliminar y consultar canciones.
 * 
 * @version 1.0
 */
public interface CancionDAO {
    
    /**
     * Agrega una nueva canción al sistema.
     * 
     * @param titulo el título de la canción.
     * @param interpreteNombres una lista de nombres de los intérpretes de la canción.
     * @param estiloNombre el nombre del estilo musical de la canción.
     * @param url la URL de la canción.
     * @param duracion la duración de la canción en segundos.
     * @return el identificador único de la canción agregada.
     */
    int addCancion(String titulo, List<String> interpreteNombres, String estiloNombre, String url, int duracion);

    /**
     * Actualiza una canción existente en el sistema.
     * 
     * @param id el identificador único de la canción.
     * @param titulo el nuevo título de la canción.
     * @param interpreteNombres una lista de nuevos nombres de los intérpretes de la canción.
     * @param estiloNombre el nuevo nombre del estilo musical de la canción.
     * @param numReproducciones el nuevo número de reproducciones de la canción.
     */
    void updateCancion(int id, String titulo, List<String> interpreteNombres, String estiloNombre, long numReproducciones);

    /**
     * Elimina una canción del sistema.
     * 
     * @param id el identificador único de la canción a eliminar.
     */
    void removeCancion(int id);

    /**
     * Obtiene una canción por su identificador único.
     * 
     * @param id el identificador único de la canción.
     * @return la canción correspondiente al identificador, o null si no se encuentra.
     */
    Cancion getCancion(int id);

    /**
     * Obtiene todas las canciones del sistema.
     * 
     * @return una lista de todas las canciones.
     */
    List<Cancion> getAllCanciones();

    /**
     * Obtiene las canciones más populares del sistema, hasta un máximo de resultados especificado.
     * 
     * @param maxResults el número máximo de canciones a retornar.
     * @return una lista de las canciones más populares.
     */
    List<Cancion> getTopCanciones(int maxResults);

    /**
     * Consulta una lista de canciones que coinciden con los criterios de búsqueda especificados.
     * 
     * @param titulo el título de la canción (o parte del título) a buscar.
     * @param interpretes los nombres de los intérpretes a buscar.
     * @param estilo el nombre del estilo musical a buscar.
     * @return una lista de canciones que coinciden con los criterios de búsqueda.
     */
    List<Cancion> queryListaCanciones(String titulo, String interpretes, String estilo);

	void aumentarReproduccion(int id);
}
