package com.Ecommerce.store.users

-commerce.store.users

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserDetailsService {
    private final com.codewithmosh.store.users.UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        var userDetails = userRepository.findByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException("User not found"));
        return new User(
                userDetails.getEmail(),
                userDetails.getPassword(),
                Collections.emptyList()
        );
    }
}
