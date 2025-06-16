package com.iekakmi.bookmgr_api;

import com.iekakmi.bookmgr_api.domain.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.iekakmi.bookmgr_api.domain.entities.Book;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.List;

@SpringBootTest
public class DomainTests {
	@Autowired
	private BookRepository repo;
	
	@Test
	void test_getAllBooks() {
		List<Book> result = repo.findAll().stream().toList();
		
		Assertions.assertThat(result).isNotNull();
	}
}