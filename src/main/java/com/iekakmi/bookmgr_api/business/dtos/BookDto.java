package com.iekakmi.bookmgr_api.business.dtos;

import com.iekakmi.bookmgr_api.domain.entities.Book;
import jakarta.validation.constraints.*;
import java.util.stream.Collectors;
import java.util.Set;

public class BookDto {
	
	@NotBlank(message = "ISBN is mandatory")
    @Size(max = 20, message = "ISBN cannot exceed 20 characters")
	private String isbn;
	
	@NotBlank(message = "Title is mandatory")
    @Size(max = 255, message = "Title cannot exceed 255 characters")
    private String title;
	
	@Size(max = 100, message = "Category cannot exceed 100 characters")
    private String category;
	
	@NotNull(message = "Publication year is mandatory")
    private Integer publicationYear;
	
    private Set<AuthorDto> authors;
    
    private Set<Integer> authorIds;
    
    public BookDto() {}
    
    public BookDto(Book entity) {
    	setIsbn(entity.getIsbn());
    	setTitle(entity.getTitle());
    	setCategory(entity.getCategory());
    	setPublicationYear(entity.getPublicationYear());
    	setAuthors(entity.getAuthors().stream().map(x -> new AuthorDto(x)).collect(Collectors.toSet()));
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(Integer publicationYear) {
        this.publicationYear = publicationYear;
    }

    public Set<AuthorDto> getAuthors() {
        return authors;
    }

    public void setAuthors(Set<AuthorDto> authors) {
        this.authors = authors;
    }
    
    public Set<Integer> getAuthorIds() {
        return authorIds;
    }

    public void setAuthorIds(Set<Integer> authorIds) {
        this.authorIds = authorIds;
    }
    
    public static Book getEntity(BookDto dto, boolean newEntity) {
    	Book entity = new Book();
    	entity.setIsbn(dto.getIsbn());
    	entity.setTitle(dto.getTitle());
    	entity.setCategory(dto.getCategory());
    	entity.setPublicationYear(dto.getPublicationYear());
    	return entity;
    }
}