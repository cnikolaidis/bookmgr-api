package com.iekakmi.bookmgr_api.business.dtos;

import com.iekakmi.bookmgr_api.domain.entities.Book;
import jakarta.validation.constraints.*;
import java.util.stream.Collectors;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
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

    public BookDto(Book entity) {
    	setIsbn(entity.getIsbn());
    	setTitle(entity.getTitle());
    	setCategory(entity.getCategory());
    	setPublicationYear(entity.getPublicationYear());
    	setAuthors(entity.getAuthors().stream().map(AuthorDto::new).collect(Collectors.toSet()));
    }
}