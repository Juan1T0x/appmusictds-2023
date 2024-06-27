package umu.tds.dao;

import java.util.List;

import umu.tds.model.Interprete;

public interface InterpreteDAO {
	int addInterprete(String nombre);

	void updateInterprete(int id, String nombre);

	void removeInterprete(int id);

	Interprete getInterprete(int id);

	List<Interprete> getAllInterpretes();
}
