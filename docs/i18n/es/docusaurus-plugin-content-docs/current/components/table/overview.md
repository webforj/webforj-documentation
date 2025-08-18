---
sidebar_position: 1
title: Table
hide_giscus_comments: true
sidebar_class_name: has-new-content
_i18n_hash: 9e123638ff60f46c96d369bce79da44e
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-table" />
<DocChip chip='since' label='24.00' />
<JavadocLink type="table" location="com/webforj/component/table/Table" top='true'/>

La clase `Table` es un componente versátil diseñado para presentar información tabular de manera estructurada y fácilmente comprensible. Optimizado para manejar grandes conjuntos de datos con alto rendimiento, este componente ofrece visualización avanzada y un conjunto completo de eventos para la interacción dinámica del usuario.

<ComponentDemo 
path='/webforj/datatable?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/DataTableView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>


## Creando una `Table` {#creating-a-table}

Para crear y poblar una `Table` en una aplicación, se pueden seguir los siguientes pasos:

### 1. Crear una clase de entidad {#1-create-an-entity-class}

Define una clase para representar las entidades (datos) que deseas mostrar en la tabla. En el ejemplo, esta clase es MusicRecord.

```java
public class MusicRecord {
    // Campos y métodos para representar los atributos de cada registro
}
```

### 2. Crear un repositorio {#2-create-a-repository}

Una vez que se ha creado una clase de entidad, utilízala para llenar una colección de estas entidades con los datos deseados.

A partir de estos datos, se necesita crear un `Repository` para su uso dentro de la `Table`. Se proporciona la clase `CollectionRepository` para convertir cualquier colección válida de Java en un `Repository` utilizable, evitando la necesidad de implementar tu propia clase de `Repository`.

```java
List<MusicRecord> data = new Gson().fromJson(
    Assets.contentOf(
        Assets.resolveContextUrl("context://data/CDStore.json")
    ), new TypeToken<List<MusicRecord>>() {}
);

CollectionRepository<MusicRecord> dataRepository = new CollectionRepository<>(data);
```

### 3. Instanciar `Table` y agregar columnas {#3-instantiate-table-and-add-columns}

Instancia un nuevo objeto `Table`, y utiliza uno de los métodos de fábrica proporcionados para agregar las columnas deseadas a la `Table` recién creada:

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
Alternativamente, el método `setItems()` puede recibir cualquier colección válida de Java, lo que creará una `CollectionRepository` internamente para ti. 
:::

A continuación se muestra un ejemplo de los pasos anteriores implementados para crear un componente `Table` básico:


<ComponentDemo 
path='/webforj/tablebasic?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableBasicView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>

## Estilo

<TableBuilder name="Table" />
