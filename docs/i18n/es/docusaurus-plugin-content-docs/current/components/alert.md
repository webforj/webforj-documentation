---
title: Alert
sidebar_position: 5
description: >-
  Display contextual feedback messages with the Alert component, including
  themes, expanses, dismissible close events, and rich content.
_i18n_hash: 1008b6fbbcfab4361660689ceb08135c
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-alert" />
<DocChip chip='since' label='25.00' />
<JavadocLink type="alert" location="com/webforj/component/alert/Alert" top='true'/>

El componente `Alert` en webforJ proporciona mensajes de retroalimentación contextual para los usuarios. Es una forma versátil de mostrar información importante, advertencias o notificaciones en tu aplicación.

Los alertas ayudan a llamar la atención sobre información clave sin interrumpir el flujo de trabajo del usuario. Son perfectos para mensajes del sistema, retroalimentación de validación de formularios o actualizaciones de estado que necesitan ser claramente visibles pero no intrusivas.

<!-- INTRO_END -->

## Creación de alertas {#creating-alerts}

Un `Alert` puede contener contenido rico como texto, botones y otros componentes. Establece un tema para distinguir visualmente el tipo de mensaje que se está mostrando.

<ComponentDemo
path='/webforj/alert'
files={['src/main/java/com/webforj/samples/views/alert/AlertView.java']}
height='110px'
/>

## Descartando alertas {#dismissing-alerts}

Si deseas dar a los usuarios la opción de descartar el `Alert`, puedes hacerlo cerrable llamando al método `setClosable()`.

```java
Alert alert = new Alert("¡Atención! Esta alerta se puede descartar.");
alert.setClosable(true);
```
Las alertas a menudo hacen más que mostrar mensajes; pueden desencadenar acciones de seguimiento cuando se descartan. Utiliza el `AlertCloseEvent` para manejar estos casos y responder cuando el usuario descarta el `Alert`.

<ComponentDemo
path='/webforj/closablealert'
files={['src/main/java/com/webforj/samples/views/alert/ClosableAlertView.java']}
height='100px'
/>

:::tip Componente de Alerta Reutilizable
Cerrar la alerta solo la oculta; no destruye el componente, por lo que puedes reutilizarlo más tarde si es necesario.
:::

## Estilo {#styling}

### Temas {#themes}

El componente `Alert` soporta múltiples <JavadocLink type="foundation" location="com/webforj/component/Theme">temas</JavadocLink> para distinguir visualmente diferentes tipos de mensajes, como éxito, error, advertencia o información. Estos temas se pueden aplicar utilizando el método `setTheme()` o directamente en el constructor.

<ComponentDemo
path='/webforj/alertthemes'
files={['src/main/java/com/webforj/samples/views/alert/AlertThemesView.java']}
height='650px'
/>

### Dimensiones {#expanses}

La dimensión define el tamaño visual del componente `Alert`. Puedes configurarlo utilizando el método `setExpanse()`. Las opciones disponibles provienen del enum Expanse: `XSMALL`, `SMALL`, `MEDIUM`, `LARGE` y `XLARGE`.

<ComponentDemo
path='/webforj/alertexpanses'
files={['src/main/java/com/webforj/samples/views/alert/AlertExpansesView.java']}
height='600px'
/>

<TableBuilder name="Alert" />
