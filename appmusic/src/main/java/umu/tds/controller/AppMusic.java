package umu.tds.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import umu.tds.model.Cancion;
import umu.tds.model.EstiloMusical;
import umu.tds.model.Interprete;
import umu.tds.model.RepositorioCanciones;
import umu.tds.model.RepositorioInterpretes;
import umu.tds.model.RepositorioUsuarios;
import umu.tds.model.Usuario;
import umu.tds.persistencia.AdaptadorCancionDAO;
import umu.tds.persistencia.AdaptadorInterpreteDAO;
import umu.tds.persistencia.AdaptadorUsuarioDAO;
import umu.tds.persistencia.DAOException;
import umu.tds.persistencia.FactoriaDAO;

public class AppMusic {

	private static AppMusic unicaInstancia = null;

	private AdaptadorCancionDAO adaptadorCancion;
	private AdaptadorInterpreteDAO adaptadorInterprete;
	private AdaptadorUsuarioDAO adaptadorUsuario;

	private RepositorioCanciones repoCanciones;
	private RepositorioInterpretes repoInterpretes;
	private RepositorioUsuarios repoUsuarios;

	private AppMusic() {
		inicializarAdaptadores();
		inicializarRepositorios();
	}

	public static AppMusic getUnicaInstancia() {
		if (unicaInstancia == null)
			unicaInstancia = new AppMusic();
		return unicaInstancia;
	}

	public Cancion registrarCancion(String mp3, EstiloMusical estilo) {
		String titulo = getTituloMp3(mp3);
		List<Interprete> interpretes = getInterpretes(getInterpretesMp3(mp3));
		Cancion cancion = new Cancion(titulo, interpretes, estilo);

		adaptadorCancion.registrarCancion(cancion);
		repoCanciones.addCancion(cancion);
		return cancion;
	}

	public Interprete registrarInterprete(String nombre) {
		Interprete interprete = new Interprete(nombre);
		adaptadorInterprete.registrarInterprete(interprete);
		repoInterpretes.addInterprete(interprete);
		return interprete;
	}

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

		List<String> lista = new ArrayList<String>();

		while (matcher.find())
			lista.add(matcher.group().trim());

		return lista;
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

	public Optional<Interprete> getInterprete(String nombre) {
		return getInterpretes().stream().filter(i -> nombre.equals(i.getNombre())).findFirst();
	}

	public List<Interprete> getInterpretes() {
		return repoInterpretes.getInterpretes();
	}

	public void registrarUsuario(String email, Date fechaNac, String user, String password, boolean premium) {
		Usuario usuario = new Usuario(email, fechaNac, user, password, premium);
		adaptadorUsuario.registrarUsuario(usuario);
		repoUsuarios.addUsuario(usuario);
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
}
