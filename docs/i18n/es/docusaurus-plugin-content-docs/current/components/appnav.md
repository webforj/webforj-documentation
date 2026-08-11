---
title: AppNav
sidebar_position: 6
sidebar_class_name: new-content
description: >-
  Build hierarchical side navigation menus with AppNav and AppNavItem, linking
  to routes, registered views, or external URLs.
_i18n_hash: 7283cd36346dd18b131a5393db8e8fd3
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-app-nav" />
<DocChip chip="name" label="dwc-app-nav-item" />
<DocChip chip='since' label='24.12' />
<JavadocLink type="appnav" location="com/webforj/component/appnav/AppNav" top='true'/>

El componente `AppNav` crea un menú de navegación lateral a partir de las entradas de `AppNavItem`. Los elementos pueden enlazar a vistas internas o recursos externos, anidarse bajo elementos principales para formar menús jerárquicos y llevar íconos, insignias u otros componentes para proporcionar a los usuarios más contexto de un vistazo.

<!-- INTRO_END -->

## Agregar y anidar elementos {#adding-and-nesting-items}

Las instancias de `AppNavItem` se utilizan para poblar la estructura de `AppNav`. Estos elementos pueden ser enlaces simples o encabezados de grupos anidados que contienen elementos secundarios. Los encabezados de grupo sin enlaces actúan como contenedores expandibles.

Utiliza `addItem()` para incluir elementos en la navegación:

```java
AppNavItem dashboard = new AppNavItem("Dashboard", "/dashboard");
AppNavItem admin = new AppNavItem("Admin");
admin.addItem(new AppNavItem("Users", "/admin/users"));
admin.addItem(new AppNavItem("Settings", "/admin/settings"));

AppNav nav = new AppNav();
nav.addItem(dashboard);
nav.addItem(admin);
```

:::tip Enlazando elementos de grupo
Los elementos de nivel superior en un árbol de navegación suelen estar destinados a ser expandibles, no enlaces clicables. Establecer una `path` en tales elementos puede confundir a los usuarios que esperan que revelen subelementos en lugar de navegar a otro lado.

