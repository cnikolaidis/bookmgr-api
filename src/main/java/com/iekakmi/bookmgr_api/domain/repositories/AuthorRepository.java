package com.iekakmi.bookmgr_api.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.iekakmi.bookmgr_api.domain.entities.Author;

public interface AuthorRepository extends JpaRepository<Author, Integer> {}