---
title: Tree
sidebar_position: 150
_i18n_hash: 8f653af18f5e041d09896794f560d30a
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-tree" />
<DocChip chip='since' label='25.01' />
<JavadocLink type="foundation" location="com/webforj/component/tree/Tree" top='true'/>

El componente `Tree` organiza datos como una jerarquía de nodos. Cada nodo tiene una clave única y una etiqueta. Los nodos se conectan para formar relaciones padre-hijo. Puedes expandir o colapsar nodos para mostrar u ocultar sus hijos. Los íconos aclaran qué tipo de nodo estás viendo y si está seleccionado. La selección permite elegir un nodo o varios a la vez.

## Modelo de nodo y estructura de árbol {#node-model-and-tree-structure}

### El rol de `TreeNode` {#the-role-of-treenode}

Cada pieza de datos en el árbol está envuelta en un `TreeNode`. Este objeto contiene la clave, la etiqueta de texto y enlaces a sus nodos padre e hijos. El nodo raíz es especial: existe en cada árbol pero no es visible. Sirve como contenedor para todos los nodos de primer nivel, facilitando la gestión interna de la estructura del árbol.

Debido a que los nodos mantienen referencias a sus padres e hijos, recorrer el árbol es sencillo. Ya sea que desees mover hacia arriba, hacia abajo, o encontrar un nodo específico por clave, las conexiones siempre son accesibles.

### Creación y gestión de nodos {#node-creation-and-management}

Los nodos se crean utilizando métodos de fábrica simples, ya sea proporcionando una clave y texto o solo texto (que sirve como clave). Esto garantiza que cada nodo sea válido y identificable de manera única.

Agregar nodos al árbol implica llamar a `add()` o `insert()` en un nodo padre. Estos métodos manejan la asignación de la referencia padre y notifican al árbol para actualizar su interfaz de usuario.

Ejemplo:

```java
TreeNode parent = Tree.node("Padre");
TreeNode child = Tree.node("Hijo");
parent.add(child);
tree.add(parent);
```

:::info Solo un Padre
Intentar asignar el mismo nodo a más de un padre resultará en una excepción. Esta salvaguarda asegura que el árbol mantenga una jerarquía apropiada al evitar que los nodos tengan múltiples padres, lo que rompería la integridad de la estructura y causaría comportamientos inesperados.
:::

<ComponentDemo 
path='/webforj/tree?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tree/TreeView.java'
height='300px'
/>

### Modificando nodos {#modifying-nodes}

Puedes actualizar la etiqueta de un nodo llamando a `setText(String text)`. Este método cambia el texto mostrado para el nodo en el árbol.

Para eliminar un nodo hijo específico, utiliza `remove(TreeNode child)`. Esto desvincula al hijo de su padre y lo elimina de la estructura del árbol. También borra la referencia al padre.

Si deseas eliminar todos los hijos de un nodo, llama a `removeAll()`. Esto elimina cada nodo hijo, limpia sus referencias de padre y vacía la lista de hijos.

Cada nodo admite el almacenamiento de información adicional en el lado del servidor utilizando `setUserData(Object key, Object data)`. Esto te permite asociar metadatos o referencias arbitrarias con el nodo, sin exponer estos datos al cliente o a la interfaz de usuario.

:::tip Usando la Demo para Editar el Texto del Nodo
En la demo, haz doble clic en un nodo para abrir un editor para su texto. Ingresa el nuevo texto y guárdalo para actualizar la etiqueta del nodo en el árbol.
:::

<ComponentDemo 
path='/webforj/treemodify?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tree/TreeModifyView.java'
height='320px'
/>

## Íconos {#icons}

Los íconos proporcionan pistas visuales sobre lo que representan los nodos y su estado. Mejoran la legibilidad al distinguir tipos de nodos y el estado de selección de un vistazo. El componente `Tree` admite la configuración de íconos predeterminados a nivel global, la personalización de íconos por nodo y la alternancia de la visibilidad de íconos.

### Íconos globales {#global-icons}

El árbol te permite establecer íconos predeterminados para grupos colapsados, grupos expandidos, nodos hoja e hojas seleccionadas.

Ejemplo:

```java
tree.setCollapsedIcon(TablerIcon.create("folder"));
tree.setExpandedIcon(TablerIcon.create("folder-opened"));
tree.setLeafIcon(TablerIcon.create("file"));
tree.setLeafSelectedIcon(TablerIcon.create("file-checked"));
```

