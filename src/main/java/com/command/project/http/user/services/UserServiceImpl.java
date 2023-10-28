package com.command.project.http.user.services;

import com.command.project.http.user.interfaces.UserService;
import com.command.project.security.dto.CreateNewUserRespDTO;
import com.command.project.security.dto.SignupRequestDTO;
import com.command.project.http.user.models.User;
import com.command.project.http.user.interfaces.UserRepository;
import com.command.project.security.service.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public CreateNewUserRespDTO createNewUser(SignupRequestDTO signupRequestDTO) throws Exception {
        if (signupRequestDTO.getEmail().isEmpty() || signupRequestDTO.getPassword().isEmpty()){
            throw new Exception("Не указан логин или пароль пользователя!");
        }

        if (userRepository.existsUserByEmail(signupRequestDTO.getEmail().get())){
            throw new Exception("Пользователь с почтой " + signupRequestDTO.getEmail() + " уже существует!");
        }

        User user = new User();
        user.setEmail(signupRequestDTO.getEmail().get());
        user.setPassword(passwordEncoder.encode(signupRequestDTO.getPassword().get()));
        userRepository.save(user);

        return new CreateNewUserRespDTO();
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findUserByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Not found user with email " + email));
        return UserDetailsImpl.build(user);
    }
}
