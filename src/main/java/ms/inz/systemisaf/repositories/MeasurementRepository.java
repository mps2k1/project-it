package ms.inz.systemisaf.repositories;

import ms.inz.systemisaf.model.Measurement;
import ms.inz.systemisaf.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MeasurementRepository extends JpaRepository<Measurement,Long> {
    Optional<Measurement> findTopByUserOrderByCreatedAtDesc(User user);
}
