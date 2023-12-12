package umu.tds.model;

import java.util.List;

public class Cancion {

	private int codigo;
	private String titulo;
	private List <Interprete> interpretes;
	private EstiloMusical estilo;

	private long numReproducciones;

	public Cancion(String titulo, List<Interprete> interpretes, EstiloMusical estilo) {
		this.codigo = 0;
		this.titulo = titulo;
		this.interpretes = interpretes;
		this.estilo = estilo;

		this.numReproducciones = 0;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public List<Interprete> getInterpretes() {
		return interpretes;
	}

	public void setInterpretes(List<Interprete> interpretes) {
		this.interpretes = interpretes;
	}

	public EstiloMusical getEstilo() {
		return estilo;
	}

	public void setEstilo(EstiloMusical estilo) {
		this.estilo = estilo;
	}

	public long getNumReproducciones() {
		return numReproducciones;
	}

	public void setNumReproducciones(long numReproducciones) {
		this.numReproducciones = numReproducciones;
	}
	
	public int getCodigo() {
		return codigo;
	}
	
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	
	@Override
	public String toString() {
		return "Cancion [codigo=" + codigo + ", titulo=" + titulo + ", interpretes=" + interpretes + ", estilo="
				+ estilo + ", numReproducciones=" + numReproducciones + "]";
	}

}
