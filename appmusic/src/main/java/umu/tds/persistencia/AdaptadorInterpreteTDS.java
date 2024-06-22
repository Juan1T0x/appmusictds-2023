package umu.tds.persistencia;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import beans.Entidad;
import beans.Propiedad;
import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;
import umu.tds.model.Interprete;

public class AdaptadorInterpreteTDS implements AdaptadorInterpreteDAO {

	private static final String NOMBRE = "nombre";

	private static final String INTERPRETE = "interprete";

	private static ServicioPersistencia servPersistencia;

	private static AdaptadorInterpreteTDS unicaInstancia = null;

	public static AdaptadorInterpreteTDS getUnicaInstancia() {
		if (unicaInstancia == null)
			return new AdaptadorInterpreteTDS();
		return unicaInstancia;
	}

	private AdaptadorInterpreteTDS() {
		servPersistencia = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
	}

	@Override
	public void registrarInterprete(Interprete interprete) {
		Entidad eInterprete = null;

		try {
			eInterprete = servPersistencia.recuperarEntidad(interprete.getCodigo());
		} catch (NullPointerException e) {
		}
		if (eInterprete != null)
			return;

		eInterprete = new Entidad();
		eInterprete.setNombre(INTERPRETE);
		eInterprete
				.setPropiedades(new ArrayList<Propiedad>(Arrays.asList(new Propiedad(NOMBRE, interprete.getNombre()))));

		eInterprete = servPersistencia.registrarEntidad(eInterprete);

		interprete.setCodigo(eInterprete.getId());

	}

	@Override
	public void borrarInterprete(Interprete interprete) {
		Entidad eInterprete = servPersistencia.recuperarEntidad(interprete.getCodigo());
		servPersistencia.borrarEntidad(eInterprete);

	}

	@Override
	public void modificarInterprete(Interprete interprete) {
		Entidad eInterprete = servPersistencia.recuperarEntidad(interprete.getCodigo());

		for (Propiedad prop : eInterprete.getPropiedades()) {
			if (prop.getNombre().equals("codigo"))
				prop.setValor(String.valueOf(interprete.getCodigo()));
			else if (prop.getNombre().equals(NOMBRE))
				prop.setValor(interprete.getNombre());
			servPersistencia.modificarPropiedad(prop);
		}

	}

	@Override
	public Interprete recuperarInterprete(int codigo) {
		Entidad eInterprete;
		String nombre;

		eInterprete = servPersistencia.recuperarEntidad(codigo);
		nombre = servPersistencia.recuperarPropiedadEntidad(eInterprete, NOMBRE);

		Interprete interprete = new Interprete(nombre);
		interprete.setCodigo(codigo);
		return interprete;

	}

	@Override
	public List<Interprete> recuperarInterpretees() {
		List<Entidad> eInterpretes = servPersistencia.recuperarEntidades(INTERPRETE);
		List<Interprete> interpretes = new ArrayList<Interprete>();

		for (Entidad eInterprete : eInterpretes)
			interpretes.add(recuperarInterprete(eInterprete.getId()));
		return interpretes;
	}

}
