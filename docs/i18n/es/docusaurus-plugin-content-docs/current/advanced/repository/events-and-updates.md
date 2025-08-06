---
title: Events and updates
sidebar_position: 5
_i18n_hash: 45321f5a931bc6efb56376b53662be32
---
<!-- vale off -->
# Eventos y actualizaciones <DocChip chip='since' label='24.00' />
<!-- vale on -->

Los eventos de `Repository` te permiten reaccionar a cambios en los datos. Más allá de las actualizaciones automáticas de la interfaz de usuario, puedes escuchar cambios para activar lógica personalizada.

## Ciclo de vida del evento `Repository` {#repository-event-lifecycle}

Cada llamada a `commit()` inicia un <JavadocLink type="data" location="com/webforj/data/repository/event/RepositoryCommitEvent" code="true">RepositoryCommitEvent</JavadocLink>. Este evento lleva información sobre lo que cambió:

```java
repository.onCommit(event -> {
    // Obtener todas las entidades comprometidas
    List<Customer> commits = event.getCommits();
    
    // Verificar si se actualizó una sola entidad
    if (event.isSingleCommit()) {
        Customer updated = event.getFirstCommit();
        System.out.println("Actualizado: " + updated.getName());
    }
});
```

El evento te indica:
- `getCommits()` - Lista de entidades que fueron comprometidas
- `isSingleCommit()` - Si esta fue una actualización de entidad única
- `getFirstCommit()` - Método conveniente para obtener la primera (o única) entidad

Para `commit()` sin parámetros, el evento contiene todas las entidades que actualmente están en el repositorio después de filtrar.

## Estrategias de actualización {#update-strategies}

Las dos firmas de commit sirven para diferentes propósitos:

```java
// Actualización de entidad única - eficiente para cambios individuales
Customer customer = customers.get(0);
customer.setStatus("VIP");
repository.commit(customer);

// Actualización masiva - eficiente para múltiples cambios
products.clear();
products.addAll(loadProductsFromCsv());
repository.commit();
```

Los commits de entidad única son quirúrgicos: le dicen a los componentes conectados exactamente qué fila cambió. La [`Table`](../../components/table/overview) puede actualizar solo las celdas de esa fila sin tocar nada más.

Los commits masivos actualizan todo. Úselos cuando:
- Varias entidades cambiaron
- Agregaste o eliminaste elementos
- No estás seguro de qué cambió

## Patrones de interfaz de usuario reactiva {#reactive-ui-patterns}

Los eventos de `Repository` te permiten mantener sincronizadas las pantallas de resumen con tus datos:

```java
// Etiquetas que se actualizan automáticamente
repository.onCommit(event -> {
    double total = sales.stream().mapToDouble(Sale::getAmount).sum();
    totalLabel.setText(String.format("Total: $%.2f", total));
    countLabel.setText("Ventas: " + sales.size());
});

// Conteos de resultados en vivo
repository.onCommit(e -> {
    long count = repository.findAll().count();
    resultsLabel.setText(count + " productos encontrados");
});
```

Estos escuchadores se activan en cada commit, ya sea por acciones del usuario, importaciones de datos o actualizaciones programáticas. El evento te da acceso a las entidades comprometidas, pero a menudo recalcularás desde la colección de origen para incluir todos los datos actuales.

## Gestión de memoria {#memory-management}

Los escuchadores de eventos mantienen referencias a tus componentes. Si no los eliminas, el `Repository` mantiene tus componentes en memoria incluso después de que ya no se muestren:

```java
// Mantener referencia para eliminar más tarde
ListenerRegistration<RepositoryCommitEvent<Data>> registration = 
    repository.onCommit(event -> {
        updateDisplay(event.getCommits());
    });

// Limpiar el escuchador cuando el componente se destruye
if (registration != null) {
    registration.remove();
}
```

El método `onCommit()` devuelve un `ListenerRegistration`. Guarda esta referencia y llama a `remove()` cuando tu componente se destruya o ya no necesite actualizaciones. Esto previene fugas de memoria en aplicaciones de larga duración.
