package app.test.barogojwt.api.authorization;

import app.test.barogojwt.api.authorization.request.LoginRequest;
import app.test.barogojwt.api.authorization.response.TokenDto;
import app.test.barogojwt.config.jwt.TokenProvider;
import app.test.barogojwt.domain.authorizationservice.application.authorization.AuthorizationHandler;
import javax.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/authorization")
public class AuthorizationController {

    private final AuthorizationHandler authorizationHandler;
    private final TokenProvider tokenProvider;

    public AuthorizationController(AuthorizationHandler authorizationHandler,
            TokenProvider tokenProvider) {
        this.authorizationHandler = authorizationHandler;
        this.tokenProvider = tokenProvider;
    }

    @PostMapping("/auth")
    public ResponseEntity<TokenDto> authorize(@Valid @RequestBody LoginRequest loginRequest) {

        TokenDto tokenDto = authorizationHandler.loginUser(loginRequest.getUsername(), loginRequest.getPassword());

        return new ResponseEntity<>(tokenDto, HttpStatus.OK);
    }

    @PostMapping("/refresh")
    public ResponseEntity<TokenDto> createNewAccessToken(@RequestHeader HttpHeaders header) {

        String newAccessToken = tokenProvider.regenerationAccessToken(header.get("access_token").get(0),header.get("refresh_token").get(0));
        TokenDto tokenDto = new TokenDto(newAccessToken, header.get("refresh_token").get(0));

        return new ResponseEntity<>(tokenDto, HttpStatus.OK);
    }

}
