package mx.edu.utez.PrestamosyLibros.controller;

import jakarta.validation.Valid;
import mx.edu.utez.PrestamosyLibros.controller.dto.PrestamoResponse;
import mx.edu.utez.PrestamosyLibros.controller.dto.SolicitarPrestamoRequest;
import mx.edu.utez.PrestamosyLibros.service.PrestamoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/prestamos")
public class PrestamoController {

    private final PrestamoService prestamoService;

    public PrestamoController(PrestamoService prestamoService) {
        this.prestamoService = prestamoService;
    }

    @PostMapping
    public ResponseEntity<PrestamoResponse> solicitar(@Valid @RequestBody SolicitarPrestamoRequest payload) {
        return ResponseEntity.status(HttpStatus.CREATED).body(prestamoService.solicitarPrestamo(payload));
    }

    @PatchMapping("/{id}/devolver")
    public ResponseEntity<PrestamoResponse> devolver(@PathVariable Long id) {
        return ResponseEntity.ok(prestamoService.devolverLibro(id));
    }

    @PatchMapping("/{id}/renovar")
    public ResponseEntity<PrestamoResponse> renovar(@PathVariable Long id) {
        return ResponseEntity.ok(prestamoService.renovarPrestamo(id));
    }
}
