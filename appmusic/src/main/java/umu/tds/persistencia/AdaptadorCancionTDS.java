package umu.tds.persistencia;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

import beans.Entidad;
import beans.Propiedad;
import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;
import umu.tds.model.Cancion;
import umu.tds.model.EstiloMusical;
import umu.tds.model.Interprete;

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
			if (propiedad.getNombre().equals("codigo"))
				propiedad.setValor(String.valueOf(cancion.getCodigo()));
			else if (propiedad.getNombre().equals(NOMBRE))
				propiedad.setValor(cancion.getTitulo());
			else if (propiedad.getNombre().equals(INTERPRETES))
				propiedad.setValor(obtenerNombreInterpretes(cancion.getInterpretes()));
			else if (propiedad.getNombre().equals(ESTILO))
				propiedad.setValor(cancion.getEstilo().getNombre());

			servPersistencia.modificarPropiedad(propiedad);
		}
	}

	@Override
	public Cancion recuperarCancion(int codigo) {
		Entidad eCancion;
		String nombre;
		List<Interprete> interpretes;
		EstiloMusical estilo;

		eCancion = servPersistencia.recuperarEntidad(codigo);

		nombre = servPersistencia.recuperarPropiedadEntidad(eCancion, NOMBRE);
		estilo = new EstiloMusical(servPersistencia.recuperarPropiedadEntidad(eCancion, ESTILO));

		interpretes = obtenerInterpretesDesdeCodigos(servPersistencia.recuperarPropiedadEntidad(eCancion, INTERPRETES));

		Cancion cancion = new Cancion(nombre, interpretes, estilo);
		cancion.setCodigo(eCancion.getId());

		return cancion;
	}

	@Override
	public List<Cancion> recuperarCanciones() {
		List<Entidad> eCanciones = servPersistencia.recuperarEntidades(CANCION);
		List<Cancion> canciones = new ArrayList<Cancion>();

		for (Entidad eCancion : eCanciones) {
			canciones.add(recuperarCancion(eCancion.getId()));
		}
		return canciones;
	}

	private String obtenerNombreInterpretes(List<Interprete> interpretes) {
		String lineas = "";
		for (Interprete i : interpretes)
			lineas += i.getNombre() + " ";
		return lineas.trim();
	}

	public List<Interprete> obtenerInterpretesDesdeCodigos(String codigos) {
		List<Interprete> interpretes = new ArrayList<Interprete>();
		StringTokenizer strTok = new StringTokenizer(codigos, " ");
		AdaptadorInterpreteTDS adaptadorInterprete = AdaptadorInterpreteTDS.getUnicaInstancia();

		while (strTok.hasMoreElements())
			interpretes.add(adaptadorInterprete.recuperarInterprete(Integer.valueOf((String) strTok.nextElement())));
		return interpretes;
	}

}
