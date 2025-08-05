---
title: Repository
sidebar_position: 1
sidebar_class_name: new-content
_i18n_hash: 8dfc90f24bba893de434f1a41d5776c6
---
<!-- vale off -->
# Repositorio <DocChip chip='since' label='24.00' />
<!-- vale on -->

El patrón `Repository` en webforJ proporciona una forma estandarizada de gestionar y consultar colecciones de entidades. Actúa como una capa de abstracción entre sus componentes de UI y los datos, facilitando el trabajo con diferentes fuentes de datos mientras se mantiene un comportamiento consistente.

## Por qué usar repositorio {#why-use-repository}

`Repository` elimina las actualizaciones manuales mientras mantiene tus datos originales intactos:

```java
// Sin Repository - actualizaciones manuales
List<Customer> customers = loadCustomers();
Table<Customer> table = new Table<>();
table.setItems(customers);

// Agregar requiere recarga completa
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
repository.commit(newCustomer); // Solo actualiza lo que ha cambiado
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

El `Repository` actúa como un puente entre tus datos y componentes de UI. Cuando los datos cambian, notificas al repositorio a través del método `commit()`:

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
repository.commit(); // Actualiza la vista
```

El método commit tiene dos firmas:
- `commit()` - Indica al repositorio que refresque todo. Activa un `RepositoryCommitEvent` con todos los datos actuales.
- `commit(entity)` - Apunta a una entidad específica. El repositorio encuentra esta entidad por su clave y actualiza solo los elementos de UI afectados.

:::important Comprometimiento de entidades individuales
Esta distinción es importante para el rendimiento. Cuando actualizas un campo en una tabla de 1000 filas, `commit(entity)` actualiza solo esa celda, mientras que `commit()` refrescaría todas las filas.
:::

## Filtrando datos {#filtering-data}

El filtro del repositorio controla qué datos fluyen hacia los componentes conectados. Tu colección subyacente permanece sin cambios porque el filtro actúa como una lente:

```java
// Filtrar por disponibilidad de stock
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

Cuando estableces un filtro, el `Repository`:
1. Aplica el predicado a cada elemento de tu colección.
2. Crea un flujo filtrado de elementos coincidentes.
3. Notifica a los componentes conectados que actualicen su visualización.

El filtro persiste hasta que lo cambias. Los nuevos elementos añadidos a la colección se prueban automáticamente contra el filtro actual.

## Trabajando con claves de entidades {#working-with-entity-keys}

Cuando tus entidades implementan <JavadocLink type="data" location="com/webforj/data/HasEntityKey" code="true">HasEntityKey</JavadocLink>, el repositorio puede encontrar y actualizar elementos específicos por su ID:

```java
public class Customer implements HasEntityKey {
    private String customerId;
    private String name;
    private String email;
    
    @Override
    public Object getEntityKey() {
        return customerId;
    }
    
    // Constructor y métodos getter/setter...
}

// Buscar por clave
Optional<Customer> customer = repository.find("C001");

// Actualizar cliente específico
customer.ifPresent(c -> {
    c.setEmail("newemail@example.com");
    repository.commit(c); // Solo se actualiza la fila de este cliente
});
```

Sin `HasEntityKey`:
- `repository.find("C001")` no encontrará tu cliente porque busca un objeto que sea igual a "C001".
- `repository.commit(entity)` sigue funcionando, pero depende de la igualdad de objetos.
- Los componentes de UI no pueden seleccionar elementos por ID, solo por referencia de objeto.

## Integración con UI {#ui-integration}

`Repository` se integra con componentes que son conscientes de los datos:

```java
// Crear repositorio y tabla
List<Customer> customers = new ArrayList<>();
CollectionRepository<Customer> repository = new CollectionRepository<>(customers);

Table<Customer> table = new Table<>();
table.setRepository(repository);
table.addColumn("ID", Customer::getId);
table.addColumn("Nombre", Customer::getName);
table.addColumn("Email", Customer::getEmail);

// Agregar datos - la tabla se actualiza automáticamente
customers.add(new Customer("C001", "Alice Johnson", "alice@example.com"));
repository.commit();
```

## Próximos pasos {#next-steps}

<DocCardList className="topics-section" />
