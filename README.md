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
- `MongoDB` as data persistence layer.

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

