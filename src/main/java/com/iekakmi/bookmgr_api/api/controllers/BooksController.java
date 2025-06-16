package com.iekakmi.bookmgr_api.api.controllers;

import com.iekakmi.bookmgr_api.business.services.BookService;
import org.springframework.web.bind.annotation.*;
import com.iekakmi.bookmgr_api.business.dtos.*;
import org.springframework.http.ResponseEntity;
import java.util.List;
import java.net.URI;

@RestController
@RequestMapping("books")
public class BooksController {
	private final BookService svc;

	public BooksController(BookService svc) {
		this.svc = svc;
	}

	@GetMapping
	public ResponseEntity<?> get() {
		List<BookDto> result = svc.getBooks();
		return ResponseEntity
				.ok(result);
	}
	
	@GetMapping("{isbn}")
	public ResponseEntity<?> getByIsbn(@PathVariable("isbn") String isbn) {
		BookDto result = svc.getBookByIsbn(isbn);
		return ResponseEntity
				.ok(result);
	}
	
	@GetMapping("{isbn}/authors")
	public ResponseEntity<?> getAuthorsByBook(@PathVariable("isbn") String isbn) {
		List<AuthorDto> result = svc.getAuthorsByBookIsbn(isbn);
		return ResponseEntity
				.ok(result);
	}
	
	@PostMapping
	public ResponseEntity<?> create(@RequestBody BookDto dto) {
		BookDto result = svc.createBook(dto);
		return ResponseEntity
				.created(URI.create("/books"))
				.body(result);
	}
	
	@PostMapping("{isbn}/authors")
	public ResponseEntity<?> create(@PathVariable("isbn") String isbn, @RequestBody BookDto dto) {
		BookDto result = svc.assignAuthorsToBook(isbn, dto.getAuthorIds());
		return ResponseEntity
				.created(URI.create(String.format("/%s/books", isbn)))
				.body(result);
	}
	
	@PutMapping
	public ResponseEntity<?> update(@RequestBody BookDto dto) {
		BookDto result = svc.updateBook(dto);
		return ResponseEntity
				.accepted()
				.body(result);
	}
	
	@DeleteMapping("{isbn}")
	public ResponseEntity<?> delete(@PathVariable("isbn") String isbn) {
		svc.deleteBook(isbn);
		return ResponseEntity
				.noContent()
				.build();
	}
}