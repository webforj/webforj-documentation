---
sidebar_position: 1
title: Upgrading Guides
hide_table_of_contents: true
hide_giscus_comments: true
_i18n_hash: 5b67f3c7842c20cbef9c77df8f3dd69a
---
<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

El ciclo de lanzamiento de webforJ sigue un modelo estructurado y predecible para garantizar la estabilidad, el rendimiento y la innovación continua. Este documento proporciona una visión general de cómo se planifican los lanzamientos, qué tipos de lanzamientos se pueden esperar y cómo los usuarios pueden mantenerse informados y preparados.

## Tipos de lanzamientos de webforJ {#types-of-webforj-releases}

webforJ sigue un modelo de lanzamiento estructurado que incluye los siguientes tipos de lanzamientos:

### 1. Lanzamientos mayores {#1-major-releases}
- Ocurren anualmente.
- Introducen nuevas características, mejoras y ampliaciones significativas.
- Pueden requerir cambios de configuración o adaptación de aplicaciones existentes.
- Se identifican con un versionado como **webforJ 20.00, webforJ 21.00, etc.**

### 2. Lanzamientos menores {#2-minor-releases}
- Ocurren varias veces a lo largo del año (aproximadamente cada seis a ocho semanas).
- Proporcionan mejoras incrementales, optimizaciones y características menores nuevas.
- Se identifican con un versionado como **webforJ 20.01, webforJ 20.02, etc.**

### 3. Parcheos y lanzamientos de correcciones de errores {#3-patches-and-bug-fix-releases}
- Se lanzan si es necesario.
- Abordan errores críticos, problemas de rendimiento y vulnerabilidades de seguridad.
- Se identifican con numeración adicional como **webforJ 20.01.1, webforJ 20.01.2, etc.**

## Qué esperar con cada lanzamiento {#what-to-expect-with-each-release}

### Mejoras de características {#feature-enhancements}
- Los lanzamientos mayores y menores introducen nuevas capacidades, optimizaciones e integraciones.
- Las hojas de ruta de características se comparten en las notas de lanzamiento para ayudar a los usuarios a planificar con anticipación.

:::info Compatibilidad hacia atrás
Si bien se hacen esfuerzos para mantener la compatibilidad, los lanzamientos mayores pueden incluir cambios que requieran ajustes en las aplicaciones. Se recomienda a los usuarios revisar las notas de lanzamiento para conocer las características obsoletas.
:::

### Actualizaciones de seguridad {#security-updates}
- La seguridad es una prioridad, y las vulnerabilidades críticas se abordan en los lanzamientos de parches tan pronto como sea posible.

:::tip Compilaciones de instantáneas
Las compilaciones de instantáneas están disponibles antes de la mayoría de los lanzamientos. Se alienta a los usuarios a probarlas para identificar problemas temprano y proporcionar comentarios. Consulte el artículo sobre [Instantáneas](/docs/configuration/snapshots) para aprender a usar las instantáneas de webforJ y dónde conseguirlas.
:::

## Cómo mantenerse actualizado {#how-to-stay-updated}

### Notas y anuncios de lanzamiento {#release-notes-and-announcements}
- Cada lanzamiento viene acompañado de [notas de lanzamiento](https://github.com/webforj/webforj/releases) detalladas que describen nuevas características, correcciones de errores y cualquier acción requerida.
- Se recomienda a los usuarios suscribirse al [blog](../../blog) de webforJ para obtener actualizaciones oportunas.

:::tip Recomendaciones de actualización
Los clientes deben planificar las actualizaciones según las necesidades comerciales y los requisitos de estabilidad. Se alienta a los usuarios a mantenerse en la última versión para beneficiarse de las mejoras de rendimiento y las nuevas características.
:::

### Soporte y compatibilidad {#support-and-compatibility}
- webforJ proporciona documentación y guías de actualización para lanzamientos mayores.
- Los foros comunitarios y los canales de soporte al cliente están disponibles para resolución de problemas y asistencia.

<DocCardList className="topics-section" />
