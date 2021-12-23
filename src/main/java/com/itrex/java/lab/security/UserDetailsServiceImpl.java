package com.itrex.java.lab.security;

import com.itrex.java.lab.persistence.dataimpl.UserRepository;
import com.itrex.java.lab.persistence.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service("userDetailsServiceImpl")
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> user = userRepository.findUserByEmail(username);

        if (user.isEmpty()) {
            throw new UsernameNotFoundException(String.format("User with email: %s does not exist", username));
        }
        return SecurityUserDetails.fromUser(user.get());
    }
}
