## Issue Log

### Issue #3

After testing my APIs in Postman, I realised that the error message that I set in the Bookmark model was not showing. 

This was an easy fix. I had to add the following server settings in `application.properties`
```
server.error.include-message=always
server.error.include-binding-errors=always
```

### Issue #2

I was setting up a test to assert that the data returned from the GET request contains the right data. My Bookmark model was initially 
setup have a field called `Rating` with `byte` as the data type, as ratings are only supposed to contain the numbers 1-5. 

The last line of my test case was `.andExpect(jsonPath("$.rating).value(bookmarks.get(0).getTitle())`, and my result from running the test was 
```
Expected: <1>
Was: <1b>
```

My guess is that the error occurred somewhere along the lines of serializing/deserializing the data. This is another issue that I did not manage 
to find a solution for, so I used a workaround for now and changed the field to an `int` type. 

### Issue #1

After setting up the test files, I tried running a simple test. However, I ran into an error 
`org.springframework.beans.factory.BeanCreationException: Error creating bean with name 'MyController'`. 

I'm going to be honest, I'm not sure what the underlying issue was, but adding `@RunWith(SpringRunner.class)` solved the issue. 
From what I understood after Googling (links below), this annotation provides a bridge between Spring Boot test features and JUnit.

src: <a href="https://stackoverflow.com/questions/58901288/springrunner-vs-springboottest">Stack Overflow</a>, <a href="https://www.baeldung.com/spring-boot-testing">Baeldung</a>