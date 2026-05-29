---
sidebar_position: 1
title: Upgrading Guides
hide_table_of_contents: true
hide_giscus_comments: true
sidebar_class_name: has-new-content
_i18n_hash: 31ec5b4108bae52597797c3add587e4c
---
<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

El ciclo de lanzamiento de webforJ sigue un modelo estructurado y predecible para garantizar estabilidad, rendimiento e innovación continua. Este documento proporciona una visión general de cómo se planifican los lanzamientos, qué tipos de lanzamientos se pueden esperar y cómo los usuarios pueden mantenerse informados y preparados.

<AISkillTip skill="webforj-upgrading-versions" />

## Tipos de lanzamientos de webforJ {#types-of-webforj-releases}

webforJ sigue un modelo de lanzamiento estructurado que incluye los siguientes tipos de lanzamientos:

### 1. Lanzamientos mayores {#1-major-releases}
- Ocurren anualmente.
- Introducen nuevas características, mejoras y optimizaciones significativas.
- Pueden requerir cambios en la configuración o adaptación de aplicaciones existentes.
- Se identifican con versionado como **webforJ 20.00, webforJ 21.00, etc.**

### 2. Lanzamientos menores {#2-minor-releases}
- Ocurren varias veces a lo largo del año (aproximadamente cada seis a ocho semanas).
- Proporcionan mejoras incrementales, optimizaciones y nuevas características menores.
- Se identifican con versionado como **webforJ 20.01, webforJ 20.02, etc.**

### 3. Parches y lanzamientos de corrección de errores {#3-patches-and-bug-fix-releases}
- Se lanzan si es necesario.
- Abordan errores críticos, problemas de rendimiento y vulnerabilidades de seguridad.
- Se identifican con numeración adicional como **webforJ 20.01.1, webforJ 20.01.2, etc.**

## Qué esperar con cada lanzamiento {#what-to-expect-with-each-release}

### Mejoras de características {#feature-enhancements}
- Los lanzamientos mayores y menores introducen nuevas capacidades, optimizaciones e integraciones.
- Las hojas de ruta de características se comparten en las notas de lanzamiento para ayudar a los usuarios a planificar con anticipación.

:::info Compatibilidad hacia atrás
Si bien se hace un esfuerzo por mantener la compatibilidad, los lanzamientos mayores pueden incluir cambios que requieran ajustes en las aplicaciones. Se anima a los usuarios a revisar las notas de lanzamiento para conocer las características obsoletas.
:::

### Actualizaciones de seguridad {#security-updates}
- La seguridad es una prioridad, y las vulnerabilidades críticas se abordan en los lanzamientos de parches tan pronto como sea posible.

:::tip Compilaciones instantáneas
Las compilaciones instantáneas están disponibles antes de la mayoría de los lanzamientos. Se alienta a los usuarios a probarlas para identificar problemas temprano y proporcionar comentarios. Consulte el artículo sobre [Instantáneas](/docs/configuration/snapshots) para aprender cómo utilizar instantáneas de webforJ y dónde obtenerlas.
:::

## Cómo mantenerse actualizado {#how-to-stay-updated}

### Notas de lanzamiento y anuncios {#release-notes-and-announcements}
- Cada lanzamiento va acompañado de [notas de lanzamiento](https://github.com/webforj/webforj/releases) detalladas que describen nuevas características, correcciones de errores y cualquier acción requerida.
- Los usuarios deben suscribirse al [blog](../../blog) de webforJ para recibir actualizaciones oportunas.

:::tip Recomendaciones de actualización
Los clientes deben planificar las actualizaciones en función de las necesidades comerciales y los requisitos de estabilidad. Se alienta a los usuarios a mantenerse en la última versión para beneficiarse de mejoras de rendimiento y nuevas características.
:::

### Soporte y compatibilidad {#support-and-compatibility}
- webforJ proporciona documentación y guías de actualización para lanzamientos mayores.
- Están disponibles foros comunitarios y canales de soporte al cliente para solucionar problemas y asistencia.

<DocCardList className="topics-section" />
