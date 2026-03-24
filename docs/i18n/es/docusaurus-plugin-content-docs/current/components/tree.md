---
title: Tree
sidebar_position: 150
_i18n_hash: 280fb07f73ba1172b33bd0617ded7876
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-tree" />
<DocChip chip='since' label='25.01' />
<JavadocLink type="foundation" location="com/webforj/component/tree/Tree" top='true'/>

El componente `Tree` organiza los datos como una jerarquía de nodos. Cada nodo contiene una clave única y una etiqueta. Los nodos se conectan para formar relaciones padre-hijo. Puedes expandir o colapsar nodos para mostrar u ocultar sus hijos. Los íconos clarifican qué tipo de nodo estás visualizando y si está seleccionado. La selección permite elegir un nodo o varios a la vez.

<!-- INTRO_END -->

## Modelo de nodo y estructura de árbol {#node-model-and-tree-structure}

### El rol de `TreeNode` {#the-role-of-treenode}

Cada pieza de datos en el árbol está envuelta en un `TreeNode`. Este objeto contiene la clave, la etiqueta de texto y los enlaces a sus nodos padre e hijos. El nodo raíz es especial: existe en cada árbol pero no es visible. Sirve como el contenedor para todos los nodos de nivel superior, facilitando la gestión interna de la estructura del árbol.

Dado que los nodos mantienen referencias a sus padres e hijos, recorrer el árbol es sencillo. Ya sea que quieras moverte hacia arriba, hacia abajo o encontrar un nodo específico por clave, las conexiones siempre están accesibles.

### Creación y gestión de nodos {#node-creation-and-management}

Los nodos se crean utilizando métodos de fábrica simples, ya sea proporcionando una clave y texto o solo texto (que también actúa como la clave). Esto garantiza que cada nodo sea válido y identificable de manera única.

Agregar nodos al árbol implica llamar a `add()` o `insert()` en un nodo padre. Estos métodos se encargan de asignar la referencia del padre y notificar al árbol para que actualice su interfaz de usuario.

Ejemplo:

```java
TreeNode parent = Tree.node("Parent");
TreeNode child = Tree.node("Child");
parent.add(child);
tree.add(parent);
```

:::info Solo un padre
Intentar asignar el mismo nodo a más de un padre resultará en una excepción. Esta salvaguarda asegura que el árbol mantenga una jerarquía adecuada al evitar que los nodos tengan múltiples padres, lo que rompería la integridad de la estructura y causaría comportamientos inesperados.
:::

<ComponentDemo 
path='/webforj/tree?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tree/TreeView.java'
height='300px'
/>

### Modificación de nodos {#modifying-nodes}

Puedes actualizar la etiqueta de un nodo llamando a `setText(String text)`. Este método cambia el texto mostrado para el nodo en el árbol.

Para eliminar un nodo hijo específico, utiliza `remove(TreeNode child)`. Esto separa al hijo de su padre y lo elimina de la estructura del árbol. También se borra la referencia del padre.

Si deseas eliminar todos los hijos de un nodo, llama a `removeAll()`. Esto elimina cada nodo hijo, borra sus referencias de padre y vacía la lista de hijos.

Cada nodo admite almacenar información adicional en el lado del servidor utilizando `setUserData(Object key, Object data)`. Esto te permite asociar metadatos o referencias arbitrarias con el nodo, sin exponer estos datos al cliente o a la interfaz de usuario.

:::tip Uso de la demostración para editar el texto del nodo
En la demostración, haz doble clic en un nodo para abrir un editor para su texto. Ingresa el nuevo texto y guárdalo para actualizar la etiqueta del nodo en el árbol.
:::

<ComponentDemo 
path='/webforj/treemodify?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tree/TreeModifyView.java'
height='320px'
/>

## Íconos {#icons}

Los íconos proporcionan pistas visuales sobre lo que representan los nodos y su estado. Mejoran la legibilidad al distinguir los tipos de nodos y el estado de selección de un vistazo. El componente `Tree` admite establecer íconos predeterminados globalmente, personalizar íconos por nodo y alternar la visibilidad de los íconos.

### Íconos globales {#global-icons}

El árbol te permite establecer íconos predeterminados para grupos colapsados, grupos expandidos, nodos hoja e hojas seleccionadas.

Ejemplo:

```java
tree.setCollapsedIcon(TablerIcon.create("folder"));
tree.setExpandedIcon(TablerIcon.create("folder-opened"));
tree.setLeafIcon(TablerIcon.create("file"));
tree.setLeafSelectedIcon(TablerIcon.create("file-checked"));
```

:::tip Fuentes de íconos
Un ícono puede ser cualquier definición de ícono válida de webforJ [icon](./icon) o un archivo de recurso cargado a través de un [protocolo de activos soportado](../managing-resources/assets-protocols) por webforJ.
:::

### Íconos por nodo {#per-node-icons}

Puedes anular los valores predeterminados globales asignando íconos a nodos individuales. Esto es útil cuando ciertos nodos representan conceptos diferentes, como carpetas de "proyecto" o archivos especiales.

Ejemplo:

