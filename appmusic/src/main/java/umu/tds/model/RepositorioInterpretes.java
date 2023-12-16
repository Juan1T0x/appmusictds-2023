package umu.tds.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import umu.tds.persistencia.AdaptadorInterpreteDAO;
import umu.tds.persistencia.DAOException;
import umu.tds.persistencia.FactoriaDAO;

public class RepositorioInterpretes {
	private Map<Integer, Interprete> interpretes;
	private static RepositorioInterpretes unicaInstancia = null;
	
	private FactoriaDAO dao;
	AdaptadorInterpreteDAO adaptadorInterprete;

	private RepositorioInterpretes() {
		try {
			dao = FactoriaDAO.getInstancia(FactoriaDAO.DAO_TDS);
			adaptadorInterprete = dao.getInterpreteDAO();
			interpretes = new HashMap<Integer, Interprete>();
			this.cargarCatalogo();
		} catch(DAOException e) {
			e.printStackTrace();
		}
	}

	public static RepositorioInterpretes getUnicaInstancia() {
		if (unicaInstancia == null)
			unicaInstancia = new RepositorioInterpretes();
		return unicaInstancia;
	}
	
	public List<Interprete> getInterpretes() {
		List<Interprete> lista = new ArrayList<Interprete>();
		
		for (Interprete interprete: interpretes.values())
			lista.add(interprete);
		return lista;
	}
	
	public Interprete getInterprete(int key) {
		return interpretes.get(key);
	}
	
	public void addInterprete(Interprete interprete) {
		interpretes.put(interprete.getCodigo(), interprete);
	}
	
	public void cargarCatalogo() throws DAOException {
		List<Interprete> interpretesBD = adaptadorInterprete.recuperarInterpretees();
		for (Interprete interprete: interpretesBD)
			interpretes.put(interprete.getCodigo(), interprete);
	}
	
}
