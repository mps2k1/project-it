package ms.inz.systemisaf.repositories;
import ms.inz.systemisaf.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findById(Long userId);
}
