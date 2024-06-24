package umu.tds.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import umu.tds.model.Cancion;
import umu.tds.model.EstiloMusical;
import umu.tds.model.Interprete;
import umu.tds.model.Playlist;
import umu.tds.model.Usuario;

public class JPACancionDAO implements CancionDAO {

	private static JPACancionDAO instance;

	private JPACancionDAO() {
	}

	public static JPACancionDAO getInstance() {
		if (instance == null) {
			instance = new JPACancionDAO();
		}
		return instance;
	}

	@Override
	public int addCancion(String titulo, List<String> interpreteNombres, String estiloNombre) {
		EntityManager em = JPAUtil.getEntityManager();
		em.getTransaction().begin();

		// Buscar o crear EstiloMusical
		EstiloMusical estilo = findOrCreateEstiloMusical(em, estiloNombre);

		// Buscar o crear Interpretes
		List<Interprete> interpretes = new ArrayList<>();
		for (String nombre : interpreteNombres) {
			Interprete interprete = findOrCreateInterprete(em, nombre);
			interpretes.add(interprete);
		}

		// Crear la canción
		Cancion cancion = new Cancion();
		cancion.setTitulo(titulo);
		cancion.setNumReproducciones(0); // Número de reproducciones se establece a 0
		cancion.setEstilo(estilo);
		cancion.setInterpretes(interpretes);

		em.persist(cancion);
		em.getTransaction().commit();
		em.close();

		return cancion.getId();
	}

	@Override
	public void updateCancion(int id, String titulo, List<String> interpreteNombres, String estiloNombre,
			long numReproducciones) {
		EntityManager em = JPAUtil.getEntityManager();
		em.getTransaction().begin();

		Cancion cancion = em.find(Cancion.class, id);
		if (cancion != null) {
			cancion.setTitulo(titulo);
			cancion.setNumReproducciones(numReproducciones);

			// Update Estilo
			EstiloMusical estilo = findOrCreateEstiloMusical(em, estiloNombre);
			cancion.setEstilo(estilo);

			// Update Interpretes
			List<Interprete> interpretes = interpreteNombres.stream().map(nombre -> findOrCreateInterprete(em, nombre))
					.collect(Collectors.toList());
			cancion.setInterpretes(interpretes);

			em.merge(cancion);
		}

		em.getTransaction().commit();
		em.close();
	}

	@Override
	public void removeCancion(int id) {
		EntityManager em = JPAUtil.getEntityManager();
		em.getTransaction().begin();

		Cancion cancion = em.find(Cancion.class, id);
		if (cancion != null) {
			// Remove references from playlists
			List<Playlist> playlists = em
					.createQuery("SELECT p FROM Playlist p JOIN p.canciones c WHERE c.id = :cancionId", Playlist.class)
					.setParameter("cancionId", id).getResultList();
			for (Playlist playlist : playlists) {
				playlist.getCanciones().remove(cancion);
				em.merge(playlist);
			}

			// Remove references from users' recent songs
			List<Usuario> usuarios = em
					.createQuery("SELECT u FROM Usuario u JOIN u.cancionesRecientes c WHERE c.id = :cancionId",
							Usuario.class)
					.setParameter("cancionId", id).getResultList();
			for (Usuario usuario : usuarios) {
				usuario.getCancionesRecientes().remove(cancion);
				em.merge(usuario);
			}

			em.remove(cancion);
		}

		em.getTransaction().commit();
		em.close();
	}

	@Override
	public Cancion getCancion(int id) {
		EntityManager em = JPAUtil.getEntityManager();
		Cancion cancion = em.find(Cancion.class, id);
		em.close();
		return cancion;
	}

	@Override
	public List<Cancion> getAllCanciones() {
		EntityManager em = JPAUtil.getEntityManager();
		List<Cancion> canciones = em.createQuery("SELECT c FROM Cancion c", Cancion.class).getResultList();
		em.close();
		return canciones;
	}

	private EstiloMusical findOrCreateEstiloMusical(EntityManager em, String nombre) {
		TypedQuery<EstiloMusical> query = em.createQuery("SELECT e FROM EstiloMusical e WHERE e.nombre = :nombre",
				EstiloMusical.class);
		query.setParameter("nombre", nombre);
		List<EstiloMusical> resultados = query.getResultList();

		if (resultados.isEmpty()) {
			EstiloMusical nuevoEstilo = new EstiloMusical();
			nuevoEstilo.setNombre(nombre);
			em.persist(nuevoEstilo);
			return nuevoEstilo;
		} else {
			return resultados.get(0);
		}
	}

	private Interprete findOrCreateInterprete(EntityManager em, String nombre) {
		TypedQuery<Interprete> query = em.createQuery("SELECT i FROM Interprete i WHERE i.nombre = :nombre",
				Interprete.class);
		query.setParameter("nombre", nombre);
		List<Interprete> resultados = query.getResultList();

		if (resultados.isEmpty()) {
			Interprete nuevoInterprete = new Interprete();
			nuevoInterprete.setNombre(nombre);
			em.persist(nuevoInterprete);
			return nuevoInterprete;
		} else {
			return resultados.get(0);
		}
	}
}
