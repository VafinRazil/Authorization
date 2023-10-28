package com.command.project.http.user.interfaces;

import com.command.project.security.dto.CreateNewUserRespDTO;
import com.command.project.security.dto.SignupRequestDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    CreateNewUserRespDTO createNewUser(SignupRequestDTO signupRequestDTO) throws Exception;
}
