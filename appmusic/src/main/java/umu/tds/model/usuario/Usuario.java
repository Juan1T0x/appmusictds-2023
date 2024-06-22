package umu.tds.model.usuario;

import java.util.Date;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

import umu.tds.model.cancion.Cancion;

public class Usuario {

	public static final int MAX = 10;

	private int codigo;
	private String email;
	private Date fechaNac;
	private String user;
	private String password;

	private boolean premium;
	private Queue<Cancion> cancionesRecientes;

	// private List <Playlist> playlists;

	public Usuario(String email, Date fechaNac, String user, String password, boolean premium) {
		this.codigo = 0;
		this.email = email;
		this.fechaNac = fechaNac;
		this.user = user;
		this.password = password;

		this.premium = false;
		this.cancionesRecientes = new ArrayBlockingQueue<Cancion>(MAX);
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getFechaNac() {
		return fechaNac;
	}

	public void setFechaNac(Date fechaNac) {
		this.fechaNac = fechaNac;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isPremium() {
		return premium;
	}

	public void setPremium(boolean premium) {
		this.premium = premium;
	}

	public Queue<Cancion> getCancionesRecientes() {
		return cancionesRecientes;
	}

	public void setCancionesRecientes(Queue<Cancion> cancionesRecientes) {
		this.cancionesRecientes = cancionesRecientes;
	}

	public boolean addCancionRecientes(Cancion cancion) {
		return this.cancionesRecientes.add(cancion);
	}

	@Override
	public String toString() {
		return "Usuario [codigo=" + codigo + ", email=" + email + ", fechaNac=" + fechaNac + ", user=" + user
				+ ", password=" + password + ", premium=" + premium + ", cancionesRecientes=" + cancionesRecientes
				+ "]";
	}

}
