package umu.tds.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.swing.table.DefaultTableModel;

import org.kohsuke.github.GitHub;
import org.kohsuke.github.GitHubBuilder;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import javafx.util.Duration;
import tds.CargadorCanciones.CancionesEvent;
import tds.CargadorCanciones.CargadorCanciones;
import tds.CargadorCanciones.ICancionesListener;
import umu.tds.dao.CancionDAO;
import umu.tds.dao.EstiloMusicalDAO;
import umu.tds.dao.InterpreteDAO;
import umu.tds.dao.UsuarioDAO;
import umu.tds.descuento.Descuento;
import umu.tds.descuento.DescuentoFactory;
import umu.tds.factory.DAOFactory;
import umu.tds.model.Cancion;
import umu.tds.model.EstiloMusical;
import umu.tds.model.Interprete;
import umu.tds.model.Playlist;
import umu.tds.model.Usuario;
import umu.tds.player.Player;
import umu.tds.validation.ValidationException;

public class AppMusic implements ICancionesListener {

	private static AppMusic instance = null;

	private static final String FECHA_DEFAULT = "1970-01-01";
	private static final String FORMATO_FECHA = "yyyy-MM-dd";
	private static final String SEP_PDF = "      ";
	private static final int BUF_SIZE = 2048;
	private static final String MP3 = ".mp3";
	private static final String SEP_INTERPRETE = "-";
	private static String PATH;
	private static String PATH_CANCIONES = "src\\main\\resources\\canciones\\";

	private CancionDAO cancionDAO;
	private InterpreteDAO interpreteDAO;
	private EstiloMusicalDAO estiloMusicalDAO;
	private UsuarioDAO usuarioDAO;
	private CargadorCanciones cargadorCanciones;

	private Player player;

	private Usuario usuarioActual;

	private Playlist playlistActual;
	private Cancion cancionActual;
	private int indicePlaylist;
	private String modoReproduccion;

	private AppMusic() {

		inicializarAdaptadores();
		cargadorCanciones = new CargadorCanciones();
		player = new Player();
		cargadorCanciones.addCancionesListener(this);
		player.setOnEndOfMedia(() -> next()); // Detectar el fin de la canción y avanzar automáticamente
	}

	public static AppMusic getInstance() {
		if (instance == null) {
			String s = new File(".").getAbsolutePath();
			PATH = s.substring(0, PATH.length() - 1);
			instance = new AppMusic();

		}
		return instance;
	}

