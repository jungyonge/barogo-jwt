package app.test.barogojwt.api.user;

import app.test.barogojwt.api.user.request.SignupRequest;
import app.test.barogojwt.api.user.response.UserDto;
import app.test.barogojwt.config.security.CustomUserDetails;
import app.test.barogojwt.domain.userservice.application.user.CreateUserHandler;
import app.test.barogojwt.domain.userservice.application.user.GetUserHandler;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final CreateUserHandler createUserHandler;

    private final GetUserHandler getUserHandler;

    public UserController(CreateUserHandler createUserHandler, GetUserHandler getUserHandler) {
        this.createUserHandler = createUserHandler;
        this.getUserHandler = getUserHandler;
    }


    @PostMapping("/signup")
    public ResponseEntity<UserDto> signup(
            @Valid @RequestBody SignupRequest signupRequest) {

        var user = createUserHandler.createUser(signupRequest.getUsername(),
                signupRequest.getPassword(), signupRequest.getNickname(),
                signupRequest.getUsertype());

        return ResponseEntity.ok(new UserDto(user.getUsername(),
                user.getNickname()));
    }

    @GetMapping("/me")
    @Secured({"ROLE_NORMAL_USER"})
    public ResponseEntity<UserDto> getProfile(CustomUserDetails userDetails, @RequestParam String username) {
        var user = getUserHandler.getUserByUsername(userDetails.getId(), username);
        return ResponseEntity.ok(new UserDto(user.getUsername(), user.getNickname()));
    }
}


