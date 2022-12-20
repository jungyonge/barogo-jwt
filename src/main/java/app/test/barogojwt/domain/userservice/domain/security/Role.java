package app.test.barogojwt.domain.userservice.domain.security;

import app.test.barogojwt.domain.userservice.domain.user.User;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table
@Getter
@NoArgsConstructor
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Enumerated(EnumType.STRING)
    private RoleName name;

    private LocalDateTime created;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "roles")
    private Set<User> users = new HashSet<>();

    public String getDesc() {
        return this.name.getDesc();
    }
}
