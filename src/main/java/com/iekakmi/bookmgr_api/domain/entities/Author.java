package com.iekakmi.bookmgr_api.domain.entities;

import jakarta.persistence.*;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;
import java.util.*;

@Getter
@Entity
@Table(name = "authors")
public class Author {

    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Setter
    @Column(nullable = false)
    private String name;

    @Setter
    private String nationality;

    @Setter
    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @ManyToMany(mappedBy = "authors")
    private Set<Book> books = new HashSet<>();
}