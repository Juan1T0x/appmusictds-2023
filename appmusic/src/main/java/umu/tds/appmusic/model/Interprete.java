package umu.tds.appmusic.model;

public class Interprete {

	private String nombre;

	public Interprete(String nombre) {
		super();
		this.nombre = nombre;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Override
	public String toString() {
		return "Interprete [nombre=" + nombre + "]";
	}

}
