package umu.tds.validation;

import java.util.regex.Pattern;

public class ValidadorEmail implements ValidadorUsuario {

	private static final Pattern EMAIL_PATTERN = Pattern.compile("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");

	@Override
	public void validar(String email) throws ValidationException {
		if (email == null || !EMAIL_PATTERN.matcher(email).matches()) {
			throw new ValidationException("El email no es válido.");
		}
	}
}