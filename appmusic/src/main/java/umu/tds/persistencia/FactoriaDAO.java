package umu.tds.persistencia;

public abstract class FactoriaDAO {
	private static FactoriaDAO instance;

	public static final String DAO_TDS = "umu.tds.persistencia.TDSFactoriaDAO";

	@SuppressWarnings("deprecation")
	public static FactoriaDAO getInstancia(String tipo) throws DAOException {
		if (instance == null)
			try {
				instance = (FactoriaDAO) Class.forName(tipo).newInstance();
			} catch (Exception e) {
				throw new DAOException(e.getMessage());
			}
		return instance;
	}

	public static FactoriaDAO getInstancia() throws DAOException {
		return getInstancia(FactoriaDAO.DAO_TDS);
	}

	protected FactoriaDAO() {
	}

	public abstract AdaptadorCancionDAO getCancionDAO();

	public abstract AdaptadorInterpreteDAO getInterpreteDAO();

	public abstract AdaptadorUsuarioDAO getUsuarioDAO();

}
