---
title: Tree
sidebar_position: 150
_i18n_hash: b161d0d5855f65cb593cf23bc2695d5b
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-tree" />
<DocChip chip='since' label='25.01' />
<JavadocLink type="foundation" location="com/webforj/component/tree/Tree" top='true'/>

El componente `Tree` organiza datos como una jerarquía de nodos. Cada nodo contiene una clave única y una etiqueta. Los nodos se conectan para formar relaciones de padre e hijo. Puedes expandir o colapsar nodos para mostrar u ocultar sus hijos. Los íconos aclaran qué tipo de nodo estás viendo y si está seleccionado. La selección admite la elección de un nodo o varios a la vez.

## Modelo de nodo y estructura del árbol {#node-model-and-tree-structure}

### El papel de `TreeNode` {#the-role-of-treenode}

Cada pieza de datos en el árbol está envuelta en un `TreeNode`. Este objeto contiene la clave, la etiqueta de texto y enlaces a sus nodos padre e hijos. El nodo raíz es especial: existe en cada árbol pero no es visible. Sirve como contenedor para todos los nodos de nivel superior, haciendo que la estructura del árbol sea más fácil de gestionar internamente.

Dado que los nodos mantienen referencias a sus padres e hijos, recorrer el árbol es sencillo. Ya sea que quieras moverte hacia arriba, hacia abajo o encontrar un nodo específico por clave, las conexiones siempre están accesibles.

### Creación y gestión de nodos {#node-creation-and-management}

Los nodos se crean utilizando métodos de fábrica simples, ya sea proporcionando una clave y texto o solo texto (que actúa como la clave). Esto garantiza que cada nodo sea válido y fácilmente identificable.

Agregar nodos al árbol implica llamar a `add()` o `insert()` en un nodo padre. Estos métodos manejan la asignación de la referencia del padre y notifican al árbol para actualizar su UI.

Ejemplo:

```java
TreeNode parent = Tree.node("Parent");
TreeNode child = Tree.node("Child");
parent.add(child);
tree.add(parent);
```

:::info Solo un padre
Intentar asignar el mismo nodo a más de un padre resultará en una excepción. Esta salvaguarda asegura que el árbol mantenga una jerarquía adecuada al prevenir que los nodos tengan múltiples padres, lo que rompería la integridad de la estructura y causaría comportamientos inesperados.
:::

<ComponentDemo 
path='/webforj/tree?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tree/TreeView.java'
height='300px'
/>

### Modificando nodos {#modifying-nodes}

Actualizas la etiqueta de un nodo llamando a `setText(String text)`. Este método cambia el texto mostrado para el nodo en el árbol.

Para eliminar un nodo hijo específico, usa `remove(TreeNode child)`. Esto separa al hijo de su padre y lo elimina de la estructura del árbol. También borra la referencia del padre.

Si deseas limpiar todos los hijos de un nodo, llama a `removeAll()`. Esto elimina cada nodo hijo, borra sus referencias de padre y vacía la lista de hijos.

Cada nodo admite almacenar información adicional en el lado del servidor usando `setUserData(Object key, Object data)`. Esto te permite asociar metadatos o referencias arbitrarias con el nodo, sin exponer estos datos al cliente o a la UI.

:::tip Usando la demostración para editar el texto del nodo
En la demostración, haz doble clic en un nodo para abrir un editor para su texto. Ingresa el nuevo texto y guárdalo para actualizar la etiqueta del nodo en el árbol.
:::

<ComponentDemo 
path='/webforj/treemodify?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tree/TreeModifyView.java'
height='320px'
/>

## Íconos {#icons}

Los íconos proporcionan pistas visuales sobre lo que representan los nodos y su estado. Mejoran la legibilidad al distinguir tipos de nodos y el estado de selección de un vistazo. El componente `Tree` admite la configuración de íconos predeterminados a nivel global, personalización de íconos por nodo y alternancia de la visibilidad de íconos.

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
Un ícono puede ser cualquier definición de [ícono](./icon) válida de webforJ o un archivo de recurso cargado a través de [protocolos de activos soportados](../managing-resources/assets-protocols) de webforJ.
:::

### Íconos por nodo {#per-node-icons}

