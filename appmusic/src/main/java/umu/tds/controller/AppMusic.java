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

import javax.swing.table.DefaultTableModel;

import org.kohsuke.github.GitHub;
import org.kohsuke.github.GitHubBuilder;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

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
import umu.tds.validation.ValidationException;

public class AppMusic implements ICancionesListener {

	private static AppMusic instance = null;
	private static String PATH_CANCIONES = new File(".").getAbsolutePath();

	private CancionDAO cancionDAO;
	private InterpreteDAO interpreteDAO;
	private EstiloMusicalDAO estiloMusicalDAO;
	private UsuarioDAO usuarioDAO;
	private CargadorCanciones cargadorCanciones;

	private Usuario usuarioActual;

	private AppMusic() {

		inicializarAdaptadores();
		cargadorCanciones = new CargadorCanciones();
		cargadorCanciones.addCancionesListener(this);

	}

	public static AppMusic getInstance() {
		if (instance == null)
			instance = new AppMusic();
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
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					Date fechaNac = sdf.parse("1970-01-01");
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
					StringBuilder linea = new StringBuilder("      " + c.getTitulo() + "      ");
					for (Interprete i : c.getInterpretes()) {
						linea.append(i.getNombre()).append("     ");
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

				List<String> interpretes = Arrays.asList(interpreteCanciones.split("-"));

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

		StringBuilder pathCancion = new StringBuilder(PATH_CANCIONES.substring(0, PATH_CANCIONES.length() - 1));

		pathCancion.append("src\\main\\resources\\canciones\\");

		pathCancion.append(estilo + File.separator);

		crearDirectorio(pathCancion.toString());

		pathCancion.append(interprete + "-");
		pathCancion.append(titulo + ".mp3");

		FileOutputStream fos = new FileOutputStream(pathCancion.toString());

		byte[] buffer = new byte[2048];

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

}
