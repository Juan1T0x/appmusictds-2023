package umu.tds.player;

import java.io.File;
import java.io.FileNotFoundException;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import umu.tds.model.Cancion;

public class Player {
	// canciones almacenadas en src/main/resources
	private Cancion cancionActual = null;
	private MediaPlayer mediaPlayer;

	public Player() {
		// existen otras formas de lanzar JavaFX desde Swing
		try {
			com.sun.javafx.application.PlatformImpl.startup(() -> {
			});
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("Exception: " + ex.getMessage());
		}
	}

	public void play(String boton, Cancion cancion) {
		switch (boton) {
		case "play":
			try {
				setCancionActual(cancion);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			mediaPlayer.play();

			break;
		case "stop":
			mediaPlayer.stop();
			break;
		case "pause":
			mediaPlayer.pause();
			break;
		}
	}

	private void setCancionActual(Cancion cancion) throws FileNotFoundException {
		if (cancionActual != cancion) {
			cancionActual = cancion;
			String rutaCancion = cancion.getUrl();
			File archivoCancion = new File(rutaCancion);
			if (!archivoCancion.exists()) {
				throw new FileNotFoundException("Fichero canción no encontrado: " + rutaCancion);
			}

			String rutaArchivo = archivoCancion.toURI().toString();
			System.out.println(rutaArchivo);

			Media hit = new Media(rutaArchivo);
			mediaPlayer = new MediaPlayer(hit);
		}
	}

	public void setMediaPlayer(MediaPlayer mediaPlayer) {
		this.mediaPlayer = mediaPlayer;
	}

	public MediaPlayer getMediaPlayer() {
		return mediaPlayer;
	}

	public void setOnEndOfMedia(Runnable onEndOfMedia) {
		if (mediaPlayer != null) {
			mediaPlayer.setOnEndOfMedia(onEndOfMedia);
		}
	}

	public Duration getCurrentTime() {
		if (mediaPlayer != null) {
			return mediaPlayer.getCurrentTime();
		}
		return Duration.ZERO;
	}

	public Duration getTotalDuration() {
		if (mediaPlayer != null) {
			return mediaPlayer.getTotalDuration();
		}
		return Duration.UNKNOWN;
	}

	public void seek(Duration seekTime) {
		if (mediaPlayer != null) {
			mediaPlayer.seek(seekTime);
		}
	}
}