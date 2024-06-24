package umu.tds.validation;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ValidadorFechaNac implements ValidadorUsuario {

	@Override
	public void validar(String fechaNacStr) throws ValidationException {
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date fechaNac = dateFormat.parse(fechaNacStr);
			if (fechaNac.after(new Date())) {
				throw new ValidationException("La fecha de nacimiento no puede ser futura.");
			}
		} catch (Exception e) {
			throw new ValidationException("La fecha de nacimiento no es válida.");
		}
	}
}