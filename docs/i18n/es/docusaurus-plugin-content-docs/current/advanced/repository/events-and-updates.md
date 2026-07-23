---
title: Events and updates
sidebar_position: 5
description: >-
  React to repository commits with RepositoryCommitEvent listeners, choose
  single-entity or bulk updates, and manage listener registrations.
_i18n_hash: 9ce2e7d25b38cd0d04acd30c80edffb3
---
<!-- vale off -->
# Eventos y actualizaciones <DocChip chip='since' label='24.00' />
<!-- vale on -->

Los eventos del `Repository` te permiten reaccionar a cambios de datos. Más allá de las actualizaciones automáticas de la interfaz de usuario, puedes escuchar cambios para desencadenar lógica personalizada.

## Ciclo de vida del evento del `Repository` {#repository-event-lifecycle}

Cada llamada a `commit()` genera un <JavadocLink type="data" location="com/webforj/data/repository/event/RepositoryCommitEvent" code="true">RepositoryCommitEvent</JavadocLink>. Este evento lleva información sobre lo que cambió:

```java
repository.onCommit(event -> {
  // Obtener todas las entidades comprometidas
  List<Customer> commits = event.getCommits();

  // Comprobar si es una actualización de una sola entidad
  if (event.isSingleCommit()) {
    Customer updated = event.getFirstCommit();
    System.out.println("Actualizado: " + updated.getName());
  }
});
```

El evento te indica:
- `getCommits()` - Lista de entidades que fueron comprometidas
- `isSingleCommit()` - Si esta fue una actualización de una sola entidad específica
- `getFirstCommit()` - Método conveniente para obtener la primera (o única) entidad

Para `commit()` sin parámetros, el evento contiene todas las entidades actualmente en el repositorio después de aplicar el filtro.

## Estrategias de actualización {#update-strategies}

Las dos firmas de commit sirven para diferentes propósitos:

```java
// Actualización de una sola entidad - eficiente para cambios individuales
Customer customer = customers.get(0);
customer.setStatus("VIP");
repository.commit(customer);

// Actualización masiva - eficiente para múltiples cambios
products.clear();
products.addAll(loadProductsFromCsv());
repository.commit();
```

Los commits de una sola entidad son quirúrgicos; le dicen a los componentes conectados exactamente qué fila cambió. La [`Table`](../../components/table/overview) puede actualizar solo las celdas de esa fila sin tocar nada más.

Los commits masivos actualizan todo. Úsalos cuando:
- Cambiaron múltiples entidades
- Has agregado o eliminado elementos
- No estás seguro de qué cambió

## Patrones de UI reactiva {#reactive-ui-patterns}

Los eventos del `Repository` te permiten mantener las displays resumen sincronizadas con tus datos:

```java
// Etiquetas de actualización automática
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

Estos listeners se activan en cada commit, ya sea por acciones del usuario, importaciones de datos o actualizaciones programáticas. El evento te da acceso a las entidades comprometidas, pero a menudo calcularás de nuevo desde la colección fuente para incluir todos los datos actuales.

## Gestión de memoria {#memory-management}

Los listeners de eventos mantienen referencias a tus componentes. Si no los eliminas, el `Repository` mantiene tus componentes en memoria incluso después de que ya no se muestren:

```java
// Mantener referencia para eliminar más tarde
ListenerRegistration<RepositoryCommitEvent<Data>> registration =
  repository.onCommit(event -> {
    updateDisplay(event.getCommits());
  });

// Limpiar el listener cuando el componente es destruido
if (registration != null) {
  registration.remove();
}
```

El método `onCommit()` devuelve un `ListenerRegistration`. Almacena esta referencia y llama a `remove()` cuando tu componente es destruido o cuando ya no necesita actualizaciones. Esto previene pérdidas de memoria en aplicaciones de larga duración.
