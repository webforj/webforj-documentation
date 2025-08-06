---
title: SideMenu
sidebar_position: 3
hide_table_of_contents: true
_i18n_hash: c5fb775f5867b54eb53b0e1e63b90e20
---
<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

<!-- vale off -->
# Arquetipo de SideMenu
<!-- vale on -->

Para proyectos que necesitan un sistema de navegación estructurado, el arquetipo `sidemenu` es un excelente punto de partida. Este arquetipo contiene un menú lateral y un área de contenido, y está diseñado para ayudarte a crear aplicaciones con una estructura de navegación clara e intuitiva, facilitando que los usuarios encuentren y accedan a diferentes partes de tu aplicación.

:::tip Usando startforJ
Para tener más control sobre la personalización y configuración, puedes usar [startforJ](https://docs.webforj.com/startforj/) para crear tu proyecto: solo selecciona el arquetipo `SideMenu` al elegir las opciones de configuración.
:::

## Usando el arquetipo `sidemenu` {#using-the-sidemenu-archetype}

<ComponentArchetype
project="sidemenu"
/>

## Ejecutando la aplicación {#running-the-app}

Antes de ejecutar tu aplicación, instala los [prerequisitos](../../introduction/prerequisites) si aún no lo has hecho. 
Luego, navega al directorio raíz del proyecto y ejecuta el siguiente comando:

```bash
# para una aplicación webforJ estándar
mvn jetty:run

# para webforJ + Spring Boot
mvn spring-boot:run
```

Una vez que el servidor esté en funcionamiento, abre tu navegador y ve a [http://localhost:8080](http://localhost:8080) para ver la aplicación.
