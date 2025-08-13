# Food Investigator 

Food Investigator is a multi-access IT system that uses a database of functional food products developed by the Center for Functional Food Research ([Centrum Badań Nad Żywnością Funkcjonalną CBŻF](https://f-food.pl/)) . The system serves as an assistant supporting the selection of food products based on a user-personalized dietary profile.

## System Architecture
Food Investigator system consists of two layers:
- Data Layer,
- Business Logic Layer.

## Technologies
- Docker,
- PostgreSQL,
- Spring Boot Framework.

## Data Layer
The compose.yml file was used to create a container instance with RDBMS (Relational Database Management System) PostgreSQL. 
A Functional Food database (`functionalfood`) was created in the RDBMS. There are four additional database roles created in RDBMS:
- `admin` role (role which represents the functionalfood database owner),
- `authenticationmodule` role (used for authentication users),
- `accountmodule` role (used for accounts management),
- `functionalfoodmodule` role (used for managing functional food products and dietary profiles/templates).

Each role has a unique set of permissions (eg. `READ`, `CREATE`, `UPDATE`) to provide data access security.

##  Business Logic Layer
The business logic layer is implemented as web app which provide REST API. The web app consists of three layer:
- Controllers layer,
- Services layer,
- Repositories layer.

#### Connection Pools
The web application uses HikariCP connection pools to improve performance. Each connection pool is associated with a specific database role.

#### ORM Object-Relational Mapping 
JPA annotation were used to map entities to database tables. 

#### Transactional processing
The business logic requires that a set of specific operations be treated as a single logical unit. To achieve this, the application uses transactional processing. 

## Running
```console
mvn spring-boot:run
```
