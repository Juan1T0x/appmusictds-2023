package umu.tds.persistencia;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;
import java.util.concurrent.ArrayBlockingQueue;

import beans.Entidad;
import beans.Propiedad;
import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;
import umu.tds.model.Cancion;
import umu.tds.model.Usuario;

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
		Entidad eUsuario = null;
		try {
			eUsuario = servPersistencia.recuperarEntidad(usuario.getCodigo());
		} catch (NullPointerException e) {
		}

		if (eUsuario != null)
			return;

		eUsuario = new Entidad();

		eUsuario.setNombre(USUARIO);

		eUsuario.setPropiedades(new ArrayList<Propiedad>(Arrays.asList(new Propiedad(EMAIL, usuario.getEmail()),
				new Propiedad(FECHA_NAC, dateFormat.format(usuario.getFechaNac())),
				new Propiedad(USER, usuario.getUser()), new Propiedad(PASSWORD, usuario.getPassword()),
				new Propiedad(PREMIUM, String.valueOf(usuario.isPremium())),
				new Propiedad(CANCIONES_RECIENTES, obtenerStringCancionesRecientes(usuario.getCancionesRecientes())))));

		eUsuario = servPersistencia.registrarEntidad(eUsuario);
		usuario.setCodigo(eUsuario.getId());

	}

	@Override
	public void borrarUsuario(Usuario usuario) {
		Entidad eUsuario = servPersistencia.recuperarEntidad(usuario.getCodigo());

		servPersistencia.borrarEntidad(eUsuario);
	}

	@Override
	public void modificarUsuario(Usuario usuario) {
		Entidad eUsuario = servPersistencia.recuperarEntidad(usuario.getCodigo());

		for (Propiedad propiedad : eUsuario.getPropiedades()) {
			if (propiedad.getNombre().equals(CODIGO))
				propiedad.setValor(String.valueOf(usuario.getCodigo()));
			else if (propiedad.getNombre().equals(EMAIL))
				propiedad.setValor(usuario.getEmail());
			else if (propiedad.getNombre().equals(FECHA_NAC))
				propiedad.setValor(dateFormat.format(usuario.getFechaNac()));
			else if (propiedad.getNombre().equals(USER))
				propiedad.setValor(usuario.getUser());
			else if (propiedad.getNombre().equals(PASSWORD))
				propiedad.setValor(usuario.getPassword());
			else if (propiedad.getNombre().equals(PREMIUM))
				propiedad.setValor(String.valueOf(usuario.isPremium()));
			else if (propiedad.getNombre().equals(CANCIONES_RECIENTES))
				propiedad.setValor(obtenerStringCancionesRecientes(usuario.getCancionesRecientes()));

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
		boolean premium = Boolean.getBoolean(servPersistencia.recuperarPropiedadEntidad(eUsuario, PREMIUM));

		Usuario usuario = new Usuario(email, fechaNac, user, password, premium);

		Queue<Cancion> cancionesRecientes = obtenerCancionesDesdeCodigos(
				servPersistencia.recuperarPropiedadEntidad(eUsuario, CANCIONES_RECIENTES));

		for (Cancion cancion : cancionesRecientes)
			usuario.addCancionRecientes(cancion);

		return usuario;
	}

	@Override
	public List<Usuario> recuperarUsuarios() {
		List<Entidad> eUsuarios = servPersistencia.recuperarEntidades(USUARIO);
		List<Usuario> usuarios = new ArrayList<Usuario>();

		for (Entidad eUsuario : eUsuarios)
			usuarios.add(recuperarUsuario(eUsuario.getId()));

		return usuarios;
	}

	private String obtenerStringCancionesRecientes(Queue<Cancion> cancionesRecientes) {
		String lineas = "";
		for (Cancion cancion : cancionesRecientes)
			lineas += cancion.getCodigo() + " ";
		return lineas.trim();
	}

	public Queue<Cancion> obtenerCancionesDesdeCodigos(String codigos) {
		Queue<Cancion> cancionesRecientes = new ArrayBlockingQueue<Cancion>(Usuario.MAX);
		StringTokenizer strTok = new StringTokenizer(codigos, " ");
		AdaptadorCancionTDS adaptadorCancion = AdaptadorCancionTDS.getUnicaInstancia();

		while (strTok.hasMoreElements())
			cancionesRecientes.add(adaptadorCancion.recuperarCancion(Integer.valueOf((String) strTok.nextElement())));
		return cancionesRecientes;
	}

}
