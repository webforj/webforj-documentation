---
title: AppNav
sidebar_position: 6
sidebar_class_name: new-content
description: >-
  Build hierarchical side navigation menus with AppNav and AppNavItem, linking
  to routes, registered views, or external URLs.
_i18n_hash: c4ad786639b189058a38c808bd99ec8b
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-app-nav" />
<DocChip chip='since' label='24.12' />
<JavadocLink type="appnav" location="com/webforj/component/layout/appnav/AppNav" top='true'/>

El componente `AppNav` crea un menú de navegación lateral a partir de entradas de `AppNavItem`. Los elementos pueden vincularse a vistas internas o recursos externos, anidarse bajo elementos parentales para formar menús jerárquicos y llevar iconos, insignias u otros componentes para dar a los usuarios más contexto de un vistazo.

## Agregar y anidar elementos <DocChip chip="name" label="dwc-app-nav-item" /> {#adding-and-nesting-items}

Las instancias de `AppNavItem` se utilizan para poblar la estructura de `AppNav`. Estos elementos pueden ser enlaces simples o encabezados de grupo anidados que contienen elementos secundarios. Los encabezados de grupo sin enlaces actúan como contenedores expandibles.

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

:::tip Vinculando elementos de grupo
Los elementos de nivel superior en un árbol de navegación están típicamente destinados a ser expandibles, no enlaces clickeables. Establecer un `path` en tales elementos puede confundir a los usuarios que esperan que revelen elementos secundarios en lugar de navegar a otro lugar.

