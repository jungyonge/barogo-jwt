package app.test.barogojwt.api.user.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SignupRequest {

    @NotNull
    @Size(min = 3, max = 30)
    private String username;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull
    @Size(min = 12, max = 50)
    private String password;

    @NotNull
    @Size(min = 3, max = 30)
    private String nickname;

    @NotNull
    @Size(min = 3, max = 30)
    private String usertype;
}
