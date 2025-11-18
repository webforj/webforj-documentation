---
title: AppNav
sidebar_position: 6
_i18n_hash: faa14d827865b1697b369a9787315dcd
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-app-nav" />
<DocChip chip="name" label="dwc-app-nav-item" />
<DocChip chip='since' label='24.12' />
<JavadocLink type="appnav" location="com/webforj/component/appnav/AppNav" top='true'/> 

El componente `AppNav` en webforJ proporciona un menú de navegación lateral flexible y organizado con soporte para estructuras planas y jerárquicas. Cada entrada es un `AppNavItem`, que puede representar un enlace simple o un grupo que contiene subelementos. Los elementos pueden estar vinculados a vistas internas o recursos externos, mejorados con íconos, insignias u otros componentes.

## Agregar y anidar elementos {#adding-and-nesting-items}

Las instancias de `AppNavItem` se utilizan para poblar la estructura de `AppNav`. Estos elementos pueden ser enlaces simples o encabezados de grupo anidados que contienen elementos hijos. Los encabezados de grupo sin enlaces actúan como contenedores expandibles.

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

:::tip Vinculación de Elementos de Grupo
Los elementos de nivel superior en un árbol de navegación generalmente están destinados a ser expandibles, no enlaces clicables. Establecer un `path` en tales elementos puede confundir a los usuarios que esperan que revelen subelementos en lugar de navegar a otro lugar.

Si deseas que el encabezado del grupo active una acción personalizada (como abrir documentación externa), mantén la ruta del grupo vacía y, en su lugar, agrega un control interactivo como un [`IconButton`](./icon#icon-buttons) al sufijo del elemento. Esto mantiene la experiencia de usuario consistente y limpia.
:::

<!--vale off-->
<AppLayoutViewer 
path='/webforj/appnav/Social?'  
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/appnav/AppNavView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/appnav/AppNavPageView.java']}
/>
<!--vale on-->

## Vinculación de Elementos {#linking-items}

Cada `AppNavItem` puede navegar a una vista interna o a un enlace externo. Puedes definir esto usando rutas estáticas o clases de vista registradas.

### Rutas estáticas {#static-paths}

Utiliza rutas de cadena para definir enlaces directamente:

```java
AppNavItem docs = new AppNavItem("Docs", "/docs");
AppNavItem help = new AppNavItem("Help", "https://support.example.com");
```

### Vistas registradas {#registered-views}

Si tus vistas están registradas con el [router](../routing/overview), puedes pasar la clase en lugar de una URL codificada:

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

## Comportamiento de destino {#target-behavior}

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

`AppNavItem` admite componentes de prefijo y sufijo. Utiliza estos para proporcionar claridad visual con íconos, insignias o botones.

- **Prefijo**: aparece antes de la etiqueta, útil para íconos.
- **Sufijo**: aparece después de la etiqueta, excelente para insignias o acciones.

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

## Estilizando `AppNavItem` {#styling-appnavitem}

<TableBuilder name="AppNavItem" />
