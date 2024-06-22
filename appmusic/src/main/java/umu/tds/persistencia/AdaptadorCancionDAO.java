package umu.tds.persistencia;

import java.util.List;

import umu.tds.model.Cancion;

public interface AdaptadorCancionDAO {

	public void registrarCancion(Cancion cancion);

	public void borrarCancion(Cancion cancion);

	public void modificarCancion(Cancion cancion);

	public Cancion recuperarCancion(int codigo);

	public List<Cancion> recuperarCanciones();
}
