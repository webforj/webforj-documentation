---
title: Spring Data JPA
sidebar_position: 20
description: >-
  Adapt Spring Data JPA repositories to webforJ Table and Repository components
  with SpringDataRepository, pagination, sorting, and Specifications.
_i18n_hash: 04bda665f576fabc5db3284e9ec201e9
---
Spring Data JPA es el estándar de facto para el acceso a datos en aplicaciones Spring, proporcionando abstracciones de repositorio, métodos de consulta y especificaciones para consultas complejas. El adaptador `SpringDataRepository` de webforJ conecta los repositorios de Spring Data con los componentes de UI de webforJ, permitiéndote asociar entidades JPA directamente a componentes de UI, implementar filtros dinámicos con Especificaciones JPA, y manejar la paginación.

El adaptador detecta qué interfaces de Spring Data implementa tu repositorio - ya sea `CrudRepository`, `PagingAndSortingRepository`, o `JpaSpecificationExecutor` - y proporciona automáticamente las características correspondientes en tu UI. Esto significa que tus repositorios de Spring Data existentes funcionan con los componentes de webforJ sin modificación, manteniendo la seguridad de tipos y utilizando tu modelo de dominio existente.

:::tip[Aprende más sobre Spring Data JPA]
Para una comprensión completa de las características y métodos de consulta de Spring Data JPA, consulta la [documentación de Spring Data JPA](https://docs.spring.io/spring-data/jpa/reference/).
:::

## Usando SpringDataRepository {#using-springdatarepository}

La clase `SpringDataRepository` conecta los repositorios de Spring Data JPA con la interfaz Repository de webforJ, haciéndolos compatibles con componentes de UI como [`Table`](../../components/table/overview) mientras conserva todas las características de Spring Data.

```java
// Tu repositorio de Spring Data
@Autowired
private PersonRepository personRepository;

// Envuelve con SpringDataRepository
SpringDataRepository<Person, Long> adapter = new SpringDataRepository<>(personRepository);

// Usa con Table de webforJ
Table<Person> table = new Table<>();
table.setRepository(adapter);
```

### Detección de interfaces {#interface-detection}

Los repositorios de Spring Data utilizan la herencia de interfaces para agregar capacidades. Comienzas con operaciones CRUD básicas y añades interfaces para características como paginación o especificaciones:

```java
// Solo operaciones CRUD básicas
public interface CustomerRepository extends CrudRepository<Customer, Long> {}

// CRUD + Paginación + Ordenación
public interface CustomerRepository extends PagingAndSortingRepository<Customer, Long> {}

// Repositorio completo
public interface CustomerRepository extends JpaRepository<Customer, Long>, JpaSpecificationExecutor<Customer> {}
```

`SpringDataRepository` examina qué interfaces implementa tu repositorio y adapta su comportamiento en consecuencia. Si tu repositorio soporta paginación, el adaptador habilita consultas paginadas. Si implementa `JpaSpecificationExecutor`, puedes usar filtrado dinámico con especificaciones.

### Capacidades del repositorio {#repository-capabilities}

Cada interfaz de Spring Data agrega capacidades específicas que `SpringDataRepository` puede usar:

- **CrudRepository** - Operaciones básicas: `save`, `delete`, `findById`, `findAll`
- **PagingAndSortingRepository** - Agrega consultas paginadas y ordenación
- **JpaRepository** - Combina CRUD y paginación/ordenación con operaciones por lotes
- **JpaSpecificationExecutor** - Consultas dinámicas usando Especificaciones JPA

### Creando un repositorio de Spring Data {#creating-a-spring-data-repository}

Para máxima compatibilidad con los componentes de webforJ, crea repositorios que implementen tanto `JpaRepository` como `JpaSpecificationExecutor`:

```java title="PersonRepository.java"
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository
  extends JpaRepository<Person, Long>,
      JpaSpecificationExecutor<Person> {
  // Métodos de consulta personalizados pueden ir aquí
}
```

Esta combinación proporciona:

- Operaciones de búsqueda por ID
- Paginación con rendimiento óptimo
- Capacidades de ordenación
- Filtrado mediante Especificaciones de la API de Persistencia de Java
- Operaciones de conteo con y sin filtros

## Trabajando con `Table` {#working-with-table}

El siguiente ejemplo utiliza un `PersonRepository` que extiende `JpaRepository` y `JpaSpecificationExecutor`. Esta combinación permite la ordenación a través de los encabezados de las columnas y el filtrado dinámico con especificaciones.

```java title="TableView.java"
@Route
public class TableView extends Composite<Div> {
  private SpringDataRepository<Person, Long> repository;
  private Table<Person> table = new Table<>();

  public TableView(PersonRepository personRepository) {
    // Envuelve el repositorio de Spring Data para webforJ
    repository = new SpringDataRepository<>(personRepository);

    // Conéctate a la tabla
    table.setRepository(repository);

    // Define columnas
    table.addColumn("name", Person::getFullName)
          .setPropertyName("firstName"); // Ordenar por la propiedad JPA real
    table.addColumn("email", Person::getEmail);
    table.addColumn("age", person ->
          person.getAge() != null ? person.getAge().toString() : "");
    table.addColumn("city", Person::getCity);
    table.addColumn("profession", Person::getProfession);

    // Habilitar ordenación
    table.getColumns().forEach(column -> column.setSortable(true));
  }
}
```

El método `setPropertyName()` es importante para la ordenación - indica al adaptador qué propiedad JPA usar en la cláusula `ORDER BY` al ordenar por esa columna. Sin él, la ordenación no funcionará para propiedades computadas como `getFullName()`.

## Filtrado con especificaciones JPA {#filtering-with-jpa-specifications}

`SpringDataRepository` utiliza Especificaciones JPA para consultas dinámicas y se aplican a las operaciones `findBy` y `count` del repositorio.

:::tip[Aprende más sobre filtrado]
Para entender cómo funciona el filtrado con repositorios de webforJ, incluyendo filtros base y composición de filtros, consulta la [documentación del repositorio](../../advanced/repository/overview).
:::

```java
// Filtrar por ciudad
Specification<Person> cityFilter = (root, query, cb) ->
  cb.equal(root.get("city"), "Nueva York");
repository.setFilter(cityFilter);

// Múltiples condiciones
Specification<Person> complexFilter = (root, query, cb) ->
  cb.and(
    cb.equal(root.get("profession"), "Ingeniero"),
    cb.greaterThanOrEqualTo(root.get("age"), 25)
  );
repository.setFilter(complexFilter);

// Limpiar filtro
repository.setFilter(null);
```
