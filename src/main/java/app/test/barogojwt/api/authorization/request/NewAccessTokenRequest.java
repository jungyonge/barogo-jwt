package app.test.barogojwt.api.authorization.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class NewAccessTokenRequest {

    private String access_token;
    private String refresh_token;

}
