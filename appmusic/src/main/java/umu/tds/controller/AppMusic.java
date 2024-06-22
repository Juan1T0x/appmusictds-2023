package umu.tds.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.kohsuke.github.GitHub;
import org.kohsuke.github.GitHubBuilder;

import umu.tds.model.EstiloMusical;
import umu.tds.model.cancion.Cancion;
import umu.tds.model.cancion.RepositorioCanciones;
import umu.tds.model.interprete.Interprete;
import umu.tds.model.interprete.RepositorioInterpretes;
import umu.tds.model.usuario.RepositorioUsuarios;
import umu.tds.model.usuario.Usuario;
import umu.tds.persistencia.AdaptadorCancionDAO;
import umu.tds.persistencia.AdaptadorInterpreteDAO;
import umu.tds.persistencia.AdaptadorUsuarioDAO;
import umu.tds.persistencia.DAOException;
import umu.tds.persistencia.FactoriaDAO;
import umu.tds.validation.BirthdateValidator;
import umu.tds.validation.EmailValidator;
import umu.tds.validation.Validator;

public class AppMusic {

	private static AppMusic unicaInstancia = null;

	private AdaptadorCancionDAO adaptadorCancion;
	private AdaptadorInterpreteDAO adaptadorInterprete;
	private AdaptadorUsuarioDAO adaptadorUsuario;

	private RepositorioCanciones repoCanciones;
	private RepositorioInterpretes repoInterpretes;
	private RepositorioUsuarios repoUsuarios;

	private Validator<String> emailValidator;
	private Validator<Date> birthdateValidator;

	private AppMusic() {
		inicializarAdaptadores();
		inicializarRepositorios();
		inicializarValidadores();
	}

	public static AppMusic getUnicaInstancia() {
		if (unicaInstancia == null)
			unicaInstancia = new AppMusic();
		return unicaInstancia;
	}

	// Métodos para gestionar canciones

	public Cancion registrarCancion(String mp3, EstiloMusical estilo) {
		String titulo = getTituloMp3(mp3);
		List<Interprete> interpretes = getInterpretes(getInterpretesMp3(mp3));
		Cancion cancion = new Cancion(titulo, interpretes, estilo);

		adaptadorCancion.registrarCancion(cancion);
		repoCanciones.addCancion(cancion);
		return cancion;
	}

	public void modificarCancion(Cancion cancion) {
		adaptadorCancion.modificarCancion(cancion);
		repoCanciones.addCancion(cancion); // Update the repository as well
	}

	public void borrarCancion(Cancion cancion) {
		adaptadorCancion.borrarCancion(cancion);
		repoCanciones.getCanciones().removeIf(c -> c.getCodigo() == cancion.getCodigo()); // Remove from repository
	}

	public List<Cancion> recuperarCanciones() {
		return repoCanciones.getCanciones();
	}

	// Métodos para gestionar intérpretes

	public Interprete registrarInterprete(String nombre) {
		Interprete interprete = new Interprete(nombre);
		adaptadorInterprete.registrarInterprete(interprete);
		repoInterpretes.addInterprete(interprete);
		return interprete;
	}

	public void modificarInterprete(Interprete interprete) {
		adaptadorInterprete.modificarInterprete(interprete);
		repoInterpretes.addInterprete(interprete); // Update the repository as well
	}

	public void borrarInterprete(Interprete interprete) {
		adaptadorInterprete.borrarInterprete(interprete);
		repoInterpretes.getInterpretes().removeIf(i -> i.getCodigo() == interprete.getCodigo()); // Remove from
																									// repository
	}

	public List<Interprete> recuperarInterpretes() {
		return repoInterpretes.getInterpretes();
	}

	public List<Interprete> getInterpretes() {
		return recuperarInterpretes();
	}

	public Optional<Interprete> getInterprete(String nombre) {
		return getInterpretes().stream().filter(i -> nombre.equals(i.getNombre())).findFirst();
	}

	public List<Interprete> getInterpretes(List<String> nombres) {
		List<Interprete> lista = new ArrayList<>();
		for (String s : nombres) {
			Optional<Interprete> oInterprete = getInterprete(s);
			if (!oInterprete.isPresent()) {
				Interprete interprete = registrarInterprete(s);
				lista.add(interprete);
			} else {
				lista.add(oInterprete.get());
			}
		}
		return lista;
	}

	// Métodos para gestionar usuarios

	public void registrarUsuario(String email, Date fechaNac, String user, String password, boolean premium) {
		if (!emailValidator.validate(email)) {
			throw new IllegalArgumentException("Invalid email format");
		}
		if (!birthdateValidator.validate(fechaNac)) {
			throw new IllegalArgumentException("Invalid birthdate");
		}

		Usuario usuario = new Usuario(email, fechaNac, user, password, premium);
		adaptadorUsuario.registrarUsuario(usuario);
		repoUsuarios.addUsuario(usuario);
	}

	public void modificarUsuario(Usuario usuario) {
		adaptadorUsuario.modificarUsuario(usuario);
		repoUsuarios.addUsuario(usuario); // Update the repository as well
	}

	public void borrarUsuario(Usuario usuario) {
		adaptadorUsuario.borrarUsuario(usuario);
		repoUsuarios.getUsuarios().removeIf(u -> u.getCodigo() == usuario.getCodigo()); // Remove from repository
	}

	public List<Usuario> recuperarUsuarios() {
		return repoUsuarios.getUsuarios();
	}

	public Optional<Usuario> getUsuario(String email) {
		return getUsuarios().stream().filter(u -> email.equals(u.getEmail())).findFirst();
	}

	public List<Usuario> getUsuarios() {
		return repoUsuarios.getUsuarios();
	}

	// Métodos de utilidad

	public String getTituloMp3(String mp3) {
		String patron = "(?<=-)[\\w\\s]+(?=\\.mp3)";

		Pattern regex = Pattern.compile(patron);
		Matcher matcher = regex.matcher(mp3);

		String titulo = "";
		if (matcher.find())
			titulo = matcher.group(0).trim();

		return titulo;
	}

	public List<String> getInterpretesMp3(String mp3) {
		String patron = "[\\w\\s]+(?=&)|[\\w\\s]+(?=-)";
		Pattern reg = Pattern.compile(patron);
		Matcher matcher = reg.matcher(mp3);

		List<String> lista = new ArrayList<>();

		while (matcher.find())
			lista.add(matcher.group().trim());

		return lista;
	}

	public boolean loginConGitHub(String usuario, String password) {
		try {
			GitHub github = new GitHubBuilder().withPassword(usuario, password).build();
			return github.isCredentialValid();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	private void inicializarRepositorios() {
		repoCanciones = RepositorioCanciones.getUnicaInstancia();
		repoInterpretes = RepositorioInterpretes.getUnicaInstancia();
		repoUsuarios = RepositorioUsuarios.getUnicaInstancia();
	}

	private void inicializarAdaptadores() {
		FactoriaDAO factoria = null;
		try {
			factoria = FactoriaDAO.getInstancia(FactoriaDAO.DAO_TDS);
		} catch (DAOException e) {
			e.printStackTrace();
		}
		adaptadorUsuario = factoria.getUsuarioDAO();
		adaptadorCancion = factoria.getCancionDAO();
		adaptadorInterprete = factoria.getInterpreteDAO();
	}

	private void inicializarValidadores() {
		emailValidator = new EmailValidator();
		birthdateValidator = new BirthdateValidator();
	}
}
