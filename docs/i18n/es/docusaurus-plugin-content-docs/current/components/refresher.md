---
title: Refresher
sidebar_position: 101
_i18n_hash: 99793e9f95d4c5a052014f677aa8a6cb
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-refresher" />
<DocChip chip='since' label='25.00' />
<JavadocLink type="refresher" location="com/webforj/component/refresher/Refresher" top='true'/>

Pull-to-refresh es un patrón común en interfaces móviles y amigables con toques, y el componente `Refresher` lo trae a contenedores desplazables en webforJ. A medida que los usuarios deslizan hacia abajo más allá de un umbral configurable, transita a través de estados visuales: `pull`, `release`, y `refreshing`, cada uno con un ícono personalizable y texto localizado. Funciona bien con [`InfiniteScroll`](../components/infinitescroll) para recargar o restablecer contenido a través de entrada basada en gestos.

<!-- INTRO_END -->

## Instanciación e internacionalización {#instantiation-and-internationalization}

Agrega un `Refresher` instanciándolo y registrando un oyente de actualización. Cuando las operaciones de actualización se completen, llama a `finish()` para restablecer el componente a su estado inactivo.

:::info Cómo activar el `Refresher`
Para activar el `Refresher`, **haz clic y arrastra hacia abajo** desde la parte superior del área desplazable. Si bien este gesto es familiar en dispositivos móviles, es menos común en desktop; asegúrate de mantener y tirar con tu ratón.
:::

<ComponentDemo
path='/webforj/refresher'
frame='mobile'
files={[
  'src/main/java/com/webforj/samples/views/refresher/RefresherView.java',
  'src/main/resources/static/css/refresher/refresher.css',
]}
/>

Este enfoque se usa comúnmente para refrescar listas paginadas o reiniciar la carga de desplazamiento infinito.

### Internacionalización {#internationalization}

Cada etiqueta de estado también se puede localizar utilizando el objeto `RefresherI18n`. Los tres estados son:

- Pull: Texto del gesto inicial (por ejemplo, "Desliza hacia abajo para actualizar")
- Release: Umbral de activación alcanzado (por ejemplo, "Suelta para actualizar")
- Refresh: Estado de carga (por ejemplo, "Actualizando")

Esto permite soporte multilingüe y ajustes de marca según sea necesario.

<ComponentDemo
path='/webforj/refresheri18n'
frame='mobile'
files={[
  'src/main/java/com/webforj/samples/views/refresher/RefresherI18nView.java',
  'src/main/resources/static/css/refresher/refresher.css',
]}
/>

## Personalización de iconos {#icon-customization}

Puedes cambiar los [`Icons`](../components/icon) utilizados para las etapas de `pull`/`release` y `refreshing` utilizando un [`Icon`](../components/icon) predefinido o una [URL de Icono](../managing-resources/assets-protocols). Estos son útiles cuando deseas aplicar marca o una animación personalizada.

<ComponentDemo
path='/webforj/refreshericon'
frame='mobile'
files={[
  'src/main/java/com/webforj/samples/views/refresher/RefresherIconView.java',
  'src/main/resources/static/css/refresher/refresher.css',
]}
/>

## Configuración del comportamiento de tira {#pull-behavior-configuration}

### Umbral {#threshold}

Establece cuán lejos debe tirar el usuario hacia abajo (en píxeles) antes de activar la actualización:

```java
refresher.setThreshold(80); // predeterminado: 80px
```

### Máximo umbral {#threshold-maximum}

Para definir la distancia máxima de tirón permitida, usa el método `setThresholdMax()`:

```java
refresher.setThresholdMax(160);
```

Estos umbrales controlan la sensibilidad del gesto y la curva de resistencia.

## Gestión de estado {#state-management}

El componente `Refresher` mantiene su propio estado interno y comunica los cambios de estado a través de eventos. Cuando un usuario tira hacia abajo más allá del umbral definido, el `Refresher` emite un evento de actualización al que puedes responder registrando un oyente `onRefresh()`.

Dentro de este oyente, se espera que realices la operación requerida, como obtener nuevos datos o restablecer una lista, y luego llamas explícitamente a:

```java
refresher.finish();
```
:::warning Falta `finish()`
Si olvidas llamar a `finish()`, el refresher permanecerá en el estado de carga indefinidamente.
:::

También puedes deshabilitar programáticamente el `Refresher` en cualquier momento para evitar que el usuario active el comportamiento de actualización:

```java
refresher.setEnabled(false);
```

Esto es útil cuando las actualizaciones deben estar temporalmente deshabilitadas, por ejemplo, durante una pantalla de carga o mientras se está ejecutando otro proceso crítico.

## Estilo {#styling}

### Temas {#themes}

El componente `Refresher` admite múltiples temas para distinguir visualmente diferentes estados o para coincidir con la apariencia de tu aplicación. Los temas se pueden aplicar utilizando el método `setTheme()`.

El siguiente ejemplo cicla a través de todos los temas disponibles cada vez que tiras para actualizar, dándote una vista previa en vivo de cómo se ve el `Refresher` a través de diferentes temas:

<ComponentDemo
path='/webforj/refresherthemes'
frame='mobile'
files={[
  'src/main/java/com/webforj/samples/views/refresher/RefresherThemesView.java',
  'src/main/resources/static/css/refresher/refresher.css',
]}
/>

<TableBuilder name="Refresher" />
