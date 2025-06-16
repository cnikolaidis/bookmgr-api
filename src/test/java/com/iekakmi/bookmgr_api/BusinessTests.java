package com.iekakmi.bookmgr_api;

import org.springframework.beans.factory.annotation.Autowired;
import com.iekakmi.bookmgr_api.business.services.BookService;
import org.springframework.boot.test.context.SpringBootTest;
import com.iekakmi.bookmgr_api.business.dtos.BookDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.List;

@SpringBootTest
public class BusinessTests {
	@Autowired
	private BookService svc;
	
	@Test
	void test_getAllBooks() {
		List<BookDto> result = svc.getBooks();
		
		Assertions.assertThat(result).isNotNull();
	}
}