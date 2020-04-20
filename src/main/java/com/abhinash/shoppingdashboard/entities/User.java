package com.abhinash.shoppingdashboard.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "users")
public class User {

    @Id
    @Field(name = "user_id")
    private String userId;

    private String email;

    private String name;

    @Field(name = "user_name")
    private String username;

    private String password;

    @Field(name = "email_verified")
    private Boolean emailVerified;

    @DBRef
    private Set<Role> roles;


}
