---
sidebar_position: 20
title: Lists
hide_giscus_comments: true
_i18n_hash: 15effbe238b9ea86c975499ed2faa30b
---
<JavadocLink type="foundation" location="com/webforj/component/list/DwcList"/>

:::info
Esta sección describe características comunes de todos los componentes de lista, y no es una clase que se pueda instanciar o usar directamente.
:::

Hay tres tipos de listas disponibles para su uso dentro de tus aplicaciones: [`ListBox`](listbox), [`ChoiceBox`](choicebox) y [`ComboBox`](combobox). Estos componentes muestran una lista de elementos clave-valor, y proporcionan métodos para agregar, eliminar, seleccionar y gestionar los elementos dentro de la lista.

Esta página describe las características y el comportamiento compartidos de todos los componentes de lista, mientras que los detalles específicos para cada uno se cubren en sus respectivas páginas.

## Usando `ListItem` {#using-listitem}

Los componentes de lista están compuestos por <JavadocLink type="foundation" location="com/webforj/component/list/ListItem"  code="true">ListItem</JavadocLink> objetos, que representan elementos individuales dentro de una lista. Cada <JavadocLink type="foundation" location="com/webforj/component/list/ListItem"  code="true">ListItem</JavadocLink> está asociado con una clave única y un texto para mostrar. Las características importantes de la clase <JavadocLink type="foundation" location="com/webforj/component/list/ListItem"  code="true">ListItem</JavadocLink> incluyen:

- Un <JavadocLink type="foundation" location="com/webforj/component/list/ListItem"  code="true">ListItem</JavadocLink> encapsula una clave única `Object` y un texto `String` que se muestra dentro del componente de lista. 
- Puedes construir un <JavadocLink type="foundation" location="com/webforj/component/list/ListItem"  code="true">ListItem</JavadocLink> proporcionando una clave y un texto, o especificando solo el texto para que se genere una clave aleatoria.

## Gestionando objetos `ListItem` con la API {#managing-listitem-objects-with-the-api}

Los diversos componentes de lista ofrecen varios métodos para gestionar la lista de elementos y mantener un estado consistente entre la lista y el cliente. Al usar estos métodos, puedes gestionar efectivamente los elementos dentro de la lista. La API te permite interactuar y manipular la lista para satisfacer los requisitos de tu aplicación.

### Agregar elementos {#adding-items}

- **Agregar un elemento**:

   - Para agregar un `ListItem` a la lista, puedes usar el <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#add(com.webforj.component.list.ListItem)' code="true">add(ListItem item)</JavadocLink> método.
   - También puedes agregar un nuevo `ListItem` especificando la clave y el texto usando el <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#add(java.lang.Object,java.lang.String)' code="true">add(Object key, String text)</JavadocLink> o <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#add(java.lang.String)' code="true">add(String text)</JavadocLink> método.


- **Insertar un elemento en un índice específico:**

   - Para insertar un elemento en un índice específico, usa el <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#insert(int,com.webforj.component.list.ListItem)' code="true">insert(int index, ListItem item)</JavadocLink> método.
   - Puedes insertar un elemento con clave y texto usando el <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#insert(int,java.lang.Object,java.lang.String)' code="true">insert(int index, Object key, String text)</JavadocLink> o <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#insert(int,java.lang.String)' code="true">insert(int index, String text)</JavadocLink> método.

- **Insertar múltiples elementos:** 

   - Puedes insertar múltiples elementos en un índice especificado usando el <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#insert(int,java.util.List)' code="true">insert(int index, List< ListItem > items)</JavadocLink> método.

:::tip
Para optimizar el rendimiento, en lugar de activar un mensaje de servidor a cliente cada vez que usas el método `add()`, es más eficiente crear primero una lista de <JavadocLink type="foundation" location="com/webforj/component/list/ListItem"  code="true">ListItem</JavadocLink> objetos. Una vez que tengas esta lista, puedes agregarlos todos de una vez usando el método `insert(int index, List<ListItem> items)`. Este enfoque reduce la comunicación entre el servidor y el cliente, mejorando la eficiencia general. Para pautas detalladas sobre esto y otras mejores prácticas en la arquitectura webforJ, consulta [Interacción Cliente/Servidor](/docs/architecture/client-server).
:::

### Eliminando elementos {#removing-items}

- **Eliminar un elemento:**

   - Para eliminar un elemento de la lista, usa el <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#remove(int)' code="true">remove(int index)</JavadocLink> o <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#remove(java.lang.Object)' code="true">remove(Object key)</JavadocLink> método.

- **Eliminar todos los elementos:**
   - Puedes eliminar todos los elementos de la lista usando <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#removeAll()' code="true">removeAll()</JavadocLink>.

### Seleccionando elementos {#selecting-items}

Todos los tipos de lista implementan la interfaz `SelectableList`. Esta interfaz permite múltiples formas diferentes de seleccionar el `ListItem` actual.

#### Con un `ListItem` dado {#with-a-given-listitem}

<JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#select(com.webforj.component.list.ListItem)' code="true">select(ListItem item)</JavadocLink> toma un `ListItem` como parámetro para seleccionar.

```java {4}
List demoList = new List();
ListItem demoItem = new ListItem("demo","Elemento de Demostración");
demoList.add(demoItem);
demoList.select(demoItem);
```

