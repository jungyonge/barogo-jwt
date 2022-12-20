package app.test.barogojwt.domain.userservice.infrastructure.domain.role;

import app.test.barogojwt.domain.userservice.domain.security.Role;
import app.test.barogojwt.domain.userservice.domain.security.RoleName;
import app.test.barogojwt.domain.userservice.domain.security.RoleRepository;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class RoleRepositoryImpl implements RoleRepository {

    private final RoleJpaRepository roleJpaRepository;

    @Override
    public Optional<Role> getRoleByName(RoleName name) {
        return roleJpaRepository.findByName(name);
    }
}
