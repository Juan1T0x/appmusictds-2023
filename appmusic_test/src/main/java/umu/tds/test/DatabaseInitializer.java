package umu.tds.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import umu.tds.dao.CancionDAO;
import umu.tds.dao.EstiloMusicalDAO;
import umu.tds.dao.InterpreteDAO;
import umu.tds.dao.UsuarioDAO;
import umu.tds.factory.DAOFactory;
import umu.tds.model.Usuario;
import umu.tds.validation.ValidationException;

public class DatabaseInitializer {

	public static void main(String[] args) {
		// Initialize DAOs
		DAOFactory daoFactory = DAOFactory.getDAOFactory(DAOFactory.JPA);
		CancionDAO cancionDAO = daoFactory.getCancionDAO();
		InterpreteDAO interpreteDAO = daoFactory.getInterpreteDAO();
		EstiloMusicalDAO estiloMusicalDAO = daoFactory.getEstiloMusicalDAO();
		UsuarioDAO usuarioDAO = daoFactory.getUsuarioDAO();

		// Define styles
		List<String> estilos = Arrays.asList("Pop", "Rock", "Jazz", "Classical", "Hip-Hop");
		List<Integer> estiloIds = new ArrayList<>();
		for (String estilo : estilos) {
			estiloIds.add(estiloMusicalDAO.addEstiloMusical(estilo));
		}

		// Define interpreters
		List<String> interpretes = Arrays.asList("Artist A", "Band B", "Musician C", "Singer D", "Group E");
		List<Integer> interpreteIds = new ArrayList<>();
		for (String interprete : interpretes) {
			interpreteIds.add(interpreteDAO.addInterprete(interprete));
		}

		// Define songs
		List<String> canciones = Arrays.asList("Song 1", "Track 2", "Melody 3", "Tune 4", "Harmony 5", "Rhythm 6");
		List<Integer> cancionIds = new ArrayList<>();
		for (String cancion : canciones) {
			cancionIds.add(cancionDAO.addCancion(cancion, interpretes, estilos.get(0)));
		}

		// Define users
		List<Usuario> usuarios = new ArrayList<>();
		Usuario user1 = new Usuario();
		user1.setEmail("user1@example.com");
		user1.setFechaNac(new Date());
		user1.setUser("user1");
		user1.setPassword("password1");
		user1.setPremium(true);
		usuarios.add(user1);

		Usuario user2 = new Usuario();
		user2.setEmail("user2@example.com");
		user2.setFechaNac(new Date());
		user2.setUser("user2");
		user2.setPassword("password2");
		user2.setPremium(false);
		usuarios.add(user2);

		Usuario user3 = new Usuario();
		user3.setEmail("user3@example.com");
		user3.setFechaNac(new Date());
		user3.setUser("user3");
		user3.setPassword("password3");
		user3.setPremium(true);
		usuarios.add(user3);

		Usuario user4 = new Usuario();
		user4.setEmail("user4@example.com");
		user4.setFechaNac(new Date());
		user4.setUser("user4");
		user4.setPassword("password4");
		user4.setPremium(false);
		usuarios.add(user4);

		Usuario user5 = new Usuario();
		user5.setEmail("user5@example.com");
		user5.setFechaNac(new Date());
		user5.setUser("user5");
		user5.setPassword("password5");
		user5.setPremium(true);
		usuarios.add(user5);

		List<Integer> usuarioIds = new ArrayList<>();
		for (Usuario usuario : usuarios) {
			try {
				if (usuarioDAO.addUsuario(usuario.getEmail(), usuario.getFechaNac(), usuario.getUser(),
						usuario.getPassword(), usuario.isPremium())) {
					usuarioIds.add(usuario.getId());
				}
			} catch (ValidationException e) {
				System.err.println("Validation error: " + e.getMessage());
			}
		}

		// Create playlists for each user
		for (int usuarioId : usuarioIds) {
			List<Integer> sublist1 = cancionIds.subList(0, 3);
			List<Integer> sublist2 = cancionIds.subList(3, cancionIds.size());
			usuarioDAO.addPlaylistToUsuario(usuarioId+1, "Favorites", sublist1);
			usuarioDAO.addPlaylistToUsuario(usuarioId+1, "Chill Vibes", sublist2);
			usuarioDAO.addPlaylistToUsuario(usuarioId+1, "Workout Mix", cancionIds);
		}

		System.out.println("Initial database load completed successfully!");
	}
}
