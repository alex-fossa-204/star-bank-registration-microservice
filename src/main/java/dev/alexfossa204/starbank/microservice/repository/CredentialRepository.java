package dev.alexfossa204.starbank.microservice.repository;

import dev.alexfossa204.starbank.microservice.repository.model.Credential;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CredentialRepository extends JpaRepository<Credential, Long> {

    Optional<Credential> findCredentialByPhoneLoginOrPassportLogin(String phoneLogin, String passportLogin);

    Optional<Credential> findCredentialByPhoneLogin(String phoneLogin);

}
