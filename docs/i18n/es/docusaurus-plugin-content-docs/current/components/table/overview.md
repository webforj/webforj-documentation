---
sidebar_position: 1
title: Table
hide_giscus_comments: true
_i18n_hash: 3b4ddfbfc0fb9c5d67fa60136a23af73
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-table" />
<DocChip chip='since' label='24.00' />
<JavadocLink type="table" location="com/webforj/component/table/Table" top='true'/>

La clase `Table` es un componente versátil diseñado para presentar información tabular de manera estructurada y fácilmente comprensible. Optimizando el manejo de grandes conjuntos de datos con alto rendimiento, este componente ofrece visualización avanzada y un conjunto integral de eventos para la interacción dinámica del usuario.

<!-- INTRO_END -->

## Creando una `Table` {#creating-a-table}

<!-- vale off -->
<ComponentDemo
path='/webforj/datatable'
files={['src/main/java/com/webforj/samples/views/table/DataTableView.java']}
height='600px'
/>
<!-- vale on -->

Para crear y llenar una `Table` en una aplicación, se pueden seguir los siguientes pasos:

### 1. Crear una clase de entidad {#1-create-an-entity-class}

Define una clase para representar las entidades (datos) que deseas mostrar en la tabla. En el ejemplo, esta clase es MusicRecord.

```java
public class MusicRecord {
  // Campos y métodos para representar los atributos de cada registro
}
```

### 2. Crear un repositorio {#2-create-a-repository}

Una vez que se ha creado una clase de entidad, utilízala para llenar una colección de estas entidades con los datos deseados.

A partir de estos datos, se necesita crear un `Repository` para su uso dentro de la `Table`. La clase `CollectionRepository` se proporciona para convertir cualquier colección de Java válida en un `Repository` utilizable, prescindiendo de la necesidad de implementar tu propia clase `Repository`.

```java
List<MusicRecord> data = new Gson().fromJson(
  Assets.contentOf(
    Assets.resolveContextUrl("context://data/CDStore.json")
  ), new TypeToken<List<MusicRecord>>() {}
);

CollectionRepository<MusicRecord> dataRepository = new CollectionRepository<>(data);
```

:::tip Más información
Para obtener más información sobre el patrón `Repository` en webforJ, consulta los [artículos sobre Repositorios](/docs/advanced/repository/overview).
:::

### 3. Instanciar `Table` y añadir columnas {#3-instantiate-table-and-add-columns}

Instancia un nuevo objeto `Table` y utiliza uno de los métodos de fábrica proporcionados para agregar las columnas deseadas a la `Table` recién creada:

```java
Table<MusicRecord> table = new Table<>();
table.addColumn("Número", MusicRecord::getNumber);
table.addColumn("Título", MusicRecord::getTitle);
table.addColumn("Artista", MusicRecord::getArtist);
table.addColumn("Género", MusicRecord::getMusicType);
table.addColumn("Costo", MusicRecord::getCost);
```

### 4. Establecer los datos de la `Table` {#4-set-the-table-data}

Finalmente, establece el `Repository` para la `Table` creada en el paso anterior:

```java
table.setRepository(Service.getMusicRecords());
```

:::info
Alternativamente, el método `setItems()` puede recibir cualquier colección de Java válida, lo que creará un `CollectionRepository` automáticamente para ti.
:::

A continuación se muestra un ejemplo de los pasos anteriores implementados para crear un componente básico de `Table`:


<ComponentDemo
path='/webforj/tablebasic'
files={[
  'src/main/java/com/webforj/samples/views/table/TableBasicView.java',
  'src/main/java/com/webforj/samples/views/table/MusicRecord.java',
  'src/main/java/com/webforj/samples/views/table/Service.java',
]}
height='600px'
/>

## Estilo

<TableBuilder name="Table" />
