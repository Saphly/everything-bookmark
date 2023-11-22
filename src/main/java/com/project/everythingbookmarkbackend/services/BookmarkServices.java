package com.project.everythingbookmarkbackend.services;

import com.project.everythingbookmarkbackend.model.Bookmark;
import com.project.everythingbookmarkbackend.repositories.BookmarkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class BookmarkServices {
    @Autowired
    private BookmarkRepository bookmarkRepository;

    public List<Bookmark> getAllBookmarks() {
        return bookmarkRepository.findAll();
    }

    public Bookmark addBookmark(Bookmark bookmark) {
        return bookmarkRepository.save(bookmark);
    }

    public Bookmark updateBookmark(String _id, Bookmark bookmark) {
        if(!bookmarkRepository.existsById(_id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "That bookmark could not be found");
        };

        return bookmarkRepository.save(bookmark);
    }
}
