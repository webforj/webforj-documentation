---
title: Refresher
sidebar_position: 101
description: >-
  Enable pull-to-refresh on scrollable areas with the Refresher component, with
  pull, release, and refreshing states and i18n labels.
_i18n_hash: 9bb531347032e46ccbb9e7fa28c403f8
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-refresher" />
<DocChip chip='since' label='25.00' />
<JavadocLink type="refresher" location="com/webforj/component/refresher/Refresher" top='true'/>

Pull-to-refresh es un patrón común en interfaces móviles y amigables al toque, y el componente `Refresher` lo lleva a contenedores desplazables en webforJ. A medida que los usuarios deslizan hacia abajo más allá de un umbral configurable, transita a través de estados visuales: `pull`, `release`, y `refreshing`, cada uno con un ícono personalizable y texto localizado. Se complementa bien con [`InfiniteScroll`](../components/infinitescroll) para recargar o restablecer contenido a través de entrada basada en gestos.

<!-- INTRO_END -->

## Instanciación e internacionalización {#instantiation-and-internationalization}

Añade un `Refresher` instanciándolo y registrando un oyente de actualización. Cuando las operaciones de actualización se completan, llama a `finish()` para restablecer el componente a su estado inactivo.

:::info Cómo activar el `Refresher`
Para activar el `Refresher`, **haz clic y arrastra hacia abajo** desde la parte superior del área desplazable. Mientras que este gesto es familiar en dispositivos móviles, es menos común en escritorio—asegúrate de mantener y tirar con tu ratón.
:::

<ComponentDemo
path='/webforj/refresher'
frame='mobile'
files={[
  'src/main/java/com/webforj/samples/views/refresher/RefresherView.java',
  'src/main/frontend/css/refresher/refresher.css',
]}
/>

Este enfoque se utiliza comúnmente para actualizar listas paginadas o reiniciar la carga de desplazamiento infinito.

### Internacionalización {#internationalization}

Cada etiqueta de estado también puede ser localizada utilizando el objeto `RefresherI18n`. Los tres estados son:

- Pull: Texto del gesto inicial (por ejemplo, "Desliza hacia abajo para actualizar")
- Release: Umbral de activación alcanzado (por ejemplo, "Suelta para actualizar")
- Refresh: Estado de carga (por ejemplo, "Actualizando")

Esto permite soporte multilingüe y ajustes de marca según sea necesario.

<ComponentDemo
path='/webforj/refresheri18n'
frame='mobile'
files={[
  'src/main/java/com/webforj/samples/views/refresher/RefresherI18nView.java',
  'src/main/frontend/css/refresher/refresher.css',
]}
/>

## Personalización de íconos {#icon-customization}

Puedes cambiar los [`Íconos`](../components/icon) utilizados para las etapas de `pull`/`release` y `refreshing` utilizando un [`Icon`](../components/icon) predefinido o una [URL de ícono](../managing-resources/assets-protocols). Estos son útiles cuando deseas aplicar branding o una animación personalizada.

<ComponentDemo
path='/webforj/refreshericon'
frame='mobile'
files={[
  'src/main/java/com/webforj/samples/views/refresher/RefresherIconView.java',
  'src/main/frontend/css/refresher/refresher.css',
]}
/>

## Configuración del comportamiento de arrastre {#pull-behavior-configuration}

### Umbral {#threshold}

Establece cuán lejos debe arrastrar el usuario (en píxeles) antes de activar la actualización:

```java
refresher.setThreshold(80); // predeterminado: 80px
```

### Máxima umbral {#threshold-maximum}

Para definir la distancia máxima de arrastre permitida, utiliza el método `setThresholdMax()`:

```java
refresher.setThresholdMax(160);
```

Estos umbrales controlan la sensibilidad del gesto y la curva de resistencia.

## Gestión de estados {#state-management}

El componente `Refresher` mantiene su propio estado interno y comunica cambios de estado a través de eventos. Cuando un usuario arrastra hacia abajo más allá del umbral definido, el `Refresher` emite un evento de actualización al que puedes responder registrando un oyente `onRefresh()`.

Dentro de este oyente, se espera que realices la operación requerida—como recuperar nuevos datos o restablecer una lista—y luego llames explícitamente a:

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

Esto es útil cuando las actualizaciones deben ser temporalmente deshabilitadas—por ejemplo, durante una pantalla de carga o mientras se está ejecutando otro proceso crítico.

## Estilo {#styling}

### Temas {#themes}

El componente `Refresher` admite múltiples temas para distinguir visualmente diferentes estados o coincidir con el aspecto y la sensación de tu aplicación. Los temas se pueden aplicar utilizando el método `setTheme()`.

El siguiente ejemplo cicla a través de todos los temas disponibles cada vez que deslizas para actualizar, dándote una vista previa en vivo de cómo se ve el `Refresher` a través de diferentes temas:

<ComponentDemo
path='/webforj/refresherthemes'
frame='mobile'
files={[
  'src/main/java/com/webforj/samples/views/refresher/RefresherThemesView.java',
  'src/main/frontend/css/refresher/refresher.css',
]}
/>

<TableBuilder name="Refresher" />
