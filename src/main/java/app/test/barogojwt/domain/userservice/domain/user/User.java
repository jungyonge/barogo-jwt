package app.test.barogojwt.domain.userservice.domain.user;

import app.test.barogojwt.domain.userservice.domain.UserDomainValidationMessage;
import app.test.barogojwt.domain.userservice.domain.security.Role;
import app.test.barogojwt.support.domain.DomainValidationException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "`user`")
@Getter
@NoArgsConstructor
public class User {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", length = 50, unique = true)
    private String userId;

    @Column(name = "password", length = 100)
    private String password;

    @Column(name = "nickname", length = 50)
    private String nickname;

    @Column(name = "activated")
    private boolean activated;

    @Enumerated(EnumType.STRING)
    private UserStatus userStatus;

    @ManyToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    @JoinTable(name = "user_authority", joinColumns = {@JoinColumn(name = "id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")})
    private List<Role> roles = new ArrayList<>();

    private LocalDateTime created;

    private LocalDateTime updated;

    private LocalDateTime lastLoggedIn;

    public void addRole(Role role) {
        if (this.roles.stream().anyMatch(r -> r.getId() == role.getId())) {
            throw new DomainValidationException(UserDomainValidationMessage.ROLE_ALREADY_EXIST);
        }

        this.roles.add(role);
    }

    public User(String userId, String password, String nickname) {

        this.setUserId(userId);
        this.setPassword(password);
        this.setNickname(nickname);
        this.setActivated(true);
        this.setUserStatus(UserStatus.JOINED);
        LocalDateTime now = LocalDateTime.now();
        this.setCreated(now);
        this.setLastLoggedIn(now);
    }


    public static User create(String userId, String password, String nickname) {
        return new User(userId, password, nickname);
    }

    private void setUserId(String userId) {
        this.userId = userId;
    }

    private void setPassword(String password) {
        this.password = password;
    }

    private void setNickname(String nickname) {
        this.nickname = nickname;
    }

    private void setCreated(LocalDateTime created) {
        this.created = created;
    }

    private void setUpdated(LocalDateTime updated) {
        this.updated = updated;
    }

    private void setLastLoggedIn(LocalDateTime lastLoggedIn) {
        this.lastLoggedIn = lastLoggedIn;
    }


    private void setActivated(boolean activated) {
        this.activated = activated;
    }

    private void setUserStatus(UserStatus userStatus) {
        this.userStatus = userStatus;
    }
}
