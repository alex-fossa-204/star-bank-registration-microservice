package dev.alexfossa204.starbank.registration.mapper;

public interface EntityToDtoMapper <E, D> {

    D mapEntityToDto(E entity);

    E mapDtoToEntity(D dto);

}
