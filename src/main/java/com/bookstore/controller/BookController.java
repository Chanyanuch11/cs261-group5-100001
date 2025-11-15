package com.bookstore.controller;

import com.bookstore.model.Book;
import com.bookstore.repo.BookRepository;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookRepository bookRepository;

    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    // ✅ Get all books
    @GetMapping
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    // ✅ Add a new book
    @PostMapping
    public Book addBook(@RequestBody Book book) {
        return bookRepository.save(book);
    }

    // ✅ Search books by title

 // ✅ Unified Search: keyword + category
    @GetMapping("/search")
    public List<Book> searchBooks(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String category
    ) {
        if ((keyword == null || keyword.isBlank()) && (category == null || category.isBlank())) {
            return bookRepository.findAll(); // no filters
        }
        if (keyword != null && !keyword.isBlank() && category != null && !category.isBlank()) {
            return bookRepository.findByTitleContainingIgnoreCaseAndCategoryContainingIgnoreCase(keyword, category);
        }
        if (keyword != null && !keyword.isBlank()) {
            return bookRepository.findByTitleContainingIgnoreCase(keyword);
        }
        return bookRepository.findByCategoryContainingIgnoreCase(category);
    }

    @GetMapping("/search/category")
    public List<Book> searchByCategory(@RequestParam String category) {
        return bookRepository.findByCategoryContainingIgnoreCase(category);
    }
 // ✅ Get all distinct categories
 // ✅ Get all distinct categories directly from DB
    @GetMapping("/categories")
    public List<String> getCategories() {
        return bookRepository.findDistinctCategories();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        return bookRepository.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
}
 
}
