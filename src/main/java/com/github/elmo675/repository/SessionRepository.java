package com.github.elmo675.repository;

import com.github.elmo675.model.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The interface User repository.
 *
 * @author Emil Burdach
 */
@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {}
