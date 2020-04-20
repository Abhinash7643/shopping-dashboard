package com.abhinash.shoppingdashboard.repository;

import com.abhinash.shoppingdashboard.entities.Role;
import com.abhinash.shoppingdashboard.util.RoleName;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface RoleRepos extends MongoRepository<Role, Long> {

    Optional<Role> findByRoleName(RoleName roleName);
}
