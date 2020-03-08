package neo.repository;

import neo.domain.Rental;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RentalRepository extends JpaRepository<Rental, Long> {
    List<Rental> findByUser_Id(Long userId);
    List<Rental> findByUser_IdAndStatus(Long userId, String status);
}
