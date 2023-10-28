package com.command.project.security.service;

import com.command.project.http.user.models.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class UserDetailsImpl implements UserDetails {

    private Long id;
    private String email;
    private String password;

    private Collection<? extends GrantedAuthority> authorities;

    public UserDetailsImpl(
            Long id,
            String email,
            String password,
            Collection<? extends GrantedAuthority> authorities
    ) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }

    public static UserDetailsImpl build(User user){
        List<SimpleGrantedAuthority> authorities = convertUserRolesToGrantedAuthorities(user);
        return new UserDetailsImpl(user.getId(), user.getEmail(), user.getPassword(), authorities);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    private static List<SimpleGrantedAuthority> convertUserRolesToGrantedAuthorities(User user) {
        return user.getSetRole()
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                .toList();
    }
}
