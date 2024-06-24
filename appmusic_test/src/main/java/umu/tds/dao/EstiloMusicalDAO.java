package umu.tds.dao;

import java.util.List;

import umu.tds.model.EstiloMusical;

public interface EstiloMusicalDAO {
	int addEstiloMusical(String nombre);

	void updateEstiloMusical(int id, String nombre);

	void removeEstiloMusical(int id);

	EstiloMusical getEstiloMusicalByName(String nombre);

	List<EstiloMusical> getAllEstilosMusicales();
}
