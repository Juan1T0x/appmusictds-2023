package tds.CargadorCanciones;

import java.io.Serializable;
import java.net.URISyntaxException;
import java.util.Vector;

public class CargadorCanciones implements Serializable, IBuscadorCanciones {

	/**
	 * 
	 */
	private static final long serialVersionUID = -52358321257082546L;
	private String ruta;
	private Canciones archivoCanciones; // Propiedad ligada
	private Vector<ICancionesListener> cancionesListeners;

	public CargadorCanciones() {
		this.ruta = "";
		this.cancionesListeners = new Vector<ICancionesListener>();
	}

	public void setRuta(String newRuta) {
		setArchivoCanciones(newRuta);
	}

	@Override
	public void setArchivoCanciones(String newRuta) {
		String oldRuta = this.ruta;
		this.ruta = newRuta;
		if (oldRuta.equals(newRuta))
			return;
		try {
			this.archivoCanciones = MapperCancionesXMLtoJava.cargarCanciones(this.ruta);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		CancionesEvent event = new CancionesEvent(this, oldRuta, newRuta, archivoCanciones);
		notificarCambioCanciones(event);
	}

	public String getRuta() {
		return this.ruta;
	}

	public Canciones getArchivoCanciones() {
		return this.archivoCanciones;
	}

	private void notificarCambioCanciones(CancionesEvent event) {
		Vector<ICancionesListener> lista;
		synchronized (this) {
			lista = (Vector<ICancionesListener>) cancionesListeners.clone();
		}
		for (int i = 0; i < lista.size(); i++) {
			ICancionesListener listener = (ICancionesListener) lista.elementAt(i);
			listener.enteradoCambioRuta(event);
		}
	}
	
	public synchronized void addCancionesListener(ICancionesListener listener) {
		cancionesListeners.addElement(listener);
	}

	public synchronized void removeCancionesListener(ICancionesListener listener) {
		cancionesListeners.removeElement(listener);
	}
}
