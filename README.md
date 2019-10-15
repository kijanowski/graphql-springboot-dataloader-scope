# Spring Boot based GraphQL project showing different DataLoader scopes

This application is part of [this blog post](https://blog.softwaremill.com/graphql-dataloader-in-spring-boot-singleton-or-request-scoped-16699436f680) about the possible scopes of the GraphQL's DataLoader pattern.

Build:
```
$ ./gradlew clean build
```
Run to test the `global` scope:
```
$ java -jar build/libs/graphql-springboot-scope-0.0.1-SNAPSHOT.jar --spring.profiles.active=global
```

Run to test the `request` scope:
```
$ java -jar build/libs/graphql-springboot-scope-0.0.1-SNAPSHOT.jar --spring.profiles.active=request
```

GraphiQL, an in-browser IDE for exploring GraphQL, is embedded through `graphiql-spring-boot-starter`
and available at `http://localhost:8081/graphiql`.

Sample query to be run:

```
{
  animals {
    name
    color
    countries {
      name
    }
  }
}
```
