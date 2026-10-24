package mx.edu.utez.PrestamosyLibros.exception.customExceptions;

public class BadRequestException extends RuntimeException {

    public BadRequestException(String mensaje) {
        super(mensaje);
    }
}
