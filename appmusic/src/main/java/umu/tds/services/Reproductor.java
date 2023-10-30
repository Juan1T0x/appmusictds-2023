package umu.tds.services;

public class Reproductor {

	private static Reproductor instance = new Reproductor();
	
	private Reproductor() {
		
	}
	
	public static Reproductor getInstance() {
		return instance;
	}
}
