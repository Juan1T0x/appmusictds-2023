package umu.tds.validation;

// Patro strategy

public interface Validador {
    void validar(String valor) throws ValidationException;
}
