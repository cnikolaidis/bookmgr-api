package com.iekakmi.bookmgr_api.business.dtos;

import com.iekakmi.bookmgr_api.domain.entities.Author;
import com.iekakmi.bookmgr_api.domain.entities.Book;
import jakarta.validation.constraints.*;
import java.util.stream.Collectors;
import java.time.LocalDate;
import java.util.Set;

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

    public AuthorDto() {}

    public AuthorDto(Author entity) {
    	setId(entity.getId());
    	setName(entity.getName());
    	setNationality(entity.getNationality());
    	setDateOfBirth(entity.getDateOfBirth());
    	setBookIsbns(entity.getBooks().stream().map(Book::getIsbn).collect(Collectors.toSet()));
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Set<String> getBookIsbns() {
        return bookIsbns;
    }

    public void setBookIsbns(Set<String> bookIsbns) {
        this.bookIsbns = bookIsbns;
    }
}