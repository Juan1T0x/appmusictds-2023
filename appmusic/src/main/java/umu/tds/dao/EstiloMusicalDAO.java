package umu.tds.dao;

import java.util.List;

import umu.tds.model.EstiloMusical;

/**
 * Interfaz para la gestión de estilos musicales en el sistema.
 * Define los métodos necesarios para agregar, actualizar, eliminar y consultar estilos musicales.
 * 
 * @version 1.0
 */
public interface EstiloMusicalDAO {
    
    /**
     * Agrega un nuevo estilo musical al sistema.
     * 
     * @param nombre el nombre del estilo musical.
     * @return el identificador único del estilo musical agregado.
     */
    int addEstiloMusical(String nombre);

    /**
     * Actualiza un estilo musical existente en el sistema.
     * 
     * @param id el identificador único del estilo musical.
     * @param nombre el nuevo nombre del estilo musical.
     */
    void updateEstiloMusical(int id, String nombre);

    /**
     * Elimina un estilo musical del sistema.
     * 
     * @param id el identificador único del estilo musical a eliminar.
     */
    void removeEstiloMusical(int id);

    /**
     * Obtiene un estilo musical por su nombre.
     * 
     * @param nombre el nombre del estilo musical.
     * @return el estilo musical correspondiente al nombre, o null si no se encuentra.
     */
    EstiloMusical getEstiloMusicalByName(String nombre);

    /**
     * Obtiene todos los estilos musicales del sistema.
     * 
     * @return una lista de todos los estilos musicales.
     */
    List<EstiloMusical> getAllEstilosMusicales();
}
