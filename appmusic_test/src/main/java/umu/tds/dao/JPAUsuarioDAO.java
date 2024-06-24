package umu.tds.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import umu.tds.model.Cancion;
import umu.tds.model.Playlist;
import umu.tds.model.Usuario;
import umu.tds.validation.ValidadorEmail;
import umu.tds.validation.ValidadorFechaNac;
import umu.tds.validation.ValidadorPassword;
import umu.tds.validation.ValidadorUsuario;
import umu.tds.validation.ValidationException;

public class JPAUsuarioDAO implements UsuarioDAO {

	private static JPAUsuarioDAO instance;

	private JPAUsuarioDAO() {
	}

	public static JPAUsuarioDAO getInstance() {
		if (instance == null) {
			instance = new JPAUsuarioDAO();
		}
		return instance;
	}

	@Override
	public boolean addUsuario(String email, Date fechaNac, String user, String password, boolean premium)
			throws ValidationException {

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String fechaNacStr = dateFormat.format(fechaNac);

		// Validar datos del usuario
		ValidadorUsuario validadorEmail = new ValidadorEmail();
		ValidadorUsuario validadorFechaNac = new ValidadorFechaNac();
		ValidadorUsuario validadorPassword = new ValidadorPassword();

		validadorEmail.validar(email);
		validadorFechaNac.validar(fechaNacStr);
		validadorPassword.validar(password);

		Usuario usuario = new Usuario();
		usuario.setEmail(email);
		usuario.setFechaNac(fechaNac);
		usuario.setUser(user);
		usuario.setPassword(password);
		usuario.setPremium(premium);
		usuario.setCancionesRecientes(new ArrayList<>(Usuario.MAX));
		usuario.setPlaylists(new ArrayList<>());

		EntityManager em = JPAUtil.getEntityManager();
		em.getTransaction().begin();
		em.persist(usuario);
		em.getTransaction().commit();
		em.close();

		return true;
	}

	@Override
	public void updateUsuario(int id, String email, Date fechaNac, String user, String password, boolean premium) {
		EntityManager em = JPAUtil.getEntityManager();
		em.getTransaction().begin();

		Usuario usuario = em.find(Usuario.class, id);
		if (usuario != null) {
			usuario.setEmail(email);
			usuario.setFechaNac(fechaNac);
			usuario.setUser(user);
			usuario.setPassword(password);
			usuario.setPremium(premium);
			em.merge(usuario);
		}

		em.getTransaction().commit();
		em.close();
	}

	@Override
	public void removeUsuario(int id) {
		EntityManager em = JPAUtil.getEntityManager();
		em.getTransaction().begin();

		Usuario usuario = em.find(Usuario.class, id);
		if (usuario != null) {
			em.remove(usuario);
		}

		em.getTransaction().commit();
		em.close();
	}

	@Override
	public Usuario getUsuario(int id) {
		EntityManager em = JPAUtil.getEntityManager();
		Usuario usuario = em.find(Usuario.class, id);
		em.close();
		return usuario;
	}

	@Override
	public List<Usuario> getAllUsuarios() {
		EntityManager em = JPAUtil.getEntityManager();
		TypedQuery<Usuario> query = em.createQuery("SELECT u FROM Usuario u", Usuario.class);
		List<Usuario> usuarios = query.getResultList();
		em.close();
		return usuarios;
	}

	@Override
	public int addPlaylistToUsuario(int usuarioId, String nombrePlaylist, List<Integer> cancionIds) {
		EntityManager em = JPAUtil.getEntityManager();
		em.getTransaction().begin();

		Usuario usuario = em.find(Usuario.class, usuarioId);
		if (usuario == null) {
			em.getTransaction().rollback();
			em.close();
			throw new IllegalArgumentException("Usuario no encontrado con ID: " + usuarioId);
		}

		Playlist playlist = new Playlist();
		playlist.setNombre(nombrePlaylist);

		List<Cancion> canciones = em.createQuery("SELECT c FROM Cancion c WHERE c.id IN :ids", Cancion.class)
				.setParameter("ids", cancionIds).getResultList();
		playlist.setCanciones(canciones);

		usuario.getPlaylists().add(playlist);

		em.persist(playlist);
		em.merge(usuario);
		em.getTransaction().commit();
		em.close();

		return playlist.getId();
	}

	@Override
	public void updatePlaylist(int usuarioId, String nombrePlaylist, String nuevoNombre, List<Integer> cancionIds) {
		EntityManager em = JPAUtil.getEntityManager();
		em.getTransaction().begin();

		Usuario usuario = em.find(Usuario.class, usuarioId);
		if (usuario == null) {
			em.getTransaction().rollback();
			em.close();
			throw new IllegalArgumentException("Usuario no encontrado con ID: " + usuarioId);
		}

		Playlist playlist = usuario.getPlaylists().stream().filter(p -> p.getNombre().equals(nombrePlaylist))
				.findFirst().orElse(null);

		if (playlist == null) {
			em.getTransaction().rollback();
			em.close();
			throw new IllegalArgumentException("Playlist no encontrada con nombre: " + nombrePlaylist);
		}

		List<Cancion> canciones = em.createQuery("SELECT c FROM Cancion c WHERE c.id IN :ids", Cancion.class)
				.setParameter("ids", cancionIds).getResultList();
		playlist.setNombre(nuevoNombre);
		playlist.setCanciones(canciones);

		em.merge(playlist);
		em.getTransaction().commit();
		em.close();
	}

