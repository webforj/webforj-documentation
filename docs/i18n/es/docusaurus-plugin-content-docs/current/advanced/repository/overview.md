---
title: Repository
sidebar_position: 1
description: >-
  Bridge data collections to UI components with CollectionRepository, commit
  changes for automatic sync, and apply base filters.
_i18n_hash: 77548d09e4d712ce8f508917d3b953ae
---
<!-- vale off -->
# Repositorio <DocChip chip='since' label='24.00' />
<!-- vale on -->


El patrón `Repository` en webforJ proporciona una forma estandarizada de gestionar y consultar colecciones de entidades. Actúa como una capa de abstracción entre tus componentes de UI y los datos, facilitando el trabajo con diferentes fuentes de datos mientras se mantiene un comportamiento coherente.

## Por qué usar un repositorio {#why-use-repository}

`Repository` elimina actualizaciones manuales mientras mantiene tus datos originales intactos:

```java
// Sin Repository - actualizaciones manuales
List<Customer> customers = loadCustomers();
Table<Customer> table = new Table<>();
table.setItems(customers);

// Agregar requiere una recarga completa
customers.add(newCustomer);
table.setItems(customers); // Debe recargar todo
```

```java
// Con Repository - sincronización automática
List<Customer> customers = loadCustomers();
CollectionRepository<Customer> repository = new CollectionRepository<>(customers);
Table<Customer> table = new Table<>();
table.setRepository(repository);

// Agregar se sincroniza automáticamente
customers.add(newCustomer);
repository.commit(newCustomer); // Solo actualiza lo que cambió
```


## Repositorio de colecciones {#collection-repository}

El <JavadocLink type="data" location="com/webforj/data/repository/CollectionRepository" code="true">CollectionRepository</JavadocLink> es la implementación más común y envuelve cualquier colección de Java:

```java
// Desde ArrayList
List<Customer> customers = new ArrayList<>();
CollectionRepository<Customer> customerRepo = new CollectionRepository<>(customers);

// Desde HashSet
Set<String> tags = new HashSet<>();
CollectionRepository<String> tagRepo = new CollectionRepository<>(tags);

// Desde cualquier colección
Collection<Employee> employees = getEmployeesFromHR();
CollectionRepository<Employee> employeeRepo = new CollectionRepository<>(employees);
```


## Sincronización de datos {#data-synchronization}

El `Repository` actúa como un puente entre tus datos y los componentes de UI. Cuando los datos cambian, notificas al repositorio a través del método `commit()`:

```java
List<Product> products = new ArrayList<>();
CollectionRepository<Product> repository = new CollectionRepository<>(products);

// Agregar nuevo producto
Product newProduct = new Product("P4", "Gizmo", 79.99, 15);
products.add(newProduct);
repository.commit(); // Todos los componentes conectados se actualizan

// Actualizar producto existente
products.get(0).setPrice(89.99);
repository.commit(products.get(0)); // Solo actualiza esta fila específica

// Eliminar producto
products.remove(2);
repository.commit(); // Refresca la vista
```

El método commit tiene dos firmas:
- `commit()` - Indica al repositorio que refresque todo. Desencadena un `RepositoryCommitEvent` con todos los datos actuales.
- `commit(entity)` - Se dirige a una entidad específica. El repositorio encuentra esta entidad por su clave y actualiza solo los elementos de UI afectados.

:::important Comprometiendo entidades individuales
Esta distinción es importante para el rendimiento. Cuando actualizas un campo en una tabla de 1000 filas, `commit(entity)` actualiza solo esa celda, mientras que `commit()` refrescaría todas las filas.
:::

## Filtrando datos {#filtering-data}

El filtro del repositorio controla qué datos fluyen hacia los componentes conectados. Tu colección subyacente permanece sin cambios porque el filtro actúa como una lente:

```java
// Filtrar por disponibilidad en stock
repository.setBaseFilter(product -> product.getStock() > 0);

// Filtrar por categoría
repository.setBaseFilter(product -> "Electrónica".equals(product.getCategory()));

// Combinar múltiples condiciones
repository.setBaseFilter(product ->
  product.getCategory().equals("Electrónica") &&
  product.getStock() > 0 &&
  product.getPrice() < 100.0
);

// Limpiar filtro
repository.setBaseFilter(null);
```

Cuando configuras un filtro, el `Repository`:
1. Aplica el predicado a cada elemento en tu colección
2. Crea un flujo filtrado de elementos que coinciden
3. Notifica a los componentes conectados para que actualicen su visualización

El filtro persiste hasta que lo cambies. Los nuevos elementos añadidos a la colección son automáticamente evaluados contra el filtro actual.


## Trabajando con claves de entidad {#working-with-entity-keys}

El repositorio necesita identificar entidades de manera única para soportar operaciones como `find()` y `commit(entity)`. Hay dos maneras de definir cómo se identifican las entidades:

### Usando la interfaz `HasEntityKey` {#using-hasentitykey}

Implementa <JavadocLink type="data" location="com/webforj/data/HasEntityKey" code="true">HasEntityKey</JavadocLink> en tu clase de entidad:

```java
public class Customer implements HasEntityKey {
  private String customerId;
  private String name;
  private String email;

  @Override
  public Object getEntityKey() {
    return customerId;
  }

  // Constructor y getters/setters...
}

// Buscar por clave
Optional<Customer> customer = repository.find("C001");

// Actualizar cliente específico
customer.ifPresent(c -> {
  c.setEmail("newemail@example.com");
  repository.commit(c); // Solo se actualiza la fila de este cliente
});
```

### Usando proveedor de clave personalizado <DocChip chip='since' label='25.10' /> {#using-custom-key-provider}

Para entidades donde no puedes o no deseas implementar `HasEntityKey` (como entidades JPA), usa `setKeyProvider()`:

```java
@Entity
public class Product {
  @Id
  private Long id;
  private String name;
  private double price;

  // Entidad gestionada por JPA
}

// Configurar repositorio para usar el método getId()
CollectionRepository<Product> repository = new CollectionRepository<>(products);
repository.setKeyProvider(Product::getId);

// Ahora la búsqueda funciona con el ID
Optional<Product> product = repository.find(123L);
```

### Elegir un enfoque {#choosing-approach}

Ambos enfoques funcionan, pero `setKeyProvider()` es preferido cuando:
- Trabajas con entidades JPA que tienen campos `@Id`
- No puedes modificar la clase de entidad
- Necesitas diferentes estrategias de clave para diferentes repositorios

Usa `HasEntityKey` cuando:
- Controlas la clase de entidad
- La lógica de extracción de clave es compleja
- Quieres que la entidad defina su propia identidad


## Integración con UI {#ui-integration}

`Repository` se integra con componentes con conocimiento de datos:

```java
// Crear repositorio y tabla
List<Customer> customers = new ArrayList<>();
CollectionRepository<Customer> repository = new CollectionRepository<>(customers);

Table<Customer> table = new Table<>();
table.setRepository(repository);
table.addColumn("ID", Customer::getId);
table.addColumn("Nombre", Customer::getName);
table.addColumn("Correo Electrónico", Customer::getEmail);

// Agregar datos - la tabla se actualiza automáticamente
customers.add(new Customer("C001", "Alice Johnson", "alice@example.com"));
repository.commit();
```


## Próximos pasos {#next-steps}

<DocCardList className="topics-section" />
