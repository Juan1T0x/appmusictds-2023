package umu.tds.appmusic.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Usuario {

	private static int max = 10;

	private String email;
	private Date fechaNac;
	private String user;
	private String password;

	private boolean premium;
	private List<Cancion> cancionesRecientes;

	public Usuario(String email, Date fechaNac, String user, String password) {
		super();
		this.email = email;
		this.fechaNac = fechaNac;
		this.user = user;
		this.password = password;

		this.premium = false;
		cancionesRecientes = new ArrayList<Cancion>(max);
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

	public static int getMax() {
		return max;
	}

	public static void setMax(int max) {
		Usuario.max = max;
	}

	public List<Cancion> getCancionesRecientes() {
		return cancionesRecientes;
	}

	public void setCancionesRecientes(List<Cancion> cancionesRecientes) {
		this.cancionesRecientes = cancionesRecientes;
	}

	@Override
	public String toString() {
		return "Usuario [email=" + email + ", fechaNac=" + fechaNac + ", user=" + user + ", password=" + password
				+ ", premium=" + premium + ", cancionesRecientes=" + cancionesRecientes + "]";
	}

}
