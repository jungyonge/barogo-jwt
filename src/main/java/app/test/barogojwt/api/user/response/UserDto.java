package app.test.barogojwt.api.user.response;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Set;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
public class UserDto {

    @NotNull
    @Size(min = 3, max = 50)
    private String username;

    @NotNull
    @Size(min = 3, max = 50)
    private String nickname;

}
