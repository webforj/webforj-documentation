---
title: AppNav
sidebar_position: 6
sidebar_class_name: new-content
description: >-
  Build hierarchical side navigation menus with AppNav and AppNavItem, linking
  to routes, registered views, or external URLs.
_i18n_hash: afb61d8d44c3f5dcb03f533954baafc1
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-app-nav" />
<DocChip chip="name" label="dwc-app-nav-item" />
<DocChip chip="name" label="dwc-app-nav-label" />
<DocChip chip='since' label='24.12' />
<JavadocLink type="appnav" location="com/webforj/component/appnav/AppNav" top='true'/>

El componente `AppNav` crea un menú de navegación lateral a partir de entradas `AppNavItem`. Los elementos pueden enlazar a vistas internas o recursos externos, anidarse bajo elementos padres para formar menús jerárquicos y llevar íconos, insignias u otros componentes para dar a los usuarios un contexto adicional de un vistazo.

<!-- INTRO_END -->

## Agregar y anidar elementos {#adding-and-nesting-items}

Las instancias `AppNavItem` se utilizan para poblar la estructura `AppNav`. Estos elementos pueden ser enlaces simples o encabezados de grupos anidados que contienen elementos hijos. Los encabezados de grupo sin enlaces actúan como contenedores expandibles.

Utilice `addItem()` para incluir elementos en la navegación:

```java
AppNavItem dashboard = new AppNavItem("Tablero", "/dashboard");
AppNavItem admin = new AppNavItem("Administrar");
admin.addItem(new AppNavItem("Usuarios", "/admin/users"));
admin.addItem(new AppNavItem("Configuración", "/admin/settings"));

AppNav nav = new AppNav();
nav.addItem(dashboard);
nav.addItem(admin);
```

:::tip Vinculando elementos de grupo
Los elementos de nivel superior en un árbol de navegación suelen estar destinados a ser expandibles, no enlaces clicables. Establecer un `path` en dichos elementos puede confundir a los usuarios que esperan que revelen subelementos en lugar de navegar a otra parte.

