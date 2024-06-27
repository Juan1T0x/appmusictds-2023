package umu.tds.dao;

import java.util.Date;
import java.util.List;

import umu.tds.model.Cancion;
import umu.tds.model.Playlist;
import umu.tds.model.Usuario;
import umu.tds.validation.ValidationException;

public interface UsuarioDAO {
	boolean addUsuario(String email, Date fechaNac, String user, String password, boolean premium)
			throws ValidationException;

	void updateUsuario(int id, String email, Date fechaNac, String user, String password, boolean premium);

	void removeUsuario(int id);

	Usuario getUsuario(int id);

	List<Usuario> getAllUsuarios();

	int addPlaylistToUsuario(int usuarioId, String nombrePlaylist, List<Integer> cancionIds);

	void updatePlaylist(int usuarioId, String nombrePlaylist, String nuevoNombre, List<Integer> cancionIds);

	void removePlaylist(int usuarioId, String nombrePlaylist);

	List<Cancion> getAllCancionesFromPlaylist(int usuarioId, String nombrePlaylist);

	List<Playlist> getAllPlaylists(int usuarioId);

	void addCancionReciente(int usuarioId, int cancionId);

	void clearCancionesRecientes(int usuarioId);

	boolean login(String user, String password);

	Usuario getUsuarioByUsername(String usuario);

	List<Cancion> getCancionesRecientes(int usuarioId);

	void removeCancionFromPlaylist(int usuarioId, int playlistId, int cancionId);

	void setPremium(int usuarioId, boolean premium);
}
