package mx.edu.utez.PrestamosyLibros.exception.customExceptions;

public class NotFoundException extends RuntimeException {

    public NotFoundException(String mensaje) {
        super(mensaje);
    }
}
