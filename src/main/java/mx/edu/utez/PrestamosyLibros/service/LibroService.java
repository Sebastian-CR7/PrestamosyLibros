package mx.edu.utez.PrestamosyLibros.service;

import mx.edu.utez.PrestamosyLibros.controller.dto.LibroRequest;
import mx.edu.utez.PrestamosyLibros.controller.dto.LibroResponse;
import mx.edu.utez.PrestamosyLibros.exception.customExceptions.NotFoundException;
import mx.edu.utez.PrestamosyLibros.model.Libro;
import mx.edu.utez.PrestamosyLibros.repository.libro.LibroRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LibroService {

    //inyeccion de dependencias
    private final LibroRepository libroRepository;

    public LibroService(LibroRepository libroRepository) {
        this.libroRepository = libroRepository;
    }

    //servicios
    @Transactional
    public LibroResponse crearLibro(LibroRequest payload) {
        Libro libro = new Libro();
        libro.setTitulo(payload.getTitulo());
        libro.setEjemplaresDisponibles(payload.getEjemplaresDisponibles());

        return LibroResponse.from(libroRepository.save(libro));
    }

    @Transactional(readOnly = true)
    public LibroResponse obtenerLibro(Long id) {
        return LibroResponse.from(buscarLibro(id));
    }

    public Libro buscarLibro(Long id) {
        return libroRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("El libro con id " + id + " no existe"));
    }
}
