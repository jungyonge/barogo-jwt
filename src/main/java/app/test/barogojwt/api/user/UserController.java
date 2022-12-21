package app.test.barogojwt.api.user;

import app.test.barogojwt.api.user.request.LoginRequest;
import app.test.barogojwt.api.user.request.SignupRequest;
import app.test.barogojwt.api.user.response.UserDto;
import app.test.barogojwt.api.user.response.TokenDto;

import app.test.barogojwt.config.jwt.JwtFilter;
import app.test.barogojwt.config.jwt.TokenProvider;
import app.test.barogojwt.domain.userservice.application.user.CreateUserHandler;
import app.test.barogojwt.domain.userservice.application.user.LoginUserHandler;
import javax.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final CreateUserHandler createUserHandler;

    private final LoginUserHandler loginUserHandler;

    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    public UserController(CreateUserHandler createUserHandler, LoginUserHandler loginUserHandler,
            TokenProvider tokenProvider,
            AuthenticationManagerBuilder authenticationManagerBuilder) {
        this.createUserHandler = createUserHandler;
        this.loginUserHandler = loginUserHandler;
        this.tokenProvider = tokenProvider;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
    }

    @PostMapping("/signup")
    public ResponseEntity<UserDto> signup(
            @Valid @RequestBody SignupRequest signupRequest) {

        createUserHandler.createUser(signupRequest.getUsername(),
                signupRequest.getPassword(), signupRequest.getNickname());

        return ResponseEntity.ok(new UserDto(signupRequest.getUsername(),
                signupRequest.getNickname()));
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDto> authorize(@Valid @RequestBody LoginRequest loginRequest) {

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.createToken(authentication);

        loginUserHandler.loginUser(loginRequest.getUsername());

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);

        return new ResponseEntity<>(new TokenDto(jwt), httpHeaders, HttpStatus.OK);
    }
}
