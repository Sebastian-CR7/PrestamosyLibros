package mx.edu.utez.PrestamosyLibros.service;

import mx.edu.utez.PrestamosyLibros.controller.dto.PrestamoResponse;
import mx.edu.utez.PrestamosyLibros.controller.dto.SolicitarPrestamoRequest;
import mx.edu.utez.PrestamosyLibros.exception.customExceptions.BadRequestException;
import mx.edu.utez.PrestamosyLibros.exception.customExceptions.NotFoundException;
import mx.edu.utez.PrestamosyLibros.model.Estado;
import mx.edu.utez.PrestamosyLibros.model.Lector;
import mx.edu.utez.PrestamosyLibros.model.Libro;
import mx.edu.utez.PrestamosyLibros.model.Prestamo;
import mx.edu.utez.PrestamosyLibros.repository.lector.LectorRepository;
import mx.edu.utez.PrestamosyLibros.repository.libro.LibroRepository;
import mx.edu.utez.PrestamosyLibros.repository.prestamo.PrestamoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class PrestamoService {

    //inyeccion de dependencias
    private final PrestamoRepository prestamoRepository;
    private final LectorRepository lectorRepository;
    private final LibroRepository libroRepository;
    private final LectorService lectorService;
    private final LibroService libroService;

    public PrestamoService(PrestamoRepository prestamoRepository, LectorRepository lectorRepository, LibroRepository libroRepository, LectorService lectorService, LibroService libroService) {
        this.prestamoRepository = prestamoRepository;
        this.lectorRepository = lectorRepository;
        this.libroRepository = libroRepository;
        this.lectorService = lectorService;
        this.libroService = libroService;
    }

    //servicios
    @Transactional
    public PrestamoResponse solicitarPrestamo(SolicitarPrestamoRequest payload) {
        Lector lector = lectorService.buscarLector(payload.getLectorId());
        if (!lector.getActivo()) {
            throw new BadRequestException("El lector con id " + lector.getId() + " no esta activo");
        }

        Libro libro = libroService.buscarLibro(payload.getLibroId());
        if (libro.getEjemplaresDisponibles() < 1) {
            throw new BadRequestException("No hay ejemplares disponibles del libro con id " + libro.getId());
        }

        int limite = lector.getMembresiaPremium() ? 5 : 3;
        long activos = prestamoRepository.countByLectorIdAndEstadoIn(
                lector.getId(), List.of(Estado.PRESTADO, Estado.VENCIDO));
        if (activos >= limite) {
            throw new BadRequestException("El lector ya alcanzo el limite de " + limite + " prestamos activos");
        }

        libro.setEjemplaresDisponibles(libro.getEjemplaresDisponibles() - 1);
        libroRepository.save(libro);

        Prestamo prestamo = new Prestamo();
        prestamo.setLector(lector);
        prestamo.setLibro(libro);
        prestamo.setFechaPrestamo(LocalDate.now());
        prestamo.setFechaDevolucionMaxima(LocalDate.now().plusDays(14));
        prestamo.setEstado(Estado.PRESTADO);

        return PrestamoResponse.from(prestamoRepository.save(prestamo));
    }

    @Transactional
    public PrestamoResponse devolverLibro(Long prestamoId) {
        Prestamo prestamo = buscarPrestamo(prestamoId);

        if (prestamo.getEstado() == Estado.DEVUELTO) {
            throw new BadRequestException("El prestamo con id " + prestamoId + " ya fue devuelto");
        }

        boolean tarde = LocalDate.now().isAfter(prestamo.getFechaDevolucionMaxima());

        prestamo.setEstado(Estado.DEVUELTO);

        Libro libro = prestamo.getLibro();
        libro.setEjemplaresDisponibles(libro.getEjemplaresDisponibles() + 1);
        libroRepository.save(libro);

        if (tarde) {
            Lector lector = prestamo.getLector();
            lector.setActivo(false);
            lectorRepository.save(lector);
        }

        return PrestamoResponse.from(prestamoRepository.save(prestamo));
    }

    @Transactional
    public PrestamoResponse renovarPrestamo(Long prestamoId) {
        Prestamo prestamo = buscarPrestamo(prestamoId);

        if (prestamo.getEstado() == Estado.VENCIDO || prestamo.getEstado() == Estado.DEVUELTO) {
            throw new BadRequestException("El prestamo con id " + prestamoId + " esta " + prestamo.getEstado() + " y no se puede renovar");
        }

        prestamo.setFechaDevolucionMaxima(prestamo.getFechaDevolucionMaxima().plusDays(7));

        return PrestamoResponse.from(prestamoRepository.save(prestamo));
    }

    @Transactional(readOnly = true)
    public List<PrestamoResponse> listarPrestamosActivosLector(Long lectorId) {
        lectorService.buscarLector(lectorId);

        return prestamoRepository.findByLectorIdAndEstadoIn(
                        lectorId, List.of(Estado.PRESTADO, Estado.VENCIDO))
                .stream()
                .map(PrestamoResponse::from)
                .toList();
    }

    private Prestamo buscarPrestamo(Long id) {
        return prestamoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("El prestamo con id " + id + " no existe"));
    }
}
