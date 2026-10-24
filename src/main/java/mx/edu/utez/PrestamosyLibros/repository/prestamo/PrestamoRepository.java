package mx.edu.utez.PrestamosyLibros.repository.prestamo;

import mx.edu.utez.PrestamosyLibros.model.Estado;
import mx.edu.utez.PrestamosyLibros.model.Prestamo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrestamoRepository extends JpaRepository<Prestamo, Long> {

    long countByLectorIdAndEstadoIn(Long lectorId, List<Estado> estados);

    List<Prestamo> findByLectorIdAndEstadoIn(Long lectorId, List<Estado> estados);
}
