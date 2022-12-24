package app.test.barogojwt.api.user;

import app.test.barogojwt.api.user.request.SignupRequest;
import app.test.barogojwt.api.user.response.UserDto;

import app.test.barogojwt.config.security.CustomUserDetails;
import app.test.barogojwt.domain.userservice.application.user.CreateUserHandler;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final CreateUserHandler createUserHandler;

    public UserController(CreateUserHandler createUserHandler) {
        this.createUserHandler = createUserHandler;
    }


    @PostMapping("/signup")
    public ResponseEntity<UserDto> signup(
            @Valid @RequestBody SignupRequest signupRequest) {

        createUserHandler.createUser(signupRequest.getUsername(),
                signupRequest.getPassword(), signupRequest.getNickname(),
                signupRequest.getUsertype());

        return ResponseEntity.ok(new UserDto(signupRequest.getUsername(),
                signupRequest.getNickname()));
    }

    @GetMapping("/me")
    @Secured({"ROLE_NORMAL_USER"})
    @Transactional(readOnly = true)
    public ResponseEntity<UserDto> getProfile(CustomUserDetails userDetails) {

        return ResponseEntity.ok(new UserDto(userDetails.getUsername(),
                userDetails.getNickname()));
    }
}


