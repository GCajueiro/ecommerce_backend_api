package com.ecommerce.liven.ecommerce_backend.api.controller.auth;

import com.ecommerce.liven.ecommerce_backend.api.model.LoginBody;
import com.ecommerce.liven.ecommerce_backend.api.model.LoginResponse;
import com.ecommerce.liven.ecommerce_backend.api.model.RegistrationBody;
import com.ecommerce.liven.ecommerce_backend.exeption.ExistingUser;
import com.ecommerce.liven.ecommerce_backend.model.UsuarioLocal;
import com.ecommerce.liven.ecommerce_backend.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity registraUsuario(@Valid @RequestBody RegistrationBody registrationBody) //Requestbody indica onde está o payload//
    {
        try{
            userService.regUsuarioLocal(registrationBody);
            return ResponseEntity.ok().build();
        } catch (ExistingUser existingUser) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> loginUser(@Valid @RequestBody LoginBody loginBody) {
        String jwt = userService.login(loginBody);
        if (jwt == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } else {
            LoginResponse response = new LoginResponse();
            response.setJwt(jwt);
            return ResponseEntity.ok(response);
        }
    }

    @GetMapping("/check")
    public UsuarioLocal getProfileLogged(@AuthenticationPrincipal UsuarioLocal usuarioLocal){
        return usuarioLocal;
    }
}