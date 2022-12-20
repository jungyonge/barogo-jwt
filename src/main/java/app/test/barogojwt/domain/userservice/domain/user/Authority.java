package app.test.barogojwt.domain.userservice.domain.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "authority")
@Getter
@NoArgsConstructor
public class Authority {

   @Id
   @Column(name = "authority_name", length = 50)
   private String authorityName;
}
