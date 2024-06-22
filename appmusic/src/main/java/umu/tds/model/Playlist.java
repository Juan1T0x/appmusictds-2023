package umu.tds.model;

import java.util.List;

import umu.tds.model.cancion.Cancion;

public class Playlist {

	private String nombre;
	private List<Cancion> canciones;

	public Playlist(String nombre, List<Cancion> canciones) {
		super();
		this.nombre = nombre;
		this.canciones = canciones;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<Cancion> getCanciones() {
		return canciones;
	}

	public void setCanciones(List<Cancion> canciones) {
		this.canciones = canciones;
	}

	@Override
	public String toString() {
		return "Playlist [nombre=" + nombre + ", canciones=" + canciones + "]";
	}

}
