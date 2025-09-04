---
title: SideMenu
sidebar_position: 3
hide_table_of_contents: true
_i18n_hash: 0d0c302e47e1711d573c9bf6860547ae
---
<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

<!-- vale off -->
# Archetipo de SideMenu
<!-- vale on -->

Para proyectos que necesitan un sistema de navegación estructurado, el archetipo `sidemenu` es un excelente punto de partida. Este archetipo contiene un menú lateral y un área de contenido, y está diseñado para ayudarte a crear aplicaciones con una estructura de navegación clara e intuitiva, facilitando a los usuarios encontrar y acceder a diferentes partes de tu aplicación.

:::tip Usando startforJ
Para tener más control sobre la personalización y configuración, puedes usar [startforJ](https://docs.webforj.com/startforj/) para crear tu proyecto; solo selecciona el archetipo `SideMenu` al elegir las opciones de configuración.
:::

## Usando el archetipo `sidemenu` {#using-the-sidemenu-archetype}

<ComponentArchetype
project="sidemenu"
/>

## Ejecutando la aplicación {#running-the-app}

Antes de ejecutar tu aplicación, instala los [prerrequisitos](../../introduction/prerequisites) si aún no lo has hecho. Luego, navega al directorio raíz del proyecto y ejecuta el siguiente comando:

```bash
# para aplicación estándar webforJ
mvn jetty:run

# para webforJ + Spring Boot
mvn spring-boot:run
```

Una vez que el servidor esté en ejecución, abre tu navegador y ve a [http://localhost:8080](http://localhost:8080) para ver la aplicación.
