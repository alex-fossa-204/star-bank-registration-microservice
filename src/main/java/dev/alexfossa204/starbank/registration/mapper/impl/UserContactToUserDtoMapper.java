package dev.alexfossa204.starbank.registration.mapper.impl;

import dev.alexfossa204.starbank.registration.mapper.EntityToDtoMapper;
import dev.alexfossa204.starbank.registration.service.dto.broker.UserDto;
import dev.alexfossa204.starbank.registration.repository.model.UserContact;
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
