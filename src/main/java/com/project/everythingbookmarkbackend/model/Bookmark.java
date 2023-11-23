package com.project.everythingbookmarkbackend.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
//    @FutureOrPresent
    private String dateAdded;

    @JsonProperty("dateStarted")
    private String dateStarted;

    @JsonProperty("dateFinished")
    private String dateFinished;

    @JsonProperty("notes")
    private String notes;

    @JsonProperty("rating")
    @Min(value = 1, message = "Minimum value of Rating is 1")
    @Max(value = 5, message = "Maximum value of Rating is 5")
    private int rating;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
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

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

}
