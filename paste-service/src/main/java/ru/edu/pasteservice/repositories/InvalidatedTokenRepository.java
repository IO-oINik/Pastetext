package ru.edu.pasteservice.repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.edu.pasteservice.models.InvalidatedToken;

import java.util.List;
import java.util.Optional;

public interface InvalidatedTokenRepository extends JpaRepository<InvalidatedToken, String> {
    Optional<InvalidatedToken> findByToken(String token);
    void deleteByToken(String token);
    boolean existsByToken(String token);
    List<InvalidatedToken> findAllByOrderByAddedAtAsc(Pageable pageable);
}
