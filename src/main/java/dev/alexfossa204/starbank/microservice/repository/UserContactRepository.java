package dev.alexfossa204.starbank.microservice.repository;

import dev.alexfossa204.starbank.microservice.repository.model.UserContact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserContactRepository extends JpaRepository<UserContact, Long> {

    Optional<UserContact> findByPhoneNumber(String phoneNumber);
}