:::tip Fuentes de Íconos
Un ícono puede ser cualquier definición de [ícono](./icon) válida de webforJ o un archivo de recurso cargado a través de un [protocolo de activos soportados](../managing-resources/assets-protocols) por webforJ.
:::

### Íconos por nodo {#per-node-icons}

Puedes sobrescribir los valores predeterminados globales asignando íconos a nodos individuales. Esto es útil cuando ciertos nodos representan conceptos diferentes, como carpetas de “proyectos” o archivos especiales.

Ejemplo:

```java
node.setIcon(TablerIcon.create("project"));
node.setSelectedIcon(TablerIcon.create("project-selected"));
```

### Visibilidad de íconos {#icon-visibility}

A veces, es posible que desees ocultar íconos para grupos o hojas para reducir el desorden. El componente te permite alternar la visibilidad a nivel global para estas categorías, lo que te permite simplificar la apariencia del árbol sin perder la estructura.

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

Los nodos pueden ser expandido o colapsados para controlar qué partes del árbol son visibles. Esto permite centrarse en secciones relevantes y soporta escenarios como carga perezosa o actualizaciones de datos dinámicos.

### Operaciones de expansión y colapso {#expand-and-collapse-operations}

El árbol admite la expansión y el colapso de nodos individuales ya sea por su clave o referencia directa. También puedes expandir o colapsar todos los descendientes de un nodo a la vez.

Estas operaciones te permiten controlar cuánto del árbol es visible y soportan la carga perezosa de datos o el enfoque en áreas de interés.

Ejemplo:

```java
tree.expand(node);
tree.collapse(key);

// colapsar subárboles
tree.expandFrom(node);
tree.collapseFrom(node);
```

:::info Colapsando la raíz
El nodo raíz ancla el árbol pero permanece oculto. Colapsar la raíz normalmente ocultaría todo, haciendo que el árbol parezca vacío. Para evitar esto, colapsar la raíz colapsa en realidad a todos sus hijos pero mantiene la raíz expandida internamente, asegurando que el árbol aún muestre su contenido correctamente.
:::

### Carga perezosa de nodos {#lazy-loading-nodes}

El árbol soporta la carga perezosa de hijos de nodo al reaccionar a eventos de expansión. Cuando un usuario expande un nodo, tu aplicación puede cargar o generar dinámicamente los hijos de ese nodo. Esto mejora el rendimiento al cargar solo las partes visibles del árbol bajo demanda.

Utiliza el evento `onExpand` para detectar cuando un nodo es expandido. Dentro del manejador, verifica si los hijos del nodo son marcadores de posición (por ejemplo, un spinner o un nodo vacío) y reemplázalos con datos reales una vez cargados.

<ComponentDemo 
path='/webforj/treelazyload?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tree/TreeLazyLoadView.java'
height='250px'
/>

## Selección {#selection}

La selección controla qué nodos son elegidos por el usuario. El componente `Tree` admite modos flexibles y APIs para seleccionar, deseleccionar y consultar nodos.

### Modos de selección {#selection-modes}

Puedes elegir si el árbol permite seleccionar un solo nodo a la vez o múltiples nodos simultáneamente. Cambiar de selección múltiple a única deselecciona automáticamente todos excepto el primer nodo seleccionado.

Ejemplo:

```java
tree.setSelectionMode(Tree.SelectionMode.SINGLE);
```

:::tip Interacción de selección múltiple
Cuando el árbol está configurado en modo de selección múltiple, los usuarios pueden seleccionar más de un nodo a la vez. La forma en que esto funciona depende del dispositivo:

* **Escritorio (ratón y teclado):** Los usuarios mantienen presionada la tecla **Ctrl** (o la tecla **Cmd** en macOS) y hacen clic en nodos para agregar o quitar de la selección actual. Esto permite seleccionar múltiples nodos individuales sin deseleccionar otros.
* **Dispositivos móviles y táctiles:** Como las teclas modificadoras no están disponibles, los usuarios simplemente tocan nodos para seleccionarlos o deseleccionarlos. Cada toque alterna el estado de selección de ese nodo, permitiendo una fácil selección múltiple a través de simples toques.
:::

### Seleccionando y deseleccionando {#selecting-and-deselecting}

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

## Estilo {#styling}

<TableBuilder name="Tree" />
