package mx.edu.utez.PrestamosyLibros.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.PrestamosyLibros.model.Lector;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LectorResponse {

    private Long id;
    private String nombre;
    private Boolean activo;
    private Boolean membresiaPremium;

    public static LectorResponse from(Lector lector) {
        return new LectorResponse(lector.getId(), lector.getNombre(),
                lector.getActivo(), lector.getMembresiaPremium());
    }
}
