package app.test.barogojwt.domain.userservice.domain.security;

import java.util.Optional;

public interface RoleRepository {

    Optional<Role> getRoleByName(RoleName name);
}
