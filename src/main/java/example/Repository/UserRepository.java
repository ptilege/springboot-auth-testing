package example.Repository;

import example.Model.Register;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Register, Long> {
    Register findByEmail(String email); // Update to find by email
}
