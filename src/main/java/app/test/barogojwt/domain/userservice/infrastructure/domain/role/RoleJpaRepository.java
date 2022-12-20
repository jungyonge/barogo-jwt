package app.test.barogojwt.domain.userservice.infrastructure.domain.role;


import app.test.barogojwt.domain.userservice.domain.security.Role;
import app.test.barogojwt.domain.userservice.domain.security.RoleName;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface RoleJpaRepository extends CrudRepository<Role, Integer> {

    Optional<Role> findByName(RoleName name);
}