	@Override
	public void removePlaylist(int usuarioId, String nombrePlaylist) {
		EntityManager em = JPAUtil.getEntityManager();
		em.getTransaction().begin();

		Usuario usuario = em.find(Usuario.class, usuarioId);
		if (usuario == null) {
			em.getTransaction().rollback();
			em.close();
			throw new IllegalArgumentException("Usuario no encontrado con ID: " + usuarioId);
		}

		Playlist playlist = usuario.getPlaylists().stream().filter(p -> p.getNombre().equals(nombrePlaylist))
				.findFirst().orElse(null);

		if (playlist != null) {
			usuario.getPlaylists().remove(playlist);
			em.remove(playlist);
			em.merge(usuario);
		} else {
			em.getTransaction().rollback();
			em.close();
			throw new IllegalArgumentException("Playlist no encontrada con nombre: " + nombrePlaylist);
		}

		em.getTransaction().commit();
		em.close();
	}

	@Override
	public void addCancionReciente(int usuarioId, int cancionId) {
		EntityManager em = JPAUtil.getEntityManager();
		em.getTransaction().begin();

		Usuario usuario = em.find(Usuario.class, usuarioId);
		Cancion cancion = em.find(Cancion.class, cancionId);

		if (usuario != null && cancion != null) {
			List<Cancion> recientes = usuario.getCancionesRecientes();
			if (recientes.size() >= Usuario.MAX) {
				recientes.remove(0);
			}
			recientes.add(cancion);
			usuario.setCancionesRecientes(recientes);
			em.merge(usuario);
		}

		em.getTransaction().commit();
		em.close();
	}

	@Override
	public void clearCancionesRecientes(int usuarioId) {
		EntityManager em = JPAUtil.getEntityManager();
		em.getTransaction().begin();

		Usuario usuario = em.find(Usuario.class, usuarioId);
		if (usuario != null) {
			usuario.getCancionesRecientes().clear();
			em.merge(usuario);
		}

		em.getTransaction().commit();
		em.close();
	}

	@Override
	public boolean login(String user, String password) {
		EntityManager em = JPAUtil.getEntityManager();
		TypedQuery<Usuario> query = em
				.createQuery("SELECT u FROM Usuario u WHERE u.user = :user AND u.password = :password", Usuario.class);
		query.setParameter("user", user);
		query.setParameter("password", password);
		List<Usuario> result = query.getResultList();
		em.close();
		return !result.isEmpty();
	}

	@Override
	public Usuario getUsuarioByUsername(String username) {
		EntityManager em = JPAUtil.getEntityManager();
		TypedQuery<Usuario> query = em.createQuery("SELECT u FROM Usuario u WHERE u.user = :username", Usuario.class);
		query.setParameter("username", username);
		Usuario usuario = null;
		try {
			usuario = query.getSingleResult();
		} catch (NoResultException e) {
			// Manejar el caso en el que no se encuentra el usuario
		} finally {
			em.close();
		}
		return usuario;
	}

	@Override
	public List<Playlist> getAllPlaylists(int usuarioId) {
		EntityManager em = JPAUtil.getEntityManager();
		List<Playlist> playlists = null;
		try {
			// Retrieve the user by ID
			Usuario usuario = em.find(Usuario.class, usuarioId);
			if (usuario != null) {
				// Initialize the playlists collection to avoid lazy loading issues
				playlists = usuario.getPlaylists();
				playlists.size(); // Force initialization
			}
		} finally {
			em.close();
		}
		return playlists;
	}

	@Override
	public List<Cancion> getAllCancionesFromPlaylist(int usuarioId, String nombrePlaylist) {
		EntityManager em = JPAUtil.getEntityManager();
		try {
			TypedQuery<Playlist> query = em.createQuery(
					"SELECT DISTINCT p FROM Playlist p JOIN FETCH p.canciones c JOIN FETCH c.interpretes WHERE p.nombre = :nombre AND p.id IN (SELECT pl.id FROM Usuario u JOIN u.playlists pl WHERE u.id = :usuarioId)",
					Playlist.class);
			query.setParameter("nombre", nombrePlaylist);
			query.setParameter("usuarioId", usuarioId);
			List<Playlist> playlists = query.getResultList();
			if (playlists.isEmpty()) {
				return null;
			}
			// Esto asegura que todos los intérpretes estén cargados
			playlists.get(0).getCanciones().forEach(c -> c.getInterpretes().size());
			return playlists.get(0).getCanciones();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			em.close();
		}
	}
}
