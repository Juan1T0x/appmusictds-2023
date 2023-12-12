package umu.tds.model;

public class Interprete {

	private int codigo;
	private String nombre;
	
	public Interprete(String nombre) {
		this.codigo = 0;
		this.nombre = nombre;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Override
	public String toString() {
		return "Interprete [codigo=" + codigo + ", nombre=" + nombre + "]";
	}

}
