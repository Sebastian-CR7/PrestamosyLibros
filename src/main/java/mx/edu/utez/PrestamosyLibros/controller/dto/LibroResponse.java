package mx.edu.utez.PrestamosyLibros.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.PrestamosyLibros.model.Libro;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LibroResponse {

    private Long id;
    private String titulo;
    private Integer ejemplaresDisponibles;

    public static LibroResponse from(Libro libro) {
        return new LibroResponse(libro.getId(), libro.getTitulo(), libro.getEjemplaresDisponibles());
    }
}
