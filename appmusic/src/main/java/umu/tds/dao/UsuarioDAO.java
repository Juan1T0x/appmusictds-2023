package umu.tds.dao;

import java.util.Date;
import java.util.List;

import umu.tds.model.Cancion;
import umu.tds.model.Playlist;
import umu.tds.model.Usuario;
import umu.tds.validation.ValidationException;

/**
 * Interfaz para la gestión de usuarios en el sistema.
 * Define los métodos necesarios para agregar, actualizar, eliminar y consultar usuarios, así como para gestionar sus playlists y canciones recientes.
 * 
 * @version 1.0
 */
public interface UsuarioDAO {
    
    /**
     * Agrega un nuevo usuario al sistema.
     * 
     * @param email el correo electrónico del usuario.
     * @param fechaNac la fecha de nacimiento del usuario.
     * @param user el nombre de usuario.
     * @param password la contraseña del usuario.
     * @param premium el estado premium del usuario.
     * @return true si el usuario fue agregado exitosamente, false en caso contrario.
     * @throws ValidationException si hay un error de validación al agregar el usuario.
     */
    boolean addUsuario(String email, Date fechaNac, String user, String password, boolean premium) throws ValidationException;

    /**
     * Actualiza un usuario existente en el sistema.
     * 
     * @param id el identificador único del usuario.
     * @param email el nuevo correo electrónico del usuario.
     * @param fechaNac la nueva fecha de nacimiento del usuario.
     * @param user el nuevo nombre de usuario.
     * @param password la nueva contraseña del usuario.
     * @param premium el nuevo estado premium del usuario.
     */
    void updateUsuario(int id, String email, Date fechaNac, String user, String password, boolean premium);

    /**
     * Elimina un usuario del sistema.
     * 
     * @param id el identificador único del usuario a eliminar.
     */
    void removeUsuario(int id);

    /**
     * Obtiene un usuario por su identificador único.
     * 
     * @param id el identificador único del usuario.
     * @return el usuario correspondiente al identificador, o null si no se encuentra.
     */
    Usuario getUsuario(int id);

    /**
     * Obtiene todos los usuarios del sistema.
     * 
     * @return una lista de todos los usuarios.
     */
    List<Usuario> getAllUsuarios();

    /**
     * Agrega una nueva playlist a un usuario.
     * 
     * @param usuarioId el identificador único del usuario.
     * @param nombrePlaylist el nombre de la nueva playlist.
     * @param cancionIds una lista de identificadores de las canciones que pertenecen a la playlist.
     * @return el identificador único de la playlist agregada.
     */
    int addPlaylistToUsuario(int usuarioId, String nombrePlaylist, List<Integer> cancionIds);

    /**
     * Actualiza una playlist existente de un usuario.
     * 
     * @param usuarioId el identificador único del usuario.
     * @param nombrePlaylist el nombre de la playlist a actualizar.
     * @param nuevoNombre el nuevo nombre de la playlist.
     * @param cancionIds una lista de nuevos identificadores de canciones para la playlist.
     */
    void updatePlaylist(int usuarioId, String nombrePlaylist, String nuevoNombre, List<Integer> cancionIds);

    /**
     * Elimina una playlist de un usuario.
     * 
     * @param usuarioId el identificador único del usuario.
     * @param nombrePlaylist el nombre de la playlist a eliminar.
     */
    void removePlaylist(int usuarioId, String nombrePlaylist);

    /**
     * Obtiene todas las canciones de una playlist de un usuario.
     * 
     * @param usuarioId el identificador único del usuario.
     * @param nombrePlaylist el nombre de la playlist.
     * @return una lista de todas las canciones de la playlist.
     */
    List<Cancion> getAllCancionesFromPlaylist(int usuarioId, String nombrePlaylist);

    /**
     * Obtiene todas las playlists de un usuario.
     * 
     * @param usuarioId el identificador único del usuario.
     * @return una lista de todas las playlists del usuario.
     */
    List<Playlist> getAllPlaylists(int usuarioId);

    /**
     * Agrega una canción a la lista de canciones recientes de un usuario.
     * 
     * @param usuarioId el identificador único del usuario.
     * @param cancionId el identificador único de la canción.
     */
    void addCancionReciente(int usuarioId, int cancionId);

    /**
     * Limpia la lista de canciones recientes de un usuario.
     * 
     * @param usuarioId el identificador único del usuario.
     */
    void clearCancionesRecientes(int usuarioId);

    /**
     * Inicia sesión con un usuario y contraseña.
     * 
     * @param user el nombre de usuario.
     * @param password la contraseña del usuario.
     * @return true si el inicio de sesión es exitoso, false en caso contrario.
     */
    boolean login(String user, String password);

    /**
     * Obtiene un usuario por su nombre de usuario.
     * 
     * @param usuario el nombre de usuario.
     * @return el usuario correspondiente al nombre de usuario, o null si no se encuentra.
     */
    Usuario getUsuarioByUsername(String usuario);

    /**
     * Obtiene la lista de canciones recientes de un usuario.
     * 
     * @param usuarioId el identificador único del usuario.
     * @return una lista de canciones recientes del usuario.
     */
    List<Cancion> getCancionesRecientes(int usuarioId);

    /**
     * Elimina una canción de una playlist de un usuario.
     * 
     * @param usuarioId el identificador único del usuario.
     * @param playlistId el identificador único de la playlist.
     * @param cancionId el identificador único de la canción a eliminar.
     */
    void removeCancionFromPlaylist(int usuarioId, int playlistId, int cancionId);

    /**
     * Establece el estado premium de un usuario.
     * 
     * @param usuarioId el identificador único del usuario.
     * @param premium el nuevo estado premium del usuario.
     */
    void setPremium(int usuarioId, boolean premium);
}
