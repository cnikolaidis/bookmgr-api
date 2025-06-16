package com.iekakmi.bookmgr_api.api.controllers;

import com.iekakmi.bookmgr_api.business.services.AuthorService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import com.iekakmi.bookmgr_api.business.dtos.*;
import java.util.List;
import java.net.URI;

@RestController
@RequestMapping("authors")
public class AuthorsController {
	private final AuthorService svc;

	public AuthorsController(AuthorService svc) {
		this.svc = svc;
	}

	@GetMapping
	public ResponseEntity<?> get() {
		List<AuthorDto> result = svc.getAuthors();
		return ResponseEntity
				.ok(result);
	}
	
	@GetMapping("{id}")
	public ResponseEntity<?> getById(@PathVariable("id") int id) {
		AuthorDto result = svc.getAuthorById(id);
		return ResponseEntity
				.ok(result);
	}
	
	@GetMapping("{id}/books")
	public ResponseEntity<?> getBooksByAuthor(@PathVariable("id") int id) {
		List<BookDto> result = svc.getBooksByAuthorId(id);
		return ResponseEntity
				.ok(result);
	}
	
	@PostMapping
	public ResponseEntity<?> create(@RequestBody AuthorDto dto) {
		AuthorDto result = svc.createAuthor(dto);
		return ResponseEntity
				.created(URI.create("/authors"))
				.body(result);
	}
	
	@PutMapping
	public ResponseEntity<?> update(@RequestBody AuthorDto dto) {
		AuthorDto result = svc.updateAuthor(dto);
		return ResponseEntity
				.accepted()
				.body(result);
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<?> delete(@PathVariable("id") int id) {
		svc.deleteAuthor(id);
		return ResponseEntity
				.noContent()
				.build();
	}
}