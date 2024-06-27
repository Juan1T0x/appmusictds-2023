package umu.tds.factory;

import umu.tds.dao.CancionDAO;
import umu.tds.dao.EstiloMusicalDAO;
import umu.tds.dao.InterpreteDAO;
import umu.tds.dao.UsuarioDAO;

public abstract class DAOFactory {
	public static final int JPA = 1;
	private static DAOFactory instance;

	public static DAOFactory getDAOFactory(int factoryType) {
		if (instance == null) {
			switch (factoryType) {
			case JPA:
				instance = JPADAOFactory.getInstance();
				break;
			// Puedes agregar más factorías aquí si es necesario
			}
		}
		return instance;
	}

	public abstract CancionDAO getCancionDAO();

	public abstract InterpreteDAO getInterpreteDAO();

	public abstract EstiloMusicalDAO getEstiloMusicalDAO();

	public abstract UsuarioDAO getUsuarioDAO();
}
