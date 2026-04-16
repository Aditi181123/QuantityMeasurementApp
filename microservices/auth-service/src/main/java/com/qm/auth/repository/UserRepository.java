package com.qm.auth.repository;

import com.qm.auth.model.User;  // Entity class
import org.springframework.data.jpa.repository.JpaRepository;  // Base repository interface
import org.springframework.stereotype.Repository;  // Marks as Spring component
import java.util.Optional;  


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    Optional<User> findByProviderAndProviderId(String provider, String providerId);
}
