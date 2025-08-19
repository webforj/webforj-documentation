---
title: Tabs
sidebar_position: 2
hide_table_of_contents: true
_i18n_hash: bd6e6de9bb8396f7926e01ac2f34cfc3
---
<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

# Arquetipo de pestañas

El proyecto inicial `tabs` genera una aplicación con una interfaz de pestañas simple. Ideal para proyectos que requieren múltiples vistas o secciones accesibles a través de pestañas, este arquetipo proporciona una forma limpia y organizada de gestionar diferentes partes de tu aplicación, facilitando la navegación entre varias secciones sin saturar la interfaz de usuario.

:::tip Usando startforJ
Para tener más control sobre la personalización y configuración, puedes usar [startforJ](https://docs.webforj.com/startforj/) para crear tu proyecto: solo selecciona el arquetipo `Tabs` al elegir las opciones de configuración.
:::

## Usando el arquetipo `tabs` {#using-the-tabs-archetype}

<ComponentArchetype
project="tabs"
/>

## Ejecutando la aplicación {#running-the-app}

Antes de ejecutar tu aplicación, instala los [prerequisitos](../../introduction/prerequisites) si aún no lo has hecho.
Luego, navega al directorio raíz del proyecto y ejecuta el siguiente comando:

```bash
# para la aplicación webforJ estándar
mvn jetty:run

# para webforJ + Spring Boot
mvn spring-boot:run
```

Una vez que el servidor esté en funcionamiento, abre tu navegador y ve a [http://localhost:8080](http://localhost:8080) para ver la aplicación.
