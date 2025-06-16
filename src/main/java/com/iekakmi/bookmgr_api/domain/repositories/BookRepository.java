package com.iekakmi.bookmgr_api.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.iekakmi.bookmgr_api.domain.entities.Book;

public interface BookRepository extends JpaRepository<Book, String> {}