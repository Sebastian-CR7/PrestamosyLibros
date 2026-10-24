package mx.edu.utez.PrestamosyLibros.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.PrestamosyLibros.model.Estado;
import mx.edu.utez.PrestamosyLibros.model.Prestamo;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PrestamoResponse {

    private Long id;
    private Long lectorId;
    private Long libroId;
    private LocalDate fechaPrestamo;
    private LocalDate fechaDevolucionMaxima;
    private Estado estado;

    public static PrestamoResponse from(Prestamo prestamo) {
        return new PrestamoResponse(prestamo.getId(), prestamo.getLector().getId(),
                prestamo.getLibro().getId(), prestamo.getFechaPrestamo(),
                prestamo.getFechaDevolucionMaxima(), prestamo.getEstado());
    }
}
