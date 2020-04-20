package com.abhinash.shoppingdashboard.controller;

import com.abhinash.shoppingdashboard.auth.CustomUserDetailsService;
import com.abhinash.shoppingdashboard.auth.JwtUtil;
import com.abhinash.shoppingdashboard.entities.LoginRequest;
import com.abhinash.shoppingdashboard.entities.Role;
import com.abhinash.shoppingdashboard.entities.SignUpRequest;
import com.abhinash.shoppingdashboard.entities.User;
import com.abhinash.shoppingdashboard.exception.MyException;
import com.abhinash.shoppingdashboard.mapper.AuthMapper;
import com.abhinash.shoppingdashboard.repository.RoleRepos;
import com.abhinash.shoppingdashboard.repository.UserRepos;
import com.abhinash.shoppingdashboard.util.CommonConstants;
import com.abhinash.shoppingdashboard.util.JwtAuthenticationResponse;
import com.abhinash.shoppingdashboard.util.RoleName;
import com.abhinash.shoppingdashboard.util.SnowflakeIdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by rajeevkumarsingh on 02/08/17.
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController extends AbstractBaseController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepos userRepository;

    @Autowired
    RoleRepos roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtUtil tokenProvider;

    @Autowired
    CustomUserDetailsService userDetailsService;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        // Perform the security
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsernameOrEmail(),
                        loginRequest.getPassword()
                )
        );
        //this can also be done
//        UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getUsernameOrEmail());

        // Inject into security context
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // token creation
        String jwt = tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        userRepository.existsByUsername(signUpRequest.getUsername()).
                orElseThrow(() -> new MyException("Username is already taken!" + signUpRequest.getEmail()));

        userRepository.existsByUsername(signUpRequest.getUsername()).
                orElseThrow(() -> new MyException("Email Address already in use!"));


        // Creating user's account
        User user = AuthMapper.INSTANCE.signUpReqToUser(signUpRequest);
        user.setUserId(SnowflakeIdGenerator.getInstance().getIdHex(CommonConstants.USER_PREFIX));

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Set<String> strRoles = signUpRequest.getRols();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            //setting default role as user
            Role userRole = roleRepository.findByRoleName(RoleName.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByRoleName(RoleName.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);

                        break;
                    default:
                        Role userRole = roleRepository.findByRoleName(RoleName.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }

        user.setRoles(roles);
        user = userRepository.save(user);

//        URI location = ServletUriComponentsBuilder
//                .fromCurrentContextPath().path("/users/{username}")
//                .buildAndExpand(result.getUsername()).toUri();
        return created(user);
    }
}
