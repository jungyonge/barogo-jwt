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
    private long id;

    @Column(name = "username", length = 50, unique = true)
    private String username;

    @Column(name = "password", length = 100)
    private String password;

    @Column(name = "nickname", length = 50)
    private String nickname;

    @Column(name = "activated")
    private boolean activated;

    @Enumerated(EnumType.STRING)
    private UserStatus userStatus;

    @Enumerated(EnumType.STRING)
    private UserType userType;

    @ManyToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    @JoinTable(name = "user_authority", joinColumns = {@JoinColumn(name = "user_id")},
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

    public void login() {
        this.setLastLoggedIn(LocalDateTime.now());
    }

    public User(String username, String password, String nickname, UserType usertype) {

        this.setUsername(username);
        this.setPassword(password);
        this.setNickname(nickname);
        this.setActivated(true);
        this.setUserStatus(UserStatus.JOINED);
        this.setUserType(usertype);
        LocalDateTime now = LocalDateTime.now();
        this.setCreated(now);
        this.setLastLoggedIn(now);
    }

    public static User create(String username, String password, String nickname, String usertype) {
        UserType type = null;

        if(usertype.equals(UserType.ADMIN.getValue())){
            type = UserType.ADMIN;
        }else if(usertype.equals(UserType.COURIER.getValue())){
            type = UserType.COURIER;
        } else if (usertype.equals(UserType.SHOP_OWNER.getValue())) {
            type = UserType.SHOP_OWNER;
        }
        return new User(username, password, nickname, type);
    }

    private void setUsername(String username) {
        this.username = username;
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

    private void setUserType(UserType userType) {
        this.userType = userType;
    }
}
