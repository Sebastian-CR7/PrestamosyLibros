package mx.edu.utez.PrestamosyLibros.service;

import mx.edu.utez.PrestamosyLibros.controller.dto.LectorRequest;
import mx.edu.utez.PrestamosyLibros.controller.dto.LectorResponse;
import mx.edu.utez.PrestamosyLibros.exception.customExceptions.NotFoundException;
import mx.edu.utez.PrestamosyLibros.model.Lector;
import mx.edu.utez.PrestamosyLibros.repository.lector.LectorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LectorService {

    //inyeccion de dependencias
    private final LectorRepository lectorRepository;

    public LectorService(LectorRepository lectorRepository) {
        this.lectorRepository = lectorRepository;
    }

    //servicios
    @Transactional
    public LectorResponse crearLector(LectorRequest payload) {
        Lector lector = new Lector();
        lector.setNombre(payload.getNombre());
        lector.setMembresiaPremium(payload.getMembresiaPremium());
        lector.setActivo(true);

        return LectorResponse.from(lectorRepository.save(lector));
    }

    @Transactional(readOnly = true)
    public LectorResponse obtenerLector(Long id) {
        return LectorResponse.from(buscarLector(id));
    }

    //rehabilitar lector: desbloquea a los que entregaron tarde
    @Transactional
    public LectorResponse rehabilitarLector(Long id) {
        Lector lector = buscarLector(id);
        lector.setActivo(true);

        return LectorResponse.from(lectorRepository.save(lector));
    }

    //validacion por consulta: si el lector existe
    public Lector buscarLector(Long id) {
        return lectorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("El lector con id " + id + " no existe"));
    }
}
