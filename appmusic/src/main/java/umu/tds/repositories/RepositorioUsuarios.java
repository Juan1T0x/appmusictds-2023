package umu.tds.repositories;

public class RepositorioUsuarios {

	private static RepositorioUsuarios instance = new RepositorioUsuarios();
	
	private RepositorioUsuarios() {
		
	}
	
	public static RepositorioUsuarios getInstance() {
		return instance;
	}
}
