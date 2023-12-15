package umu.tds.services;

public class Reproductor {

	private static Reproductor instance = new Reproductor();
	
	private Reproductor() {
		
	}
	
	public static Reproductor getInstance() {
		return instance;
	}
}


/*
 * import java.io.File;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
// activar reproductor
try {
com.sun.javafx.application.PlatformImpl.startup(()->{});
} catch(Exception ex) {
ex.printStackTrace();
System.out.println("Exception: " + ex.getMessage());
}
// reproducir una canción
String rutaCancion = cancion.getRutaFichero();
URL resourceURL = getClass().getResource("/canciones"+"/" + rutaCancion);
if (resourceURL == null)
throw new FileNotFoundException("Fichero canción no encontrado: " + rutaCancion);
Media hit = new Media(resourceURL.toExternalForm());
mediaPlayer = new MediaPlayer(hit);
 */