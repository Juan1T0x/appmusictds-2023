package umu.tds.factory;

import umu.tds.dao.CancionDAO;
import umu.tds.dao.EstiloMusicalDAO;
import umu.tds.dao.InterpreteDAO;
import umu.tds.dao.UsuarioDAO;

/**
 * Fábrica abstracta para obtener instancias de los DAOs.
 * Define métodos abstractos para obtener DAOs específicos.
 */
public abstract class DAOFactory {
	public static final int JPA = 1;
	private static DAOFactory instance;

	/**
	 * Obtiene la instancia de la fábrica de DAOs basada en el tipo especificado.
	 * 
	 * @param factoryType el tipo de fábrica (por ejemplo, JPA).
	 * @return la instancia de la fábrica de DAOs correspondiente.
	 */
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

	/**
	 * Obtiene el DAO para gestionar canciones.
	 * 
	 * @return una instancia de CancionDAO.
	 */
	public abstract CancionDAO getCancionDAO();

	/**
	 * Obtiene el DAO para gestionar intérpretes.
	 * 
	 * @return una instancia de InterpreteDAO.
	 */
	public abstract InterpreteDAO getInterpreteDAO();

	/**
	 * Obtiene el DAO para gestionar estilos musicales.
	 * 
	 * @return una instancia de EstiloMusicalDAO.
	 */
	public abstract EstiloMusicalDAO getEstiloMusicalDAO();

	/**
	 * Obtiene el DAO para gestionar usuarios.
	 * 
	 * @return una instancia de UsuarioDAO.
	 */
	public abstract UsuarioDAO getUsuarioDAO();
}
