package umu.tds.model.cancion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import umu.tds.persistencia.AdaptadorCancionDAO;
import umu.tds.persistencia.DAOException;
import umu.tds.persistencia.FactoriaDAO;

public class RepositorioCanciones {
	private Map<Integer, Cancion> canciones;
	private static RepositorioCanciones unicaInstancia = null;

	private FactoriaDAO dao;
	private AdaptadorCancionDAO adaptadorCancion;

	private RepositorioCanciones() {
		try {
			dao = FactoriaDAO.getInstancia(FactoriaDAO.DAO_TDS);
			adaptadorCancion = dao.getCancionDAO();
			canciones = new HashMap<Integer, Cancion>();
			this.cargarCatalogo();
		} catch (DAOException e) {
			e.printStackTrace();
		}
	}

	public static RepositorioCanciones getUnicaInstancia() {
		if (unicaInstancia == null)
			unicaInstancia = new RepositorioCanciones();
		return unicaInstancia;
	}

	public List<Cancion> getCanciones() {
		ArrayList<Cancion> lista = new ArrayList<Cancion>();

		for (Cancion cancion : canciones.values())
			lista.add(cancion);
		return lista;
	}

	public Cancion getCancion(int key) {
		return canciones.get(key);
	}

	public void addCancion(Cancion cancion) {
		canciones.put(cancion.getCodigo(), cancion);
	}

	public void cargarCatalogo() throws DAOException {
		List<Cancion> cancionesBD = adaptadorCancion.recuperarCanciones();
		for (Cancion cancion : cancionesBD)
			canciones.put(cancion.getCodigo(), cancion);
	}

}
