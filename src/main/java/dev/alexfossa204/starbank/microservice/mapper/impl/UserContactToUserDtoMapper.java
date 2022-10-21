package dev.alexfossa204.starbank.microservice.mapper.impl;

import dev.alexfossa204.starbank.microservice.mapper.EntityToDtoMapper;
import dev.alexfossa204.starbank.microservice.service.dto.broker.UserDto;
import dev.alexfossa204.starbank.microservice.repository.model.UserContact;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        componentModel = "spring"
)
public interface UserContactToUserDtoMapper extends EntityToDtoMapper<UserContact, UserDto> {

    @Mapping(target = "firstName", source = "user.passport.firstname")
    @Mapping(target = "lastName", source = "user.passport.lastname")
    @Mapping(target = "phoneLogin", source = "phoneNumber")
    @Mapping(target = "passportLogin", source = "user.passport.passportSerialNumber")
    @Mapping(target = "isClient", constant = "false")
    @Override
    UserDto mapEntityToDto(UserContact entity);
}
