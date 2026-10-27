---
title: AppNav
sidebar_position: 6
sidebar_class_name: new-content
description: >-
  Build hierarchical side navigation menus with AppNav and AppNavItem, linking
  to routes, registered views, or external URLs.
_i18n_hash: 4f5eaa3e9738a7f959de22544589d240
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-app-nav" />
<DocChip chip="name" label="dwc-app-nav-item" />
<DocChip chip="name" label="dwc-app-nav-label" />
<DocChip chip='since' label='24.12' />
<JavadocLink type="appnav" location="com/webforj/component/layout/appnav/AppNav" top='true'/>

El componente `AppNav` crea un menú de navegación lateral a partir de entradas `AppNavItem`. Los elementos pueden enlazar a vistas internas o recursos externos, anidarse bajo elementos padre para formar menús jerárquicos, y llevar íconos, insignias u otros componentes para brindar a los usuarios más contexto de un vistazo.

<!-- INTRO_END -->

## Agregando y anidando elementos {#adding-and-nesting-items}

Las instancias de `AppNavItem` se utilizan para poblar la estructura de `AppNav`. Estos elementos pueden ser enlaces simples o encabezados de grupos anidados que contienen elementos hijos. Los encabezados de grupo sin enlaces actúan como contenedores expandibles.

Utiliza `addItem()` para incluir elementos en la navegación:

```java
AppNavItem dashboard = new AppNavItem("Tablero", "/dashboard");
AppNavItem admin = new AppNavItem("Administración");
admin.addItem(new AppNavItem("Usuarios", "/admin/users"));
admin.addItem(new AppNavItem("Configuraciones", "/admin/settings"));

AppNav nav = new AppNav();
nav.addItem(dashboard);
nav.addItem(admin);
```

:::tip Enlazando elementos de grupo
Los elementos de nivel superior en un árbol de navegación normalmente están destinados a ser expandibles, no enlaces clicables. Establecer una `ruta` en dichos elementos puede confundir a los usuarios que esperan que revelen sub-elementos en lugar de navegar a otro lugar.

