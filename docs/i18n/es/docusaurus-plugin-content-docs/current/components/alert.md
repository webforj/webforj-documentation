---
title: Alert
sidebar_position: 5
_i18n_hash: 32072a9b5fdae80b41d77ee1d9742ea5
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-alert" />
<DocChip chip='since' label='25.00' />
<JavadocLink type="alert" location="com/webforj/component/alert/Alert" top='true'/>

El componente `Alert` en webforJ proporciona mensajes de retroalimentación contextual para los usuarios. Es una forma versátil de mostrar información importante, advertencias o notificaciones en tu aplicación.

Las alertas ayudan a llamar la atención sobre información clave sin interrumpir el flujo de trabajo del usuario. Son perfectas para mensajes del sistema, retroalimentación de validación de formularios o actualizaciones de estado que necesitan ser claramente visibles pero no intrusivas.

<!-- INTRO_END -->

## Creando alertas {#creating-alerts}

Un `Alert` puede contener contenido rico como texto, botones y otros componentes. Establece un tema para distinguir visualmente el tipo de mensaje que se está mostrando.

<ComponentDemo
path='/webforj/alert'
files={['src/main/java/com/webforj/samples/views/alert/AlertView.java']}
height='110px'
/>

## Descartando alertas {#dismissing-alerts}

Si deseas dar a los usuarios la opción de descartar el `Alert`, puedes hacerlo cerrable llamando al método `setClosable()`.

```java 
Alert alert = new Alert("¡Atención! Esta alerta puede ser descartada.");
closableAlert.setClosable(true);
```
Las alertas a menudo hacen más que mostrar mensajes; pueden desencadenar acciones de seguimiento cuando son descartadas. Utiliza el `AlertCloseEvent` para manejar estos casos y responder cuando el usuario descarta el `Alert`.

<ComponentDemo
path='/webforj/closablealert'
files={['src/main/java/com/webforj/samples/views/alert/ClosableAlertView.java']}
height='100px'
/>

:::tip Componente de Alerta Reutilizable
Cerrar la alerta solo la oculta; no destruye el componente, por lo que puedes reutilizarlo más adelante si es necesario.
:::


## Estilizando {#styling}

### Temas {#themes}

El componente `Alert` soporta múltiples <JavadocLink type="foundation" location="com/webforj/component/Theme"> temas </JavadocLink> para distinguir visualmente diferentes tipos de mensajes—como éxito, error, advertencia o información. Estos temas pueden aplicarse utilizando el método `setTheme()` o directamente en el constructor.

<ComponentDemo
path='/webforj/alertthemes'
files={['src/main/java/com/webforj/samples/views/alert/AlertThemesView.java']}
height='650px'
/>


### Expansiones {#expanses}

La expansión define el tamaño visual del componente `Alert`. Puedes establecerlo usando el método `setExpanse()` o pasarlo directamente al constructor. Las opciones disponibles provienen del enumerado Expanse: `XSMALL`, `SMALL`, `MEDIUM`, `LARGE`, y `XLARGE`.

<ComponentDemo
path='/webforj/alertexpanses'
files={['src/main/java/com/webforj/samples/views/alert/AlertExpansesView.java']}
height='600px'
/>

<TableBuilder name="Alerta" />
