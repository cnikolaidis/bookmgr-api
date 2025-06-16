-- Δημιουργία βάσης δεδομένων
CREATE DATABASE IF NOT EXISTS book_management_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE book_management_db;

-- Πίνακας Συγγραφέων
CREATE TABLE authors (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    nationality VARCHAR(100),
    date_of_birth DATE
);

-- Πίνακας Βιβλίων
CREATE TABLE books (
    isbn VARCHAR(20) PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    category VARCHAR(100),
    publication_year INT
);

-- Πίνακας Συσχέτισης Βιβλίων-Συγγραφέων (Many-to-Many)
CREATE TABLE book_author (
    book_isbn VARCHAR(20),
    author_id INT,
    PRIMARY KEY (book_isbn, author_id),
    FOREIGN KEY (book_isbn) REFERENCES books(isbn) ON DELETE CASCADE,
    FOREIGN KEY (author_id) REFERENCES authors(id) ON DELETE CASCADE
);