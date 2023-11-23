# Everything Bookmark Backend Server

---

## About the project

This is the backend server for my Everything Bookmark project using `Java` and `Spring Boot`.

Everything Bookmark is my own application to *(you guessed it)* bookmark anything and everything 
that I want. For example, I can bookmark books that I read and my thoughts on the book, 
my rating for the book and the date that I finish reading the book. While my application 
only supports books now, I do plan on expanding it to have more categories such as songs and websites.

As I have previous experience in building a server layer using `Node.js` and `Express.js`, I thought 
that this would be the perfect learning opportunity for me to familiarise myself with `Java`, 
and also compare the similarities/differences between frameworks.

### Developed with 

- `Java 17` using `Spring Boot` and `Maven`.
- `MongoDB` as data storage.

---

## Getting Started

### Prerequisite

You will need to have mongodb-community running first. For Mac, run:
```
brew services start mongodb-community
```

### Get the code

```
git clone https://github.com/Saphly/everything-bookmark.git
```

### Install dependencies 
```
mvn clean dependency:copy-dependencies
```

### Run the server
```
mvn spring-boot:run
```

### Run tests
```
mvn test
```

## API Endpoints

`GET` `/bookmarks`
Returns all bookmarks. For example,
```
[
  {
    "_id": "655f850c7f46a3092a7e8c06",
    "tag": "Book",
    "title": "Book title",
    "url": null,
    "dateAdded": "2023-11-23T16:59:56.396Z",
    "dateStarted": null,
    "dateFinished": null,
    "notes": "This book is awesome!",
    "rating": 1
  }
]
```

`GET` `/bookmarks/{_id}` Find specific bookmark by id. Returns status code `404` if invalid id is provided. 
```
{
    "_id": "655f850c7f46a3092a7e8c06",
    "tag": "Book",
    "title": "Book title",
    "url": null,
    "dateAdded": "2023-11-23T16:59:56.396Z",
    "dateStarted": null,
    "dateFinished": null,
    "notes": "This book is awesome!",
    "rating": 1
 }
```
`PUSH` `/bookmarks` To save a new bookmark. Returns status code `400` if any of the following fields are missing. 
```
You must have at least the following fields in the request body
{
    "tag": "Book",
    "title": "Title",
    "rating": 3,
    "dateAdded": "ISOtimestamp"
}
```

`PUT` `/bookmarks/{_id}` Use bookmark `id` to update the specific bookmark. If a bookmark with that `id`
is not found, it will return a status of `404`.
```
You must have at least the following fields in the request body
{
    "tag": "Book",
    "title": "Title",
    "rating": 3,
    "dateAdded": "ISOtimestamp"
}
```


## Reflection 

Whilst this is just a simple backend server, I have actually spent a good amount of time on this,
and it has been a great learning experience. As I had little experience in `Java` and `Spring Boot`, as well as 
using a new integrated development environment (IDE) `IntelliJ IDEA`, there was a steep learning curve for me.

The basic CRUD functionalities were all tested (not rigorously) and I achieved a test coverage of 96% for the whole application, which
was generated using the `JaCoCo` plugin. 

I have encountered several issues along the way, some of which are not resolved as of writing this. Click [Here](IssuesLog.md) 
for the log of issues encountered.

