package com.player2.server.persistence;

import com.player2.server.model.Clique;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CliqueRepository extends JpaRepository<Clique, Long> {
}
