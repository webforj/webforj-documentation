---
title: Repository
sidebar_position: 1
sidebar_class_name: has-new-content
_i18n_hash: c285de15c19063af40c61f15cebf4dc1
---
<!-- vale off -->
# Repositorio <DocChip chip='since' label='24.00' />
<!-- vale on -->

El patrón `Repositorio` en webforJ proporciona una forma estandarizada de gestionar y consultar colecciones de entidades. Actúa como una capa de abstracción entre sus componentes de interfaz de usuario y los datos, facilitando el trabajo con diferentes fuentes de datos mientras se mantiene un comportamiento consistente.

## Por qué usar repositorio {#why-use-repository}

`Repositorio` elimina actualizaciones manuales mientras mantiene sus datos originales intactos:

```java
// Sin Repositorio - actualizaciones manuales
List<Customer> customers = loadCustomers();
Table<Customer> table = new Table<>();
table.setItems(customers);

// Agregar requiere una recarga completa
customers.add(newCustomer);
table.setItems(customers); // Debe recargar todo
```

```java
// Con Repositorio - sincronización automática
List<Customer> customers = loadCustomers();
CollectionRepository<Customer> repository = new CollectionRepository<>(customers);
Table<Customer> table = new Table<>();
table.setRepository(repository);

// Agregar se sincroniza automáticamente
customers.add(newCustomer);
repository.commit(newCustomer); // Solo actualiza lo que cambió
```

## Repositorio de colección {#collection-repository}

El <JavadocLink type="data" location="com/webforj/data/repository/CollectionRepository" code="true">CollectionRepository</JavadocLink> es la implementación más común y envuelve cualquier colección de Java:

```java
// Desde ArrayList
List<Customer> customers = new ArrayList<>();
CollectionRepository<Customer> customerRepo = new CollectionRepository<>(customers);

// Desde HashSet  
Set<String> tags = new HashSet<>();
CollectionRepository<String> tagRepo = new CollectionRepository<>(tags);

// Desde cualquier Colección
Collection<Employee> employees = getEmployeesFromHR();
CollectionRepository<Employee> employeeRepo = new CollectionRepository<>(employees);
```

## Sincronización de datos {#data-synchronization}

El `Repositorio` actúa como un puente entre sus datos y los componentes de la interfaz de usuario. Cuando los datos cambian, notifica al repositorio a través del método `commit()`:

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

// Remover producto
products.remove(2);
repository.commit(); // Refresca la vista
```

El método commit tiene dos firmas:
- `commit()` - Indica al repositorio que refresque todo. Dispara un `RepositoryCommitEvent` con todos los datos actuales
- `commit(entity)` - Apunta a una entidad específica. El repositorio encuentra esta entidad por su clave y actualiza solo los elementos de interfaz de usuario afectados

:::important Comprometiendo entidades individuales
Esta distinción es importante para el rendimiento. Cuando actualiza un campo en una tabla de 1000 filas, `commit(entity)` actualiza solo esa celda, mientras que `commit()` refrescaría todas las filas.
:::

## Filtrado de datos {#filtering-data}

El filtro del repositorio controla qué datos fluyen hacia los componentes conectados. Su colección subyacente permanece sin cambios porque el filtro actúa como una lente:

```java
// Filtrar por disponibilidad de stock
repository.setBaseFilter(product -> product.getStock() > 0);

// Filtrar por categoría
repository.setBaseFilter(product -> "Electronics".equals(product.getCategory()));

// Combinar múltiples condiciones
repository.setBaseFilter(product -> 
    product.getCategory().equals("Electronics") && 
    product.getStock() > 0 && 
    product.getPrice() < 100.0
);

// Limpiar filtro
repository.setBaseFilter(null);
```

Cuando establece un filtro, el `Repositorio`:
1. Aplica el predicado a cada elemento en su colección
2. Crea un flujo filtrado de elementos coincidentes
3. Notifica a los componentes conectados que actualicen su visualización

El filtro persiste hasta que lo cambie. Los nuevos elementos añadidos a la colección se prueban automáticamente contra el filtro actual.

## Trabajando con claves de entidad {#working-with-entity-keys}

El repositorio necesita identificar entidades de manera única para soportar operaciones como `find()` y `commit(entity)`. Hay dos formas de definir cómo se identifican las entidades:

### Usando la interfaz `HasEntityKey` {#using-hasentitykey}

Implemente <JavadocLink type="data" location="com/webforj/data/HasEntityKey" code="true">HasEntityKey</JavadocLink> en su clase de entidad:

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

Para entidades donde no puede o no desea implementar `HasEntityKey` (como entidades JPA), use `setKeyProvider()`:

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

// Ahora buscar funciona con el ID
Optional<Product> product = repository.find(123L);
```

### Elegir un enfoque {#choosing-approach}

Ambos enfoques funcionan, pero `setKeyProvider()` es preferido cuando:
- Trabajando con entidades JPA que tienen campos `@Id`
- No puede modificar la clase de entidad
- Necesita diferentes estrategias de clave para diferentes repositorios

Use `HasEntityKey` cuando:
- Controla la clase de entidad
- La lógica de extracción de clave es compleja
- Desea que la entidad defina su propia identidad

## Integración de UI {#ui-integration}

`Repositorio` se integra con componentes sensibles a los datos:

```java
// Crear repositorio y tabla
List<Customer> customers = new ArrayList<>();
CollectionRepository<Customer> repository = new CollectionRepository<>(customers);

Table<Customer> table = new Table<>();
table.setRepository(repository);
table.addColumn("ID", Customer::getId);
table.addColumn("Nombre", Customer::getName);
table.addColumn("Correo electrónico", Customer::getEmail);

// Agregar datos - la tabla se actualiza automáticamente
customers.add(new Customer("C001", "Alice Johnson", "alice@example.com"));
repository.commit();
```

## Próximos pasos {#next-steps}

<DocCardList className="topics-section" />
