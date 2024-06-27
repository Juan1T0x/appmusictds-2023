package umu.tds.descuento;

public class DescuentoPorDefecto extends Descuento {
	DescuentoPorDefecto(double precio) {
		super(precio);
	}

	@Override
	public String getDescripcion() {
		return "Sin descuento";
	}

	@Override
	public double aplicarDescuento() {
		return precio;
	}
}