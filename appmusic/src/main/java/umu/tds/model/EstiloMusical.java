package umu.tds.model;

public class EstiloMusical {

	private String nombre;

	public EstiloMusical(String nombre) {
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
		return "EstiloMusical [nombre=" + nombre + "]";
	}

}
