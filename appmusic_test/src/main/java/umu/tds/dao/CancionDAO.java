package umu.tds.dao;

import java.util.List;

import umu.tds.model.Cancion;

public interface CancionDAO {
	int addCancion(String titulo, List<String> interpreteNombres, String estiloNombre);

	void updateCancion(int id, String titulo, List<String> interpreteNombres, String estiloNombre,
			long numReproducciones);

	void removeCancion(int id);

	Cancion getCancion(int id);

	List<Cancion> getAllCanciones();
}
