package com.iekakmi.bookmgr_api;

import com.iekakmi.bookmgr_api.api.controllers.BooksController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

@SpringBootTest
public class ApiTests {
	@Autowired
	private BooksController ctrl;
	
	@Test
	void test_getAllBooks() {
		ResponseEntity<?> result = ctrl.get();
		
		Assertions.assertThat(result).isNotNull();
	}
}