Si deseas que el encabezado del grupo active una acción personalizada (como abrir documentos externos), deja el path del grupo vacío y agrega un control interactivo como un [`IconButton`](./icon#icon-buttons) al sufijo del elemento. Esto mantiene la experiencia de usuario consistente y limpia.
:::

## Vinculando elementos {#linking-items}

Cada `AppNavItem` puede navegar a una vista interna o a un enlace externo. Puedes definir esto utilizando rutas estáticas o clases de vista registradas.

### Rutas estáticas {#static-paths}

Utiliza rutas de cadena para definir enlaces directamente:

```java
AppNavItem docs = new AppNavItem("Docs", "/docs");
AppNavItem help = new AppNavItem("Help", "https://support.example.com");
```

### Vistas registradas {#registered-views}

Si tus vistas están registradas con el [router](../routing/overview), puedes pasar la clase en lugar de una URL codificada de forma rígida:

```java
AppNavItem settings = new AppNavItem("Settings", SettingsView.class);
```

Si tu ruta anotada soporta [parámetros de ruta](../routing/route-patterns#named-parameters), también puedes pasar un `ParametersBag`:

```java
ParametersBag params = ParametersBag.of("id=123");
AppNavItem advanced = new AppNavItem("User", UserView.class, params);
```

### Con parámetros de consulta {#with-query-parameters}

Pasa un `ParametersBag` para incluir cadenas de consulta:

```java
ParametersBag params = ParametersBag.of("param1=value1&param2=value2");
AppNavItem advanced = new AppNavItem("Avanzado", SettingsView.class, params);
advanced.setQueryParameters(params);
```

## Comportamiento objetivo {#target-behavior}

Controla cómo se abren los enlaces utilizando `setTarget()`. Esto es especialmente útil para enlaces externos o vistas emergentes.

- **`SELF`** (predeterminado): Se abre en la vista actual.
- **`BLANK`**: Se abre en una nueva pestaña o ventana.
- **`PARENT`**: Se abre en el contexto de navegación padre.
- **`TOP`**: Se abre en el contexto de navegación de nivel superior.

```java
AppNavItem help = new AppNavItem("Help", "https://support.example.com");
help.setTarget(AppNavItem.NavigationTarget.BLANK);
```

## Prefijo y sufijo {#prefix-and-suffix}

`AppNavItem` soporta componentes de prefijo y sufijo. Utiliza estos para proporcionar claridad visual con iconos, insignias o botones.

- **Prefijo**: aparece antes de la etiqueta, útil para iconos.
- **Sufijo**: aparece después de la etiqueta, ideal para insignias o acciones.

```java
AppNavItem notifications = new AppNavItem("Alerts");
notifications.setPrefixComponent(TablerIcon.create("alert"));
notifications.setSuffixComponent(TablerIcon.create("link"));
```

## Grupos de apertura automática {#auto-opening-groups}

Utiliza `setAutoOpen(true)` en el componente `AppNav` para expandir automáticamente los grupos anidados cuando se actualiza la aplicación.

```java
nav.setAutoOpen(true);
```

## Etiquetas de sección <DocChip chip='since' label='26.02' /> <DocChip chip="name" label="dwc-app-nav-label" /> {#section-labels}

`AppNavLabel` es un encabezado no interactivo que titula una serie de elementos. Una etiqueta se aplica a cada elemento que la sigue, hasta la siguiente etiqueta o el final del menú, lo que permite que una larga lista de elementos de nivel superior se lea como unos pocos grupos nombrados sin anidarlos.

Las etiquetas se agregan con `add()` en lugar de `addItem()`, y el orden de las llamadas define las secciones:

```java
AppNav nav = new AppNav();
nav.addItem(new AppNavItem("Dashboard", DashboardView.class, TablerIcon.create("layout-dashboard")));

nav.add(new AppNavLabel("Analytics"));
nav.addItem(new AppNavItem("Overview", OverviewView.class));
nav.addItem(new AppNavItem("Reports", ReportsView.class));

nav.add(new AppNavLabel("Other"));
nav.addItem(new AppNavItem("Settings", SettingsView.class));
```

La navegación oculta una etiqueta automáticamente cuando su sección no tiene elementos visibles, por lo que una etiqueta desaparece cuando una [búsqueda](#search) filtra sus elementos o cuando todos ellos están [fijados](#pinning) en la parte superior del menú.

### Prefijo y sufijo de etiqueta {#label-prefix-and-suffix}

Al igual que `AppNavItem`, una etiqueta soporta componentes de prefijo y sufijo. Pasa un prefijo al constructor, o establece cualquiera de los dos después:

```java
AppNavLabel analytics = new AppNavLabel("Analytics", TablerIcon.create("chart-pie"));
analytics.setSuffixComponent(new Badge().setText("2").setTheme(BadgeTheme.WARNING));

nav.add(analytics);
```

El siguiente ejemplo agrupa un menú bajo tres etiquetas, la primera de las cuales lleva un prefijo de [`Icon`](./icon) y un sufijo de [`Badge`](./badge). Dashboard se sitúa encima de la primera etiqueta, por lo que no pertenece a ninguna sección.

## Fijación <DocChip chip='since' label='26.01' /> {#pinning}

Fijar permite que un usuario eleve los elementos que busca con más frecuencia a un grupo en la parte superior de la navegación, de modo que un menú profundo mantenga una lista corta de favoritos a un solo clic. Está desactivado por defecto. Actívalo a través de la configuración de fijación:

```java
AppNav nav = new AppNav();
nav.getPinning().setEnabled(true);
```

Una vez habilitado, cada elemento hoja navegable muestra un toggle de fijación. El toggle se revela al pasar el cursor y al enfocar con el teclado, por lo que permanece accesible sin ratón. Activarlo mueve el elemento al grupo fijado en la parte superior de la navegación.

Unas pocas reglas rigen lo que puede ser fijado y cómo se comporta el grupo:

- Solo los elementos hoja navegables son fijables. Los encabezados de grupo (elementos con hijos) nunca son fijables.
- El grupo fijado aparece solo una vez que algo se ha fijado, y desaparece nuevamente cuando se desfija el último elemento.
- Desfijar devuelve un elemento a su posición original exacta, incluidos los elementos anidados varios niveles dentro de grupos.
- El elemento se mueve, no se copia, por lo que cualquier contenido de prefijo o sufijo y cualquier oyente adjunto permanece funcionando mientras está en el grupo fijado.

La demostración siguiente tiene la fijación habilitada con un título de grupo personalizado y Dashboard fijado al cargar. Pasa el cursor o enfoca un elemento hoja para revelar su toggle de fijación.

### Iniciar un elemento fijado {#starting-an-item-pinned}

Inicia un elemento en el grupo fijado estableciendo su estado fijado. Usa `isPinned()` para leer el estado actual.

```java
AppNavItem reports = new AppNavItem("Reports", "/reports");
reports.setPinned(true);
```

:::info La fijación debe estar habilitada
`setPinned(true)` solo tiene efecto cuando la fijación está habilitada en el `AppNav` a través de `getPinning().setEnabled(true)`. Sin ello, la llamada no tiene efecto.
:::

### Título del grupo fijado {#pinned-group-title}

El grupo fijado se etiqueta como `Fijados` por defecto. Cambia esto para que se ajuste a tu aplicación:

```java
nav.getPinning().setTitle("Favoritos");
```

### Claves de fijación {#pin-keys}

Cada elemento fijable lleva una clave que lo identifica para la persistencia y para el [evento de fijación](#reacting-to-pin-changes). Cuando no estableces una, la clave se basa en el path del elemento, por lo que `getPinKey()` siempre devuelve un valor utilizable.

```java
AppNavItem reports = new AppNavItem("Reports", "/reports");
reports.setPinKey("reports");
```

Establece una clave explícita cuando el path pueda cambiar en tiempo de ejecución. Una clave estable mantiene una fijación emparejada con el elemento correcto a través de recargas, incluso si su URL se mueve.

### Autosalvado en almacenamiento local {#autosave}

Los elementos fijados viven solo para la vista de página actual a menos que los persistencias. El autosalvado es la opción más simple: almacena el conjunto de elementos fijados en el almacenamiento local del navegador y los restaura al recargar. Está desactivado por defecto. Necesita un `id` (o nombre) estable en el componente para la clave de almacenamiento, y el constructor `AppNav(String id)` es la forma conveniente de establecer uno:

```java
AppNav nav = new AppNav("main-nav"); // da al autosalvado una clave de almacenamiento estable
nav.getPinning().setAutosave(true);
```

:::info El autosalvado necesita un id
Sin un `id` (o nombre) en el componente, el autosalvado simplemente no hace nada, ya que no tiene una clave estable para almacenar. La persistencia es por navegador, por lo que los elementos fijados no siguen a un usuario a otro dispositivo o navegador.
:::

### Persistencia personalizada {#custom-persistence}

Para la persistencia que controlas, por ejemplo por usuario en el servidor, desactiva el autosalvado y controla el mismo a través del [evento de fijación](#reacting-to-pin-changes) y `setPinned`:

```java
nav.getPinning().setAutosave(false);

// persistir el conjunto actual de claves fijadas cada vez que cambia
nav.onPin(event -> savePins(event.getKeys()));

// al cargar, restaurar cada clave guardada
restoredKeys.forEach(key -> findItem(key).setPinned(true));
```

### Reacción a cambios de fijación {#reacting-to-pin-changes}

El evento de fijación se activa cada vez que un elemento se fija o se desfixa. Lleva el elemento que cambió, su clave, el nuevo estado fijado y el conjunto completo ordenado de claves fijadas:

```java
nav.onPin(event -> {
  AppNavItem item = event.getItem(); // el elemento que cambió, o null si ya no está en la navegación
  boolean pinned = event.isPinned();
  String key = event.getKey();
  List<String> all = event.getKeys(); // cada clave fijada, en orden fijado
});
```

`getItem()` resuelve el elemento haciendo coincidir su clave de fijación y devuelve `null` cuando el elemento ya no es parte de la navegación.

### Iconos de fijación {#pin-icons}

El toggle utiliza el icono incorporado `dwc:pin` mientras un elemento está desfijado y `dwc:pinned-off` mientras está fijado. Cambia a tus propios a través de `setUnpinnedIcon` y `setPinnedIcon`, que aceptan cualquier `IconDefinition`:

```java
nav.getPinning()
   .setUnpinnedIcon(TablerIcon.create("pin"))
   .setPinnedIcon(TablerIcon.create("pinned-off"));
```

### Toggle de fijación en pantallas táctiles {#pin-toggle-on-touchscreens}

Las pantallas táctiles no tienen un hover para revelar la fijación, por lo que el toggle está oculto por defecto. Mantenlo visible y tocable en pantallas táctiles con `setTouchVisible(true)`:

```java
nav.getPinning().setTouchVisible(true);
```

## Búsqueda <DocChip chip='since' label='26.01' /> {#search}

El campo de búsqueda filtra el menú por etiqueta de elemento a medida que el usuario escribe. Está desactivado por defecto. Puedes mostrarlo y darle un placeholder a través de la configuración de búsqueda:

```java
nav.getSearch().setFieldVisible(true);
nav.getSearch().setPlaceholder("Buscar");
```

Mientras el usuario escribe, la navegación filtra elementos por etiqueta, abre cualquier grupo que contenga una coincidencia y muestra un mensaje vacío cuando nada coincide. Los accesos directos fijados permanecen visibles mientras se busca, por lo que los favoritos del usuario siguen a un clic de distancia incluso durante el filtrado.

### Mensaje vacío {#search-empty-message}

Establece el mensaje que se muestra cuando una búsqueda no devuelve resultados. El texto plano se renderiza como texto:

```java
nav.getSearch().setEmptyMessage("No se encontraron elementos");
```

### Impulsando la búsqueda desde tu propio campo {#custom-search-box}

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
