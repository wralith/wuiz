# Wuiz

Quiz Application! 🧠\
Around %80-%90 Test Coverage, Isn't it sweet spot? 🍭

What this app does? Create and solve quizzes and get lovely results Check
[endpoints](#endpoints) for more.

## Development

Create Postgres Database in Docker

```bash
docker run --name wuiz --rm -d -p 5432:5432 \
    -e POSTGRES_USER=wuiz \
    -e POSTGRES_PASSWORD=secret \
    -e POSTGRES_DB=wuiz \
    postgres:alpine
```

Start Spring Application or Tests

```bash
cd server

./gradlew build -t  # To watch files to restart application on change
./gradlew bootRun   # Start Spring Boot application

# To run tests
./gradlew test
```

## Techs

- Java and bunch of technologies from Spring Boot umbrella
- GitHub Actions, run tests on push because why not?
- OpenAPI specs and Swagger UI for API documentation
- Postgres

## Endpoints

![endpoints](/docs/endpoints.png)

## Todos

- [ ] Few annotations to clean
- [ ] Refactor to follow Java best practices... _I need to do few readings for
      that though_
- [ ] Exception handling and better error messages
- [ ] Dockerize App and maybe docker-compose with Postgres for better
      development environment
- [ ] Frontend application maybe?
- [ ] Better result response

- [ ] Users
  - [ ] Authentication
  - [ ] Role to create quizzes

## Notes for Future

- Postgres is good but models seems like they would be great fit for document
  database, maybe I can try CouchBase or something...
- I like folder structure as flat as possible but I don't know something seems
  off.
- I believe I can break this into microservices, after I get comfortable on
  building those jars, man java tooling is not the best... At least I will break
  things into more modular way, Result and Quiz should not be coupled I guess.
- I don't know if I should use H2 in tests, what do you think Java people let me
  know.
