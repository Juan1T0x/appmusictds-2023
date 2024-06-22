package umu.tds.persistencia;

import java.util.List;

import umu.tds.model.interprete.Interprete;

public interface AdaptadorInterpreteDAO {

	public void registrarInterprete(Interprete interprete);

	public void borrarInterprete(Interprete interprete);

	public void modificarInterprete(Interprete interprete);

	public Interprete recuperarInterprete(int codigo);

	public List<Interprete> recuperarInterpretes();
}
