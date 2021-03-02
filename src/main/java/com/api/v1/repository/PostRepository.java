package com.api.v1.repository;

import com.api.v1.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The interface User repository.
 *
 * @author Emil Burdach
 */
@Repository
public interface PostRepository extends JpaRepository<Post, Long> {}
