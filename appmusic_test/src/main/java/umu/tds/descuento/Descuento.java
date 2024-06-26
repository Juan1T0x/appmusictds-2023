package umu.tds.descuento;

public abstract class Descuento {

	protected double precio;

	protected Descuento(double precio) {
		this.precio = precio;
	}

	public abstract String getDescripcion();

	public abstract double aplicarDescuento();
}