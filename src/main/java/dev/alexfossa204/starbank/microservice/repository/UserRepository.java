package dev.alexfossa204.starbank.microservice.repository;

import dev.alexfossa204.starbank.microservice.repository.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
