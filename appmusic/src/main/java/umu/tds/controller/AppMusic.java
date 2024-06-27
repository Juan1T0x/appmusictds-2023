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

/**
 * Clase controlador de la aplicación.
 * Implementa el patrón Singleton para garantizar una única instancia.
 */
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

    /**
     * Constructor privado para inicializar los adaptadores y configuraciones iniciales.
     */
    private AppMusic() {
        inicializarAdaptadores();
        cargadorCanciones = new CargadorCanciones();
        player = new Player();
        cargadorCanciones.addCancionesListener(this);
        player.setOnEndOfMedia(() -> next()); // Detectar el fin de la canción y avanzar automáticamente
    }

    /**
     * Obtiene la instancia única de la clase.
     * @return la instancia de AppMusic.
     */
    public static AppMusic getInstance() {
        if (instance == null) {
            String s = new File(".").getAbsolutePath();
            PATH = s.substring(0, s.length() - 1);
            instance = new AppMusic();
        }
        return instance;
    }

    /**
     * Realiza el login con GitHub.
     * @param email correo del usuario.
     * @param password contraseña del usuario.
     * @return true si el login fue exitoso, false en caso contrario.
     */
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

    /**
     * Registra un nuevo usuario.
     * @param email correo del usuario.
     * @param fechaNac fecha de nacimiento del usuario.
     * @param user nombre de usuario.
     * @param password contraseña del usuario.
     * @param premium indica si el usuario es premium.
     * @return true si el registro fue exitoso.
     * @throws ValidationException si ocurre un error de validación.
     */
    public boolean registrarUsuario(String email, Date fechaNac, String user, String password, boolean premium)
            throws ValidationException {
        return usuarioDAO.addUsuario(email, fechaNac, user, password, premium);
    }

    /**
     * Realiza el login del usuario.
     * @param usuario nombre de usuario.
     * @param password contraseña del usuario.
     * @return true si el login fue exitoso.
     */
    public boolean login(String usuario, String password) {
        boolean autenticado = usuarioDAO.login(usuario, password);
        if (autenticado) {
            usuarioActual = usuarioDAO.getUsuarioByUsername(usuario); // Obtener usuario autenticado
        }
        return autenticado;
    }

    /**
     * Obtiene el usuario actual.
     * @return el usuario actual.
     */
    public Usuario getUsuarioActual() {
        return usuarioActual;
    }

    /**
     * Obtiene todas las canciones.
     * @return lista de canciones.
     */
    public List<Cancion> getAllCanciones() {
        return cancionDAO.getAllCanciones();
    }

    /**
     * Obtiene todos los estilos musicales.
     * @return lista de estilos musicales.
     */
    public List<EstiloMusical> getAllEstilosMusicales() {
        return estiloMusicalDAO.getAllEstilosMusicales();
    }

    /**
     * Obtiene todas las playlists de un usuario.
     * @param usuarioId id del usuario.
     * @return lista de playlists.
     */
    public List<Playlist> getAllPlaylists(int usuarioId) {
        return usuarioDAO.getAllPlaylists(usuarioId);
    }

    /**
     * Obtiene todas las canciones de una playlist.
     * @param usuarioId id del usuario.
     * @param nombrePlaylist nombre de la playlist.
     * @return lista de canciones.
     */
    public List<Cancion> getAllCancionesFromPlaylist(int usuarioId, String nombrePlaylist) {
        return usuarioDAO.getAllCancionesFromPlaylist(usuarioId, nombrePlaylist);
    }

    /**
     * Obtiene las canciones recientes de un usuario.
     * @param usuarioId id del usuario.
     * @return lista de canciones recientes.
     */
    public List<Cancion> getCancionesRecientes(int usuarioId) {
        return usuarioDAO.getCancionesRecientes(usuarioId);
    }

    /**
     * Obtiene las canciones más populares.
     * @param maxResults número máximo de resultados.
     * @return lista de canciones más populares.
     */
    public List<Cancion> getTopCanciones(int maxResults) {
        return cancionDAO.getTopCanciones(maxResults);
    }

    /**
     * Añade una playlist a un usuario.
     * @param id id del usuario.
     * @param titulo título de la playlist.
     * @param cancionIds ids de las canciones.
     */
    public void addPlaylistToUsuario(int id, String titulo, List<Integer> cancionIds) {
        usuarioDAO.addPlaylistToUsuario(id, titulo, cancionIds);
    }

    /**
     * Actualiza una playlist de un usuario.
     * @param id id del usuario.
     * @param nombre nombre de la playlist.
     * @param titulo título de la playlist.
     * @param cancionIds ids de las canciones.
     */
    public void updatePlaylist(int id, String nombre, String titulo, List<Integer> cancionIds) {
        usuarioDAO.updatePlaylist(id, nombre, titulo, cancionIds);
    }

    /**
     * Elimina una playlist de un usuario.
     * @param id id del usuario.
     * @param nombre nombre de la playlist.
     */
    public void removePlaylist(int id, String nombre) {
        usuarioDAO.removePlaylist(id, nombre);
    }

    /**
     * Elimina una canción de una playlist de un usuario.
     * @param usuarioId id del usuario.
     * @param playlistId id de la playlist.
     * @param cancionId id de la canción.
     */
    public void removeCancionFromPlaylist(int usuarioId, int playlistId, int cancionId) {
        usuarioDAO.removeCancionFromPlaylist(usuarioId, playlistId, cancionId);
    }

    /**
     * Consulta una lista de canciones según título, intérprete y estilo.
     * @param titulo título de la canción.
     * @param interprete intérprete de la canción.
     * @param estilo estilo de la canción.
     * @return lista de canciones que coinciden con los criterios.
     */
    public List<Cancion> queryListaCanciones(String titulo, String interprete, String estilo) {
        return cancionDAO.queryListaCanciones(titulo, interprete, estilo);
    }

    /**
     * Inicializa los adaptadores DAO.
     */
    private void inicializarAdaptadores() {
        DAOFactory daoFactory = DAOFactory.getDAOFactory(DAOFactory.JPA);
        cancionDAO = daoFactory.getCancionDAO();
        interpreteDAO = daoFactory.getInterpreteDAO();
        estiloMusicalDAO = daoFactory.getEstiloMusicalDAO();
        usuarioDAO = daoFactory.getUsuarioDAO();
    }

    /**
     * Crea un archivo PDF con las playlists del usuario.
     * @param filePath ruta del archivo PDF.
     * @throws FileNotFoundException si no se encuentra el archivo.
     * @throws DocumentException si ocurre un error al crear el documento.
     */
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

    /**
     * Obtiene un descuento según el nombre.
     * @param s nombre del descuento.
     * @return el descuento correspondiente.
     */
    public Descuento getDescuento(String s) {
        return DescuentoFactory.getDescuento(s);
    }

    /**
     * Carga las canciones desde una ruta específica.
     * @param path ruta del archivo de canciones.
     */
    public void cargarCanciones(String path) {
        cargadorCanciones.setArchivoCanciones(path);
    }

    /**
     * Método llamado cuando hay un cambio en el Cargador de Canciones.
     * Descarga las canciones que el Cargador ha procesado.
     * @param evento evento de cambio de canciones.
     */
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
                continue;
            }
        }
    }

    /**
     * Descarga una canción desde una URL específica.
     * @param urlPath URL de la canción.
     * @param interprete intérprete de la canción.
     * @param estilo estilo de la canción.
     * @param titulo título de la canción.
     * @return la ruta donde se guardó la canción.
     * @throws Exception si ocurre un error durante la descarga.
     */
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

    /**
     * Crea un directorio si no existe.
     * @param path ruta del directorio.
     */
    private void crearDirectorio(String path) {
        File directorio = new File(path);
        if (!directorio.exists())
            directorio.mkdirs();
    }

    /**
     * Establece el estado premium de un usuario.
     * @param usuarioId id del usuario.
     * @param premium estado premium.
     * @return el estado premium establecido.
     */
    public boolean setPremium(int usuarioId, boolean premium) {
        usuarioDAO.setPremium(usuarioId, premium);
        return premium;
    }

    /**
     * Establece la playlist actual.
     * @param playlist la playlist a establecer.
     */
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

    /**
     * Reproduce la canción actual.
     */
    public void play() {
        if (cancionActual != null) {
            player.play("stop", null); // Detener la canción actual antes de reproducir la nueva
        }

        cancionActual = playlistActual.getCanciones().get(indicePlaylist);
        cancionDAO.aumentarReproduccion(cancionActual.getId());
        player.play("play", cancionActual);
        this.addCancionReciente(usuarioActual.getId(), cancionActual.getId());
    }

    /**
     * Pausa la reproducción de la canción.
     */
    public void pause() {
        player.play("pause", null);
    }

    /**
     * Detiene la reproducción de la canción.
     */
    public void stop() {
        player.play("stop", null);
    }

    /**
     * Avanza a la siguiente canción en la playlist.
     */
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

    /**
     * Retrocede a la canción anterior en la playlist.
     */
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

    /**
     * Obtiene la canción actual.
     * @return la canción actual.
     */
    public Cancion getCancionActual() {
        return this.cancionActual;
    }

    /**
     * Establece el modo de reproducción.
     * @param modo el modo de reproducción a establecer.
     */
    public void setModoReproduccion(String modo) {
        this.modoReproduccion = modo;
    }

    /**
     * Obtiene el tiempo actual de la canción en reproducción.
     * @return el tiempo actual de la canción.
     */
    public Duration getTiempoActual() {
        return player.getCurrentTime();
    }

    /**
     * Obtiene la duración total de la canción en reproducción.
     * @return la duración total de la canción.
     */
    public Duration getDuracion() {
        return player.getTotalDuration();
    }

    /**
     * Busca una posición específica en la canción.
     * @param seekTime el tiempo a buscar.
     */
    public void seek(Duration seekTime) {
        player.seek(seekTime);
    }

    /**
     * Añade una canción reciente al usuario.
     * @param usuarioId id del usuario.
     * @param cancionId id de la canción.
     */
    public void addCancionReciente(int usuarioId, int cancionId) {
        usuarioDAO.addCancionReciente(usuarioId, cancionId);
    }
}