	public boolean loginConGitHub(String email, String password) {
		try {
			GitHub github = new GitHubBuilder().withPassword(email, password).build();
			if (github.isCredentialValid()) {
				List<Usuario> usuarios = usuarioDAO.getAllUsuarios();
				Usuario usuarioExistente = usuarios.stream().filter(u -> u.getEmail().equalsIgnoreCase(email))
						.findFirst().orElse(null);

				if (usuarioExistente == null) {
					// El usuario no existe, registrarlo
					SimpleDateFormat sdf = new SimpleDateFormat(FORMATO_FECHA);
					Date fechaNac = sdf.parse(FECHA_DEFAULT);
					String username = email.split("@")[0];
					boolean registrado = usuarioDAO.addUsuario(email, fechaNac, username, password, false);
					if (registrado) {
						usuarioActual = usuarioDAO.getUsuarioByUsername(username);
						return true;
					} else {
						return false;
					}
				} else {
					// El usuario ya existe, hacer login
					usuarioActual = usuarioExistente;
					return true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
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

	public void crearPDF(String filePath) throws FileNotFoundException, DocumentException {
		Document documentoPDF = new Document();
		try {
			PdfWriter.getInstance(documentoPDF, new FileOutputStream(filePath));
			documentoPDF.open();
			List<Playlist> plst = getAllPlaylists(usuarioActual.getId());

			for (Playlist p : plst) {
				documentoPDF.add(new Paragraph(p.getNombre()));
				for (Cancion c : p.getCanciones()) {
					StringBuilder linea = new StringBuilder(SEP_PDF + c.getTitulo() + SEP_PDF);
					for (Interprete i : c.getInterpretes()) {
						linea.append(i.getNombre()).append(SEP_PDF);
					}
					linea.append(c.getEstilo().getNombre());
					documentoPDF.add(new Paragraph(linea.toString()));
				}
			}
		} finally {
			documentoPDF.close();
		}
	}

	public Descuento getDescuento(String s) {
		return DescuentoFactory.getDescuento(s);
	}

	public void cargarCanciones(String path) {
		cargadorCanciones.setArchivoCanciones(path);
	}

	@Override
	public void enteradoCambioRuta(CancionesEvent evento) {
		List<tds.CargadorCanciones.Cancion> cs = evento.getListaCanciones().getCancion();
		for (tds.CargadorCanciones.Cancion c : cs) {
			String tituloCanciones = c.getTitulo();
			String interpreteCanciones = c.getInterprete();
			String estiloCanciones = c.getEstilo();
			String urlPath = c.getURL();
			try {

				String path = descargarCancion(urlPath, interpreteCanciones, estiloCanciones, tituloCanciones);

				List<String> interpretes = Arrays.asList(interpreteCanciones.split(SEP_INTERPRETE));

				cancionDAO.addCancion(c.getTitulo(), interpretes, estiloCanciones, path, 0);
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}
		}

	}

	private String descargarCancion(String urlPath, String interprete, String estilo, String titulo) throws Exception {
		URL url = new URL(urlPath);
		URLConnection uc = url.openConnection();

		InputStream is = new BufferedInputStream(uc.getInputStream());

		StringBuilder pathCancion = new StringBuilder(PATH);

		pathCancion.append(PATH_CANCIONES);

		pathCancion.append(estilo + File.separator);

		crearDirectorio(pathCancion.toString());

		pathCancion.append(interprete + SEP_INTERPRETE);
		pathCancion.append(titulo + MP3);

		FileOutputStream fos = new FileOutputStream(pathCancion.toString());

		byte[] buffer = new byte[BUF_SIZE];

		int bytesLeidos;

		while ((bytesLeidos = is.read(buffer)) != -1)
			fos.write(buffer, 0, bytesLeidos);

		fos.close();
		is.close();

		return pathCancion.toString();
	}

	private void crearDirectorio(String path) {
		File directorio = new File(path);
		if (!directorio.exists())
			directorio.mkdirs();
	}

	public boolean setPremium(int usuarioId, boolean premium) {
		usuarioDAO.setPremium(usuarioId, premium);
		return premium;
	}

	public void setPlaylist(Playlist playlist) {
		if (this.modoReproduccion.equals("Aleatorio")) {
			int tamPlaylist = playlist.getCanciones().size();
			Random rand = new Random();
			indicePlaylist = rand.nextInt(tamPlaylist); // Genera un número aleatorio entre 0 y tamPlaylist-1
		} else {
			indicePlaylist = 0;
		}
		playlistActual = playlist;
	}

	public void play() {
		if (cancionActual != null) {
			player.play("stop", null); // Detener la canción actual antes de reproducir la nueva
		}

		cancionActual = playlistActual.getCanciones().get(indicePlaylist);
		cancionDAO.aumentarReproduccion(cancionActual.getId());
		player.play("play", cancionActual);
	}

	public void pause() {

		player.play("pause", null);

	}

	public void stop() {

		player.play("stop", null);

	}

	public void next() {
		if (playlistActual != null && !playlistActual.getCanciones().isEmpty()) {
			if (modoReproduccion.equals("Aleatorio")) {
				Random rand = new Random();
				indicePlaylist = rand.nextInt(playlistActual.getCanciones().size());
			} else {
				indicePlaylist = (indicePlaylist + 1) % playlistActual.getCanciones().size();
			}
			play();
		}
	}

	public void previous() {
		if (playlistActual != null && !playlistActual.getCanciones().isEmpty()) {
			if (modoReproduccion.equals("Aleatorio")) {
				Random rand = new Random();
				indicePlaylist = rand.nextInt(playlistActual.getCanciones().size());
			} else {
				indicePlaylist = (indicePlaylist - 1 + playlistActual.getCanciones().size())
						% playlistActual.getCanciones().size();
			}
			play();
		}
	}

	public Cancion getCancionActual() {
		return this.cancionActual;
	}

	public void setModoReproduccion(String modo) {
		this.modoReproduccion = modo;
	}

	public Duration getTiempoActual() {
		return player.getCurrentTime();
	}

	public Duration getDuracion() {
		return player.getTotalDuration();
	}

	public void seek(Duration seekTime) {
		player.seek(seekTime);
	}
}