Puedes sobrescribir los valores predeterminados globales asignando íconos a nodos individuales. Esto es útil cuando ciertos nodos representan conceptos diferentes, como carpetas de "proyecto" o archivos especiales.

Ejemplo:

```java
node.setIcon(TablerIcon.create("project"));
node.setSelectedIcon(TablerIcon.create("project-selected"));
```

### Visibilidad de íconos {#icon-visibility}

A veces, es posible que desees ocultar íconos para grupos o hojas para reducir el desorden. El componente te permite alternar la visibilidad a nivel global para estas categorías, permitiéndote simplificar la apariencia del árbol sin perder su estructura.

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

Los nodos pueden expandirse o colapsarse para controlar qué partes del árbol son visibles. Esto permite centrarse en secciones relevantes y admite escenarios como carga diferida o actualizaciones dinámicas de datos.

### Operaciones de expandir y colapsar {#expand-and-collapse-operations}

El árbol admite la expansión y el colapso de nodos individuales por su clave o referencia directa. También puedes expandir o colapsar todos los descendientes de un nodo a la vez.

Estas operaciones te permiten controlar cuánto del árbol es visible y admiten la carga diferida de datos o el enfoque en áreas de interés.

Ejemplo:

```java
tree.expand(node);
tree.collapse(key);

// colapsar subárboles
tree.expandFrom(node);
tree.collapseFrom(node);
```

:::info Colapsando la raíz
El nodo raíz ancla el árbol pero permanece oculto. Colapsar la raíz normalmente ocultaría todo, haciendo que el árbol aparezca vacío. Para evitar esto, colapsar la raíz realmente colapsa todos sus hijos pero mantiene la raíz expandida internamente, asegurando que el árbol aún muestre su contenido correctamente.
:::

### Carga diferida de nodos {#lazy-loading-nodes}

El árbol admite la carga diferida de hijos de nodos reaccionando a eventos de expansión. Cuando un usuario expande un nodo, tu aplicación puede cargar o generar los hijos de ese nodo dinámicamente. Esto mejora el rendimiento al cargar solo las partes visibles del árbol bajo demanda.

Utiliza el evento `onExpand` para detectar cuándo se expande un nodo. Dentro del controlador, verifica si los hijos del nodo son marcadores de posición (por ejemplo, un spinner o nodo vacío) y reemplázalos con datos reales una vez cargados.

<ComponentDemo 
path='/webforj/treelazyload?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tree/TreeLazyLoadView.java'
height='250px'
/>

## Selección {#selection}

La selección controla qué nodos son elegidos por el usuario. El componente `Tree` admite modos flexibles y APIs para seleccionar, deseleccionar y consultar nodos.

### Modos de selección {#selection-modes}

Puedes elegir si el árbol permite seleccionar un solo nodo a la vez o múltiples nodos simultáneamente. Cambiar de selección múltiple a única automáticamente deselecciona todos menos el primer nodo seleccionado.

Ejemplo:

```java
tree.setSelectionMode(Tree.SelectionMode.SINGLE);
```

:::tip Interacción de selección múltiple
Cuando el árbol está configurado en modo de selección múltiple, los usuarios pueden seleccionar más de un nodo a la vez. La forma en que esto funciona depende del dispositivo:

* **Escritorio (ratón y teclado):** Los usuarios mantienen presionada la tecla **Ctrl** (o la tecla **Cmd** en macOS) y hacen clic en los nodos para agregar o quitar de la selección actual. Esto permite seleccionar múltiples nodos individuales sin deseleccionar otros.
* **Dispositivos móviles y táctiles:** Dado que no están disponibles las teclas modificadoras, los usuarios simplemente tocan los nodos para seleccionarlos o deseleccionarlos. Cada toque alterna el estado de selección de ese nodo, lo que facilita la selección múltiple a través de toques simples.
:::

### Selección y deselección {#selecting-and-deselecting}

Los nodos pueden ser seleccionados o deseleccionados por referencia, clave, individualmente o en lotes. También puedes seleccionar o deseleccionar todos los hijos de un nodo de una sola vez.

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

Puedes obtener la selección actual utilizando el código que se muestra a continuación:

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

## Estilo {#styling}

<TableBuilder name="Tree" />
