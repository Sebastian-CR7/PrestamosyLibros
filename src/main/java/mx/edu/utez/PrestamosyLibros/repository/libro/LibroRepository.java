package mx.edu.utez.PrestamosyLibros.repository.libro;

import mx.edu.utez.PrestamosyLibros.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LibroRepository extends JpaRepository<Libro, Long> {
}
