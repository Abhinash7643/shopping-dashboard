package com.abhinash.shoppingdashboard.service;

import com.abhinash.shoppingdashboard.auth.AuthenticationFacade;
import com.abhinash.shoppingdashboard.auth.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuditorAwareService implements AuditorAware<String> {

    //implementing getCurrentAuditor  to mark entity objects created and modified.
    @Autowired
    AuthenticationFacade authenticationFacade;

    @Override
    public Optional<String> getCurrentAuditor() {
        Authentication authentication = authenticationFacade.getAuthentication();
        if(null == authentication){
            return Optional.empty();
        }
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        return Optional.ofNullable(userPrincipal.getId());
    }
}