Si deseas que el encabezado del grupo desencadene una acción personalizada (como abrir documentos externos), deja la ruta del grupo vacía y en su lugar añade un control interactivo como un [`IconButton`](./icon#icon-buttons) al sufijo del elemento. Esto mantiene la UX consistente y limpia.
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

Cada `AppNavItem` puede navegar a una vista interna o a un enlace externo. Puedes definir esto utilizando rutas estáticas o clases de vista registradas.

### Rutas estáticas {#static-paths}

Utiliza rutas de cadena para definir enlaces directamente:

```java
AppNavItem docs = new AppNavItem("Documentos", "/docs");
AppNavItem help = new AppNavItem("Ayuda", "https://support.example.com");
```

### Vistas registradas {#registered-views}

Si tus vistas están registradas con el [enrutador](../routing/overview), puedes pasar la clase en lugar de una URL codificada:

```java
AppNavItem settings = new AppNavItem("Configuraciones", SettingsView.class);
```

Si tu ruta anotada admite [parámetros de ruta](../routing/route-patterns#named-parameters), también puedes pasar un `ParametersBag`:

```java
ParametersBag params = ParametersBag.of("id=123");
AppNavItem advanced = new AppNavItem("Usuario", UserView.class, params);
```

### Con parámetros de consulta {#with-query-parameters}

Pasa un `ParametersBag` para incluir cadenas de consulta:

```java
ParametersBag params = ParametersBag.of("param1=value1&param2=value2");
AppNavItem advanced = new AppNavItem("Avanzado", SettingsView.class, params);
advanced.setQueryParameters(params);
```

## Comportamiento del objetivo {#target-behavior}

Controla cómo se abren los enlaces usando `setTarget()`. Esto es especialmente útil para enlaces externos o vistas emergentes.

- **`SELF`** (predeterminado): Se abre en la vista actual.
- **`BLANK`**: Se abre en una nueva pestaña o ventana.
- **`PARENT`**: Se abre en el contexto de navegación padre.
- **`TOP`**: Se abre en el contexto de navegación de nivel superior.

```java
AppNavItem help = new AppNavItem("Ayuda", "https://support.example.com");
help.setTarget(AppNavItem.NavigationTarget.BLANK);
```

## Prefijo y sufijo {#prefix-and-suffix}

`AppNavItem` soporta componentes de prefijo y sufijo. Utiliza estos para proporcionar claridad visual con íconos, insignias o botones.

- **Prefijo**: aparece antes de la etiqueta, útil para íconos.
- **Sufijo**: aparece después de la etiqueta, ideal para insignias o acciones.

```java
AppNavItem notifications = new AppNavItem("Alertas");
notifications.setPrefixComponent(TablerIcon.create("alerta"));
notifications.setSuffixComponent(TablerIcon.create("enlace"));
```

## Grupos de auto-apertura {#auto-opening-groups}

Utiliza `setAutoOpen(true)` en el componente `AppNav` para expandir automáticamente grupos anidados cuando la aplicación se refresca.

```java
nav.setAutoOpen(true);
```

## Etiquetas de sección <DocChip chip='since' label='26.02' /> {#section-labels}

`AppNavLabel` es un encabezado no interactivo que titula un conjunto de elementos. Una etiqueta se aplica a cada elemento que la sigue, hasta la próxima etiqueta o el final del menú, lo que permite que una larga lista de elementos de nivel superior se lea como algunos grupos nombrados sin anidarlos.

Las etiquetas se agregan con `add()` en lugar de `addItem()`, y el orden de las llamadas define las secciones:

```java
AppNav nav = new AppNav();
nav.addItem(new AppNavItem("Tablero", DashboardView.class, TablerIcon.create("layout-dashboard")));

nav.add(new AppNavLabel("Analíticas"));
nav.addItem(new AppNavItem("Resumen", OverviewView.class));
nav.addItem(new AppNavItem("Informes", ReportsView.class));

nav.add(new AppNavLabel("Otros"));
nav.addItem(new AppNavItem("Configuraciones", SettingsView.class));
```

El nav oculta automáticamente una etiqueta cuando su sección no tiene elementos visibles, así que una etiqueta desaparece cuando una [búsqueda](#search) filtra sus elementos o cuando todos ellos son [fijados](#pinning) en la parte superior del menú.

### Prefijo y sufijo de etiqueta {#label-prefix-and-suffix}

Al igual que `AppNavItem`, una etiqueta soporta componentes de prefijo y sufijo. Pasa un prefijo al constructor, o establece cualquiera de los dos después:

```java
AppNavLabel analytics = new AppNavLabel("Analíticas", TablerIcon.create("chart-pie"));
analytics.setSuffixComponent(new Badge().setText("2").setTheme(BadgeTheme.WARNING));

nav.add(analytics);
```

El ejemplo a continuación agrupa un menú bajo tres etiquetas, la primera de las cuales lleva un prefijo de [`Icon`](./icon) y un sufijo de [`Badge`](./badge). El Tablero se sitúa por encima de la primera etiqueta, por lo que no pertenece a ninguna sección.

<ComponentDemo
path='/webforj/appnavlabel/Dashboard'
frame='desktop'
files={[
  'src/main/java/com/webforj/samples/views/appnav/AppNavLabelView.java',
  'src/main/java/com/webforj/samples/views/appnav/AppNavLabelPageView.java',
]}
/>

## Fijación <DocChip chip='since' label='26.01' /> {#pinning}

La fijación permite a un usuario elevar los elementos que más busca a un grupo en la parte superior de la navegación, de modo que un menú profundo aún mantenga una lista corta de favoritos en un solo clic. Está desactivada por defecto. Actívala a través de la configuración de fijación:

```java
AppNav nav = new AppNav();
nav.getPinning().setEnabled(true);
```

Una vez habilitada, cada elemento navegable muestra un interruptor para fijar. El interruptor se revela al pasar el mouse y al enfocarse desde el teclado, por lo que se mantiene accesible sin un mouse. Activarlo mueve el elemento al grupo fijado en la parte superior de la navegación.

Algunas reglas rigen lo que se puede fijar y cómo se comporta el grupo:

- Solo los elementos hoja navegables son fijables. Los encabezados de grupo (elementos con hijos) nunca son fijables.
- El grupo fijado aparece solo una vez que se ha fijado algo, y desaparece nuevamente cuando el último elemento es desajustado.
- Desajustar devuelve un elemento a su posición original exacta, incluidos los elementos anidados a varios niveles dentro de grupos.
- El elemento es movido, no copiado, por lo que cualquier contenido de prefijo o sufijo y cualquier escucha adjunta a él siguen funcionando mientras está en el grupo fijado.

El demo a continuación tiene la fijación habilitada con un título de grupo personalizado y el Tablero fijado al cargar. Pasa el mouse o enfoca un elemento hoja para revelar su interruptor de fijación.

<ComponentDemo
path='/webforj/appnavpinning/Dashboard'
frame='desktop'
files={[
  'src/main/java/com/webforj/samples/views/appnav/AppNavPinningView.java',
  'src/main/java/com/webforj/samples/views/appnav/AppNavPinningPageView.java',
]}
/>

### Iniciando un elemento fijado {#starting-an-item-pinned}

Inicia un elemento en el grupo fijado ajustando su estado de fijación. Usa `isPinned()` para leer el estado actual.

```java
AppNavItem reports = new AppNavItem("Informes", "/reports");
reports.setPinned(true);
```

:::info La fijación debe estar habilitada
`setPinned(true)` solo tiene efecto cuando la fijación está habilitada en el `AppNav` a través de `getPinning().setEnabled(true)`. Sin ello, la llamada no tiene efecto.
:::

### Título del grupo fijado {#pinned-group-title}

El grupo fijado está etiquetado como `Fijados` por defecto. Cambia su nombre para adaptarlo a tu aplicación:

```java
nav.getPinning().setTitle("Favoritos");
```

### Claves de fijación {#pin-keys}

Cada elemento fijable lleva una clave que lo identifica para persistencia y para el [evento de fijación](#reacting-to-pin-changes). Cuando no estableces una, la clave se basa en la ruta del elemento, así que `getPinKey()` siempre devuelve un valor utilizable.

```java
AppNavItem reports = new AppNavItem("Informes", "/reports");
reports.setPinKey("reports");
```

Establece una clave explícita cuando la ruta puede cambiar en tiempo de ejecución. Una clave estable mantiene una fijación coincidida con el elemento correcto a través de recargas, incluso si su URL se mueve.

### Autosave en almacenamiento local {#autosave}

Las fijaciones solo viven para la vista actual de la página a menos que las persistas. La autosave es la opción más sencilla: almacena el conjunto de elementos fijados en el almacenamiento local del navegador y los restaura al recargar. Está desactivada por defecto. Necesita un `id` (o nombre) estable en el componente para la clave de almacenamiento, y el constructor `AppNav(String id)` es la forma conveniente de establecer uno:

```java
AppNav nav = new AppNav("main-nav"); // le da a autosave una clave de almacenamiento estable
nav.getPinning().setAutosave(true);
```

:::info La autosave necesita un id
Sin un `id` (o nombre) en el componente, la autosave no hace nada en silencio, ya que no tiene una clave estable para almacenar. La persistencia es por navegador, por lo que las fijaciones no siguen a un usuario a otro dispositivo o navegador.
:::

### Persistencia personalizada {#custom-persistence}

Para una persistencia que tú controles, por ejemplo por usuario en el servidor, desactiva la autosave y guía tú mismo a través del [evento de fijación](#reacting-to-pin-changes) y `setPinned`:

```java
nav.getPinning().setAutosave(false);

// persiste el conjunto actual de claves fijadas cada vez que cambia
nav.onPin(event -> savePins(event.getKeys()));

// al cargar, restaura cada clave guardada
restoredKeys.forEach(key -> findItem(key).setPinned(true));
```

### Reaccionando a los cambios de fijación {#reacting-to-pin-changes}

El evento de fijación se activa siempre que un elemento es fijado o desajustado. Lleva el elemento que cambió, su clave, el nuevo estado de fijación y el conjunto completo ordenado de claves fijadas:

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

El interruptor utiliza el ícono integrado `dwc:pin` mientras un elemento no está fijado y `dwc:pinned-off` mientras está fijado. Cambia por el tuyo propio a través de `setUnpinnedIcon` y `setPinnedIcon`, que aceptan cualquier `IconDefinition`:

```java
nav.getPinning()
   .setUnpinnedIcon(TablerIcon.create("pin"))
   .setPinnedIcon(TablerIcon.create("pinned-off"));
```

### Interruptor de fijación en pantallas táctiles {#pin-toggle-on-touchscreens}

Las pantallas táctiles no tienen un hover para revelar la fijación, por lo que el interruptor está oculto por defecto. Mantenlo visible y seleccionable en pantallas táctiles con `setTouchVisible(true)`:

```java
nav.getPinning().setTouchVisible(true);
```

## Búsqueda <DocChip chip='since' label='26.01' /> {#search}

El campo de búsqueda filtra el menú por la etiqueta del elemento a medida que el usuario escribe. Está desactivado por defecto. Puedes mostrarlo y darle un texto de marcador de posición a través de la configuración de búsqueda:

```java
nav.getSearch().setFieldVisible(true);
nav.getSearch().setPlaceholder("Buscar");
```

A medida que el usuario escribe, el nav filtra los elementos por etiqueta, abre cualquier grupo que contenga una coincidencia y muestra un mensaje vacío cuando no hay coincidencias. Los accesos directos fijados permanecen visibles mientras se busca, por lo que los favoritos de un usuario continúan a un clic de distancia incluso en medio del filtro.

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

Oculta el campo integrado y alimenta el filtro desde un campo propio. Envía el término actual a través de `setTerm`:

```java
nav.getSearch().setFieldVisible(false);

myField.onModify(event -> nav.getSearch().setTerm(event.getText()));
```

Para reaccionar a lo que el usuario escribe en el campo integrado, escucha el evento de búsqueda:

```java
nav.onSearch(event -> log(event.getTerm()));
```

## Estilizando `AppNavItem` {#styling-appnavitem}

<TableBuilder name="AppNavItem" />
