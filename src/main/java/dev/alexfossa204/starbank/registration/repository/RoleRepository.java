package dev.alexfossa204.starbank.registration.repository;

import dev.alexfossa204.starbank.registration.repository.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository <Role, Long> {

    Optional<Role> findByRoleName(String roleName);

}
