package umu.tds.test;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import umu.tds.dao.CancionDAO;
import umu.tds.dao.EstiloMusicalDAO;
import umu.tds.dao.InterpreteDAO;
import umu.tds.dao.JPACancionDAO;
import umu.tds.dao.JPAEstiloMusicalDAO;
import umu.tds.dao.JPAInterpreteDAO;
import umu.tds.dao.JPAUsuarioDAO;
import umu.tds.dao.UsuarioDAO;
import umu.tds.validation.ValidationException;

public class DatabaseInitializer {

	public static void initialize() {
		UsuarioDAO usuarioDAO = JPAUsuarioDAO.getInstance();
		EstiloMusicalDAO estiloMusicalDAO = JPAEstiloMusicalDAO.getInstance();
		InterpreteDAO interpreteDAO = JPAInterpreteDAO.getInstance();
		CancionDAO cancionDAO = JPACancionDAO.getInstance();

		// Crear usuario admin
		try {
			usuarioDAO.addUsuario("admin@example.com", new Date(), "admin1", "admin1", true);
		} catch (ValidationException e) {
			e.printStackTrace();
		}
		int adminId = usuarioDAO.getUsuarioByUsername("admin1").getId();

		// Crear estilos musicales
		List<String> estilos = Arrays.asList("Pop", "Rock", "Jazz", "Clasica", "Hip Hop", "Reggae", "Electronica");
		for (String estilo : estilos) {
			estiloMusicalDAO.addEstiloMusical(estilo);
		}

		// Crear canciones para cada estilo
		addCancionesPorEstilo("Pop",
				Arrays.asList("Michael Jackson - Thriller", "Madonna - Like a Prayer", "Prince - Purple Rain"),
				cancionDAO, interpreteDAO, estiloMusicalDAO);
		addCancionesPorEstilo("Rock", Arrays.asList("Led Zeppelin - Stairway to Heaven", "Queen - Bohemian Rhapsody",
				"The Beatles - Hey Jude"), cancionDAO, interpreteDAO, estiloMusicalDAO);
		addCancionesPorEstilo("Jazz", Arrays.asList("Miles Davis - So What", "John Coltrane - Giant Steps",
				"Duke Ellington - Take the 'A' Train"), cancionDAO, interpreteDAO, estiloMusicalDAO);
		addCancionesPorEstilo("Clasica",
				Arrays.asList("Beethoven - Symphony No.5", "Mozart - Requiem", "Bach - Toccata and Fugue in D Minor"),
				cancionDAO, interpreteDAO, estiloMusicalDAO);
		addCancionesPorEstilo("Hip Hop",
				Arrays.asList("Tupac - Changes", "Notorious B.I.G. - Juicy", "Eminem - Lose Yourself"), cancionDAO,
				interpreteDAO, estiloMusicalDAO);
		addCancionesPorEstilo("Reggae", Arrays.asList("Bob Marley - No Woman No Cry", "Peter Tosh - Legalize It",
				"Jimmy Cliff - The Harder They Come"), cancionDAO, interpreteDAO, estiloMusicalDAO);
		addCancionesPorEstilo("Electronica",
				Arrays.asList("Daft Punk - One More Time", "The Chemical Brothers - Galvanize", "Deadmau5 - Strobe"),
				cancionDAO, interpreteDAO, estiloMusicalDAO);

		// Crear playlists para el usuario admin
		addPlaylist(adminId, "Favoritas", Arrays.asList("Michael Jackson - Thriller",
				"Led Zeppelin - Stairway to Heaven", "Miles Davis - So What"), cancionDAO, usuarioDAO);
		addPlaylist(adminId, "Workout Mix",
				Arrays.asList("Daft Punk - One More Time", "Eminem - Lose Yourself", "Queen - Bohemian Rhapsody"),
				cancionDAO, usuarioDAO);
		addPlaylist(adminId, "Clásicas",
				Arrays.asList("Beethoven - Symphony No.5", "Mozart - Requiem", "Bach - Toccata and Fugue in D Minor"),
				cancionDAO, usuarioDAO);
	}

	private static void addCancionesPorEstilo(String estilo, List<String> canciones, CancionDAO cancionDAO,
			InterpreteDAO interpreteDAO, EstiloMusicalDAO estiloMusicalDAO) {
		for (String cancionData : canciones) {
			String[] parts = cancionData.split(" - ");
			String interpreteNombre = parts[0];
			String titulo = parts[1];

			// Añadir intérprete si no existe
			if (interpreteDAO.getAllInterpretes().stream().noneMatch(i -> i.getNombre().equals(interpreteNombre))) {
				interpreteDAO.addInterprete(interpreteNombre);
			}

			// Añadir canción
			cancionDAO.addCancion(titulo, Arrays.asList(interpreteNombre), estilo, "", 0);
		}
	}

	private static void addPlaylist(int usuarioId, String nombrePlaylist, List<String> canciones, CancionDAO cancionDAO,
			UsuarioDAO usuarioDAO) {
		List<Integer> cancionIds = canciones.stream().map(titulo -> {
			String[] parts = titulo.split(" - ");
			String interpreteNombre = parts[0];
			String tituloCancion = parts[1];
			return cancionDAO.getAllCanciones().stream()
					.filter(c -> c.getTitulo().equals(tituloCancion)
							&& c.getInterpretes().get(0).getNombre().equals(interpreteNombre))
					.findFirst().get().getId();
		}).collect(Collectors.toList());

		usuarioDAO.addPlaylistToUsuario(usuarioId, nombrePlaylist, cancionIds);
	}

	public static void main(String[] args) {
		initialize();
		System.out.println("Database initialized successfully.");
	}
}