Si deseas que el encabezado del grupo dispare una acción personalizada (como abrir documentos externos), mantén la ruta del grupo vacía y en su lugar añade un control interactivo como un [`IconButton`](./icon#icon-buttons) al sufijo del elemento. Esto mantiene la experiencia del usuario consistente y limpia.
:::

<!--vale off-->
<ComponentDemo
path='/webforj/appnav/Social'
frame='desktop'
files={[
  'src/main/java/com/webforj/samples/views/appnav/AppNavView.java',
  'src/main/java/com/webforj/samples/views/appnav/AppNavPageView.java',
]}
/>
<!--vale on-->

## Enlazando elementos {#linking-items}

Cada `AppNavItem` puede navegar a una vista interna o un enlace externo. Puedes definir esto utilizando rutas estáticas o clases de vista registradas.

### Rutas estáticas {#static-paths}

Utiliza rutas de cadena para definir enlaces directamente:

```java
AppNavItem docs = new AppNavItem("Docs", "/docs");
AppNavItem help = new AppNavItem("Help", "https://support.example.com");
```

### Vistas registradas {#registered-views}

Si tus vistas están registradas con el [enrutador](../routing/overview), puedes pasar la clase en lugar de una URL codificada:

```java
AppNavItem settings = new AppNavItem("Settings", SettingsView.class);
```

Si tu ruta anotada admite [parámetros de ruta](../routing/route-patterns#named-parameters), también puedes pasar un `ParametersBag`:

```java
ParametersBag params = ParametersBag.of("id=123");
AppNavItem advanced = new AppNavItem("User", UserView.class, params);
```

### Con parámetros de consulta {#with-query-parameters}

Pasa un `ParametersBag` para incluir cadenas de consulta:

```java
ParametersBag params = ParametersBag.of("param1=value1&param2=value2");
AppNavItem advanced = new AppNavItem("Advanced", SettingsView.class, params);
advanced.setQueryParameters(params);
```

## Comportamiento objetivo {#target-behavior}

Controla cómo se abren los enlaces utilizando `setTarget()`. Esto es especialmente útil para enlaces externos o vistas emergentes.

- **`SELF`** (predeterminado): Abre en la vista actual.
- **`BLANK`**: Abre en una nueva pestaña o ventana.
- **`PARENT`**: Abre en el contexto de navegación padre.
- **`TOP`**: Abre en el contexto de navegación de nivel superior.

```java
AppNavItem help = new AppNavItem("Help", "https://support.example.com");
help.setTarget(AppNavItem.NavigationTarget.BLANK);
```

## Prefijo y sufijo {#prefix-and-suffix}

`AppNavItem` admite componentes de prefijo y sufijo. Utiliza estos para proporcionar claridad visual con íconos, insignias o botones.

- **Prefijo**: aparece antes de la etiqueta, útil para íconos.
- **Sufijo**: aparece después de la etiqueta, ideal para insignias o acciones.

```java
AppNavItem notifications = new AppNavItem("Alerts");
notifications.setPrefixComponent(TablerIcon.create("alert");
notifications.setSuffixComponent(TablerIcon.create("link"));
```

## Grupos de apertura automática {#auto-opening-groups}

Utiliza `setAutoOpen(true)` en el componente `AppNav` para expandir automáticamente los grupos anidados cuando la app se refresca.

```java
nav.setAutoOpen(true);
```

## Fijación <DocChip chip='since' label='26.01' /> {#pinning}

La fijación permite a un usuario elevar los elementos que más busca a un grupo en la parte superior de la navegación, por lo que un menú profundo mantiene una lista corta de favoritos al alcance de un clic. Está desactivada por defecto. Actívala a través de la configuración de fijación:

```java
AppNav nav = new AppNav();
nav.getPinning().setEnabled(true);
```

Una vez habilitada, cada elemento navegable hoja muestra un toggle de fijación. El toggle se revela al pasar el ratón sobre él o al enfocar con el teclado, por lo que permanece accesible sin un ratón. Activarlo mueve el elemento al grupo fijado en la parte superior de la navegación.

Algunas reglas gobiernan qué se puede fijar y cómo se comporta el grupo:

- Solo los elementos navegables hoja son fijables. Los encabezados de grupo (elementos con hijos) nunca son fijables.
- El grupo fijado aparece solo una vez que algo está fijado y desaparece nuevamente cuando el último elemento es desanclado.
- Desanclar devuelve un elemento a su posición original exacta, incluidos los elementos anidados a varios niveles dentro de grupos.
- El elemento se mueve, no se copia, por lo que cualquier contenido de prefijo o sufijo y cualquier listener adjunto a él sigue funcionando mientras está en el grupo fijado.

La demostración a continuación tiene la fijación habilitada con un título de grupo personalizado y el Dashboard fijado al cargar. Pasa el ratón o enfoca un elemento hoja para revelar su toggle de fijación.

<ComponentDemo
path='/webforj/appnavpinning/Dashboard'
frame='desktop'
files={[
  'src/main/java/com/webforj/samples/views/appnav/AppNavPinningView.java',
  'src/main/java/com/webforj/samples/views/appnav/AppNavPinningPageView.java',
]}
/>

### Comenzar un elemento fijado {#starting-an-item-pinned}

Comienza un elemento en el grupo fijado estableciendo su estado de fijación. Utiliza `isPinned()` para leer el estado actual.

```java
AppNavItem reports = new AppNavItem("Reports", "/reports");
reports.setPinned(true);
```

:::info La fijación debe estar habilitada
`setPinned(true)` solo tiene efecto cuando la fijación está habilitada en el `AppNav` a través de `getPinning().setEnabled(true)`. Sin ello, la llamada no tiene efecto.
:::

### Título del grupo fijado {#pinned-group-title}

El grupo fijado se etiqueta como `Pinned` por defecto. Cámbialo para que se ajuste a tu app:

```java
nav.getPinning().setTitle("Favoritos");
```

### Claves de fijación {#pin-keys}

Cada elemento fijable lleva una clave que lo identifica para la persistencia y para el [evento de fijación](#reacting-to-pin-changes). Cuando no configuras una, la clave vuelve a la ruta del elemento, por lo que `getPinKey()` siempre devuelve un valor utilizable.

```java
AppNavItem reports = new AppNavItem("Reports", "/reports");
reports.setPinKey("reports");
```

Establece una clave explícita cuando la ruta pueda cambiar en tiempo de ejecución. Una clave estable mantiene un pin emparejado con el elemento correcto a través de recargas incluso si su URL se mueve.

### Guardado automático en almacenamiento local {#autosave}

Los pines solo viven durante la vista de la página actual a menos que los persistas. El guardado automático es la opción más simple: almacena el conjunto de elementos fijados en el almacenamiento local del navegador y los restaura en la recarga. Está desactivado por defecto. Necesita un `id` (o nombre) estable en el componente para la clave de almacenamiento, y el constructor `AppNav(String id)` es la manera conveniente de establecer uno:

```java
AppNav nav = new AppNav("main-nav"); // proporciona una clave de almacenamiento estable para el guardado automático
nav.getPinning().setAutosave(true);
```

:::info El guardado automático necesita un id
Sin un `id` (o nombre) en el componente, el guardado automático no hace nada en silencio, ya que no tiene una clave estable bajo la cual almacenar. La persistencia es por navegador, por lo que los pines no siguen a un usuario a otro dispositivo o navegador.
:::

### Persistencia personalizada {#custom-persistence}

Para la persistencia que controlas, por ejemplo, por usuario en el servidor, desactiva el guardado automático y dirígelo tú mismo a través del [evento de fijación](#reacting-to-pin-changes) y `setPinned`:

```java
nav.getPinning().setAutosave(false);

// persiste el conjunto actual de claves fijadas siempre que cambie
nav.onPin(event -> savePins(event.getKeys()));

// al cargar, restaura cada clave guardada
restoredKeys.forEach(key -> findItem(key).setPinned(true));
```

### Reacción a los cambios de fijación {#reacting-to-pin-changes}

El evento de fijación se activa cada vez que un elemento es fijado o desanclado. Lleva el elemento que cambió, su clave, el nuevo estado de fijación y el conjunto completo ordenado de claves fijadas:

```java
nav.onPin(event -> {
  AppNavItem item = event.getItem(); // el elemento que cambió, o null si ya no está en la navegación
  boolean pinned = event.isPinned();
  String key = event.getKey();
  List<String> all = event.getKeys(); // cada clave fijada, en orden fijado
});
```

`getItem()` resuelve el elemento emparejando su clave de fijación y devuelve `null` cuando el elemento ya no forma parte de la navegación.

### Iconos de fijación {#pin-icons}

El toggle utiliza el ícono `dwc:pin` incorporado mientras un elemento está desanclado y `dwc:pinned-off` mientras está fijado. Cambia a tus propios íconos a través de `setUnpinnedIcon` y `setPinnedIcon`, que aceptan cualquier `IconDefinition`:

```java
nav.getPinning()
   .setUnpinnedIcon(TablerIcon.create("pin"))
   .setPinnedIcon(TablerIcon.create("pinned-off"));
```

### Toggle de fijación en pantallas táctiles {#pin-toggle-on-touchscreens}

Las pantallas táctiles no tienen hover para revelar la fijación, por lo que el toggle está oculto allí por defecto. Manténlo visible y táctil en pantallas táctiles con `setTouchVisible(true)`:

```java
nav.getPinning().setTouchVisible(true);
```

## Búsqueda <DocChip chip='since' label='26.01' /> {#search}

El campo de búsqueda filtra el menú por etiqueta del elemento conforme el usuario escribe. Está desactivado por defecto. Puedes mostrarlo y darle un marcador de posición a través de la configuración de búsqueda:

```java
nav.getSearch().setFieldVisible(true);
nav.getSearch().setPlaceholder("Buscar");
```

A medida que el usuario escribe, la navegación filtra los elementos por etiqueta, abre cualquier grupo que contenga una coincidencia y muestra un mensaje vacío cuando no hay coincidencias. Los accesos directos fijados se mantienen visibles mientras se busca, por lo que los favoritos de un usuario siguen a un clic de distancia incluso durante el filtro.

<ComponentDemo
path='/webforj/appnavsearch/Dashboard'
frame='desktop'
files={[
  'src/main/java/com/webforj/samples/views/appnav/AppNavSearchView.java',
  'src/main/java/com/webforj/samples/views/appnav/AppNavSearchPageView.java',
]}
/>

### Mensaje vacío {#search-empty-message}

Establece el mensaje que se muestra cuando una búsqueda no devuelve resultados. El texto plano se renderiza como texto:

```java
nav.getSearch().setEmptyMessage("No se encontraron elementos");
```

### Controlando la búsqueda desde tu propio campo {#custom-search-box}

Oculta el campo incorporado y alimenta el filtro desde un input propio. Envía el término actual a través de `setTerm`:

```java
nav.getSearch().setFieldVisible(false);

myField.onModify(event -> nav.getSearch().setTerm(event.getText()));
```

Para reaccionar a lo que el usuario escribe en el campo incorporado, escucha el evento de búsqueda:

```java
nav.onSearch(event -> log(event.getTerm()));
```

## Estilizando `AppNavItem` {#styling-appnavitem}

<TableBuilder name="AppNavItem" />
