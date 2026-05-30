
package com.meows.meows.config;

 /* Basic template for AuthController */


 import com.meows.meows.dto.LoginRequest;
 import com.meows.meows.dto.LoginResponse;
 import com.meows.meows.dto.register.RegisterRequest;
 import com.meows.meows.dto.register.RegisterResponse;
 import com.meows.meows.service.UserService;
 import jakarta.validation.Valid;
 import org.springframework.http.HttpStatus;
 import org.springframework.http.ResponseEntity;
 import org.springframework.web.bind.annotation.PostMapping;
 import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        System.out.println("ENTROU NO LOGIN");

        return ResponseEntity.ok(userService.loginUser(loginRequest));
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@Valid @RequestBody RegisterRequest registerRequest) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userService.registerUser(registerRequest));

    }
}
