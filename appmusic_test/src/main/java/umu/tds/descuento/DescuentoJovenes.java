package umu.tds.descuento;

public class DescuentoJovenes extends Descuento {
	DescuentoJovenes(double precio) {
		super(precio);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getDescripcion() {
		return "Descuento especial para jóvenes menores de 25 años";
	}

	@Override
	public double aplicarDescuento() {
		return precio * 0.85;
	}
}
