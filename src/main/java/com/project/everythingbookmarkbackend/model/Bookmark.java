package com.project.everythingbookmarkbackend.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("bookmarks")
public class Bookmark {

    @Id
    @JsonProperty("_id")
    private String _id;

    @JsonProperty("tag")
    @NotEmpty(message = "Bookmark must have a tag")
    private String tag;

    @JsonProperty("title")
    @NotEmpty(message = "Bookmark must have a title")
    private String title;

    @JsonProperty("url")
    private String url;

    @JsonProperty("dateAdded")
    @NotEmpty(message = "Bookmark must have a date added")
    private String dateAdded;

    @JsonProperty("dateStarted")
    private String dateStarted;

    @JsonProperty("dateFinished")
    private String dateFinished;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @JsonProperty("notes")
    private String notes;

    @JsonProperty("rating")
    private byte rating;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getBookmarkTag() {
        return tag;
    }

    public void setBookmarkTag(String tag) {
        this.tag = tag;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(String dateAdded) {
        this.dateAdded = dateAdded;
    }

    public String getDateStarted() {
        return dateStarted;
    }

    public void setDateStarted(String dateStarted) {
        this.dateStarted = dateStarted;
    }

    public String getDateFinished() {
        return dateFinished;
    }

    public void setDateFinished(String dateFinished) {
        this.dateFinished = dateFinished;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public byte getRating() {
        return rating;
    }

    public void setRating(byte rating) {
        this.rating = rating;
    }

}
