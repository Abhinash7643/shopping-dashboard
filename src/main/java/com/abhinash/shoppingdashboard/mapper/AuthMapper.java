package com.abhinash.shoppingdashboard.mapper;

import com.abhinash.shoppingdashboard.entities.SignUpRequest;
import com.abhinash.shoppingdashboard.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public abstract class AuthMapper {

    public static final AuthMapper INSTANCE = Mappers.getMapper(AuthMapper.class);

    public abstract User signUpReqToUser(SignUpRequest signUpRequest);

}
