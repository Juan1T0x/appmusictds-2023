package umu.tds.controller;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.swing.table.DefaultTableModel;

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
			usuarioActual = usuarioDAO.getUsuarioByUsername(usuario).get(); // Obtener usuario autenticado
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

	public Optional<List<Playlist>> getAllPlaylists(int usuarioId) {
		return usuarioDAO.getAllPlaylists(usuarioId);
	}

	public Optional<List<Cancion>> getAllCancionesFromPlaylist(int usuarioId, String nombrePlaylist) {
		return usuarioDAO.getAllCancionesFromPlaylist(usuarioId, nombrePlaylist);
	}

	public List<Cancion> getCancionesRecientes(int usuarioId) {
		return usuarioDAO.getCancionesRecientes(usuarioId);
	}

	public List<Cancion> getTopCanciones(int maxResults) {
		return cancionDAO.getTopCanciones(maxResults);
	}

	public List<Integer> getSelectedCancionIdsFromTable(DefaultTableModel modeloTabla) {
		// TODO Auto-generated method stub
		return null;
	}

	public void addPlaylistToUsuario(int id, String titulo, List<Integer> cancionIds) {
		usuarioDAO.addPlaylistToUsuario(id, titulo, cancionIds);
	}

	public void updatePlaylist(int id, String nombre, String titulo, List<Integer> cancionIds) {
		usuarioDAO.updatePlaylist(id, nombre, titulo, cancionIds);
	}

	public void removePlaylist(int id, String nombre) {
		usuarioDAO.removePlaylist(id, nombre);
	}

	public void removeCancionFromPlaylist(int usuarioId, int playlistId, int cancionId) {
		usuarioDAO.removeCancionFromPlaylist(usuarioId, playlistId, cancionId);
	}
	
	public List<Cancion> queryListaCanciones(String titulo, String interprete, String estilo) {
		return cancionDAO.queryListaCanciones(titulo, interprete, estilo);
	}


	private void inicializarAdaptadores() {
		DAOFactory daoFactory = DAOFactory.getDAOFactory(DAOFactory.JPA);
		cancionDAO = daoFactory.getCancionDAO();
		interpreteDAO = daoFactory.getInterpreteDAO();
		estiloMusicalDAO = daoFactory.getEstiloMusicalDAO();
		usuarioDAO = daoFactory.getUsuarioDAO();
	}


}
