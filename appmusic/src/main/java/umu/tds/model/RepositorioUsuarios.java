package umu.tds.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import umu.tds.persistencia.AdaptadorUsuarioDAO;
import umu.tds.persistencia.DAOException;
import umu.tds.persistencia.FactoriaDAO;

public class RepositorioUsuarios {

	private Map<Integer, Usuario> usuarios;
	private static RepositorioUsuarios unicaInstancia = null;

	private FactoriaDAO dao;
	private AdaptadorUsuarioDAO adaptadorUsuario;

	private RepositorioUsuarios() {
		try {
			dao = FactoriaDAO.getInstancia(FactoriaDAO.DAO_TDS);
			adaptadorUsuario = dao.getUsuarioDAO();
			this.cargarCatalogo();
		} catch (DAOException e) {
			e.printStackTrace();
		}
	}

	public static RepositorioUsuarios getUnicaInstancia() {
		if (unicaInstancia == null)
			unicaInstancia = new RepositorioUsuarios();
		return unicaInstancia;
	}

	public List<Usuario> getCanciones() {
		ArrayList<Usuario> lista = new ArrayList<Usuario>();

		for (Usuario usuario : usuarios.values())
			lista.add(usuario);
		return lista;
	}

	public Usuario getUsuario (int key) {
		return usuarios.get(key);
	}

	public void addUsuario(Usuario usuario) {
		usuarios.put(usuario.getCodigo(), usuario);
	}

	public void cargarCatalogo() throws DAOException {
		List<Usuario> usuariosBD = adaptadorUsuario.recuperarUsuarios();
		for (Usuario usuario : usuariosBD)
			usuarios.put(usuario.getCodigo(), usuario);
	}
}
