package umu.tds.persistencia;

import java.util.List;

import umu.tds.model.cancion.Cancion;

public interface AdaptadorCancionDAO {

	public void registrarCancion(Cancion cancion);

	public void borrarCancion(Cancion cancion);

	public void modificarCancion(Cancion cancion);

	public Cancion recuperarCancion(int codigo);

	public List<Cancion> recuperarCanciones();
}