Si desea que el encabezado del grupo desencadene una acción personalizada (como abrir documentos externos), mantenga el path del grupo vacío y en su lugar agregue un control interactivo como un [`IconButton`](./icon#icon-buttons) al sufijo del elemento. Esto mantiene la experiencia de usuario consistente y limpia.
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

## Vinculando elementos {#linking-items}

Cada `AppNavItem` puede navegar a una vista interna o a un enlace externo. Puede definir esto utilizando rutas estáticas o clases de vista registradas.

### Rutas estáticas {#static-paths}

Utilice rutas de cadenas para definir enlaces directamente:

```java
AppNavItem docs = new AppNavItem("Documentos", "/docs");
AppNavItem help = new AppNavItem("Ayuda", "https://support.example.com");
```

### Vistas registradas {#registered-views}

Si sus vistas están registradas con el [router](../routing/overview), puede pasar la clase en lugar de una URL codificada:

```java
AppNavItem settings = new AppNavItem("Configuración", SettingsView.class);
```

Si su ruta anotada admite [parámetros de ruta](../routing/route-patterns#named-parameters), también puede pasar un `ParametersBag`:

```java
ParametersBag params = ParametersBag.of("id=123");
AppNavItem advanced = new AppNavItem("Usuario", UserView.class, params);
```

### Con parámetros de consulta {#with-query-parameters}

Pase un `ParametersBag` para incluir cadenas de consulta:

```java
ParametersBag params = ParametersBag.of("param1=value1&param2=value2");
AppNavItem advanced = new AppNavItem("Avanzado", SettingsView.class, params);
advanced.setQueryParameters(params);
```

## Comportamiento objetivo {#target-behavior}

Controle cómo se abren los enlaces utilizando `setTarget()`. Esto es especialmente útil para enlaces externos o vistas emergentes.

- **`SELF`** (predeterminado): Abre en la vista actual.
- **`BLANK`**: Abre en una nueva pestaña o ventana.
- **`PARENT`**: Abre en el contexto de navegación padre.
- **`TOP`**: Abre en el contexto de navegación de nivel superior.

```java
AppNavItem help = new AppNavItem("Ayuda", "https://support.example.com");
help.setTarget(AppNavItem.NavigationTarget.BLANK);
```

## Prefijo y sufijo {#prefix-and-suffix}

`AppNavItem` admite componentes de prefijo y sufijo. Úselos para proporcionar claridad visual con íconos, insignias o botones.

- **Prefijo**: aparece antes de la etiqueta, útil para íconos.
- **Sufijo**: aparece después de la etiqueta, excelente para insignias o acciones.

```java
AppNavItem notifications = new AppNavItem("Alertas");
notifications.setPrefixComponent(TablerIcon.create("alert"));
notifications.setSuffixComponent(TablerIcon.create("link"));
```

## Grupos de apertura automática {#auto-opening-groups}

Utilice `setAutoOpen(true)` en el componente `AppNav` para expandir automáticamente grupos anidados cuando se actualiza la aplicación.

```java
nav.setAutoOpen(true);
```

## Etiquetas de sección <DocChip chip='since' label='26.02' /> {#section-labels}

`AppNavLabel` es un encabezado no interactivo que titula un conjunto de elementos. Una etiqueta se aplica a cada elemento que la sigue, hasta la siguiente etiqueta o el final del menú, lo que permite que una larga lista de elementos de nivel superior se lea como algunos grupos nombrados sin anidarlos.

Las etiquetas se agregan con `add()` en lugar de `addItem()`, y el orden de las llamadas define las secciones:

```java
AppNav nav = new AppNav();
nav.addItem(new AppNavItem("Tablero", DashboardView.class, TablerIcon.create("layout-dashboard")));

nav.add(new AppNavLabel("Analítica"));
nav.addItem(new AppNavItem("Resumen", OverviewView.class));
nav.addItem(new AppNavItem("Informes", ReportsView.class));

nav.add(new AppNavLabel("Otro"));
nav.addItem(new AppNavItem("Configuración", SettingsView.class));
```

La navegación oculta automáticamente una etiqueta cuando su sección no tiene elementos visibles, por lo que una etiqueta desaparece cuando una [búsqueda](#search) filtra sus elementos o cuando todos ellos están [fijados](#pinning) en la parte superior del menú.

### Prefijo y sufijo de etiqueta {#label-prefix-and-suffix}

Al igual que `AppNavItem`, una etiqueta admite componentes de prefijo y sufijo. Pase un prefijo al constructor, o establezca cualquiera de los dos después:

```java
AppNavLabel analytics = new AppNavLabel("Analítica", TablerIcon.create("chart-pie"));
analytics.setSuffixComponent(new Badge().setText("2").setTheme(BadgeTheme.WARNING));

nav.add(analytics);
```

El ejemplo a continuación agrupa un menú bajo tres etiquetas, la primera de las cuales lleva un prefijo de [`Icon`](./icon) y un sufijo de [`Badge`](./badge). El tablero se encuentra por encima de la primera etiqueta, por lo que no pertenece a ninguna sección.

<ComponentDemo
path='/webforj/appnavlabel/Dashboard'
frame='desktop'
files={[
  'src/main/java/com/webforj/samples/views/appnav/AppNavLabelView.java',
  'src/main/java/com/webforj/samples/views/appnav/AppNavLabelPageView.java',
]}
/>

## Fijación <DocChip chip='since' label='26.01' /> {#pinning}

La fijación permite a un usuario levantar los elementos que busca con más frecuencia en un grupo en la parte superior de la navegación, por lo que un menú profundo mantiene una lista corta de favoritos en un solo clic. Está desactivado por defecto. Actívelo a través de la configuración de fijación:

```java
AppNav nav = new AppNav();
nav.getPinning().setEnabled(true);
```

Una vez habilitado, cada elemento hoja navegable muestra un interruptor de fijación. El interruptor se revela al pasar el mouse y al enfocarse con el teclado, por lo que se mantiene accesible sin un mouse. Activarlo mueve el elemento al grupo fijo en la parte superior de la navegación.

Algunas reglas rigen qué se puede fijar y cómo se comporta el grupo:

- Solo los elementos hoja navegables son fijables. Los encabezados de grupo (elementos con hijos) nunca son fijables.
- El grupo fijado aparece solo una vez que se ha fijado algo, y desaparece nuevamente cuando el último elemento se desfija.
- Desfijar devuelve un elemento a su posición original exacta, incluidos los elementos anidados varios niveles dentro de grupos.
- El elemento se mueve, no se copia, por lo que cualquier contenido de prefijo o sufijo y cualquier oyente adjunto siguen funcionando mientras está en el grupo fijado.

La demostración a continuación tiene la fijación habilitada con un título de grupo personalizado y el tablero fijado al cargar. Pase el mouse o enfóquese en un elemento hoja para revelar su interruptor de fijación.

<ComponentDemo
path='/webforj/appnavpinning/Dashboard'
frame='desktop'
files={[
  'src/main/java/com/webforj/samples/views/appnav/AppNavPinningView.java',
  'src/main/java/com/webforj/samples/views/appnav/AppNavPinningPageView.java',
]}
/>

### Comenzar un elemento fijado {#starting-an-item-pinned}

Inicie un elemento en el grupo fijado estableciendo su estado como fijado. Utilice `isPinned()` para leer el estado actual.

```java
AppNavItem reports = new AppNavItem("Informes", "/reports");
reports.setPinned(true);
```

:::info La fijación debe estar habilitada
`setPinned(true)` solo tiene efecto cuando la fijación está habilitada en el `AppNav` a través de `getPinning().setEnabled(true)`. Sin ella, la llamada no tiene efecto.
:::

### Título del grupo fijado {#pinned-group-title}

El grupo fijado se etiqueta como `Fijado` de forma predeterminada. Cambie el nombre para adaptarlo a su aplicación:

```java
nav.getPinning().setTitle("Favoritos");
```

### Claves de fijación {#pin-keys}

Cada elemento fijable lleva una clave que lo identifica para la persistencia y para el [evento de fijación](#reacting-to-pin-changes). Cuando no establece una, la clave se basa en la ruta del elemento, por lo que `getPinKey()` siempre devuelve un valor utilizable.

```java
AppNavItem reports = new AppNavItem("Informes", "/reports");
reports.setPinKey("reports");
```

Establezca una clave explícita cuando la ruta pueda cambiar en tiempo de ejecución. Una clave estable mantiene una fijación emparejada con el elemento correcto a través de recargas incluso si su URL se mueve.

### Guardar automáticamente en el almacenamiento local {#autosave}

Las fijaciones solo viven para la vista actual de la página a menos que las persista. La función de guardado automático es la opción más simple: almacena el conjunto de elementos fijados en el almacenamiento local del navegador y los restaura al recargar. Está desactivado por defecto. Necesita un `id` (o nombre) estable en el componente para la clave de almacenamiento, y el constructor `AppNav(String id)` es la forma conveniente de establecer uno:

```java
AppNav nav = new AppNav("main-nav"); // da a autosave una clave de almacenamiento estable
nav.getPinning().setAutosave(true);
```

:::info El guardado automático necesita un id
Sin un `id` (o nombre) en el componente, el guardado automático no hace nada silenciosamente, ya que no tiene una clave estable para almacenar. La persistencia es por navegador, por lo que las fijaciones no siguen a un usuario a otro dispositivo o navegador.
:::

### Persistencia personalizada {#custom-persistence}

Para la persistencia que controla, por ejemplo, por usuario en el servidor, desactive el guardado automático y controle manualmente a través del [evento de fijación](#reacting-to-pin-changes) y `setPinned`:

```java
nav.getPinning().setAutosave(false);

// persistir el conjunto actual de claves fijadas cada vez que cambia
nav.onPin(event -> savePins(event.getKeys()));

// al cargar, restaurar cada clave guardada
restoredKeys.forEach(key -> findItem(key).setPinned(true));
```

### Reaccionando a los cambios de fijación {#reacting-to-pin-changes}

El evento de fijación se activa cada vez que un elemento es fijado o desfijado. Lleva el elemento que cambió, su clave, el nuevo estado fijado y el conjunto completo y ordenado de claves fijadas:

```java
nav.onPin(event -> {
  AppNavItem item = event.getItem(); // el elemento que cambió, o null si ya no está en la navegación
  boolean pinned = event.isPinned();
  String key = event.getKey();
  List<String> all = event.getKeys(); // cada clave fijada, en orden fijado
});
```

`getItem()` resuelve el elemento emparejando su clave de fijación, y devuelve `null` cuando el elemento ya no forma parte de la navegación.

### Íconos de fijación {#pin-icons}

El interruptor utiliza el ícono incorporado `dwc:pin` mientras un elemento no está fijado y `dwc:pinned-off` mientras está fijado. Sustitúyalo por el suyo a través de `setUnpinnedIcon` y `setPinnedIcon`, que aceptan cualquier `IconDefinition`:

```java
nav.getPinning()
   .setUnpinnedIcon(TablerIcon.create("pin"))
   .setPinnedIcon(TablerIcon.create("pinned-off"));
```

### Interruptor de fijación en pantallas táctiles {#pin-toggle-on-touchscreens}

Las pantallas táctiles no tienen hover para revelar la fijación, por lo que el interruptor está oculto allí de forma predeterminada. Manténgalo visible y táctil en pantallas táctiles con `setTouchVisible(true)`:

```java
nav.getPinning().setTouchVisible(true);
```

## Búsqueda <DocChip chip='since' label='26.01' /> {#search}

El campo de búsqueda filtra el menú por la etiqueta del elemento a medida que el usuario escribe. Está desactivado por defecto. Puede mostrarlo y darle un marcador de posición a través de la configuración de búsqueda:

```java
nav.getSearch().setFieldVisible(true);
nav.getSearch().setPlaceholder("Buscar");
```

A medida que el usuario escribe, la navegación filtra los elementos por etiqueta, abre cualquier grupo que contenga una coincidencia y muestra un mensaje vacío cuando no hay coincidencias. Los accesos directos fijados permanecen visibles mientras se busca, por lo que los favoritos de un usuario siguen a un clic de distancia incluso en medio del filtro.

<ComponentDemo
path='/webforj/appnavsearch/Dashboard'
frame='desktop'
files={[
  'src/main/java/com/webforj/samples/views/appnav/AppNavSearchView.java',
  'src/main/java/com/webforj/samples/views/appnav/AppNavSearchPageView.java',
]}
/>

### Mensaje vacío {#search-empty-message}

Establezca el mensaje que se muestra cuando una búsqueda no devuelve resultados. El texto plano se renderiza como texto:

```java
nav.getSearch().setEmptyMessage("No se encontraron elementos");
```

### Conduciendo la búsqueda desde su propio campo {#custom-search-box}

Oculte el campo incorporado y alimente el filtro desde una entrada propia. Introduzca el término actual a través de `setTerm`:

```java
nav.getSearch().setFieldVisible(false);

myField.onModify(event -> nav.getSearch().setTerm(event.getText()));
```

Para reaccionar a lo que el usuario escribe en el campo incorporado, escuche el evento de búsqueda:

```java
nav.onSearch(event -> log(event.getTerm()));
```

## Estilizando `AppNavItem` {#styling-appnavitem}

<TableBuilder name="AppNavItem" />
