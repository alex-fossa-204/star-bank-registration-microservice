package dev.alexfossa204.starbank.registration.repository;

import dev.alexfossa204.starbank.registration.repository.model.Passport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PassportRepository extends JpaRepository<Passport, Long> {
}
