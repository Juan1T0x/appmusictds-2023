package umu.tds.persistencia;

import java.util.List;

import umu.tds.model.Usuario;

public interface AdaptadorUsuarioDAO {
	
	public void registrarUsuario(Usuario usuario);
	public void borrarUsuario(Usuario usuario);
	public void modificarUsuario(Usuario usuario);
	public Usuario recuperarUsuario(int codigo);
	public List<Usuario> recuperarUsuarios();

}
