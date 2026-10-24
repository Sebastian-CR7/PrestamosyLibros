package mx.edu.utez.PrestamosyLibros.controller;

import jakarta.validation.Valid;
import mx.edu.utez.PrestamosyLibros.controller.dto.LectorRequest;
import mx.edu.utez.PrestamosyLibros.controller.dto.LectorResponse;
import mx.edu.utez.PrestamosyLibros.controller.dto.PrestamoResponse;
import mx.edu.utez.PrestamosyLibros.service.LectorService;
import mx.edu.utez.PrestamosyLibros.service.PrestamoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lectores")
public class LectorController {

    private final LectorService lectorService;
    private final PrestamoService prestamoService;

    public LectorController(LectorService lectorService, PrestamoService prestamoService) {
        this.lectorService = lectorService;
        this.prestamoService = prestamoService;
    }

    @PostMapping
    public ResponseEntity<LectorResponse> crear(@Valid @RequestBody LectorRequest payload) {
        return ResponseEntity.status(HttpStatus.CREATED).body(lectorService.crearLector(payload));
    }

    @GetMapping("/{id}")
    public ResponseEntity<LectorResponse> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(lectorService.obtenerLector(id));
    }

    @PatchMapping("/{id}/rehabilitar")
    public ResponseEntity<LectorResponse> rehabilitar(@PathVariable Long id) {
        return ResponseEntity.ok(lectorService.rehabilitarLector(id));
    }

    @GetMapping("/{id}/prestamos-activos")
    public ResponseEntity<List<PrestamoResponse>> prestamosActivos(@PathVariable Long id) {
        return ResponseEntity.ok(prestamoService.listarPrestamosActivosLector(id));
    }
}
