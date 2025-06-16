package com.iekakmi.bookmgr_api.business.services;

import com.iekakmi.bookmgr_api.business.exceptions.BusinessLayerException;
import org.springframework.beans.factory.annotation.Autowired;
import com.iekakmi.bookmgr_api.domain.repositories.*;
import com.iekakmi.bookmgr_api.domain.entities.*;
import com.iekakmi.bookmgr_api.business.dtos.*;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import jakarta.validation.Validator;
import java.util.stream.*;
import java.util.*;

@Service
public class AuthorService {
	@Autowired
	private AuthorRepository repository;
	
	@Autowired
	private Validator validator;
	
	public List<AuthorDto> getAuthors() {
		List<AuthorDto> dtos = StreamSupport
				.stream(repository.findAll().spliterator(), false)
				.map(x -> new AuthorDto(x))
				.toList();
		return dtos;
	}
	
	public AuthorDto getAuthorById(int id) throws BusinessLayerException {
		Author entity = repository
				.findById(id)
				.orElseThrow(() -> new BusinessLayerException(String.format("Author not found with ID: %d", id)));
		AuthorDto dto = new AuthorDto(entity);
		return dto;
	}
	
	public List<BookDto> getBooksByAuthorId(int authorId) throws BusinessLayerException {
        Author author = repository
        		.findById(authorId)
				.orElseThrow(() -> new BusinessLayerException(String.format("Author not found with ID: %d", authorId)));
        List<BookDto> books = author.getBooks().stream().map(x -> new BookDto(x)).toList();
        return books;
    }
	
	@Transactional
	public AuthorDto createAuthor(AuthorDto dto) throws BusinessLayerException {
		List<String> validationErrors = validator.validate(dto).stream().map(x -> x.getMessage()).toList();
		if (validationErrors.size() > 0) {
			throw new BusinessLayerException(String.join(",", validationErrors));
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
		List<String> validationErrors = validator.validate(dto).stream().map(x -> x.getMessage()).toList();
		if (validationErrors.size() > 0) {
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
		List<String> attachedIsbns = entity.getBooks().stream().map(b -> b.getIsbn()).toList();
		if (attachedIsbns.size() > 0) {
			throw new BusinessLayerException(String.format("Author with Id %d is set in book ISBN(s) [%s]", id, String.join(",", attachedIsbns)));
		}
		repository.deleteById(id);
	}
}