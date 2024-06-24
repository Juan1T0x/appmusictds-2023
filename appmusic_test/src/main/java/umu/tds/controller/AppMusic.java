package umu.tds.controller;

import java.util.Date;
import java.util.List;

import umu.tds.dao.CancionDAO;
import umu.tds.dao.EstiloMusicalDAO;
import umu.tds.dao.InterpreteDAO;
import umu.tds.dao.UsuarioDAO;
import umu.tds.factory.DAOFactory;
import umu.tds.model.Cancion;
import umu.tds.model.EstiloMusical;
import umu.tds.model.Playlist;
import umu.tds.model.Usuario;
import umu.tds.validation.ValidationException;

public class AppMusic {

	private static AppMusic instance = null;

	private CancionDAO cancionDAO;
	private InterpreteDAO interpreteDAO;
	private EstiloMusicalDAO estiloMusicalDAO;
	private UsuarioDAO usuarioDAO;

	private Usuario usuarioActual;

	private AppMusic() {

		inicializarAdaptadores();

	}

	public static AppMusic getInstance() {
		if (instance == null)
			instance = new AppMusic();
		return instance;
	}

	public boolean loginConGitHub(String usuario, String password) {
		// TODO Auto-generated method stub
		return true;
	}

	public boolean registrarUsuario(String email, Date fechaNac, String user, String password, boolean premium)
			throws ValidationException {
		return usuarioDAO.addUsuario(email, fechaNac, user, password, premium);

	}

	public boolean login(String usuario, String password) {
		boolean autenticado = usuarioDAO.login(usuario, password);
		if (autenticado) {
			usuarioActual = usuarioDAO.getUsuarioByUsername(usuario); // Obtener usuario autenticado
		}
		return autenticado;
	}

	public Usuario getUsuarioActual() {
		return usuarioActual;
	}

	public List<Cancion> getAllCanciones() {
		return cancionDAO.getAllCanciones();
	}

	public List<EstiloMusical> getAllEstilosMusicales() {
		return estiloMusicalDAO.getAllEstilosMusicales();
	}

	public List<Playlist> getAllPlaylists(int usuarioId) {
		return usuarioDAO.getAllPlaylists(usuarioId);
	}

	public List<Cancion> getAllCancionesFromPlaylist(int usuarioId, String nombrePlaylist) {
		return usuarioDAO.getAllCancionesFromPlaylist(usuarioId, nombrePlaylist);
	}

	private void inicializarAdaptadores() {
		DAOFactory daoFactory = DAOFactory.getDAOFactory(DAOFactory.JPA);
		cancionDAO = daoFactory.getCancionDAO();
		interpreteDAO = daoFactory.getInterpreteDAO();
		estiloMusicalDAO = daoFactory.getEstiloMusicalDAO();
		usuarioDAO = daoFactory.getUsuarioDAO();
	}

}