```java
node.setIcon(TablerIcon.create("project"));
node.setSelectedIcon(TablerIcon.create("project-selected"));
```

### Visibilidad de íconos {#icon-visibility}

A veces, es posible que desees ocultar íconos para grupos o hojas para reducir el desorden. El componente te permite alternar la visibilidad globalmente para estas categorías, lo que te permite simplificar la apariencia del árbol sin perder la estructura.

Ejemplo:

```java
tree.setGroupIconsVisible(false);
tree.setLeafIconsVisible(false);
```

<ComponentDemo 
path='/webforj/treeicons?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tree/TreeIconsView.java'
height='320px'
/>

## Expansión y colapso de nodos {#node-expansion-and-collapse}

Los nodos pueden expandirse o colapsarse para controlar qué partes del árbol son visibles. Esto permite centrarse en secciones relevantes y admite escenarios como la carga perezosa o actualizaciones de datos dinámicas.

### Operaciones de expandir y colapsar {#expand-and-collapse-operations}

El árbol admite la expansión y el colapso de nodos individuales por su clave o referencia directa. También puedes expandir o colapsar todos los descendientes de un nodo a la vez.

Estas operaciones te permiten controlar cuánto del árbol es visible y respaldan la carga perezosa de datos o el enfoque en áreas de interés.

Ejemplo:

```java
tree.expand(node);
tree.collapse(key);

// colapsar subárboles
tree.expandFrom(node);
tree.collapseFrom(node);
```

:::info Colapsando la raíz
El nodo raíz ancla el árbol pero permanece oculto. Colapsar la raíz normalmente ocultaría todo, haciendo que el árbol parezca vacío. Para evitar esto, colapsar la raíz colapsa todos sus hijos, pero mantiene la raíz expandida internamente, asegurando que el árbol siga mostrando su contenido correctamente.
:::

### Carga perezosa de nodos {#lazy-loading-nodes}

El árbol admite la carga perezosa de nodos hijos reaccionando a eventos de expansión. Cuando un usuario expande un nodo, tu aplicación puede cargar o generar los hijos de ese nodo dinámicamente. Esto mejora el rendimiento al cargar solo las partes visibles del árbol bajo demanda.

Utiliza el evento `onExpand` para detectar cuándo se expande un nodo. Dentro del manejador, verifica si los hijos del nodo son marcadores de posición (por ejemplo, un spinner o nodo vacío) y reemplázalos con datos reales una vez cargados.

<ComponentDemo 
path='/webforj/treelazyload?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tree/TreeLazyLoadView.java'
height='250px'
/>

## Selección {#selection}

La selección controla qué nodos son elegidos por el usuario. El componente `Tree` admite modos flexibles y API para seleccionar, deseleccionar y consultar nodos.

### Modos de selección {#selection-modes}

Puedes elegir si el árbol permite seleccionar un nodo a la vez o múltiples nodos simultáneamente. Cambiar de selección múltiple a simple selecciona automáticamente todos menos el primer nodo seleccionado.

Ejemplo:

```java
tree.setSelectionMode(Tree.SelectionMode.SINGLE);
```

:::tip Interacción de selección múltiple
Cuando el árbol está configurado en modo de selección múltiple, los usuarios pueden seleccionar más de un nodo al mismo tiempo. La forma en que esto funciona depende del dispositivo:

* **Escritorio (ratón y teclado):** Los usuarios mantienen presionada la tecla **Ctrl** (o la tecla **Cmd** en macOS) y hacen clic en nodos para agregarlos o eliminarlos de la selección actual. Esto permite seleccionar múltiples nodos individuales sin deseleccionar otros.
* **Dispositivos móviles y táctiles:** Dado que no están disponibles las teclas modificadoras, los usuarios simplemente tocan los nodos para seleccionarlos o deseleccionarlos. Cada toque alterna el estado de selección de ese nodo, permitiendo una fácil selección múltiple a través de toques simples.
:::

### Seleccionar y deseleccionar {#selecting-and-deselecting}

Los nodos pueden ser seleccionados o deseleccionados por referencia, clave, individualmente o en lotes. También puedes seleccionar o deseleccionar todos los hijos de un nodo en una sola llamada.

Ejemplo:

```java
// seleccionar nodo por referencia o clave
tree.select(node);
tree.selectKey(key);

// deseleccionar nodo por referencia o clave
tree.deselect(node);
tree.deselectAll();

// seleccionando o deseleccionando hijos de nodos
tree.selectChildren(parentNode);
tree.deselectChildren(parentNode);
```

### Recuperación del estado de selección {#selection-state-retrieval}

Puedes obtener la selección actual utilizando el código mostrado a continuación:

```java
// obtener la referencia del nodo seleccionado
TreeNode selected = tree.getSelected();
List<TreeNode> selectedItems = tree.getSelectedItems();

// obtener la clave del nodo seleccionado
Object selectedKey = tree.getSelectedKey();
List<Object> selectedKeys = tree.getSelectedKeys();
```

<ComponentDemo 
path='/webforj/treeselection?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tree/TreeSelectionView.java'
height='400px'
/>

## Estilización {#styling}

<TableBuilder name="Tree" />
