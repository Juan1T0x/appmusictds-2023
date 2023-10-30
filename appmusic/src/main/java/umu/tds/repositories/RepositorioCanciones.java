package umu.tds.repositories;

public class RepositorioCanciones {

	private static RepositorioCanciones instance = new RepositorioCanciones();

	private RepositorioCanciones() {

	}

	public static RepositorioCanciones getInstance() {
		return instance;
	}

}
