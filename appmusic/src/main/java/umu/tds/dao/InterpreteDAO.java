package umu.tds.dao;

import java.util.List;

import umu.tds.model.Interprete;

/**
 * Interfaz para la gestión de intérpretes en el sistema.
 * Define los métodos necesarios para agregar, actualizar, eliminar y consultar intérpretes.
 * 
 * @version 1.0
 */
public interface InterpreteDAO {

    /**
     * Agrega un nuevo intérprete al sistema.
     * 
     * @param nombre el nombre del intérprete.
     * @return el identificador único del intérprete agregado.
     */
    int addInterprete(String nombre);

    /**
     * Actualiza un intérprete existente en el sistema.
     * 
     * @param id el identificador único del intérprete.
     * @param nombre el nuevo nombre del intérprete.
     */
    void updateInterprete(int id, String nombre);

    /**
     * Elimina un intérprete del sistema.
     * 
     * @param id el identificador único del intérprete a eliminar.
     */
    void removeInterprete(int id);

    /**
     * Obtiene un intérprete por su identificador único.
     * 
     * @param id el identificador único del intérprete.
     * @return el intérprete correspondiente al identificador, o null si no se encuentra.
     */
    Interprete getInterprete(int id);

    /**
     * Obtiene todos los intérpretes del sistema.
     * 
     * @return una lista de todos los intérpretes.
     */
    List<Interprete> getAllInterpretes();
}
