package com.iekakmi.bookmgr_api.api.controllers;

import com.iekakmi.bookmgr_api.business.exceptions.BusinessLayerException;
import org.springframework.beans.factory.annotation.Autowired;
import com.iekakmi.bookmgr_api.business.services.BookService;
import org.springframework.web.bind.annotation.*;
import com.iekakmi.bookmgr_api.business.dtos.*;
import org.springframework.http.ResponseEntity;
import java.util.List;

@RestController
@RequestMapping("books")
public class BooksController {
	@Autowired
	private BookService svc;
	
	@GetMapping
	public ResponseEntity<?> get() {
		try {
			List<BookDto> result = svc.getBooks();
			return ResponseEntity
					.ok(result);
		}
		catch (Exception x) {
			return ResponseEntity
					.internalServerError()
					.body(x);
		}
	}
	
	@GetMapping("{isbn}")
	public ResponseEntity<?> getByIsbn(@PathVariable("isbn") String isbn) {
		try {
			BookDto result = svc.getBookByIsbn(isbn);
			return ResponseEntity
					.ok(result);
		}
		catch (Exception x) {
			return ResponseEntity
					.internalServerError()
					.body(x);
		}
	}
	
	@GetMapping("{isbn}/authors")
	public ResponseEntity<?> getAuthorsByBook(@PathVariable("isbn") String isbn) {
		try {
			List<AuthorDto> result = svc.getAuthorsByBookIsbn(isbn);
			return ResponseEntity
					.ok(result);
		}
		catch (Exception x) {
			return ResponseEntity
					.internalServerError()
					.body(x);
		}
	}
	
	@PostMapping
	public ResponseEntity<?> create(@RequestBody BookDto dto) {
		try {
			BookDto result = svc.createBook(dto);
			return ResponseEntity
					.ok(result);
		}
		catch (BusinessLayerException blx) {
			return ResponseEntity
					.internalServerError()
					.body(blx);
		}
	}
	
	@PostMapping("{isbn}/authors")
	public ResponseEntity<?> create(@PathVariable("isbn") String isbn, @RequestBody BookDto dto) {
		try {
			BookDto result = svc.assignAuthorsToBook(isbn, dto.getAuthorIds());
			return ResponseEntity
					.ok(result);
		}
		catch (BusinessLayerException blx) {
			return ResponseEntity
					.internalServerError()
					.body(blx);
		}
	}
	
	@PutMapping
	public ResponseEntity<?> update(@RequestBody BookDto dto) {
		try {
			BookDto result = svc.updateBook(dto);
			return ResponseEntity
					.ok(result);
		}
		catch (BusinessLayerException blx) {
			return ResponseEntity
					.internalServerError()
					.body(blx);
		}
	}
	
	@DeleteMapping("{isbn}")
	public ResponseEntity<?> delete(@PathVariable("isbn") String isbn) {
		try {
			svc.deleteBook(isbn);
			return ResponseEntity
					.ok(isbn);
		}
		catch (BusinessLayerException blx) {
			return ResponseEntity
					.internalServerError()
					.body(blx);
		}
	}
}