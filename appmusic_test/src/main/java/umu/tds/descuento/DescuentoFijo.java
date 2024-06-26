package umu.tds.descuento;


public class DescuentoFijo extends Descuento {
	DescuentoFijo(double precio) {
		super(precio);
	}

	@Override
	public String getDescripcion() {
		return "Aplica un descuento fijo del 20%";
	}

	@Override
	public double aplicarDescuento() {
		return precio * 0.8;
	}

}
