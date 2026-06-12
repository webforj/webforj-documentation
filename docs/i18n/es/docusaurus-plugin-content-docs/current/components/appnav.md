---
title: AppNav
sidebar_position: 6
_i18n_hash: 859da44bd50a1b3e985139da624ed4d4
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-app-nav" />
<DocChip chip="name" label="dwc-app-nav-item" />
<DocChip chip='since' label='24.12' />
<JavadocLink type="appnav" location="com/webforj/component/appnav/AppNav" top='true'/> 

El componente `AppNav` crea un menú de navegación lateral a partir de entradas `AppNavItem`. Los elementos pueden vincularse a vistas internas o recursos externos, anidarse bajo elementos principales para formar menús jerárquicos y llevar íconos, insignias u otros componentes para brindar a los usuarios más contexto de un vistazo.

<!-- INTRO_END -->

## Agregar y anidar elementos {#adding-and-nesting-items}

Las instancias de `AppNavItem` se utilizan para poblar la estructura de `AppNav`. Estos elementos pueden ser enlaces simples o encabezados de grupos anidados que contienen elementos secundarios. Los encabezados de grupo sin enlaces actúan como contenedores expandibles.

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

:::tip Vinculando Elementos de Grupo
Los elementos de nivel superior en un árbol de navegación generalmente están destinados a ser expandibles, no a ser enlaces clicables. Establecer una `path` en tales elementos puede confundir a los usuarios que esperan que revelen elementos secundarios en lugar de navegar a otro lugar.

Si deseas que el encabezado de grupo active una acción personalizada (como abrir documentos externos), mantén la ruta del grupo vacía y en su lugar añade un control interactivo como un [`IconButton`](./icon#icon-buttons) al sufijo del elemento. Esto mantiene la experiencia de usuario consistente y limpia.
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

## Vinculando Elementos {#linking-items}

Cada `AppNavItem` puede navegar a una vista interna o a un enlace externo. Puedes definir esto utilizando rutas estáticas o clases de vista registradas.

### Rutas estáticas {#static-paths}

Utiliza rutas de texto para definir enlaces directamente:

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

## Comportamiento objetivo {#target-behavior}

Controla cómo se abren los enlaces utilizando `setTarget()`. Esto es especialmente útil para enlaces externos o vistas emergentes.

- **`SELF`** (predeterminado): Se abre en la vista actual.
- **`BLANK`**: Se abre en una nueva pestaña o ventana.
- **`PARENT`**: Se abre en el contexto de navegación principal.
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
notifications.setPrefixComponent(TablerIcon.create("alert"));
notifications.setSuffixComponent(TablerIcon.create("link"));
```

## Grupos de apertura automática {#auto-opening-groups}

Utiliza `setAutoOpen(true)` en el componente `AppNav` para expandir automáticamente los grupos anidados cuando se refresca la aplicación.

```java
nav.setAutoOpen(true);
```

## Estilizando `AppNavItem` {#styling-appnavitem}

<TableBuilder name="AppNavItem" />
