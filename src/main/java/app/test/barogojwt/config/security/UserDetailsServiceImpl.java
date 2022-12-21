package app.test.barogojwt.config.security;

import app.test.barogojwt.domain.userservice.domain.user.User;
import app.test.barogojwt.domain.userservice.domain.user.UserRepository;
import app.test.barogojwt.domain.userservice.domain.user.UserStatus;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        return userRepository.getUserByUserId(userId)
                .map(user -> {
                    return createUser(userId, user);
                })
                .orElseThrow(() -> new UsernameNotFoundException("User not found."));
    }

    private org.springframework.security.core.userdetails.User createUser(String userId,
            User user) {
        if (!user.isActivated()) {
            log.error(userId + " -> 활성화되어 있지 않습니다.");
            throw new RuntimeException(userId + " -> 활성화되어 있지 않습니다.");
        }

        if (user.getUserStatus().equals(UserStatus.USER_WITHDRAWAL)) {
            log.error(userId + " -> 관리자에 의해 계정 정지가 되어 있습니다.");
            throw new RuntimeException(userId + " -> 관리자에 의해 계정 정지가 되어 있습니다.");
        }

        List<UserRole> roles = user.getRoles().stream()
                .map(role -> new UserRole(role.getId(), role.getName(), role.getCreated()))
                .collect(Collectors.toCollection(ArrayList::new));

        return new org.springframework.security.core.userdetails.User(user.getUserId(),
                user.getPassword(),
                roles);
    }
}
