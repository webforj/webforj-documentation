---
title: Spring Data JPA
sidebar_position: 20
_i18n_hash: f97a3247bcdeadf193a049bdb6d1a3bc
---
Spring Data JPA es el estándar de facto para el acceso a datos en aplicaciones Spring, proporcionando abstracciones de repositorios, métodos de consulta y especificaciones para consultas complejas. El adaptador `SpringDataRepository` de webforJ conecta los repositorios de Spring Data con los componentes de la interfaz de usuario de webforJ, lo que te permite vincular entidades JPA directamente a los componentes de la interfaz de usuario, implementar filtrado dinámico con Especificaciones de JPA y gestionar la paginación.

El adaptador detecta qué interfaces de Spring Data implementa tu repositorio, ya sea `CrudRepository`, `PagingAndSortingRepository` o `JpaSpecificationExecutor`, y proporciona automáticamente las características correspondientes en tu interfaz de usuario. Esto significa que tus repositorios de Spring Data existentes funcionan con los componentes de webforJ sin necesidad de modificación, manteniendo la seguridad de tipos y utilizando tu modelo de dominio existente.

:::tip[Aprende más sobre Spring Data JPA]
Para una comprensión integral de las características y métodos de consulta de Spring Data JPA, consulta la [documentación de Spring Data JPA](https://docs.spring.io/spring-data/jpa/reference/).
:::

## Uso de SpringDataRepository {#using-springdatarepository}

La clase `SpringDataRepository` conecta los repositorios de Spring Data JPA con la interfaz Repository de webforJ, haciéndolos compatibles con componentes de la interfaz de usuario como [`Table`](../../components/table/overview) mientras conserva todas las características de Spring Data.

```java
// Tu repositorio de Spring Data
@Autowired
private PersonRepository personRepository;

// Envuelve con SpringDataRepository
SpringDataRepository<Person, Long> adapter = new SpringDataRepository<>(personRepository);

// Usar con la Tabla de webforJ
Table<Person> table = new Table<>();
table.setRepository(adapter);
```

### Detección de interfaces {#interface-detection}

Los repositorios de Spring Data utilizan la herencia de interfaces para agregar capacidades. Comienzas con operaciones CRUD básicas y agregas interfaces para características como paginación o especificaciones:

```java
// Solo CRUD básico
public interface CustomerRepository extends CrudRepository<Customer, Long> {}

// CRUD + Paginación + Clasificación
public interface CustomerRepository extends PagingAndSortingRepository<Customer, Long> {}

// Repositorio de características completas
public interface CustomerRepository extends JpaRepository<Customer, Long>, 
                                           JpaSpecificationExecutor<Customer> {}
```

`SpringDataRepository` examina qué interfaces implementa tu repositorio y adapta su comportamiento en consecuencia. Si tu repositorio admite paginación, el adaptador habilita consultas paginadas. Si implementa `JpaSpecificationExecutor`, puedes utilizar filtrado dinámico con especificaciones.

### Capacidades del repositorio {#repository-capabilities}

Cada interfaz de Spring Data agrega capacidades específicas que `SpringDataRepository` puede utilizar:

- **CrudRepository** - Operaciones básicas: `save`, `delete`, `findById`, `findAll`
- **PagingAndSortingRepository** - Agrega consultas paginadas y clasificación
- **JpaRepository** - Combina CRUD y paginación/ordenación con operaciones en lote
- **JpaSpecificationExecutor** - Consultas dinámicas utilizando Especificaciones JPA

### Creación de un repositorio de Spring Data {#creating-a-spring-data-repository}

Para una máxima compatibilidad con los componentes de webforJ, crea repositorios que implementen tanto `JpaRepository` como `JpaSpecificationExecutor`:

```java title="PersonRepository.java"
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository
    extends JpaRepository<Person, Long>,
            JpaSpecificationExecutor<Person> {
    // Los métodos de consulta personalizados pueden ir aquí
}
```

Esta combinación proporciona:

- Operaciones de búsqueda por ID
- Paginación con rendimiento óptimo
- Capacidades de clasificación
- Filtrado mediante la Java Persistence API Specification
- Operaciones de conteo con y sin filtros

## Trabajando con `Table` {#working-with-table}

El siguiente ejemplo utiliza un `PersonRepository` que extiende `JpaRepository` y `JpaSpecificationExecutor`. Esta combinación permite la clasificación a través de los encabezados de columna y el filtrado dinámico con especificaciones.

```java title="TableView.java"
@Route
public class TableView extends Composite<Div> {
  private SpringDataRepository<Person, Long> repository;
  private Table<Person> table = new Table<>();

  public TableView(PersonRepository personRepository) {
    // Envuelve el repositorio de Spring Data para webforJ
    repository = new SpringDataRepository<>(personRepository);
    
    // Conectar a la tabla
    table.setRepository(repository);
    
    // Definir columnas
    table.addColumn("name", Person::getFullName)
          .setPropertyName("firstName"); // Ordenar por la propiedad JPA real
    table.addColumn("email", Person::getEmail);
    table.addColumn("age", person -> 
          person.getAge() != null ? person.getAge().toString() : "");
    table.addColumn("city", Person::getCity);
    table.addColumn("profession", Person::getProfession);
    
    // Habilitar clasificación
    table.getColumns().forEach(column -> column.setSortable(true));
  }
}
```

El método `setPropertyName()` es importante para la clasificación: indica al adaptador qué propiedad JPA utilizar en la cláusula `ORDER BY` al ordenar por esa columna. Sin él, la clasificación no funcionará para propiedades calculadas como `getFullName()`.

## Filtrado con especificaciones JPA {#filtering-with-jpa-specifications}

`SpringDataRepository` utiliza Especificaciones JPA para consultas dinámicas y se aplican a las operaciones `findBy` y `count` del repositorio.

:::tip[Aprende más sobre el filtrado]
Para entender cómo funciona el filtrado con los repositorios de webforJ, incluidos los filtros base y la composición de filtros, consulta la [documentación del Repositorio](../../advanced/repository/overview).
:::

```java
// Filtrar por ciudad
Specification<Person> cityFilter = (root, query, cb) -> 
    cb.equal(root.get("city"), "New York");
repository.setFilter(cityFilter);

// Condiciones múltiples
Specification<Person> complexFilter = (root, query, cb) -> 
    cb.and(
        cb.equal(root.get("profession"), "Engineer"),
        cb.greaterThanOrEqualTo(root.get("age"), 25)
    );
repository.setFilter(complexFilter);

// Limpiar filtro
repository.setFilter(null);
```
