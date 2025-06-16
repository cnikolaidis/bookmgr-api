package com.iekakmi.bookmgr_api.domain.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.*;

@Setter
@Getter
@Entity
@Table(name = "books")
public class Book {

    @Id
    private String isbn;

    @Column(nullable = false)
    private String title;

    private String category;

    @Column(name = "publication_year")
    private Integer publicationYear;

    @ManyToMany
    @JoinTable(
        name = "book_author",
        joinColumns = @JoinColumn(name = "book_isbn"),
        inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    private Set<Author> authors = new HashSet<>();
}