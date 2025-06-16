package com.iekakmi.bookmgr_api.api.controllers;

import com.iekakmi.bookmgr_api.business.exceptions.BusinessLayerException;
import com.iekakmi.bookmgr_api.business.services.AuthorService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import com.iekakmi.bookmgr_api.business.dtos.*;
import java.util.List;

@RestController
@RequestMapping("authors")
public class AuthorsController {
	private final AuthorService svc;

	public AuthorsController(AuthorService svc) {
		this.svc = svc;
	}

	@GetMapping
	public ResponseEntity<?> get() {
		try {
			List<AuthorDto> result = svc.getAuthors();
			return ResponseEntity
					.ok(result);
		}
		catch (Exception x) {
			return ResponseEntity
					.internalServerError()
					.body(x);
		}
	}
	
	@GetMapping("{id}")
	public ResponseEntity<?> getById(@PathVariable("id") int id) {
		try {
			AuthorDto result = svc.getAuthorById(id);
			return ResponseEntity
					.ok(result);
		}
		catch (Exception x) {
			return ResponseEntity
					.internalServerError()
					.body(x);
		}
	}
	
	@GetMapping("{id}/books")
	public ResponseEntity<?> getBooksByAuthor(@PathVariable("id") int id) {
		try {
			List<BookDto> result = svc.getBooksByAuthorId(id);
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
	public ResponseEntity<?> create(@RequestBody AuthorDto dto) {
		try {
			AuthorDto result = svc.createAuthor(dto);
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
	public ResponseEntity<?> update(@RequestBody AuthorDto dto) {
		try {
			AuthorDto result = svc.updateAuthor(dto);
			return ResponseEntity
					.ok(result);
		}
		catch (BusinessLayerException blx) {
			return ResponseEntity
					.internalServerError()
					.body(blx);
		}
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<?> delete(@PathVariable("id") int id) {
		try {
			svc.deleteAuthor(id);
			return ResponseEntity
					.ok(id);
		}
		catch (BusinessLayerException blx) {
			return ResponseEntity
					.internalServerError()
					.body(blx);
		}
	}
}