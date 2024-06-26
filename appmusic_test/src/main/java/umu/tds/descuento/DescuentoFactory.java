package umu.tds.descuento;

public class DescuentoFactory {

	public static final double PRECIO = 11.99;

	public static Descuento getDescuento(String tipo) {
		switch (tipo) {
		case "Descuento Fijo":
			return new DescuentoFijo(PRECIO);
		case "Descuento Jóvenes":
			return new DescuentoJovenes(PRECIO);
		default:
			return new DescuentoPorDefecto(PRECIO);
		}
	}
}