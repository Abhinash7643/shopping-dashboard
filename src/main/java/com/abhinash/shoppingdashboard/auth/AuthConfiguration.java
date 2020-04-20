package com.abhinash.shoppingdashboard.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
@Configuration  // Enable security config. This annotation denotes config for spring security.
public class AuthConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private RestAuthenticationEntryPoint unauthorizedHandler;

    @Autowired
    private UserDetailsService userDetailsService;


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    //For authentication
    @Autowired
    public void configureGlobal( AuthenticationManagerBuilder auth ) throws Exception {
        auth.userDetailsService( userDetailsService )
                .passwordEncoder( passwordEncoder() );
    }

//    @Bean
//    public JwtAuthorizationFilter jwtAuthenticationFilter() {
//        return new JwtAuthorizationFilter();
//    }

    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Autowired
    private JwtUtil tokenProvider;

    //for outherization
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {

        httpSecurity
                // Disable CSRF (cross site request forgery)
                // Cross-origin resource sharing
                .cors().and().csrf().disable()
                // handle an authorized attempts
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler)
                // make sure we use stateless session; session won't be used to store user's state.
                // No session will be created or used by spring security
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
//                .authorizeRequests().antMatchers("/*/greet").permitAll()
//                .antMatchers("/api/test/**").permitAll()
////                .anyRequest().authenticated();
                .antMatcher("/*/secure/**").authorizeRequests()
                .antMatchers("/*/secure/**").authenticated()
                .and().addFilter(new JwtAuthorizationFilter(authenticationManager(), tokenProvider));


                // Add a filter to validate user credentials and add token in the response header

                // What's the authenticationManager()?
                // An object provided by WebSecurityConfigurerAdapter, used to authenticate the user passing user's credentials
                // The filter needs this auth manager to authenticate the user.

    }
}
