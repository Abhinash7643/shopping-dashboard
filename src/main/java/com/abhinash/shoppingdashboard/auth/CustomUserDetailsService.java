package com.abhinash.shoppingdashboard.auth;

import com.abhinash.shoppingdashboard.entities.User;
import com.abhinash.shoppingdashboard.exception.MyException;
import com.abhinash.shoppingdashboard.repository.UserRepos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepos userRepos;


    //
    // Remember that Spring needs roles to be in this format: "ROLE_" + userRole (i.e. "ROLE_ADMIN")
    // So, we need to set it to that format, so we can verify and compare roles (i.e. hasRole("ADMIN")).
    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail)
            throws UsernameNotFoundException {
        // Let people login with either username or email
        User user = userRepos.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found with username or email : " + usernameOrEmail)
                );

        // The "User" class is provided by Spring and represents a model class for user to be returned by UserDetailsService
        // And used by auth manager to verify and check user authentication.
        return UserPrincipal.create(user);
    }

    @Transactional
    public UserDetails loadUserById(String userId) {
        User user = userRepos.findById(userId).orElseThrow(
                () -> new MyException("User id" + userId)
        );

        return UserPrincipal.create(user);
    }
}
