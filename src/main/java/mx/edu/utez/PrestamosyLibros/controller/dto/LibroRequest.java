package mx.edu.utez.PrestamosyLibros.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LibroRequest {

    @NotBlank(message = "El titulo es obligatorio")
    private String titulo;

    @NotNull(message = "Los ejemplares disponibles son obligatorios")
    @PositiveOrZero(message = "Los ejemplares no pueden ser negativos")
    private Integer ejemplaresDisponibles;
}
