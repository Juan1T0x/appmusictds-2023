package umu.tds.validation;

// Patro strategy

public interface ValidadorUsuario {
    void validar(String valor) throws ValidationException;
}
