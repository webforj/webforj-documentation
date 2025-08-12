---
title: Alert
sidebar_position: 5
_i18n_hash: d6b9cd03da84860fd2768d9633f3b38a
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-alert" />
<DocChip chip='since' label='25.00' />
<JavadocLink type="alert" location="com/webforj/component/alert/Alert" top='true'/>

El componente `Alert` en webforJ proporciona mensajes de retroalimentación contextual para los usuarios. Es una forma versátil de mostrar información importante, advertencias o notificaciones en tu aplicación.

Los alertas ayudan a llamar la atención sobre información clave sin interrumpir el flujo de trabajo del usuario. Son perfectos para mensajes del sistema, retroalimentación de validación de formularios o actualizaciones de estado que necesitan ser claramente visibles pero no intrusivas.

Aquí tienes un ejemplo de un componente de alerta:

<ComponentDemo 
path='/webforj/alert?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/alert/AlertView.java'
height = '100px'
/>

## Dismissión de alertas {#dismissing-alerts}

Si deseas dar a los usuarios la opción de desestimar el `Alert`, puedes hacerlo cerrable llamando al método `setClosable()`.

```java 
Alert alert = new Alert("¡Atención! Esta alerta se puede desestimar.");
closableAlert.setClosable(true);
```
Las alertas a menudo hacen más que mostrar mensajes: pueden activar acciones de seguimiento cuando se desestiman. Usa el `AlertCloseEvent` para manejar estos casos y responder cuando el usuario desestime el `Alert`.

<ComponentDemo 
path='/webforj/closablealert?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/alert/ClosableAlertView.java'
height = '100px'
/>

:::tip Componente de Alerta Reutilizable
Cerrar la alerta solo la oculta; no destruye el componente, por lo que puedes reutilizarlo más tarde si es necesario.
:::


## Estilizado {#styling}

### Temas {#themes}

El componente `Alert` soporta múltiples <JavadocLink type="foundation" location="com/webforj/component/Theme"> temas </JavadocLink> para distinguir visualmente diferentes tipos de mensajes, como éxito, error, advertencia o información. Estos temas se pueden aplicar utilizando el método `setTheme()` o directamente en el constructor.

<ComponentDemo 
path='/webforj/alertthemes?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/alert/AlertThemesView.java' 
height = '475px'
/>


### Expansiones {#expanses}

La expansión define el tamaño visual del componente `Alert`. Puedes establecerlo utilizando el método `setExpanse()` o pasarlo directamente al constructor. Las opciones disponibles provienen del enum Expanse: `XSMALL`, `SMALL`, `MEDIUM`, `LARGE`, y `XLARGE`.

<ComponentDemo 
path='/webforj/alertexpanses?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/alert/AlertExpansesView.java'
height = '400px'
/>

<TableBuilder name="Alert" />
