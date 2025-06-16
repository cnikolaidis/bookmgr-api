package com.iekakmi.bookmgr_api.business.services;

import com.iekakmi.bookmgr_api.business.exceptions.BusinessLayerException;
import com.iekakmi.bookmgr_api.domain.repositories.*;
import com.iekakmi.bookmgr_api.domain.entities.*;
import com.iekakmi.bookmgr_api.business.dtos.*;
import jakarta.validation.ConstraintViolation;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import jakarta.validation.Validator;
import java.util.stream.*;
import java.util.*;

@Service
public class BookService {
	private final BookRepository repository;
	private final AuthorRepository authorRepository;
	private final Validator validator;

	public BookService(BookRepository repository, AuthorRepository authorRepository, Validator validator) {
		this.repository = repository;
		this.authorRepository = authorRepository;
		this.validator = validator;
	}

	public List<BookDto> getBooks() {
        return repository.findAll().stream()
                .map(BookDto::new)
                .toList();
	}
	
	public BookDto getBookByIsbn(String isbn) throws BusinessLayerException {
		Book entity = repository
				.findById(isbn)
				.orElseThrow(() -> new BusinessLayerException(String.format("Book not found with ISBN: %s", isbn)));
        return new BookDto(entity);
	}
	
	public List<AuthorDto> getAuthorsByBookIsbn(String isbn) throws BusinessLayerException {
        Book entity = repository
        		.findById(isbn)
				.orElseThrow(() -> new BusinessLayerException(String.format("Book not found with ISBN: %s", isbn)));
        return entity.getAuthors().stream().map(AuthorDto::new).toList();
    }
	
	@Transactional
	public BookDto createBook(BookDto dto) throws BusinessLayerException {
		return saveBook(dto);
	}
	
	@Transactional
	public BookDto updateBook(BookDto dto) throws BusinessLayerException {
		return saveBook(dto);
	}
	
	@Transactional
	public void deleteBook(String isbn) throws BusinessLayerException {
		Book entity = repository
				.findById(isbn)
				.orElseThrow(() -> new BusinessLayerException(String.format("Book not found with ISBN: %s", isbn)));
		List<String> attachedAuthors = entity.getAuthors().stream().map(b -> String.format("%d", b.getId())).toList();
		if (!attachedAuthors.isEmpty()) {
			throw new BusinessLayerException(String.format("Book with ISBN %s is set for Author id(s) [%s]", isbn, String.join(",", attachedAuthors)));
		}
		repository.deleteById(isbn);
	}

	@Transactional
	public BookDto assignAuthorsToBook(String isbn, Set<Integer> authorIds) throws BusinessLayerException {
        Book book = repository
        		.findById(isbn)
				.orElseThrow(() -> new BusinessLayerException(String.format("Book not found with ISBN: %s", isbn)));

        Set<Author> authors = new HashSet<>();
        for (int id : authorIds) {
            Author author = authorRepository
            		.findById(id)
                    .orElseThrow(() -> new BusinessLayerException(String.format("Author not found with ID: %d", id)));
            authors.add(author);
        }

        book.setAuthors(authors);
        repository.save(book);
        return new BookDto(book);
    }
	
	private BookDto saveBook(BookDto dto) throws BusinessLayerException {
		List<String> validationErrors = validator.validate(dto).stream().map(ConstraintViolation::getMessage).toList();
		if (!validationErrors.isEmpty()) {
			throw new BusinessLayerException(String.join(",", validationErrors));
		}
		Book dbEntity = repository.findById(dto.getIsbn()).orElse(new Book());
		dbEntity.setIsbn(dto.getIsbn());
		dbEntity.setTitle(dto.getTitle());
		dbEntity.setPublicationYear(dto.getPublicationYear());
		dbEntity.setCategory(dto.getCategory());
		Set<Author> authors = dto.getAuthorIds().stream()
				.map(id -> authorRepository.findById(id).orElse(null))
				.filter(Objects::nonNull)
				.collect(Collectors.toSet());
		dbEntity.setAuthors(authors);
		dbEntity = repository.save(dbEntity);
		return new BookDto(dbEntity);
	}
}