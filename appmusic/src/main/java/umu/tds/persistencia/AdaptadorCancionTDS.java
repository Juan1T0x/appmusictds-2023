package umu.tds.persistencia;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

import beans.Entidad;
import beans.Propiedad;
import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;
import umu.tds.model.EstiloMusical;
import umu.tds.model.cancion.Cancion;
import umu.tds.model.interprete.Interprete;

public class AdaptadorCancionTDS implements AdaptadorCancionDAO {

	private static final String ESTILO = "estilo";
	private static final String INTERPRETES = "interpretes";
	private static final String NOMBRE = "nombre";
	private static final String CANCION = "cancion";

	private static ServicioPersistencia servPersistencia;
	private static AdaptadorCancionTDS unicaInstancia = null;

	public static AdaptadorCancionTDS getUnicaInstancia() {
		if (unicaInstancia == null)
			unicaInstancia = new AdaptadorCancionTDS();
		return unicaInstancia;
	}

	private AdaptadorCancionTDS() {
		servPersistencia = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
	}

	@Override
	public void registrarCancion(Cancion cancion) {
		Entidad eCancion = null;
		try {
			eCancion = servPersistencia.recuperarEntidad(cancion.getCodigo());
		} catch (NullPointerException e) {
			// Manejo de excepción
		}

		if (eCancion != null)
			return;

		eCancion = new Entidad();
		eCancion.setNombre(CANCION);
		eCancion.setPropiedades(new ArrayList<Propiedad>(Arrays.asList(new Propiedad(NOMBRE, cancion.getTitulo()),
				new Propiedad(INTERPRETES, obtenerNombreInterpretes(cancion.getInterpretes())),
				new Propiedad(ESTILO, cancion.getEstilo().getNombre()))));

		eCancion = servPersistencia.registrarEntidad(eCancion);
		cancion.setCodigo(eCancion.getId());
	}

	@Override
	public void borrarCancion(Cancion cancion) {
		Entidad eCancion = servPersistencia.recuperarEntidad(cancion.getCodigo());
		servPersistencia.borrarEntidad(eCancion);
	}

	@Override
	public void modificarCancion(Cancion cancion) {
		Entidad eCancion = servPersistencia.recuperarEntidad(cancion.getCodigo());

		for (Propiedad propiedad : eCancion.getPropiedades()) {
			switch (propiedad.getNombre()) {
			case "codigo":
				propiedad.setValor(String.valueOf(cancion.getCodigo()));
				break;
			case NOMBRE:
				propiedad.setValor(cancion.getTitulo());
				break;
			case INTERPRETES:
				propiedad.setValor(obtenerNombreInterpretes(cancion.getInterpretes()));
				break;
			case ESTILO:
				propiedad.setValor(cancion.getEstilo().getNombre());
				break;
			}
			servPersistencia.modificarPropiedad(propiedad);
		}
	}

	@Override
	public Cancion recuperarCancion(int codigo) {
		Entidad eCancion = servPersistencia.recuperarEntidad(codigo);

		String nombre = servPersistencia.recuperarPropiedadEntidad(eCancion, NOMBRE);
		EstiloMusical estilo = new EstiloMusical(servPersistencia.recuperarPropiedadEntidad(eCancion, ESTILO));
		List<Interprete> interpretes = obtenerInterpretesDesdeCodigos(
				servPersistencia.recuperarPropiedadEntidad(eCancion, INTERPRETES));

		Cancion cancion = new Cancion(nombre, interpretes, estilo);
		cancion.setCodigo(eCancion.getId());

		return cancion;
	}

	@Override
	public List<Cancion> recuperarCanciones() {
		List<Entidad> eCanciones = servPersistencia.recuperarEntidades(CANCION);
		List<Cancion> canciones = new ArrayList<>();

		for (Entidad eCancion : eCanciones) {
			canciones.add(recuperarCancion(eCancion.getId()));
		}
		return canciones;
	}

	private String obtenerNombreInterpretes(List<Interprete> interpretes) {
		StringBuilder lineas = new StringBuilder();
		for (Interprete i : interpretes) {
			lineas.append(i.getNombre()).append(" ");
		}
		return lineas.toString().trim();
	}

	private List<Interprete> obtenerInterpretesDesdeCodigos(String codigos) {
		List<Interprete> interpretes = new ArrayList<>();
		StringTokenizer strTok = new StringTokenizer(codigos, " ");
		AdaptadorInterpreteTDS adaptadorInterprete = AdaptadorInterpreteTDS.getUnicaInstancia();

		while (strTok.hasMoreElements()) {
			interpretes.add(adaptadorInterprete.recuperarInterprete(Integer.parseInt(strTok.nextToken())));
		}
		return interpretes;
	}
}
