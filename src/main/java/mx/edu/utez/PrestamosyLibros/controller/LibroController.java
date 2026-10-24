package mx.edu.utez.PrestamosyLibros.controller;

import jakarta.validation.Valid;
import mx.edu.utez.PrestamosyLibros.controller.dto.LibroRequest;
import mx.edu.utez.PrestamosyLibros.controller.dto.LibroResponse;
import mx.edu.utez.PrestamosyLibros.service.LibroService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/libros")
public class LibroController {

    private final LibroService libroService;

    public LibroController(LibroService libroService) {
        this.libroService = libroService;
    }

    @PostMapping
    public ResponseEntity<LibroResponse> crear(@Valid @RequestBody LibroRequest payload) {
        return ResponseEntity.status(HttpStatus.CREATED).body(libroService.crearLibro(payload));
    }

    @GetMapping("/{id}")
    public ResponseEntity<LibroResponse> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(libroService.obtenerLibro(id));
    }
}
