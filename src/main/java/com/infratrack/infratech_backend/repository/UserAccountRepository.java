package com.infratrack.infratech_backend.repository;

import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import com.infratrack.infratech_backend.model.UserAccount;

public interface UserAccountRepository extends MongoRepository<UserAccount, String> {
    boolean existsByEmailIgnoreCase(String email);
    Optional<UserAccount> findByEmailIgnoreCase(String email);
}
