package com.iekakmi.bookmgr_api.business.dtos;

import com.iekakmi.bookmgr_api.domain.entities.Author;
import com.iekakmi.bookmgr_api.domain.entities.Book;
import jakarta.validation.constraints.*;
import java.util.stream.Collectors;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
public class AuthorDto {
	private int id;
    
	@NotBlank(message = "Author name is mandatory")
    @Size(max = 100, message = "Name cannot exceed 100 characters")
	private String name;
	
	@Size(max = 100, message = "Nationality cannot exceed 100 characters")
    private String nationality;
	
	@Past(message = "Date of birth has to be a past date")
    private LocalDate dateOfBirth;

    private Set<String> bookIsbns;

    public AuthorDto(Author entity) {
    	setId(entity.getId());
    	setName(entity.getName());
    	setNationality(entity.getNationality());
    	setDateOfBirth(entity.getDateOfBirth());
    	setBookIsbns(entity.getBooks().stream().map(Book::getIsbn).collect(Collectors.toSet()));
    }
}