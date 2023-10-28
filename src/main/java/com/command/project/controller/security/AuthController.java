package com.command.project.controller.security;

import com.command.project.http.user.interfaces.UserService;
import com.command.project.security.dto.JwtRequestDTO;
import com.command.project.security.dto.JwtResponseDTO;
import com.command.project.security.dto.SignupRequestDTO;
import com.command.project.security.util.JwtTokenUtils;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtils jwtTokenUtils;
    private final UserService userService;

    @Autowired
    public AuthController(
            AuthenticationManager authenticationManager,
            JwtTokenUtils jwtTokenUtils,
            UserService userService
    ){
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtils = jwtTokenUtils;
        this.userService = userService;
    }


    //добавление глобальной точки обработки исключений
    //отрефакторить сервис user

    /***
     * @todo глобальный класс обработки эксепшенов
     * @todo валидация dtoшек
     *
     * @param jwtRequestDTO
     * @return
     */
    @PostMapping(value = "/token")
    public ResponseEntity<?> createAuthToken(
            @RequestBody JwtRequestDTO jwtRequestDTO
    ){
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(jwtRequestDTO.getPassword(), jwtRequestDTO.getEmail()));
        }catch (BadCredentialsException e){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        UserDetails userDetails = userService.loadUserByUsername(jwtRequestDTO.getEmail());
        String token = jwtTokenUtils.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponseDTO(token, jwtRequestDTO.getEmail()));
    }

    @PostMapping(value = "/register")
    public ResponseEntity<?> registration(
            @Valid @RequestBody SignupRequestDTO signupRequestDTO
    ){
        try {
            return ResponseEntity.ok(userService.createNewUser(signupRequestDTO));
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
