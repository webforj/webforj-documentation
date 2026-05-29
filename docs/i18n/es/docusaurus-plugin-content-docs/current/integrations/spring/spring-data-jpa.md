---
title: Spring Data JPA
sidebar_position: 20
_i18n_hash: 3fe8c744a49adaaa35e1e30c53b5c60f
---
Spring Data JPA es el estándar de facto para el acceso a datos en aplicaciones Spring, proporcionando abstracciones de repositorio, métodos de consulta y especificaciones para consultas complejas. El adaptador `SpringDataRepository` de webforJ conecta los repositorios de Spring Data con los componentes de UI de webforJ, permitiéndote vincular entidades JPA directamente a componentes de UI, implementar filtrado dinámico con Especificaciones JPA y manejar paginación.

El adaptador detecta qué interfaces de Spring Data implementa tu repositorio, ya sea `CrudRepository`, `PagingAndSortingRepository` o `JpaSpecificationExecutor`, y proporciona automáticamente las características correspondientes en tu UI. Esto significa que tus repositorios existentes de Spring Data funcionan con los componentes de webforJ sin modificaciones, mientras se mantiene la seguridad de tipos y se utiliza tu modelo de dominio existente.

:::tip[Aprende más sobre Spring Data JPA]
Para una comprensión completa de las características y métodos de consulta de Spring Data JPA, consulta la [documentación de Spring Data JPA](https://docs.spring.io/spring-data/jpa/reference/).
:::

## Usando SpringDataRepository {#using-springdatarepository}

La clase `SpringDataRepository` conecta los repositorios de Spring Data JPA con la interfaz Repository de webforJ, haciéndolos compatibles con componentes de UI como [`Table`](../../components/table/overview) mientras retiene todas las características de Spring Data.

```java
// Tu repositorio de Spring Data
@Autowired
private PersonRepository personRepository;

// Envuelve con SpringDataRepository
SpringDataRepository<Person, Long> adapter = new SpringDataRepository<>(personRepository);

// Usa con la tabla de webforJ
Table<Person> table = new Table<>();
table.setRepository(adapter);
```

### Detección de interfaces {#interface-detection}

Los repositorios de Spring Data utilizan la herencia de interfaces para agregar capacidades. Comienzas con operaciones CRUD básicas y agregas interfaces para características como paginación o especificaciones:

```java
// Solo CRUD básico
public interface CustomerRepository extends CrudRepository<Customer, Long> {}

// CRUD + Paginación + Ordenación
public interface CustomerRepository extends PagingAndSortingRepository<Customer, Long> {}

// Repositorio con todas las características
public interface CustomerRepository extends JpaRepository<Customer, Long>, JpaSpecificationExecutor<Customer> {}
```

`SpringDataRepository` examina qué interfaces implementa tu repositorio y adapta su comportamiento en consecuencia. Si tu repositorio admite paginación, el adaptador habilita consultas paginadas. Si implementa `JpaSpecificationExecutor`, puedes usar filtrado dinámico con especificaciones.

### Capacidades del repositorio {#repository-capabilities}

Cada interfaz de Spring Data agrega capacidades específicas que `SpringDataRepository` puede utilizar:

- **CrudRepository** - Operaciones básicas: `save`, `delete`, `findById`, `findAll`
- **PagingAndSortingRepository** - Agrega consultas paginadas y ordenación
- **JpaRepository** - Combina CRUD y paginación/ordenación con operaciones en lote
- **JpaSpecificationExecutor** - Consultas dinámicas utilizando Especificaciones JPA

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
- Filtrado mediante la Especificación de la API de Persistencia de Java
- Operaciones de conteo con y sin filtros

## Trabajando con `Table` {#working-with-table}

El siguiente ejemplo utiliza un `PersonRepository` que extiende `JpaRepository` y `JpaSpecificationExecutor`. Esta combinación permite ordenación a través de los encabezados de columna y filtrado dinámico con especificaciones.

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
    
    // Habilitar ordenación
    table.getColumns().forEach(column -> column.setSortable(true));
  }
}
```

El método `setPropertyName()` es importante para la ordenación, ya que le indica al adaptador qué propiedad JPA usar en la cláusula `ORDER BY` al ordenar por esa columna. Sin él, la ordenación no funcionará para propiedades computadas como `getFullName()`.

## Filtrado con especificaciones JPA {#filtering-with-jpa-specifications}

`SpringDataRepository` utiliza Especificaciones JPA para consultas dinámicas que se aplican a las operaciones `findBy` y `count` del repositorio.

:::tip[Aprende más sobre el filtrado]
Para entender cómo funciona el filtrado con los repositorios de webforJ, incluyendo filtros base y composición de filtros, consulta la [documentación del Repositorio](../../advanced/repository/overview).
::: 

```java
// Filtrar por ciudad
Specification<Person> cityFilter = (root, query, cb) -> 
  cb.equal(root.get("city"), "New York");
repository.setFilter(cityFilter);

// Múltiples condiciones
Specification<Person> complexFilter = (root, query, cb) -> 
  cb.and(
    cb.equal(root.get("profession"), "Engineer"),
    cb.greaterThanOrEqualTo(root.get("age"), 25)
  );
repository.setFilter(complexFilter);

// Limpiar filtro
repository.setFilter(null);
```
