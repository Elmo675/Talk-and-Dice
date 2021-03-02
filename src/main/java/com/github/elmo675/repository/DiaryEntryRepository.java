package com.github.elmo675.repository;

import com.github.elmo675.model.DiaryEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The interface User repository.
 *
 * @author Emil Burdach
 */
@Repository
public interface DiaryEntryRepository extends JpaRepository<DiaryEntry, Long> {}
