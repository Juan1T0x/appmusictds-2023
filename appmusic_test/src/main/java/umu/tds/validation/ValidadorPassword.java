package umu.tds.validation;

public class ValidadorPassword implements ValidadorUsuario {

	@Override
	public void validar(String password) throws ValidationException {
		if (password == null || password.length() < 6) {
			throw new ValidationException("La contraseña debe tener al menos 6 caracteres.");
		}
	}
}