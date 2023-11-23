package com.project.everythingbookmarkbackend.controllers;

import com.project.everythingbookmarkbackend.model.Bookmark;
import com.project.everythingbookmarkbackend.services.BookmarkServices;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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

    @GetMapping(value = "/bookmarks/{_id}")
    public Optional<Bookmark> getBookmarkById(@PathVariable String _id) { return bookmarkServices.getBookmarkById(_id);}

    @PostMapping(value = "/bookmarks")
    @ResponseStatus(HttpStatus.CREATED)
    public Bookmark addBookmark(@Valid @RequestBody Bookmark bookmark) {
        return bookmarkServices.addBookmark(bookmark);
    }

    @PutMapping(value = "/bookmarks/{_id}")
    public Bookmark updateBookmark(@PathVariable String _id, @Valid @RequestBody Bookmark bookmark) {
        return bookmarkServices.updateBookmark(_id, bookmark);
    }

    @PostMapping("/bookmarks/{_id}")
    public void deleteBookmark(@PathVariable String _id) {
        bookmarkServices.deleteBookmark(_id);
    }
}
