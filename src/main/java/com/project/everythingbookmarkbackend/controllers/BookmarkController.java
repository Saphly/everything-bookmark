package com.project.everythingbookmarkbackend.controllers;

import com.project.everythingbookmarkbackend.model.Bookmark;
import com.project.everythingbookmarkbackend.services.BookmarkServices;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Book;
import java.util.List;

@RestController
@Validated
public class BookmarkController {
    private final BookmarkServices bookmarkServices;

    @Autowired
    public BookmarkController(BookmarkServices bookmarkServices) {
        this.bookmarkServices = bookmarkServices;
    }


    @GetMapping(value = "/bookmarks")
    public List<Bookmark> getAllBookmarks() {
        return bookmarkServices.getAllBookmarks();
    }

    @PostMapping(value = "/bookmarks")
    @ResponseStatus(HttpStatus.CREATED)
    public Bookmark addBookmark(@Valid @RequestBody Bookmark bookmark) {
        return bookmarkServices.addBookmark(bookmark);
    }

    @PutMapping(value = "/bookmarks/{_id}")
    public Bookmark updateBookmark(@PathVariable String _id, @Valid @RequestBody Bookmark bookmark) {
        return bookmarkServices.updateBookmark(_id, bookmark);
    }
}