#### Con una clave dada de un `ListItem` {#with-a-given-key-of-a-listitem}

<JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#selectKey(java.lang.Object)' code="true">selectKey(Object key)</JavadocLink> toma una clave de un `ListItem` como parámetro para seleccionar.

```java {3}
List demoList = new List();
demoList.add("demo","Elemento de Demostración");
demoList.selectKey("demo");
```

#### Con un índice dado de un `ListItem` {#with-a-given-index-of-a-listitem}

<JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#selectIndex(int)' code="true">selectIndex(int index)</JavadocLink> toma un índice de un `ListItem` como parámetro para seleccionar.

```java {3}
List demoList = new List();
demoList.add("demo","Elemento de Demostración");
demoList.selectKey(0);
```

### Otras operaciones de lista {#other-list-operations}

- **Accediendo y actualizando elementos:**

   - Para acceder a elementos por clave o índice, usa <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#getByKey(java.lang.Object)' code="true">getByKey(Object key)</JavadocLink> o <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#getByIndex(int)' code="true">getByIndex(int index)</JavadocLink>.
   - Puedes actualizar el texto de un elemento usando el <JavadocLink type="foundation" location="com/webforj/component/list/ListItem" suffix='#setText(java.lang.String)' code="true">setText(String text)</JavadocLink> método dentro de la clase <JavadocLink type="foundation" location="com/webforj/component/list/ListItem"  code="true">ListItem</JavadocLink>.

- **Recuperando información sobre la lista:**
   - Puedes obtener el tamaño de la lista usando el <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#size()' code="true">size()</JavadocLink> método.
   - Para verificar si la lista está vacía, usa el <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#isEmpty()' code="true">isEmpty()</JavadocLink> método.

### Iterando sobre listas {#iterating-over-lists}

Todos los componentes de lista implementan la interfaz Java [`Iteratable`](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/lang/Iterable.html), proporcionando una manera eficiente e intuitiva de iterar a través de los contenidos de una lista. Con esta interfaz, puedes recorrer fácilmente cada `ListItem`, lo que hace simple acceder, modificar o realizar acciones en cada elemento con un esfuerzo mínimo. La interfaz `Iterable` es un patrón estándar del lenguaje Java, asegurando que tu código sea familiar y mantenible para cualquier desarrollador de Java.

El siguiente fragmento de código demuestra dos formas sencillas de iterar a través de una lista:

```java
list.forEach(item -> {
   item.setText("Modificado: " + item.getText());
});

for (ListItem item : list) {
   item.setText("Modificado2: " + item.getText());
}
```

## Propiedades compartidas de lista {#shared-list-properties}

### Etiqueta {#label}

Todos los componentes de lista pueden recibir una etiqueta, que es un texto descriptivo o título asociado con el componente. Las etiquetas proporcionan una breve explicación o indicación para ayudar a los usuarios a entender el propósito o la selección esperada para esa lista en particular. Además de su importancia para la usabilidad, las etiquetas de lista también juegan un papel crucial en la accesibilidad, permitiendo que los lectores de pantalla y las tecnologías asistidas proporcionen información precisa y faciliten la navegación por teclado.

### Texto de ayuda {#helper-text}

Cada componente de lista puede mostrar texto de ayuda debajo de la lista usando el método `setHelperText()`. Este texto de ayuda ofrece contexto adicional o explicaciones sobre las opciones disponibles, asegurando que los usuarios tengan la información necesaria para hacer selecciones informadas.

### Alineación horizontal {#horizontal-alignment}

Todos los componentes de lista implementan la interfaz <JavadocLink type="foundation" location="com/webforj/concern/HasHorizontalAlignment" code='true'>HasHorizontalAlignment</JavadocLink>, dándote control sobre cómo se alinean el texto y el contenido dentro del componente.

Usa el método `setHorizontalAlignment()` para establecer la alineación:

- `HorizontalAlignment.LEFT` (por defecto)
- `HorizontalAlignment.MIDDLE`
- `HorizontalAlignment.RIGHT`

```java
ListBox<String> listBox = new ListBox<>();
listBox.setHorizontalAlignment(HorizontalAlignment.LEFT);
```

Para obtener la alineación actual:
```java
HorizontalAlignment alignment = listBox.getHorizontalAlignment();
```

### Expansiones {#expanses}

Todos los componentes de lista en webforJ también implementan la interfaz <JavadocLink type="foundation" location="com/webforj/concern/HasExpanse" code='true'>HasExpanse</JavadocLink>, permitiéndote ajustar el tamaño general y el peso visual del componente. Esto es útil para adaptar el componente a varios contextos de UI, como formularios, diálogos, barras laterales, etc.

Usa el método `setExpanse()` para establecer el nivel de expansión. Las opciones incluyen:

- `Expanse.NONE`
- `Expanse.XSMALL`
- `Expanse.SMALL`
- `Expanse.MEDIUM` (por defecto)
- `Expanse.LARGE`
- `Expanse.XLARGE`

```java
ListBox<String> listBox = new ListBox<>();
listBox.setExpanse(Expanse.LARGE);
```

Puedes recuperar la configuración actual usando:
```java
Expanse current = listBox.getExpanse();
```

## Temas {#topics}

<DocCardList className="topics-section" />
