---
title: Repository
sidebar_position: 1
sidebar_class_name: new-content
_i18n_hash: dc6f7bbfe82d68565cbe8da6436f080c
---
<!-- vale off -->
# Repositorio <DocChip chip='since' label='24.00' />
<!-- vale on -->


El patrón `Repositorio` en webforJ proporciona una forma estandarizada de gestionar y consultar colecciones de entidades. Actúa como una capa de abstracción entre tus componentes de UI y los datos, facilitando el trabajo con diferentes fuentes de datos mientras se mantiene un comportamiento consistente.

## Por qué usar repositorio {#why-use-repository}

`Repositorio` elimina las actualizaciones manuales mientras mantiene tus datos originales intactos:

```java
// Sin Repositorio - actualizaciones manuales
List<Customer> customers = loadCustomers();
Table<Customer> table = new Table<>();
table.setItems(customers);

// Agregar requiere recargar todo
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

// Desde cualquier colección
Collection<Employee> employees = getEmployeesFromHR();
CollectionRepository<Employee> employeeRepo = new CollectionRepository<>(employees);
```


## Sincronización de datos {#data-synchronization}

El `Repositorio` actúa como un puente entre tus datos y los componentes de UI. Cuando los datos cambian, notificas al repositorio a través del método `commit()`:

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
- `commit()` - Indica al repositorio que refresque todo. Dispara un `RepositoryCommitEvent` con todos los datos actuales.
- `commit(entity)` - Apunta a una entidad específica. El repositorio encuentra esta entidad por su clave y actualiza solo los elementos de UI afectados.

:::important Comprometiendo entidades individuales
Esta distinción es importante para el rendimiento. Cuando actualizas un campo en una tabla de 1000 filas, `commit(entity)` actualiza solo esa celda, mientras que `commit()` refrescaría todas las filas.
:::

## Filtrar datos {#filtering-data}

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

// Borrar filtro
repository.setBaseFilter(null);
```

Cuando estableces un filtro, el `Repositorio`:
1. Aplica el predicado a cada elemento en tu colección.
2. Crea un flujo filtrado de elementos que coinciden.
3. Notifica a los componentes conectados para que actualicen su visualización.

El filtro persiste hasta que lo cambies. Los nuevos elementos añadidos a la colección se prueban automáticamente contra el filtro actual.


## Trabajando con claves de entidad {#working-with-entity-keys}

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

Sin `HasEntityKey`:
- `repository.find("C001")` no encontrará a tu cliente porque busca un objeto que sea igual a "C001".
- `repository.commit(entity)` sigue funcionando, pero depende de la igualdad de objetos.
- Los componentes de UI no pueden seleccionar elementos por ID, solo por referencia de objeto.


## Integración de UI {#ui-integration}

`Repositorio` se integra con componentes conscientes de los datos:

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
