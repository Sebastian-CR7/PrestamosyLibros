package mx.edu.utez.PrestamosyLibros.controller.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SolicitarPrestamoRequest {

    @NotNull(message = "El id del lector es obligatorio")
    private Long lectorId;

    @NotNull(message = "El id del libro es obligatorio")
    private Long libroId;
}
