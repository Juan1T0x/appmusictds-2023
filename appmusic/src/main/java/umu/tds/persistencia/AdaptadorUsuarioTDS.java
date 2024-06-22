package umu.tds.persistencia;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.stream.Collectors;

import beans.Entidad;
import beans.Propiedad;
import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;
import umu.tds.model.cancion.Cancion;
import umu.tds.model.usuario.Usuario;

public class AdaptadorUsuarioTDS implements AdaptadorUsuarioDAO {

	private static final String CODIGO = "codigo";
	private static final String CANCIONES_RECIENTES = "cancionesRecientes";
	private static final String PREMIUM = "premium";
	private static final String PASSWORD = "password";
	private static final String USER = "user";
	private static final String FECHA_NAC = "fechaNac";
	private static final String EMAIL = "email";
	private static final String USUARIO = "usuario";

	private SimpleDateFormat dateFormat;

	private static ServicioPersistencia servPersistencia;
	private static AdaptadorUsuarioTDS unicaInstancia = null;

	public static AdaptadorUsuarioTDS getUnicaInstancia() {
		if (unicaInstancia == null)
			unicaInstancia = new AdaptadorUsuarioTDS();
		return unicaInstancia;
	}

	private AdaptadorUsuarioTDS() {
		servPersistencia = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
		dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	}

	@Override
	public void registrarUsuario(Usuario usuario) {
		try {
			if (servPersistencia.recuperarEntidad(usuario.getCodigo()) != null)
				return;
		} catch (NullPointerException e) {
			// El usuario no existe, proceder con el registro
		}

		Entidad eUsuario = new Entidad();
		eUsuario.setNombre(USUARIO);

		eUsuario.setPropiedades(Arrays.asList(new Propiedad(EMAIL, usuario.getEmail()),
				new Propiedad(FECHA_NAC, dateFormat.format(usuario.getFechaNac())),
				new Propiedad(USER, usuario.getUser()), new Propiedad(PASSWORD, usuario.getPassword()),
				new Propiedad(PREMIUM, String.valueOf(usuario.isPremium())),
				new Propiedad(CANCIONES_RECIENTES, obtenerStringCancionesRecientes(usuario.getCancionesRecientes()))));

		eUsuario = servPersistencia.registrarEntidad(eUsuario);
		usuario.setCodigo(eUsuario.getId());
	}

	@Override
	public void borrarUsuario(Usuario usuario) {
		Entidad eUsuario = servPersistencia.recuperarEntidad(usuario.getCodigo());
		if (eUsuario != null) {
			servPersistencia.borrarEntidad(eUsuario);
		}
	}

	@Override
	public void modificarUsuario(Usuario usuario) {
		Entidad eUsuario = servPersistencia.recuperarEntidad(usuario.getCodigo());

		if (eUsuario == null) {
			throw new IllegalArgumentException("Usuario no encontrado en la base de datos");
		}

		for (Propiedad propiedad : eUsuario.getPropiedades()) {
			if (propiedad.getNombre().equals(CODIGO)) {
				propiedad.setValor(String.valueOf(usuario.getCodigo()));
			} else if (propiedad.getNombre().equals(EMAIL)) {
				propiedad.setValor(usuario.getEmail());
			} else if (propiedad.getNombre().equals(FECHA_NAC)) {
				propiedad.setValor(dateFormat.format(usuario.getFechaNac()));
			} else if (propiedad.getNombre().equals(USER)) {
				propiedad.setValor(usuario.getUser());
			} else if (propiedad.getNombre().equals(PASSWORD)) {
				propiedad.setValor(usuario.getPassword());
			} else if (propiedad.getNombre().equals(PREMIUM)) {
				propiedad.setValor(String.valueOf(usuario.isPremium()));
			} else if (propiedad.getNombre().equals(CANCIONES_RECIENTES)) {
				propiedad.setValor(obtenerStringCancionesRecientes(usuario.getCancionesRecientes()));
			}
			servPersistencia.modificarPropiedad(propiedad);
		}
	}

	@Override
	public Usuario recuperarUsuario(int codigo) {
		Entidad eUsuario = servPersistencia.recuperarEntidad(codigo);

		String email = servPersistencia.recuperarPropiedadEntidad(eUsuario, EMAIL);
		Date fechaNac = null;
		try {
			fechaNac = dateFormat.parse(servPersistencia.recuperarPropiedadEntidad(eUsuario, FECHA_NAC));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		String user = servPersistencia.recuperarPropiedadEntidad(eUsuario, USER);
		String password = servPersistencia.recuperarPropiedadEntidad(eUsuario, PASSWORD);
		boolean premium = Boolean.parseBoolean(servPersistencia.recuperarPropiedadEntidad(eUsuario, PREMIUM));

		Usuario usuario = new Usuario(email, fechaNac, user, password, premium);

		Queue<Cancion> cancionesRecientes = obtenerCancionesDesdeCodigos(
				servPersistencia.recuperarPropiedadEntidad(eUsuario, CANCIONES_RECIENTES));

		cancionesRecientes.forEach(usuario::addCancionRecientes);

		usuario.setCodigo(codigo);

		return usuario;
	}

	@Override
	public List<Usuario> recuperarUsuarios() {
		return servPersistencia.recuperarEntidades(USUARIO).stream().map(eUsuario -> recuperarUsuario(eUsuario.getId()))
				.collect(Collectors.toList());
	}

	private String obtenerStringCancionesRecientes(Queue<Cancion> cancionesRecientes) {
		return cancionesRecientes.stream().map(cancion -> String.valueOf(cancion.getCodigo()))
				.collect(Collectors.joining(" "));
	}

	private Queue<Cancion> obtenerCancionesDesdeCodigos(String codigos) {
		Queue<Cancion> cancionesRecientes = new ArrayBlockingQueue<>(Usuario.MAX);
		StringTokenizer strTok = new StringTokenizer(codigos, " ");
		AdaptadorCancionTDS adaptadorCancion = AdaptadorCancionTDS.getUnicaInstancia();

		while (strTok.hasMoreElements()) {
			int codigoCancion = Integer.parseInt(strTok.nextToken());
			Cancion cancion = adaptadorCancion.recuperarCancion(codigoCancion);
			cancionesRecientes.add(cancion);
		}
		return cancionesRecientes;
	}
}
