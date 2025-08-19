---
title: Refresher
sidebar_position: 101
_i18n_hash: 77c3e72a5a59a55d61a7dba79efb7324
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-refresher" />
<DocChip chip='since' label='25.00' />
<JavadocLink type="refresher" location="com/webforj/component/refresher/Refresher" top='true'/>

El componente `Refresher` en webforJ habilita una interacción de arrastre para refrescar dentro de contenedores desplazables, ideal para la carga de datos dinámicos en interfaces móviles o amigables al toque. A medida que los usuarios deslizan hacia abajo más allá de un umbral configurable, el refresher transiciona a través de estados visuales: `pull`, `release` y `refreshing`. Cada estado presenta un ícono personalizable y texto localizado para comunicar claramente la retroalimentación.

Puedes usar `Refresher` junto con componentes como [`InfiniteScroll`](../components/infinitescroll) para recargar contenido o restablecer el estado a través de una entrada basada en gestos simple. El componente es completamente configurable en términos de comportamiento de interacción, apariencia, localización e integración con el resto de tu interfaz de usuario.

## Instanciación e internacionalización {#instantiation-and-internationalization}

Agrega un `Refresher` instanciándolo y registrando un oyente de refresco. Cuando las operaciones de refresco se completan, llama a `finish()` para restablecer el componente a su estado inactivo.

:::info Cómo activar el `Refresher`
Para activar el `Refresher`, **haz clic y arrastra hacia abajo** desde la parte superior del área desplazable. Mientras que este gesto es familiar en móviles, es menos común en computadoras de escritorio; asegúrate de mantener y arrastrar con tu ratón.
:::

<AppLayoutViewer
path='/webforj/refresher?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/refresher/RefresherView.java'
cssURL='/css/refresher/refresher.css'
height = '400px'
mobile='true'
/>

Este enfoque se utiliza comúnmente para refrescar listas paginadas o reiniciar la carga de desplazamiento infinito.

### Internacionalización {#internationalization}

Cada etiqueta de estado también se puede localizar utilizando el objeto `RefresherI18n`. Los tres estados son:

- Pull: Texto del gesto inicial (por ejemplo, "Desliza hacia abajo para refrescar")
- Release: Umbral de activación alcanzado (por ejemplo, "Suelta para refrescar")
- Refresh: Estado de carga (por ejemplo, "Refrescando")

Esto permite soporte multilingüe y ajustes de marca según sea necesario.

<AppLayoutViewer 
path='/webforj/refresheri18n?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/refresher/RefresherI18nView.java'
cssURL='/css/refresher/refresher.css'
height = '400px'
mobile='true'
/>

## Personalización de íconos {#icon-customization}

Puedes cambiar los [`Icons`](../components/icon) utilizados para los estados de `pull`/`release` y `refreshing` utilizando ya sea un [`Icon`](../components/icon) predefinido o una [URL de Icono](../managing-resources/assets-protocols). Estos son útiles cuando deseas aplicar una marca o una animación personalizada.

<AppLayoutViewer 
path='/webforj/refreshericon?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/refresher/RefresherIconView.java'
cssURL='/css/refresher/refresher.css'
height = '400px'
mobile='true'
/>

## Configuración del comportamiento de arrastre {#pull-behavior-configuration}

### Umbral {#threshold}

Establece cuán lejos debe arrastrar el usuario hacia abajo (en píxeles) antes de activar el refresco:

```java
refresher.setThreshold(80); // predeterminado: 80px
```

### Máximo del umbral {#threshold-maximum}

Para definir la distancia máxima de arrastre permitida, utiliza el método `setThresholdMax()`:

```java
refresher.setThresholdMax(160);
```

Estos umbrales controlan la sensibilidad del gesto y la curva de resistencia.

## Gestión del estado {#state-management}

El componente `Refresher` mantiene su propio estado interno y comunica los cambios de estado a través de eventos. Cuando un usuario arrastra hacia abajo más allá del umbral definido, el `Refresher` emite un evento de refresco al que puedes responder registrando un oyente `onRefresh()`.

Dentro de este oyente, se espera que realices la operación necesaria, como obtener nuevos datos o restablecer una lista, y luego llamar explícitamente a:

```java
refresher.finish();
```
:::warning Falta `finish()`
Si olvidas llamar a `finish()`, el refresher permanecerá en el estado de carga indefinidamente.
:::

También puedes desactivar programáticamente el `Refresher` en cualquier momento para evitar que el usuario active el comportamiento de refresco:

```java
refresher.setEnabled(false);
```

Esto es útil cuando los refrescos deben ser deshabilitados temporalmente, por ejemplo, durante una pantalla de carga o mientras se ejecuta otro proceso crítico.

## Estilo {#styling}

### Temas {#themes}

El componente `Refresher` soporta múltiples temas para distinguir visualmente diferentes estados o para coincidir con la apariencia de tu aplicación. Los temas se pueden aplicar utilizando el método `setTheme()`.

El siguiente ejemplo recorre todos los temas disponibles cada vez que arrastras para refrescar, dándote una vista previa en vivo de cómo se ve el `Refresher` a través de diferentes temas:

<AppLayoutViewer 
path='/webforj/refresherthemes?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/refresher/RefresherThemesView.java'
cssURL='/css/refresher/refresher.css'
height = '400px'
mobile='true'
/>

<TableBuilder name="Refresher" />
