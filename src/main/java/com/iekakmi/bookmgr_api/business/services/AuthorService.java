package com.iekakmi.bookmgr_api.business.services;

import com.iekakmi.bookmgr_api.business.exceptions.BusinessLayerException;
import com.iekakmi.bookmgr_api.domain.repositories.*;
import com.iekakmi.bookmgr_api.domain.entities.*;
import com.iekakmi.bookmgr_api.business.dtos.*;
import jakarta.validation.ConstraintViolation;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import jakarta.validation.Validator;
import java.util.*;

@Service
public class AuthorService {
	private final AuthorRepository repository;
	private final Validator validator;

	public AuthorService(AuthorRepository repository, Validator validator) {
		this.repository = repository;
		this.validator = validator;
	}

	public List<AuthorDto> getAuthors() {
        return repository.findAll().stream()
                .map(AuthorDto::new)
                .toList();
	}
	
	public AuthorDto getAuthorById(int id) throws BusinessLayerException {
		Author entity = repository
				.findById(id)
				.orElseThrow(() -> new BusinessLayerException(String.format("Author not found with ID: %d", id)));
        return new AuthorDto(entity);
	}
	
	public List<BookDto> getBooksByAuthorId(int authorId) throws BusinessLayerException {
        Author author = repository
        		.findById(authorId)
				.orElseThrow(() -> new BusinessLayerException(String.format("Author not found with ID: %d", authorId)));
        return author.getBooks().stream().map(BookDto::new).toList();
    }
	
	@Transactional
	public AuthorDto createAuthor(AuthorDto dto) throws BusinessLayerException {
		List<String> validationErrors = validator.validate(dto).stream().map(ConstraintViolation::getMessage).toList();
		if (!validationErrors.isEmpty()) {
			throw new BusinessLayerException(String.join(",\n", validationErrors));
		}
		Author dbEntity = repository.findById(dto.getId()).orElse(new Author());
		dbEntity.setName(dto.getName());
		dbEntity.setNationality(dto.getNationality());
		dbEntity.setDateOfBirth(dto.getDateOfBirth());
		dbEntity = repository.save(dbEntity);
		return new AuthorDto(dbEntity);
	}
	
	@Transactional
	public AuthorDto updateAuthor(AuthorDto dto) throws BusinessLayerException {
		List<String> validationErrors = validator.validate(dto).stream().map(ConstraintViolation::getMessage).toList();
		if (!validationErrors.isEmpty()) {
			throw new BusinessLayerException(String.join(",", validationErrors));
		}
		Author dbEntity = repository
				.findById(dto.getId())
				.orElseThrow(() -> new BusinessLayerException(String.format("Author not found with ID: %d", dto.getId())));
		dbEntity.setId(dto.getId());
		dbEntity.setName(dto.getName());
		dbEntity.setNationality(dto.getNationality());
		dbEntity.setDateOfBirth(dto.getDateOfBirth());
		dbEntity = repository.save(dbEntity);
		return new AuthorDto(dbEntity);
	}
	
	@Transactional
	public void deleteAuthor(int id) throws BusinessLayerException {
		Author entity = repository
				.findById(id)
				.orElseThrow(() -> new BusinessLayerException(String.format("Author not found with ID: %d", id)));
		List<String> attachedIsbns = entity.getBooks().stream().map(Book::getIsbn).toList();
		if (!attachedIsbns.isEmpty()) {
			throw new BusinessLayerException(String.format("Author with Id %d is set in book ISBN(s) [%s]", id, String.join(",", attachedIsbns)));
		}
		repository.deleteById(id);
	}
}