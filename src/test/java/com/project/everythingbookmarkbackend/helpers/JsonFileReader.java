package com.project.everythingbookmarkbackend.helpers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.everythingbookmarkbackend.model.Bookmark;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JsonFileReader {

    public static List<Bookmark> fileToObjectList() {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            List<Bookmark> bookmarks = objectMapper.readValue(JsonFileReader.class.getResourceAsStream("/data.json"),
                    objectMapper.getTypeFactory().constructCollectionType(List.class, Bookmark.class));

            return bookmarks;
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
