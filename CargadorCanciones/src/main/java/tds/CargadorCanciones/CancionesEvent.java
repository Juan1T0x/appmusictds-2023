package tds.CargadorCanciones;

import java.util.EventObject;


public class CancionesEvent extends EventObject{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8210544401037348389L;
	private String oldRuta;
	private String newRuta;
	private Canciones listaCanciones;

	public CancionesEvent(Object fuente, String oldRuta, String newRuta, Canciones listaCanciones) {
		super(fuente);
		this.oldRuta = oldRuta;
		this.newRuta = newRuta;
		this.listaCanciones = listaCanciones;
	}
	
	public String getOldFichero() {
		return oldRuta;
	}
	
	public String getNewFichero() {
		return newRuta;
	}
	
	public Canciones getListaCanciones() {
		return listaCanciones;
	}
	
}