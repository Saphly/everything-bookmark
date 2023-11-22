package com.project.everythingbookmarkbackend;

import com.project.everythingbookmarkbackend.model.Bookmark;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

@Configuration
public class TestMongoConfig {

    private static final String collectionName = "bookmarks";

    @Bean
    public static MongoTemplate mongoTemplate() {
        return new MongoTemplate(new SimpleMongoClientDatabaseFactory("mongodb://127.0.0.1:27017/everythingBookmarkTest"));
    }

    public static void clearCollection() {
        System.out.println("Deleting existing bookmarks");
        mongoTemplate().remove(new Query(), collectionName);
    }

    public static void repopulateCollection(List<Bookmark> bookmarks) {
        System.out.println("Creating bookmarks");
        System.out.println("Inserting bookmarks");
        mongoTemplate().insert(bookmarks, collectionName);
    }
}
