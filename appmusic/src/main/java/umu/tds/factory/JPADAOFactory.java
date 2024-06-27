package umu.tds.factory;

import umu.tds.dao.CancionDAO;
import umu.tds.dao.EstiloMusicalDAO;
import umu.tds.dao.InterpreteDAO;
import umu.tds.dao.JPACancionDAO;
import umu.tds.dao.JPAEstiloMusicalDAO;
import umu.tds.dao.JPAInterpreteDAO;
import umu.tds.dao.JPAUsuarioDAO;
import umu.tds.dao.UsuarioDAO;

/**
 * Implementación de la fábrica de DAOs utilizando JPA.
 * Proporciona métodos para obtener instancias de DAOs específicos.
 */
public class JPADAOFactory extends DAOFactory {

	private static JPADAOFactory instance;

	private JPADAOFactory() {
	}

	/**
	 * Obtiene la instancia singleton de la fábrica de DAOs JPA.
	 * 
	 * @return la instancia singleton de JPADAOFactory.
	 */
	public static JPADAOFactory getInstance() {
		if (instance == null) {
			instance = new JPADAOFactory();
		}
		return instance;
	}

	@Override
	public CancionDAO getCancionDAO() {
		return JPACancionDAO.getInstance();
	}

	@Override
	public InterpreteDAO getInterpreteDAO() {
		return JPAInterpreteDAO.getInstance();
	}

	@Override
	public EstiloMusicalDAO getEstiloMusicalDAO() {
		return JPAEstiloMusicalDAO.getInstance();
	}

	@Override
	public UsuarioDAO getUsuarioDAO() {
		return JPAUsuarioDAO.getInstance();
	}
}
