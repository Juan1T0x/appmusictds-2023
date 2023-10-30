package umu.tds.repositories;

public class RepositorioInterpretes {

	private static RepositorioInterpretes instance = new RepositorioInterpretes();

	private RepositorioInterpretes() {

	}

	public static RepositorioInterpretes getInstance() {
		return instance;
	}
}
