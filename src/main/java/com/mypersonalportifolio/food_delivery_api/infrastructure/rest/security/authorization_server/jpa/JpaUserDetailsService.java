package com.mypersonalportifolio.food_delivery_auth_server.infraestructure.jpa;

import com.mypersonalportifolio.food_delivery_auth_server.domain.model.User;
import com.mypersonalportifolio.food_delivery_auth_server.domain.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class JpaUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public JpaUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = findValidUser(username);
        return new CustomUserDetails(user, resolveGrantedAuthorities(user.getEmail()));
    }

    private User findValidUser(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(email));
    }

    private Collection<GrantedAuthority> resolveGrantedAuthorities(String email) {
        return userRepository.findPermissionNamesByUserEmail(email).stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());
    }
}
